package com.example.movieapp.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {
    public static final String BASE_URL="https://api.themoviedb.org";
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            retrofit= new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
