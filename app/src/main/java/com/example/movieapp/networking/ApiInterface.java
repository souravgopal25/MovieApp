package com.example.movieapp.networking;

import com.example.movieapp.model.MovieResults;
import com.example.movieapp.model.MovieReview;
import com.example.movieapp.model.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("/3/movie/{category}")
    Call<MovieResults>getlistOfMovies(
            @Path("category") String category,
            @Query("api_key") String APIKEY,
            @Query("language") String language,
            @Query("page") int page
    );
   /* https://api.themoviedb.org/3/movie/popular?api_key=c116a273a05858824b03df6c4cfcbf20&language=en-US&page=1
    https://api.themoviedb.org/3/movie/8619/videos?api_key=c116a273a05858824b03df6c4cfcbf20&language=en-US
    https://api.themoviedb.org/3/movie/8619/reviews?api_key=c116a273a05858824b03df6c4cfcbf20&language=en-US&page=1*/

    @GET("/3/movie/{movieid}/videos")
    Call<Trailer> getTrailerCall(
            @Path("movieid") int movieid,
            @Query("api_key") String APIKEY,
            @Query("language") String language


    );

    @GET("/3/movie/{movieid}/reviews")
    Call<MovieReview> getReviewCall(
            @Path("movieid") int movieid,
            @Query("api_key") String APIKEY,
            @Query("language") String language,
            @Query("page") int page

    );



}
