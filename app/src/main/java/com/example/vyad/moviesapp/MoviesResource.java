package com.example.vyad.moviesapp;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MoviesResource {
    @SerializedName("page")
    public Integer page;

    @SerializedName("total_results")
    public Integer totalResults;

    @SerializedName("total_pages")
    public Integer totalPages;

    @SerializedName("results")
    public final Movies[] results = null;

    public static class Movies implements Parcelable {
        @SerializedName("id")
        public final String mId;

        @SerializedName("vote_average")
        public final double mVoteAverage;

        @SerializedName("popularity")
        public final double mPopularity;

        @SerializedName("poster_path")
        public final String mPosterPath;

        @SerializedName("original_title")
        public final String mOriginalTitle;

        @SerializedName("backdrop_path")
        public final String mBackdropPath;

        @SerializedName("overview")
        public final String mOverview;

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
        public Movies(final String id, final String originalTitle, final String posterPath, final String overview, final double voteAverage,
                      final String releaseDate, final double popularity, final String backdropPath) {
            mId = id;
            mOriginalTitle = originalTitle;
            mPosterPath = posterPath;
            mOverview = overview;
            mVoteAverage = voteAverage;
            mReleaseDate = releaseDate;
            mPopularity = popularity;
            mBackdropPath = backdropPath;
        }

        @SerializedName("release_date")
        public final String mReleaseDate;

        public String getId() {
            return mId;
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

        public double getPopularity() {
            return mPopularity;
        }

        public String getBackdropPath() {
            return mBackdropPath;
        }

        Movies(Parcel in) {
            mId = in.readString();
            mVoteAverage = in.readDouble();
            mPopularity = in.readDouble();
            mPosterPath = in.readString();
            mOriginalTitle = in.readString();
            mBackdropPath = in.readString();
            mOverview = in.readString();
            mReleaseDate = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mId);
            dest.writeDouble(mVoteAverage);
            dest.writeDouble(mPopularity);
            dest.writeString(mPosterPath);
            dest.writeString(mOriginalTitle);
            dest.writeString(mBackdropPath);
            dest.writeString(mOverview);
            dest.writeString(mReleaseDate);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Movies> CREATOR = new Creator<Movies>() {
            @Override
            public Movies createFromParcel(Parcel in) {
                return new Movies(in);
            }

            @Override
            public Movies[] newArray(int size) {
                return new Movies[size];
            }
        };
    }
}
