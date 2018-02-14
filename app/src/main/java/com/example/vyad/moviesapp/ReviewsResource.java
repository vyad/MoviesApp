package com.example.vyad.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ReviewsResource {

    @SerializedName("id")
    public Integer id;

    @SerializedName("page")
    public Integer page;

    @SerializedName("results")
    public final Reviews[] results = null;

    @SerializedName("total_pages")
    public Integer totalPages;

    @SerializedName("total_results")
    public Integer totalResults;

    public static class Reviews implements Parcelable {

        @SerializedName("id")
        public final String mId;

        @SerializedName("author")
        public final String mAuthor;

        @SerializedName("content")
        public final String mContent;

        @SerializedName("url")
        public final String mUrl;

        public Reviews(final String id, final String author, final String content, final String url) {
            mId = id;
            mAuthor = author;
            mContent = content;
            mUrl = url;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public String getContent() {
            return mContent;
        }

        Reviews(Parcel in) {
            mId = in.readString();
            mAuthor = in.readString();
            mContent = in.readString();
            mUrl = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mId);
            dest.writeString(mAuthor);
            dest.writeString(mContent);
            dest.writeString(mUrl);
        }

        @Override
        public int describeContents() {
            return 0;
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
    }
}