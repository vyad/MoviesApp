package com.example.vyad.moviesapp;


public class Reviews {

    private final String mId;
    private final String mAuthor;
    private final String mContent;
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
