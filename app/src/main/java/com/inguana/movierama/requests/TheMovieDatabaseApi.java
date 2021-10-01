package com.inguana.movierama.requests;

import com.inguana.movierama.models.Movie;
import com.inguana.movierama.requests.responses.ConfigurationResponse;
import com.inguana.movierama.requests.responses.MovieListResponse;
import com.inguana.movierama.requests.responses.ReviewResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDatabaseApi {

    @GET("movie/popular")
    Call<MovieListResponse> popularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String page);

    @GET("search/movie")
    Call<MovieListResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") String page);

    @GET("configuration")
    Call<ConfigurationResponse> configuration(
            @Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Observable<Movie> movieDetails(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("append_to_response") String appendToResponse);

    @GET("movie/{movie_id}/similar")
    Observable<MovieListResponse> similarMovies(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String page);


    @GET("movie/{movie_id}/reviews")
    Observable<ReviewResponse> reviews(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String page);
}
