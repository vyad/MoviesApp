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

/**
 * Fetch movies data in a background thread and notify the activity when it is done.
 */
@SuppressLint("StaticFieldLeak")
public class FetchTaskUtils extends AsyncTaskLoader {
    private static final String TAG = FetchTaskUtils.class.getName();

    private final Context mContext;

    private final String mRequestType;

    private final String mMoviesId;


    public FetchTaskUtils(final Context context, final String requestType, final String moviesId) {
        super(context);
        mContext = context;
        mRequestType = requestType;
        mMoviesId = moviesId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        if (TextUtils.isEmpty(mRequestType)) {
            return null;
        }

        try {
            Resources rs = mContext.getResources();

            if (rs.getString(R.string.popular).equals(mRequestType)
                    || rs.getString(R.string.top_rated).equals(mRequestType)) {
                return NetworkUtils.getMovies(mContext, mRequestType);
            } else if (rs.getString(R.string.trailer).equals(mRequestType)) {
                return NetworkUtils.getTrailers(mContext, mMoviesId);
            } else if (rs.getString(R.string.reviews).equals(mRequestType)) {
                return NetworkUtils.getReviews(mContext, mMoviesId);
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception occurred", e);
        }
        return null;
    }

}