package com.example.vyad.moviesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import java.net.URL;

@SuppressLint("StaticFieldLeak")
class FetchTask extends AsyncTaskLoader<Movies[]> {
    private static final String TAG = FetchTask.class.getName();
    private final Context mContext;
    private final String mUrl;

    public FetchTask(final Context context, final String url) {
        super(context);
        mContext = context;
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Movies[] loadInBackground() {
        if (TextUtils.isEmpty(mUrl)) {
            return null;
        }

        try {
            URL movieRequestUrl = NetworkUtils.buildUrl(mUrl);

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