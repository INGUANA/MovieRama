package com.inguana.movierama.movieDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inguana.movierama.models.ImageConfigurations;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.models.Review;
import com.inguana.movierama.repositories.MovieRepository;

import java.util.List;

//TODO: check naming of class
public class MovieViewModel extends ViewModel {

    private MovieRepository movieRepository;
    private boolean isMovieFetched;

    public MovieViewModel() {
        movieRepository = MovieRepository.getInstance();
        isMovieFetched = false;
    }

    public boolean isMovieFetched() {
        return isMovieFetched;
    }

    public void setMovieFetched(boolean movieFetched) {
        isMovieFetched = movieFetched;
    }

    public LiveData<ImageConfigurations> getImageConfigurations() {
        return movieRepository.getImageConfigurations();
    }

    public LiveData<Boolean> getSelectedMovieTimeout() {
        return movieRepository.getSelectedMovieTimeout();
    }

    public MutableLiveData<Boolean> getIsMovieDetailsCallRetrieved() {
        return movieRepository.getIsMovieDetailsCallRetrieved();
    }

    public void movieDetailsTripleApi(int movieId, String page) {
        movieRepository.movieDetailsTripleApi(movieId, page);
    }

    public Movie getSelectedMovie() {
        return movieRepository.getSelectedMovie();
    }

    public List<Movie> getSimilarMovieList() {
        return movieRepository.getSimilarMovieList();
    }

    public List<Review> getReviewList() {
        return movieRepository.getReviewList();
    }

    public MutableLiveData<String> getErrorMessage() {
        return movieRepository.getErrorMessage();
    }
}
