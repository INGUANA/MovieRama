package com.inguana.movierama.movieDetails;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.inguana.movierama.R;
import com.inguana.movierama.adapters.HomeActivityFunctionalities;
import com.inguana.movierama.adapters.ReviewsRecyclerViewAdapter;
import com.inguana.movierama.adapters.ShowProgressBar;
import com.inguana.movierama.adapters.SimilarMoviesRecyclerViewAdapter;
import com.inguana.movierama.models.ImageConfigurations;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.models.Review;
import com.inguana.movierama.utils.ErrorMessageSnackbar;
import com.inguana.movierama.utils.Utils;

import java.util.List;

public class MovieDetailsFragment extends Fragment {

    private LinearLayout linearLayoutMovieDetailsFragment;
    private ImageView movieImageMovieDetailsFragment, movieFavoriteMovieDetailsFragment;
    private TextView movieTitleMovieDetailsFragment, movieGenreMovieDetailsFragment, movieReleaseDateMovieDetailsFragment, movieRatingMovieDetailsFragment, descriptionMovieDetailsFragment, directorMovieDetailsFragment, castMovieDetailsFragment;
    private RecyclerView similarMoviesRecyclerView, reviewsRecyclerView;
    private SimilarMoviesRecyclerViewAdapter similarMoviesRecyclerViewAdapter;
    private ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter;
    private MovieViewModel movieViewModel;
    private int movieId;
    private Movie selectedMovie;
    private String imageBaseUrl;
    private ShowProgressBar showProgressBar;
    private HomeActivityFunctionalities homeActivityFunctionalities;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            showProgressBar = (ShowProgressBar) context;
            homeActivityFunctionalities = (HomeActivityFunctionalities) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieViewModel = new ViewModelProvider(requireActivity()).get(MovieViewModel.class);

        subscribeObservers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_details_fragment, container, false);
        linearLayoutMovieDetailsFragment = view.findViewById(R.id.linearLayoutMovieDetailsFragment);
        movieImageMovieDetailsFragment = view.findViewById(R.id.movieImageMovieDetailsFragment);
        movieFavoriteMovieDetailsFragment = view.findViewById(R.id.movieFavoriteMovieDetailsFragment);
        movieTitleMovieDetailsFragment = view.findViewById(R.id.movieTitleMovieDetailsFragment);
        movieGenreMovieDetailsFragment = view.findViewById(R.id.movieGenreMovieDetailsFragment);
        movieReleaseDateMovieDetailsFragment = view.findViewById(R.id.movieReleaseDateMovieDetailsFragment);
        movieRatingMovieDetailsFragment = view.findViewById(R.id.movieRatingMovieDetailsFragment);
        descriptionMovieDetailsFragment = view.findViewById(R.id.descriptionMovieDetailsFragment);
        directorMovieDetailsFragment = view.findViewById(R.id.directorMovieDetailsFragment);
        castMovieDetailsFragment = view.findViewById(R.id.castMovieDetailsFragment);

        similarMoviesRecyclerView = view.findViewById(R.id.similarMoviesRecyclerViewMovieDetailsFragment);
        reviewsRecyclerView = view.findViewById(R.id.reviewsRecyclerViewMovieDetailsFragment);

        movieId = MovieDetailsFragmentArgs.fromBundle(getArguments()).getMovieId();

        showProgressBar.showProgressBar(true);
        initializeRecyclerViews();

        movieFavoriteMovieDetailsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (homeActivityFunctionalities != null) {
                    onFavoriteFunctionality();
                    onFavoriteChangeDrawable();
                }
            }
        });

        makeInitialCalls();

        return view;
    }

    private void onFavoriteChangeDrawable() {

        if (Utils.isFavorite(homeActivityFunctionalities.getFavoriteSet(), selectedMovie.getOriginal_title())) {
            movieFavoriteMovieDetailsFragment.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_baseline_favorite_24));
        } else {
            movieFavoriteMovieDetailsFragment.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_round_favorite_border_24));
        }
    }

    private void onFavoriteFunctionality() {
        if (Utils.isFavorite(homeActivityFunctionalities.getFavoriteSet(), selectedMovie.getOriginal_title())) {
            homeActivityFunctionalities.removeFavoriteSet(selectedMovie.getOriginal_title());
        } else {
            homeActivityFunctionalities.setFavoriteSet(selectedMovie.getOriginal_title());
        }
    }

    private void initializeLayoutValues() {

        String imagePath = selectedMovie.getPoster_path();

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(imageBaseUrl + imagePath)
                .into(movieImageMovieDetailsFragment);


        movieTitleMovieDetailsFragment.setText(selectedMovie.getOriginal_title());
        movieGenreMovieDetailsFragment.setText(Utils.getGenresAsString(selectedMovie.getGenres()));
        movieReleaseDateMovieDetailsFragment.setText(Utils.changeDateFormatting(selectedMovie.getRelease_date()));
        movieRatingMovieDetailsFragment.setText(selectedMovie.getVote_average() + "");
        if (homeActivityFunctionalities != null) {
            onFavoriteChangeDrawable();
        }
        descriptionMovieDetailsFragment.setText(selectedMovie.getOverview());
        directorMovieDetailsFragment.setText(Utils.getDirectorFromCrew(selectedMovie.getCredits().getCrew()));
        castMovieDetailsFragment.setText(Utils.getCastAsString(selectedMovie.getCredits().getCast()));

        showParent();
        showProgressBar.showProgressBar(false);
    }

    private void showParent() {
        linearLayoutMovieDetailsFragment.setVisibility(View.VISIBLE);
    }

    private void makeInitialCalls() {
        movieViewModel.movieDetailsTripleApi(movieId, "1");
    }

    private void subscribeObservers() {

        movieViewModel.getIsMovieDetailsCallRetrieved().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                selectedMovie = movieViewModel.getSelectedMovie();
                movieViewModel.setMovieFetched(true);
                similarMoviesRecyclerViewAdapter.setImageUrlList(Utils.extractImageUrls(movieViewModel.getSimilarMovieList()));
                List<Review> reviews = movieViewModel.getReviewList();
                Utils.trimReviews(reviews, 2);
                reviewsRecyclerViewAdapter.setReviewList(reviews);
                if (selectedMovie != null) {
                    showParent();
                    initializeLayoutValues();
                }
            }
        });

        movieViewModel.getSelectedMovieTimeout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !movieViewModel.isMovieFetched()) {
                    //TODO: Enhance error handling by managing more error case
                }
            }
        });

        movieViewModel.getImageConfigurations().observe(this, new Observer<ImageConfigurations>() {
            @Override
            public void onChanged(ImageConfigurations imageConfigurations) {
                imageBaseUrl = Utils.buildImageBaseUrl(imageConfigurations);
                similarMoviesRecyclerViewAdapter.setImageBaseUrl(imageBaseUrl);
            }
        });

        movieViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                ErrorMessageSnackbar.showErrorMessage(getView().getRootView(), errorMessage);
                showProgressBar.showProgressBar(false);
            }
        });
    }

    private void initializeRecyclerViews() {
        similarMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        similarMoviesRecyclerViewAdapter = new SimilarMoviesRecyclerViewAdapter();
        similarMoviesRecyclerView.setAdapter(similarMoviesRecyclerViewAdapter);

        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter();
        reviewsRecyclerView.setAdapter(reviewsRecyclerViewAdapter);
    }


}
