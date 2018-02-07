package com.example.vyad.moviesapp.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.vyad.moviesapp.data.MovieContract.MoviesEntry;

public class MoviesService extends IntentService {

    public static final String ADD_IN_FAVORITE_MOVIES_LIST = "add_favorite";

    public static final String REMOVE_FROM_FAVORITE_MOVIES_LIST = "remove_favorite";

    private static final String TAG = MoviesService.class.getName();

    public MoviesService() {
        super(MoviesService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        ContentResolver contentResolver = getContentResolver();

        if (ADD_IN_FAVORITE_MOVIES_LIST.equals(action)) {
            ContentValues cv = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            contentResolver.insert(MoviesEntry.CONTENT_URI, cv);
        } else if (REMOVE_FROM_FAVORITE_MOVIES_LIST.equals(action)) {
            String moviesId = intent.getStringExtra(Intent.EXTRA_TEXT);
            String[] selectionArgs = new String[1];
            selectionArgs[0] = moviesId;
            String selection = MoviesEntry.COLUMN_MOVIE_ID + " = ?";
            int entryDeleted = contentResolver.delete(MoviesEntry.CONTENT_URI, selection, selectionArgs);
            Log.d(TAG, "No of entry deleted " + entryDeleted);
        }
    }
}
