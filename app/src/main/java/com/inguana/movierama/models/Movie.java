package com.inguana.movierama.models;

import java.util.List;

public class Movie {
    private int id;
    private String original_title;
    private String poster_path;
    private String overview;
    private String release_date;
    private float vote_average;
    private Credits credits;
    private List<Genre> genres;
    private boolean isFavorite;

    public Movie(int id, String original_title, String poster_path, String overview, String release_date,
                 float vote_average, Credits credits, List<Genre> genres, boolean isFavorite) {
        this.id = id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.credits = credits;
        this.genres = genres;
        this.isFavorite = isFavorite;
    }

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", original_title='" + original_title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average=" + vote_average +
                ", credits=" + credits + '\'' +
                ", genres=" + genres + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
