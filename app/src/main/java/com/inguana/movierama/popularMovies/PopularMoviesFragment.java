package com.inguana.movierama.popularMovies;

import static com.inguana.movierama.utils.Constants.PREF_FAVORITE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inguana.movierama.R;
import com.inguana.movierama.adapters.HomeActivityFunctionalities;
import com.inguana.movierama.adapters.MovieRecyclerViewAdapter;
import com.inguana.movierama.adapters.OnMovieListener;
import com.inguana.movierama.models.ImageConfigurations;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.utils.ErrorMessageSnackbar;
import com.inguana.movierama.utils.Utils;
import com.inguana.movierama.utils.VerticalSpacingItemDecorator;

import java.util.List;

public class PopularMoviesFragment extends Fragment implements OnMovieListener {

    private RecyclerView popularMovieRecyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private MoviesViewModel moviesViewModel;
    private SearchView moviesSearchView;
    private NavController navController;
    private SwipeRefreshLayout swipeRefreshPopularMoviesFragment;
    private HomeActivityFunctionalities homeActivityFunctionalities;

    private static final String TAG = "PopularMoviesFragment";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            homeActivityFunctionalities = (HomeActivityFunctionalities) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);
        navController = NavHostFragment.findNavController(this);

        subscribeObservers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.popular_movies_fragment, container, false);
        popularMovieRecyclerView = view.findViewById(R.id.recyclerViewPopularMoviesFragment);
        moviesSearchView = view.findViewById(R.id.searchViewPopularMoviesFragment);
        swipeRefreshPopularMoviesFragment = view.findViewById(R.id.swipeRefreshPopularMoviesFragment);

        initializeRecyclerView();

        moviesSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieRecyclerViewAdapter.displayProgressBar();
                moviesViewModel.searchMoviesApi(newText, "1");
                return false;
            }
        });

        swipeRefreshPopularMoviesFragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesSearchView.setQuery("", false);
                moviesViewModel.searchMoviesApi("", "1");
                swipeRefreshPopularMoviesFragment.setRefreshing(false);
            }
        });

        makeInitialCalls();

        return view;
    }


    private void initializeRecyclerView() {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, getContext());
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator((int) getResources().getDimension(R.dimen.margin_30dp));
        popularMovieRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        popularMovieRecyclerView.setAdapter(movieRecyclerViewAdapter);
        popularMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        popularMovieRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1 ) && movieRecyclerViewAdapter.getItemCount() > 0) {
                    moviesViewModel.searchNextPage();
                }
            }
        });
    }

    private void subscribeObservers() {

        moviesViewModel.getSelectedMovieTimeout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean && moviesViewModel.didRetrieveMovies()) {
                    //no net
                }
            }
        });

        moviesViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    movieRecyclerViewAdapter.setQueryExhausted();
                }
            }
        });

        moviesViewModel.getImageConfigurations().observe(this, new Observer<ImageConfigurations>() {
            @Override
            public void onChanged(ImageConfigurations imageConfigurations) {
                if (imageConfigurations != null) {
                    movieRecyclerViewAdapter.setImageBaseUrl(Utils.buildImageBaseUrl(imageConfigurations));
                }
            }
        });

        moviesViewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies != null) {
                    movieRecyclerViewAdapter.setFavoriteSet(Utils.getPreference(getContext(), PREF_FAVORITE));
                    movieRecyclerViewAdapter.setMovieList(movies);
                    moviesViewModel.setDidRetrieveMovies(true);
                }
            }
        });

        moviesViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                ErrorMessageSnackbar.showErrorMessage(getView(), errorMessage);
                movieRecyclerViewAdapter.hideLoading();
            }
        });
    }


    private void makeInitialCalls() {
        moviesViewModel.imageConfigurationsApi();
        moviesViewModel.searchMoviesApi("", "1");
    }

    @Override
    public void onMovieClick(int position) {
        PopularMoviesFragmentDirections.ActionPopularMoviesFragmentToMovieDetailsFragment action =
                PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailsFragment(
                        movieRecyclerViewAdapter.getSelectedMovie(position).getId());
        navController.navigate(action);
    }

    private void onFavoriteChangeDrawable(ImageView favoriteImageView, String movieTitle) {
        if (Utils.isFavorite(homeActivityFunctionalities.getFavoriteSet(), movieTitle)) {
            favoriteImageView.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_baseline_favorite_24));
        } else {
            favoriteImageView.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_round_favorite_border_24));
        }
    }

    private void onFavoriteFunctionality(String movieTitle) {
        if (Utils.isFavorite(homeActivityFunctionalities.getFavoriteSet(), movieTitle)) {
            homeActivityFunctionalities.removeFavoriteSet(movieTitle);
        } else {
            homeActivityFunctionalities.setFavoriteSet(movieTitle);
        }
    }

    @Override
    public void onFavoriteClick(ImageView favoriteImageView, String movieTitle) {
        if (homeActivityFunctionalities != null) {
            onFavoriteFunctionality(movieTitle);
            onFavoriteChangeDrawable(favoriteImageView, movieTitle);
        }
    }
}
