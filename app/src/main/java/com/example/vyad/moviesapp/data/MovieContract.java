package com.example.vyad.moviesapp.data;


import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    private static final String CONTENT_AUTHORITY = "com.example.vyad.moviesapp";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_FAVORITE = "favorite";

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE)
                .build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movies_id";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER_PATH = "thumbnail";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_VOTE_AVERAGE = "rating";

        public static final String COLUMN_OVERVIEW = "synopsis";

        public static final String COLUMN_POPULARITY = "popularity";
    }
}
