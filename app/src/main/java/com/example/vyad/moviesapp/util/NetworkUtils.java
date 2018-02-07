/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.example.vyad.moviesapp.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.example.vyad.moviesapp.R;

/**
 * These utilities will be used to communicate with the movies servers.
 */
public final class NetworkUtils {


    private static final String API_KEY = "api_key";

    /**
     * Takes url as string and convert it into URL instance
     *
     * @param moviesUrl movies api url which need to be called
     * @return movies api URL instance.
     * @throws MalformedURLException if a string can not be converted into a url
     */
    public static URL buildUrl(final String moviesUrl) throws MalformedURLException {
        Uri uri = Uri.parse(moviesUrl).buildUpon()
                .appendQueryParameter(API_KEY, BuildConfig.API_KEY).build();
        return new URL(String.valueOf(uri));
    }

    /**
     * Establish a connection with server and read and return the response of it
     *
     * @param context application context
     * @param url     api url to to hit server
     * @return server response
     * @throws IOException if stream of a file cannot be read
     */
    public static String getResponseFromHttpUrl(final Context context, final URL url)
            throws IOException {

        if (!isOnline(context)) {
            return null;
        }

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

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

    public static String getTrailerUrl(final Context context, final String moviesId) {
        String trailerUrl = context.getResources().getString(R.string.trailer_url);
        return String.format(trailerUrl, moviesId);
    }

    public static String getReviewsUrl(final Context context, final String moviesId) {
        String reviewUrl = context.getResources().getString(R.string.reviews_url);
        return String.format(reviewUrl, moviesId);
    }

}
