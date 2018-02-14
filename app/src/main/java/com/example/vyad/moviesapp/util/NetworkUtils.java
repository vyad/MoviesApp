/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.example.vyad.moviesapp.APIInterface;
import com.example.vyad.moviesapp.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.example.vyad.moviesapp.MoviesResource;
import com.example.vyad.moviesapp.R;
import com.example.vyad.moviesapp.ReviewsResource;
import com.example.vyad.moviesapp.TrailerResource;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * These utilities will be used to communicate with the movies servers.
 */
public final class NetworkUtils {


    private static final String TAG = NetworkUtils.class.getName();
    private static final String API_KEY = "api_key";

    /**
     * checks network connectivity
     *
     * @param context application context
     * @return true if device is connected with network and false otherwise
     */
    private static boolean isOnline(final Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //noinspection ConstantConditions
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    /**
     * creates trailer url for a movie
     * @param context application context
     * @param moviesId unique movies id
     * @return String trailer url
     */
    public static String getTrailerUrl(final Context context, final String moviesId) {
        String trailerUrl = context.getResources().getString(R.string.trailer_url);
        return String.format(trailerUrl, moviesId);
    }

    /**
     * creates review url for a movie
     * @param context application context
     * @param moviesId unique movies id
     * @return String review url
     */
    public static String getReviewsUrl(final Context context, final String moviesId) {
        String reviewUrl = context.getResources().getString(R.string.reviews_url);
        return String.format(reviewUrl, moviesId);
    }

    private static APIInterface getApiInterface(final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIInterface.class);
    }

    public static MoviesResource.Movies[] getMovies(final Context context, final String path)  {

        if (!isOnline(context)) {
            return null;
        }

        APIInterface apiInterface = getApiInterface(context);
        Call<MoviesResource> call = apiInterface.listMovies(path, BuildConfig.API_KEY);
        try {
            Response<MoviesResource> response = call.execute();
            if (response != null) {
                return response.body().results;
            }
        } catch (IOException e) {
            Log.d(TAG, "Some exception occurred while hitting the service", e);
        }

        return null;
    }

    public static TrailerResource.Trailer[] getTrailers(final Context context, final String moviesId) {

        if (!isOnline(context)) {
            return null;
        }

        APIInterface apiInterface = getApiInterface(context);
        Call<TrailerResource> call = apiInterface.listTrailer(moviesId, BuildConfig.API_KEY);
        try {
            Response<TrailerResource> response = call.execute();
            if (response != null) {
                return response.body().results;
            }
        } catch (IOException e) {
            Log.d(TAG, "Some exception occurred while hitting the service", e);
        }
        return null;
    }

    public static ReviewsResource.Reviews[] getReviews(final Context context, final String moviesId) {

        if (!isOnline(context)) {
            return null;
        }

        APIInterface apiInterface = getApiInterface(context);
        Call<ReviewsResource> call = apiInterface.listReviews(moviesId, BuildConfig.API_KEY);
        try {
            Response<ReviewsResource> response = call.execute();
            if (response != null) {
                return response.body().results;
            }
        } catch (IOException e) {
            Log.d(TAG, "Some exception occurred while hitting the service", e);
        }

        return null;
    }
}
