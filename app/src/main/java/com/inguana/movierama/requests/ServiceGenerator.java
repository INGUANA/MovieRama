package com.inguana.movierama.requests;

import static com.inguana.movierama.utils.Constants.NETWORK_TIMEOUT;

import com.inguana.movierama.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static TheMovieDatabaseApi theMovieDatabaseApi = retrofit.create(TheMovieDatabaseApi.class);

    public static TheMovieDatabaseApi getTheMovieDatabaseApi() {
        return theMovieDatabaseApi;
    }
}
