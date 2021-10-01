package com.inguana.movierama.requests;

import static com.inguana.movierama.utils.Constants.API_KEY;
import static com.inguana.movierama.utils.Constants.LANGUAGE;

import androidx.lifecycle.MutableLiveData;

import com.inguana.movierama.models.ImageConfigurations;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.models.Review;
import com.inguana.movierama.requests.responses.ConfigurationResponse;
import com.inguana.movierama.requests.responses.MovieListResponse;
import com.inguana.movierama.requests.responses.ReviewResponse;
import com.inguana.movierama.utils.Constants;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieApiClient {

    private static MovieApiClient instance;
    private MutableLiveData<List<Movie>> movieList;
    private Movie selectedMovie;
    private List<Movie> similarMovieList;
    private List<Review> reviewList;
    private MutableLiveData<ImageConfigurations> imageConfigurations;
    private MutableLiveData<Boolean> selectedMovieTimeout;
    private MutableLiveData<Boolean> isMovieDetailsCallRetrieved;
    private MutableLiveData<String> errorMessage;

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        movieList = new MutableLiveData<>();
        similarMovieList = new ArrayList<>();
        reviewList = new ArrayList<>();
        imageConfigurations = new MutableLiveData<>();
        selectedMovie = new Movie();
        selectedMovieTimeout = new MutableLiveData<>();
        isMovieDetailsCallRetrieved = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public List<Movie> getSimilarMovieList() {
        return similarMovieList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public MutableLiveData<ImageConfigurations> getImageConfigurations() {
        return imageConfigurations;
    }

    public MutableLiveData<Boolean> getSelectedMovieTimeout() {
        return selectedMovieTimeout;
    }

    public MutableLiveData<Boolean> getIsMovieDetailsCallRetrieved() {
        return isMovieDetailsCallRetrieved;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void popularMoviesApi(String page) {
        TheMovieDatabaseApi theMovieDatabaseApi = ServiceGenerator.getTheMovieDatabaseApi();
        Call<MovieListResponse> responseCall = theMovieDatabaseApi
                .popularMovies(
                        Constants.API_KEY,
                        LANGUAGE,
                        page
                );

        responseCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                try {
                    if (response.code() == 200) {
                        List<Movie> list = new ArrayList<>(response.body().getResults());

                        if (page.equals("1")) {
                            movieList.setValue(list);
                        } else {
                            List<Movie> currentMovieList = movieList.getValue();
                            currentMovieList.addAll(list);
                            movieList.setValue(currentMovieList);
                        }
                    } else {
                        errorMessage.setValue("An error occurred while fetching data.");
                        movieList.setValue(null);
                    }
                } catch (Exception e) {
                    errorMessage.setValue("An error occurred while fetching data.");
                }
            }


            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                errorMessage.setValue("An error occurred while fetching data.");
                if (t instanceof SocketTimeoutException | t instanceof UnknownHostException) {
                    selectedMovieTimeout.setValue(true);
                }
            }
        });
    }

    public void searchMoviesApi(String query, String page) {
        TheMovieDatabaseApi theMovieDatabaseApi = ServiceGenerator.getTheMovieDatabaseApi();
        Call<MovieListResponse> responseCall = theMovieDatabaseApi.searchMovies(
                API_KEY,
                LANGUAGE,
                query,
                page);

        responseCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                try {
                    List<Movie> list = response.body().getResults();
                    if (response.code() == 200) {
                        if (page.equals("1")) {
                            movieList.setValue(list);
                        } else {
                            List<Movie> currentMovieList = movieList.getValue();
                            currentMovieList.addAll(list);
                            movieList.setValue(currentMovieList);
                        }
                    } else {
                        errorMessage.setValue("An error occurred while fetching data.");
                        movieList.setValue(null);
                    }
                } catch (Exception e) {
                    errorMessage.setValue("An error occurred while fetching data.");
                }
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                errorMessage.setValue("An error occurred while fetching data.");
            }
        });
    }

    public void imageConfigurationsApi() {
        TheMovieDatabaseApi theMovieDatabaseApi = ServiceGenerator.getTheMovieDatabaseApi();
        Call<ConfigurationResponse> responseCall = theMovieDatabaseApi.configuration(
                API_KEY);
        responseCall.enqueue(new Callback<ConfigurationResponse>() {
            @Override
            public void onResponse(Call<ConfigurationResponse> call, Response<ConfigurationResponse> response) {
                try {
                    ImageConfigurations configurations = response.body().getImageConfigurations();
                    if (response.code() == 200) {
                        imageConfigurations.setValue(configurations);
                    } else {
                        errorMessage.setValue("An error occurred while fetching data.");
                        imageConfigurations.setValue(null);
                    }
                } catch (Exception e) {
                    errorMessage.setValue("An error occurred while fetching data.");
                }
            }

            @Override
            public void onFailure(Call<ConfigurationResponse> call, Throwable t) {
                errorMessage.setValue("An error occurred while fetching data.");
            }
        });
    }

    public void movieDetailsTripleApi(int movieId, String page) {
        TheMovieDatabaseApi theMovieDatabaseApi = ServiceGenerator.getTheMovieDatabaseApi();
        Observable<Movie> movieDetailsResponseCall = theMovieDatabaseApi.movieDetails(
                movieId,
                API_KEY,
                LANGUAGE,
                "credits");
        Observable<MovieListResponse> similarMoviesResponseCall = theMovieDatabaseApi.similarMovies(
                movieId,
                API_KEY,
                LANGUAGE,
                page);
        Observable<ReviewResponse> reviewsResponseCall = theMovieDatabaseApi.reviews(
                movieId,
                API_KEY,
                LANGUAGE,
                "1");

        Observable.zip(
                movieDetailsResponseCall,
                similarMoviesResponseCall,
                reviewsResponseCall,
                (r1, r2, r3) -> {

                    List<Object> list = new ArrayList<>();
                    list.add(r1);
                    list.add(r2);
                    list.add(r3);
                    return list;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            response.forEach(value -> {
                                if (value instanceof Movie) {
                                    selectedMovie = ((Movie) value);
                                } else if (value instanceof MovieListResponse) {
                                    similarMovieList = ((MovieListResponse) value).getResults();
                                } else if (value instanceof ReviewResponse) {
                                    reviewList = ((ReviewResponse) value).getResults();
                                } else {
                                    errorMessage.postValue("An error occurred while fetching data.");
                                }
                            });
                            isMovieDetailsCallRetrieved.postValue(true);
                        },
                        error -> {
                            selectedMovie = null;
                            similarMovieList = null;
                            reviewList = null;
                            isMovieDetailsCallRetrieved.postValue(false);
                            errorMessage.postValue("An error occurred while fetching data.");

                        }
                );
    }

}
