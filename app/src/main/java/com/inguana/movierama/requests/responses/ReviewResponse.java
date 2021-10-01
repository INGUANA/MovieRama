package com.inguana.movierama.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inguana.movierama.models.Review;

import java.util.List;

public class ReviewResponse {

    @SerializedName("results")
    @Expose()
    private List<Review> results;

    public List<Review> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "results=" + results +
                '}';
    }
}
