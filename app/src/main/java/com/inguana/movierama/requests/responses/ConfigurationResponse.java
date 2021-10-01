package com.inguana.movierama.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inguana.movierama.models.ImageConfigurations;

public class ConfigurationResponse {

    @SerializedName("images")
    @Expose()
    private ImageConfigurations imageConfigurations;

    public ImageConfigurations getImageConfigurations() {
        return imageConfigurations;
    }

    @Override
    public String toString() {
        return "ConfigurationResponse{" +
                "imageConfigurations=" + imageConfigurations +
                '}';
    }
}
