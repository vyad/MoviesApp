package com.example.vyad.moviesapp;

public class Trailer {

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

    public String getId() {
        return mId;
    }

    public String getIso6391() {
        return mIso6391;
    }

    public String getIso31661() {
        return mIso31661;
    }

    public String getKey() {
        return mKey;
    }

    public String getSite() {
        return mSite;
    }

    public String getSize() {
        return mSize;
    }

    public String getTrailerLabel() {
        return mTrailerLabel;
    }
}
