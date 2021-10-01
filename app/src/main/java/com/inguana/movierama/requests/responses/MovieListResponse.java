package com.inguana.movierama.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inguana.movierama.models.Movie;

import java.util.List;

//========================================
//Used in endpoints:
//MoviePopularGET
//SearchMovieGET
//========================================

public class MovieListResponse {

    @SerializedName("results")
    @Expose()
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "results=" + results +
                '}';
    }
}
