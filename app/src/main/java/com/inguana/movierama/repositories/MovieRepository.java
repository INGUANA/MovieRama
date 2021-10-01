package com.inguana.movierama.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.inguana.movierama.models.ImageConfigurations;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.models.Review;
import com.inguana.movierama.requests.MovieApiClient;
import com.inguana.movierama.utils.Utils;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;
    private static MovieApiClient movieApiClient;
    private String query;
    private String page;
    private MutableLiveData<Boolean> isQueryExhausted;
    private MediatorLiveData<List<Movie>> movieList;
    private MediatorLiveData<String> errorMessage;

    public static MovieRepository getInstance() {
        if(instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
        isQueryExhausted = new MutableLiveData<>();
        movieList = new MediatorLiveData<>();
        errorMessage = new MediatorLiveData<>();
        initializeMediators();
    }

    private void initializeMediators() {
        LiveData<List<Movie>> movieListApiSource = movieApiClient.getMovieList();
        LiveData<String> errorMessageApiSource = movieApiClient.getErrorMessage();
        movieList.addSource(movieListApiSource, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if( movies != null) {
                    movieList.setValue(movies);
                    doneQuery(movies);
                } else {
                    doneQuery(null);
                }
            }
        });

        errorMessage.addSource(errorMessageApiSource, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                page = "1";
                MovieRepository.this.errorMessage.setValue(errorMessage);
            }
        });
    }

    private void doneQuery(List<Movie> list) {
        if(list != null){
            if(list.size() < 20){
                isQueryExhausted.setValue(true);
            }
        } else {
            isQueryExhausted.setValue(true);
        }
    }

    public LiveData<Boolean> isQueryExhausted() {
        return isQueryExhausted;
    }

    public MutableLiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public Movie getSelectedMovie() {
        return movieApiClient.getSelectedMovie();
    }

    public List<Movie> getSimilarMovieList() {
        return movieApiClient.getSimilarMovieList();
    }

    public List<Review> getReviewList() {
        return movieApiClient.getReviewList();
    }

    public MutableLiveData<ImageConfigurations> getImageConfigurations() {
        return movieApiClient.getImageConfigurations();
    }

    public MutableLiveData<Boolean> getSelectedMovieTimeout() {
        return movieApiClient.getSelectedMovieTimeout();
    }

    public MutableLiveData<Boolean> getIsMovieDetailsCallRetrieved() {
        return movieApiClient.getIsMovieDetailsCallRetrieved();
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void imageConfigurationsApi() {
        movieApiClient.imageConfigurationsApi();
    }

    public void searchMoviesApi(String query, String page) {
        this.query = query;
        this.page = page;
        isQueryExhausted.setValue(false);
        if(query.isEmpty()) {
            movieApiClient.popularMoviesApi(page);
        } else {
            movieApiClient.searchMoviesApi(query, page);
        }
    }

    public void movieDetailsTripleApi(int movieId, String page) {
        movieApiClient.movieDetailsTripleApi(movieId, page);
    }

    public void searchNextPage() {
        try {
            page = Utils.addNumberToString(page, 1);
            if(query.isEmpty()) {
                movieApiClient.popularMoviesApi(page);
            } else {
                movieApiClient.searchMoviesApi(query, page);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

}
