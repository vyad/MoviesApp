/*
 * Copyright (C) 2018 The Android Open Source Project
*/
package com.example.vyad.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  Wraps reviews data of movies
 */
public class Reviews implements Parcelable {
    // Unique id of a review
    private final String mId;

    //Author of review
    private final String mAuthor;

    // content of review
    private final String mContent;

    // IMDB url of review
    private final String mUrl;

    public Reviews(final String id, final String author, final String content, final String url) {
        mId = id;
        mAuthor = author;
        mContent = content;
        mUrl = url;
    }

    @SuppressWarnings("WeakerAccess")
    protected Reviews(Parcel in) {
        mId = in.readString();
        mAuthor = in.readString();
        mContent = in.readString();
        mUrl = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
        parcel.writeString(mUrl);
    }
}
