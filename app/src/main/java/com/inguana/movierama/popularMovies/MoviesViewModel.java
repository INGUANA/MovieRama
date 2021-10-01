package com.inguana.movierama.popularMovies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inguana.movierama.models.ImageConfigurations;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.repositories.MovieRepository;

import java.util.List;

public class MoviesViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private boolean didRetrieveMovies;

    public MoviesViewModel() {
        movieRepository = MovieRepository.getInstance();
        didRetrieveMovies = false;
    }


    public void searchNextPage() {
        //'if' logic about canceling
        if(!isQueryExhausted().getValue()) {
            movieRepository.searchNextPage();
        }
    }

    public LiveData<Boolean> isQueryExhausted() {
        return movieRepository.isQueryExhausted();
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieRepository.getMovieList();
    }

    public LiveData<ImageConfigurations> getImageConfigurations() {
        return movieRepository.getImageConfigurations();
    }

    public MutableLiveData<String> getErrorMessage() {
        return movieRepository.getErrorMessage();
    }

    public MutableLiveData<Boolean> getSelectedMovieTimeout() {
        return movieRepository.getSelectedMovieTimeout();
    }

    public boolean didRetrieveMovies() {
        return didRetrieveMovies;
    }

    public void setDidRetrieveMovies(boolean didRetrieveMovies) {
        this.didRetrieveMovies = didRetrieveMovies;
    }

    public void imageConfigurationsApi() {
        movieRepository.imageConfigurationsApi();
    }

    public void searchMoviesApi(String query, String page) {
        movieRepository.searchMoviesApi(query, page);
    }
}
