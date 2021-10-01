package com.inguana.movierama.models;

import java.util.Arrays;

public class ImageConfigurations {

    private String secure_base_url;
    private String[] poster_sizes;

    public ImageConfigurations(String secure_base_url, String[] poster_sizes) {
        this.secure_base_url = secure_base_url;
        this.poster_sizes = poster_sizes;
    }

    public ImageConfigurations() {
    }

    public String getSecure_base_url() {
        return secure_base_url;
    }

    public void setSecure_base_url(String secure_base_url) {
        this.secure_base_url = secure_base_url;
    }

    public String[] getPoster_sizes() {
        return poster_sizes;
    }

    public void setPoster_sizes(String[] poster_sizes) {
        this.poster_sizes = poster_sizes;
    }

    @Override
    public String toString() {
        return "ImageConfigurations{" +
                "secure_base_url='" + secure_base_url + '\'' +
                ", poster_sizes=" + Arrays.toString(poster_sizes) +
                '}';
    }
}
