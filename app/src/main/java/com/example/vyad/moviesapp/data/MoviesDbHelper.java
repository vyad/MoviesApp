/*
 * Copyright (C) 2018 The Android Open Source Project
*/
package com.example.vyad.moviesapp.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vyad.moviesapp.data.MovieContract.MoviesEntry;

/**
 * Manages local database for movies
 */
class MoviesDbHelper extends SQLiteOpenHelper {

//    Database name
    private static final String DATABASE_NAME = "movies.db";

//    Current version of database. onUpgrade method will be called only if database version is changed.

    private static final int DATABASE_VERSION = 2;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String ALTER_TABLE_ADD_COLUMN_BACKDROP_PATH =
            "ALTER TABLE " + MoviesEntry.TABLE_NAME + " ADD COLUMN" +
                    MoviesEntry.COLUMN_BACKDROP_PATH + " STRING NOT NULL";

    @SuppressLint("SQLiteString")
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                        MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        MoviesEntry.COLUMN_MOVIE_ID + " STRING NOT NULL, " +

                        MoviesEntry.COLUMN_TITLE + " STRING NOT NULL," +

                        MoviesEntry.COLUMN_BACKDROP_PATH + " STRING NOT NULL, " +

                        MoviesEntry.COLUMN_POSTER_PATH + " STRING NOT NULL, " +

                        MoviesEntry.COLUMN_RELEASE_DATE + " STRING NOT NULL, " +

                        MoviesEntry.COLUMN_OVERVIEW + " STRING NOT NULL, " +
                        MoviesEntry.COLUMN_POPULARITY + " Double NOT NULL, " +

                        MoviesEntry.COLUMN_VOTE_AVERAGE + " Double NOT NULL, " +

                        " UNIQUE (" + MoviesEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE );";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion > 1) {
            sqLiteDatabase.execSQL(ALTER_TABLE_ADD_COLUMN_BACKDROP_PATH);
        }
    }
}
