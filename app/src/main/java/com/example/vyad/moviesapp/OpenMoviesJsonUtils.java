/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
final class OpenMoviesJsonUtils {

    /**
     * parses json response from server and creates array of movies object
     *
     * @param moviesJsonStr movies data in json string format
     * @return array of movies object
     * @throws JSONException if something went wrong while parsing json; trying to get some field
     *                       which is not available.
     */
    public static Movies[] getMoviesObjectFromJsonString(final String moviesJsonStr)
            throws JSONException {

        final String RESULTS = "results";
        final String VOTE_AVERAGE = "vote_average";
        final String POPULARITY = "popularity";
        final String POSTER_PATH = "poster_path";
        final String ORIGINAL_TITLE = "original_title";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);
        JSONObject movieObject;
        String originalTitle;
        String posterPath;
        String overview;
        double voteAverage;
        String releaseDate;
        double popularity;
        Movies[] movies = new Movies[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            movieObject = moviesArray.getJSONObject(i);
            originalTitle = movieObject.getString(ORIGINAL_TITLE);
            posterPath = movieObject.getString(POSTER_PATH);
            overview = movieObject.getString(OVERVIEW);
            voteAverage = movieObject.getDouble(VOTE_AVERAGE);
            releaseDate = movieObject.getString(RELEASE_DATE);
            popularity = movieObject.getDouble(POPULARITY);
            movies[i] = new Movies(originalTitle, posterPath, overview, voteAverage, releaseDate, popularity);

        }
        return movies;
    }
}
