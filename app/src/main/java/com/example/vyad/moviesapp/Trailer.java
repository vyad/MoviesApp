/*
 * Copyright (C) 2018 The Android Open Source Project
*/
package com.example.vyad.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Wraps trailer data
 */
public class Trailer implements Parcelable {
    private final String mId;
    private final String mIso6391;
    private final String mIso31661;
    private final String mKey;
    private final String mTrailerLabel;
    private final String mSite;
    private final String mSize;

    public Trailer(final String id, final String iso6391, final String iso31661, final String key,
                   final String trailerLabel, final String site, final String size) {
        mId = id;
        mIso6391 = iso6391;
        mIso31661 = iso31661;
        mKey = key;
        mTrailerLabel = trailerLabel;
        mSite = site;
        mSize = size;
    }

    @SuppressWarnings("WeakerAccess")
    protected Trailer(Parcel in) {
        mId = in.readString();
        mIso6391 = in.readString();
        mIso31661 = in.readString();
        mKey = in.readString();
        mTrailerLabel = in.readString();
        mSite = in.readString();
        mSize = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getKey() {
        return mKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mId);
        parcel.writeString(mIso6391);
        parcel.writeString(mIso31661);
        parcel.writeString(mKey);
        parcel.writeString(mTrailerLabel);
        parcel.writeString(mSite);
        parcel.writeString(mSize);
    }
}
