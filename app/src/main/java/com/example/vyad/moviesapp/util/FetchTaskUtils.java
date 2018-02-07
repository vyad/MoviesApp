/*
 * Copyright (C) 2018 The Android Open Source Project
*/
package com.example.vyad.moviesapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import com.example.vyad.moviesapp.R;

import java.net.URL;

/**
 * Fetch movies data in a background thread and notify the activity when it is done.
 */
@SuppressLint("StaticFieldLeak")
public class FetchTaskUtils extends AsyncTaskLoader {
    private static final String TAG = FetchTaskUtils.class.getName();

    private final Context mContext;

//    Url to hit for data
    private final String mUrl;

//    This help to identify return type of data (every time we get string response data but need to return in object form).
    private final String mRequestType;

    public FetchTaskUtils(final Context context, final String url, final String requestType) {
        super(context);
        mContext = context;
        mUrl = url;
        mRequestType = requestType;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        if (TextUtils.isEmpty(mUrl)) {
            return null;
        }

        try {
            URL requestURL = NetworkUtils.buildUrl(mUrl);

            String response = NetworkUtils.getResponseFromHttpUrl(
                    mContext, requestURL);

            Resources rs = mContext.getResources();

            if (rs.getString(R.string.movies).equals(mRequestType)) {
                return OpenMoviesJsonUtils.getMoviesObjectFromJsonString(response);
            } else if (rs.getString(R.string.trailer).equals(mRequestType)) {
                return OpenMoviesJsonUtils.getTrailerObjectFromJsonString(response);
            } else if (rs.getString(R.string.reviews).equals(mRequestType)) {
                return OpenMoviesJsonUtils.getReviewsObjectFromJsonString(response);
            }

            return response;

        } catch (Exception e) {
            Log.e(TAG, "Exception occurred", e);
        }
        return null;
    }

}