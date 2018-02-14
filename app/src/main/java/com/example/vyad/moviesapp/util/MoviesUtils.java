/*
 * Copyright (C) 2018 The Android Open Source Project
*/
package com.example.vyad.moviesapp.util;

import android.database.Cursor;

import com.example.vyad.moviesapp.MoviesResource;
import com.example.vyad.moviesapp.data.MovieContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *  wraps methods which can be used in multiple class of the package and is in common with others.
 */
public class MoviesUtils {

    /**
     * Used to get list of movies object from cursor
     * @param cursor cursor having movies data
     * @return list of movies
     */
    public static MoviesResource.Movies[] getMoviesObjectFromCursor(final Cursor cursor) {
        MoviesResource.Movies[] movieArray = new MoviesResource.Movies[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            String movies_id = cursor.getString(cursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_ID));
            String title = cursor.getString(cursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_TITLE));
            String backdropPath = cursor.getString(cursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_BACKDROP_PATH));
            String posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_POSTER_PATH));
            String overview = cursor.getString(cursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_OVERVIEW));
            double voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE));
            String releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE));
            double popularity = cursor.getDouble(cursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_POPULARITY));
            MoviesResource.Movies movies = new MoviesResource.Movies(movies_id, title, posterPath, overview, voteAverage, releaseDate, popularity, backdropPath);
            movieArray[i++] = movies;
        }
        return movieArray;
    }

    /**
     * gets year of release from movie object
     *
     * @return year of release as string
     * @throws ParseException if something went wrong while parsing string into date
     */
    public static String getMoviesDateYear(final String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date formattedDate = format.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formattedDate);
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }
}
