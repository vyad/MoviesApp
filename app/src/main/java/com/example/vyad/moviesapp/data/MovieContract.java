/*
 * Copyright (C) 2018 The Android Open Source Project
*/
package com.example.vyad.moviesapp.data;


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and columns name of movies database
 */
public class MovieContract {

// Content authority unique for each application
    private static final String CONTENT_AUTHORITY = "com.example.vyad.moviesapp";

//   base content uri to be used by content resolver to access our content provider
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

//    content path for favorite
    private static final String PATH_FAVORITE = "favorite";

    /**
     * Class holds the schema of table and attribute of database.
     */
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
