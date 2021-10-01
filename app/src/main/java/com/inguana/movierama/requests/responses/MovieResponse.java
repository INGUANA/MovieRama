package com.inguana.movierama.requests.responses;

import com.inguana.movierama.models.Movie;

public class MovieResponse {

    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
