/*
 * Copyright (C) 2018 The Android Open Source Project
*/
package com.example.vyad.moviesapp;

/**
 *  Wraps reviews data of movies
 */
public class Reviews {

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

    public String getId() {
        return mId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public String getUrl() {
        return mUrl;
    }
}
