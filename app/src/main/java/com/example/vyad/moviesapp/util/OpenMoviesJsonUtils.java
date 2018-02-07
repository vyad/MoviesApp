/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp.util;

import com.example.vyad.moviesapp.Movies;
import com.example.vyad.moviesapp.Reviews;
import com.example.vyad.moviesapp.Trailer;

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
        final String ID = "id";
        final String BACKDROP_PATH = "backdrop_path";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);
        JSONObject movieObject;
        String originalTitle;
        String posterPath;
        String overview;
        double voteAverage;
        String releaseDate;
        double popularity;
        String id;
        String backdropPath;
        Movies[] movies = new Movies[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            movieObject = moviesArray.getJSONObject(i);
            id = movieObject.getString(ID);
            originalTitle = movieObject.getString(ORIGINAL_TITLE);
            posterPath = movieObject.getString(POSTER_PATH);
            overview = movieObject.getString(OVERVIEW);
            voteAverage = movieObject.getDouble(VOTE_AVERAGE);
            releaseDate = movieObject.getString(RELEASE_DATE);
            popularity = movieObject.getDouble(POPULARITY);
            backdropPath = movieObject.getString(BACKDROP_PATH);
            movies[i] = new Movies(id, originalTitle, posterPath, overview, voteAverage, releaseDate, popularity,backdropPath);

        }
        return movies;
    }

    public static Trailer[] getTrailerObjectFromJsonString(final String trailerJsonStr) throws JSONException {
        final String RESULTS = "results";
        final String ID = "id";
        final String ISO6391 = "iso_639_1";
        final String ISO31661 = "iso_3166_1";
        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";
        final String SIZE = "size";
        JSONObject trailerJson = new JSONObject(trailerJsonStr);
        JSONArray trailerJsonJSONArray = trailerJson.getJSONArray(RESULTS);
        Trailer[] trailers = new Trailer[trailerJsonJSONArray.length()];

        JSONObject trailerObject;
        for (int i = 0; i < trailerJsonJSONArray.length(); i++) {
            trailerObject = trailerJsonJSONArray.getJSONObject(i);
            trailers[i] = new Trailer(
                    trailerObject.getString(ID),
                    trailerObject.getString(ISO6391),
                    trailerObject.getString(ISO31661),
                    trailerObject.getString(KEY),
                    trailerObject.getString(NAME),
                    trailerObject.getString(SITE),
                    trailerObject.getString(SIZE)
            );
        }

        return trailers;
    }

    public static Reviews[] getReviewsObjectFromJsonString(final String reviewJsonStr) throws JSONException {
        final String RESULTS = "results";
        final String ID = "id";
        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String URL = "url";

        JSONObject reviewJson = new JSONObject(reviewJsonStr);
        JSONArray reviewJsonJSONArray = reviewJson.getJSONArray(RESULTS);
        Reviews[] reviews = new Reviews[reviewJsonJSONArray.length()];

        JSONObject reviewObject;
        for (int i = 0; i < reviewJsonJSONArray.length(); i++) {
            reviewObject = reviewJsonJSONArray.getJSONObject(i);
            reviews[i] = new Reviews(
                    reviewObject.getString(ID),
                    reviewObject.getString(AUTHOR),
                    reviewObject.getString(CONTENT),
                    reviewObject.getString(URL)
            );
        }

        return reviews;
    }
}
