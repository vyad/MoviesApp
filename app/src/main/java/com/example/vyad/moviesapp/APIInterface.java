package com.example.vyad.moviesapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("3/movie/{moviesType}")
    Call<MoviesResource> listMovies(@Path("moviesType") String moviesType, @Query("api_key") String apiKey);

    @GET("3/movie/{movies_id}/videos")
    Call<TrailerResource> listTrailer(@Path("movies_id") String moviesId, @Query("api_key") String apiKey);

    @GET("3/movie/{movies_id}/reviews")
    Call<ReviewsResource> listReviews(@Path("movies_id") String moviesId, @Query("api_key") String apiKey);
}
