
package com.example.vyad.moviesapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TrailerResource {

    @SerializedName("id")
    
    public Integer id;
    @SerializedName("results")
    
    public final Trailer[] results = null;

    public static class Trailer implements Parcelable {

        @SerializedName("id")
        public final String mId;

        @SerializedName("iso_639_1")
        public final String mIso6391;

        @SerializedName("iso_3166_1")
        public final String mIso31661;

        @SerializedName("key")
        public final String mKey;

        @SerializedName("name")
        public final String mTrailerLabel;

        @SerializedName("site")
        public final String mSite;

        @SerializedName("size")
        public final String mSize;

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

        public String getKey() {
            return mKey;
        }

        Trailer(Parcel in) {
            mId = in.readString();
            mIso6391 = in.readString();
            mIso31661 = in.readString();
            mKey = in.readString();
            mTrailerLabel = in.readString();
            mSite = in.readString();
            mSize = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mId);
            dest.writeString(mIso6391);
            dest.writeString(mIso31661);
            dest.writeString(mKey);
            dest.writeString(mTrailerLabel);
            dest.writeString(mSite);
            dest.writeString(mSize);
        }

        @Override
        public int describeContents() {
            return 0;
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
    }
}

