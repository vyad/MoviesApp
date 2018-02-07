/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.vyad.moviesapp.data.MovieContract.MoviesEntry;

public class MoviesProvider extends ContentProvider {

    private static final String TAG = MoviesProvider.class.getName();

    private MoviesDbHelper mMoviesDbHelper;

    @Override
    public boolean onCreate() {
        mMoviesDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "Query method is called");
        Cursor cursor = mMoviesDbHelper.getReadableDatabase().query(
                MoviesEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder

        );

        if (cursor != null) {
            //noinspection ConstantConditions
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "Insert method is called");
        long noOfRowInserted = mMoviesDbHelper.getWritableDatabase().insert(
                MoviesEntry.TABLE_NAME,
                null,
                contentValues
        );
        Log.d(TAG, "No of inserted row is " + noOfRowInserted);

        //noinspection ConstantConditions
        getContext().getContentResolver().notifyChange(uri, null);

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "Delete method is called");

        if (null == selection) selection = "1";

        int noOfRowDeleted = mMoviesDbHelper.getWritableDatabase().delete(
                MoviesEntry.TABLE_NAME,
                selection,
                selectionArgs
        );

        if (noOfRowDeleted != 0) {
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return noOfRowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new IllegalArgumentException("update method is not yet implemented");
    }
}
