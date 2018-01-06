package com.example.vyad.moviesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

@SuppressLint("StaticFieldLeak")
class FetchTask<S, V, T> extends AsyncTask<String, Void, Movies[]> {
    private static final String TAG = FetchTask.class.getName();
    private final Context mContext;

    /*
    initializes the context
     */
    public FetchTask(final Context context) {
        mContext = context;
    }

    @Override
    protected Movies[] doInBackground(String... urls) {

            /* if api endpoint is null then no need to hit the server */
        if (urls.length == 0) {
            return null;
        }

        try {
            URL movieRequestUrl = NetworkUtils.buildUrl(urls[0]);

            String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(
                    mContext, movieRequestUrl);

            return OpenMoviesJsonUtils.getMoviesObjectFromJsonString(
                    jsonMovieResponse);

        } catch (Exception e) {
            Log.e(TAG, "Exception occurred", e);
        }
        return null;
    }
}