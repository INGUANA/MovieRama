package com.inguana.movierama.adapters;

import android.widget.ImageView;

public interface OnMovieListener {

    void onMovieClick(int position);

    void onFavoriteClick(ImageView favouriteImageView, String movieTitle);
}
