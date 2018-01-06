/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * shows details of a movie; original title, thumbnail etc.
 */
public class DetailsActivity extends AppCompatActivity {

    private Movies mMovies;

    private static final String TAG = DetailsActivity.class.getName();
    private static final String MOVIE_THUMBNAIL_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        /* TextView to display title of a movie which is clicked */
        TextView mDisplayTitle = findViewById(R.id.tv_movie_title);

        /* ImageView to display thumbnail of the movie */
        ImageView mDisplayImage = findViewById(R.id.tv_image_thumbnail);

        /* TextView to display release date of the movie */
        TextView mDisplayReleaseYear = findViewById(R.id.tv_release_date);

        /* TextView to display average rating of the movie */
        TextView mDisplayRating = findViewById(R.id.tv_rating);

        /* TextView to display overview of the movie */
        TextView mDisplayOverview = findViewById(R.id.tv_synopsis);

        Intent intentStartedActivity = getIntent();
        mMovies = intentStartedActivity.getParcelableExtra(Intent.EXTRA_TEXT);

        // sets title of the movie
        mDisplayTitle.setText(String.valueOf(mMovies.getOriginalTitle()));

        // sets thumbnail of the movie
        String thumbnailUrl = MOVIE_THUMBNAIL_BASE_URL + mMovies.getPosterPath();
        Picasso.with(this).load(thumbnailUrl).into(mDisplayImage);

        //sets release data of the movie
        try {
            String dateYear = getMoviesDateYear();
            mDisplayReleaseYear.setText(dateYear);
        } catch (ParseException e) {
            Log.d(TAG, "Exception occurred ", e);
        }

        //sets rating of the movie
        String rating = String.format(getString(R.string.rating_format),
                String.valueOf(mMovies.getVoteAverage()));
        mDisplayRating.setText(rating);

        //sets overview of the movie
        mDisplayOverview.setText(mMovies.getOverview());
    }

    /**
     * gets year of release from movie object
     *
     * @return year of release as string
     * @throws ParseException if something went wrong while parsing string into date
     */
    private String getMoviesDateYear() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = format.parse(mMovies.getReleaseDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(year);
    }
}
