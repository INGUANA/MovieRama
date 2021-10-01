package com.inguana.movierama.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.inguana.movierama.R;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MOVIE_TYPE = 1;
    private static final int PROGRESS_BAR_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;

    private List<Movie> movieList;
    private Set<String> favoriteSet;
    private String imageBaseUrl;
    private OnMovieListener onMovieListener;
    private Context context;

    public MovieRecyclerViewAdapter(OnMovieListener onMovieListener, Context context) {
        this.onMovieListener = onMovieListener;
        this.context = context;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView movieTitle;
        TextView movieRatingListItem;
        TextView movieReleaseDateListItem;
        ImageView movieImage;
        ImageView movieFavoriteListItem;
        OnMovieListener onMovieListener;
        //TODO: enan akomi listener gia favs


        public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);

            this.onMovieListener = onMovieListener;
            movieTitle = itemView.findViewById(R.id.movieTitleListItem);
            movieRatingListItem = itemView.findViewById(R.id.movieRatingListItem);
            movieReleaseDateListItem = itemView.findViewById(R.id.movieReleaseDateListItem);
            movieImage = itemView.findViewById(R.id.movieImageListItem);
            movieFavoriteListItem = itemView.findViewById(R.id.movieFavoriteListItem);
            itemView.setOnClickListener(this);

            movieFavoriteListItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMovieListener.onFavoriteClick(movieFavoriteListItem, movieTitle.getText().toString());
                }
            });
        }

        @Override
        public void onClick(View view) {
            onMovieListener.onMovieClick(getBindingAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder result = null;

        switch (viewType) {
            case MOVIE_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recyclerview_list_item, parent, false);
                result = new MovieViewHolder(view, onMovieListener);
                break;
            }
            case PROGRESS_BAR_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_recyclerview_layout, parent, false);
                result = new ProgressBarViewHolder(view);
                break;
            }
            case EXHAUSTED_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhausted_result_recyclerview_list_item, parent, false);
                result = new ExhaustedViewHolder(view);
                break;
            }
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recyclerview_list_item, parent, false);
                result = new MovieViewHolder(view, onMovieListener);
            }
        }

        return result;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == MOVIE_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(imageBaseUrl + movieList.get(position).getPoster_path())
                    .into(((MovieViewHolder) holder).movieImage);

            ((MovieViewHolder) holder).movieTitle.setText(movieList.get(position).getOriginal_title());
            ((MovieViewHolder) holder).movieRatingListItem.setText(movieList.get(position).getVote_average() + "");
            ((MovieViewHolder) holder).movieReleaseDateListItem.setText(Utils.changeDateFormatting(movieList.get(position).getRelease_date()));

            if(Utils.isFavorite(favoriteSet, movieList.get(position).getOriginal_title())) {
                ((MovieViewHolder) holder).movieFavoriteListItem.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_baseline_favorite_24));
            } else {
                ((MovieViewHolder) holder).movieFavoriteListItem.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_round_favorite_border_24));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int result;
        if (movieList.get(position).getOriginal_title().equals("LOADING IN PROGRESS...")) {
            result = PROGRESS_BAR_TYPE;
        } else if (position == movieList.size() - 1
                && position != 0
                && !movieList.get(position).getOriginal_title().equals("EXHAUSTED...")) {
            result = PROGRESS_BAR_TYPE;
        } else if(movieList.get(position).getOriginal_title().equals("EXHAUSTED...")) {
            result = EXHAUSTED_TYPE;
        }
        else {
            result = MOVIE_TYPE;
        }
        return result;
    }

    public void setQueryExhausted() {
        hideLoading();
        Movie exhaustedMovie = new Movie();
        exhaustedMovie.setOriginal_title("EXHAUSTED...");
        movieList.add(exhaustedMovie);
        notifyDataSetChanged();
    }

    public void hideLoading() {
        if(isLoading()) {
            for(Movie movie : movieList) {
                if(movie.getOriginal_title().equals("LOADING IN PROGRESS...")) {
                    movieList.remove(movie);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displayProgressBar() {
        if (!isLoading()) {
            Movie movie = new Movie();
            movie.setOriginal_title("LOADING IN PROGRESS...");
            List<Movie> progressBarList = new ArrayList<>();
            progressBarList.add(movie);
            movieList = progressBarList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        boolean result = false;
        if (movieList != null) {
            if (movieList.size() > 0) {
                if (movieList.get(movieList.size() - 1).getOriginal_title().equals("LOADING IN PROGRESS...")) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public Movie getSelectedMovie(int position) {
        Movie result = null;
        if (movieList != null) {
            if (movieList.size() > 0) {
                result = movieList.get(position);
            }
        }
        return result;
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        this.imageBaseUrl = imageBaseUrl;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public void setFavoriteSet(Set<String> favoriteSet) {
        this.favoriteSet = favoriteSet;
    }
}


