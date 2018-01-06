/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Wraps movies data into a single object, implements parcelable to easily pass this object by
 * intents
 */
public class Movies implements Parcelable {
    private final double mVoteAverage;
    private final double mPopularity;
    private final String mPosterPath;
    private final String mOriginalTitle;
    private final String mOverview;
    private final String mReleaseDate;

    /**
     * constructs a movies object by taking movie attribute as formal parameters.
     *
     * @param originalTitle movie title
     * @param posterPath thumbnail path
     * @param overview movie synopsis
     * @param voteAverage average rating
     * @param releaseDate release date of the movie
     * @param popularity how popular this movie is
     */
    public Movies(final String originalTitle, final String posterPath, final String overview, final double voteAverage,
                  final String releaseDate, final double popularity) {
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
        mPopularity = popularity;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeDouble(mVoteAverage);
        out.writeDouble(mPopularity);
        out.writeString(mPosterPath);
        out.writeString(mOriginalTitle);
        out.writeString(mOverview);
        out.writeString(mReleaseDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Movies(Parcel in) {
        mVoteAverage = in.readDouble();
        mPopularity = in.readDouble();
        mPosterPath = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
    }
}
