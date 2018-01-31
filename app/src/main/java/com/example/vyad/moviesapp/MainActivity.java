/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Launches a movie app with list of movies in grid layout, provides a way to sort movies
 * by highest rated or most popular movie and also provide onclick listener on the movies poster
 * to open movies details.
 */
public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesClickListener,
        LoaderCallbacks<Movies[]> {

    private MoviesAdapter mMoviesAdapter;

    private static final String TAG = MainActivity.class.getName();


    //  Loader id to uniquely this loader
    private static final int MOVIES_LOADER = 22;

    private static final String MOVIES_URL = "movies_url";

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_recycler_view)
    RecyclerView mMoviesList;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_display_error_message)
    TextView mDisplayError;

    private MenuItem mSortByPopularity;

    private MenuItem mSortByRating;

    private static final String MOST_POPULAR_MOVIES_URL =
            "http://api.themoviedb.org/3/movie/popular";

    private static final String HIGHEST_RATED_MOVIES_URL =
            "http://api.themoviedb.org/3/movie/top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mMoviesList = findViewById(R.id.rv_recycler_view);

        /*
          This TextView is used to display errors and will be hidden when there are no errors
         */
        mDisplayError = findViewById(R.id.tv_display_error_message);

        ButterKnife.bind(this);

        /*
          GridLayoutManager to shows movies poster in grid form
         */
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesList.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mMoviesList.setHasFixedSize(true);

        /*
         * The MoviesAdapter is responsible for linking our movies data with the Views that
         * will end up displaying our moves data.
         */
        mMoviesAdapter = new MoviesAdapter(this);

         /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mMoviesList.setAdapter(mMoviesAdapter);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Movies[]> loader = loaderManager.getLoader(MOVIES_LOADER);
        Bundle bundle = new Bundle();
        bundle.putString(MOVIES_URL, MOST_POPULAR_MOVIES_URL);

        //If loader is not created then create the loader else restart the same loader for current
        // bundle and callbacks
        if (loader == null) {
            Log.d(TAG, "Loader is null");
            loaderManager.initLoader(MOVIES_LOADER, bundle, MainActivity.this);
        } else {
            Log.d(TAG, "Loader is not null");
            loaderManager.restartLoader(MOVIES_LOADER, bundle, MainActivity.this);
        }
    }

    @Override
    public void onClickMovies(Movies movies) {
        Intent intentToStartDetailsActivity = new Intent(this, DetailsActivity.class);
        intentToStartDetailsActivity.putExtra(Intent.EXTRA_TEXT, movies);
        startActivity(intentToStartDetailsActivity);
    }

    @Override
    public Loader<Movies[]> onCreateLoader(int id, final Bundle args) {
        Log.d(TAG, "onCreateLoader");
        return new FetchTask(this, args.getString(MOVIES_URL));
    }

    @Override
    public void onLoadFinished(Loader<Movies[]> loader, Movies[] data) {
        Log.d(TAG, "onLoadFinished");
        if (data != null) {
            loadMoviesData(data);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<Movies[]> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.settings, menu);

        /* Menu item used to sort movies item by popularity and will be hidden when movies are
         * already sorted by popularity */
        mSortByPopularity = menu.findItem(R.id.sort_popularity);

        /* Menu item to sort movies item by highest rating and will be hidden when movies are
        * already sorted by highest rating */
        mSortByRating = menu.findItem(R.id.sort_ratings);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /* If sort by popularity menu item is clicked, then hides it and show sort by highest rating
        * menu and load movies data sorted by popularity and vice versa*/
        if (id == R.id.sort_popularity) {
            resetLoader(MOST_POPULAR_MOVIES_URL);
            mSortByRating.setVisible(true);
            mSortByPopularity.setVisible(false);
            return true;
        } else if (id == R.id.sort_ratings) {
            resetLoader(HIGHEST_RATED_MOVIES_URL);
            mSortByPopularity.setVisible(true);
            mSortByRating.setVisible(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows movies data and hides error message
     */
    private void showMoviesData() {
        mDisplayError.setVisibility(View.INVISIBLE);
        mMoviesList.setVisibility(View.VISIBLE);
    }

    /**
     * Shows error message and hides movies data
     */
    private void showErrorMessage() {
        mDisplayError.setVisibility(View.VISIBLE);
        mMoviesList.setVisibility(View.INVISIBLE);
    }

    /**
     * loads adapter with movies data
     *
     * @param movies array of movies data
     */
    private void loadMoviesData(final Movies[] movies) {
        showMoviesData();
        mMoviesAdapter.setMoviesData(movies);
    }

    /**
     * Restart the loader to fetch latest movies data
     * @param moviesUrl movies url
     */
    private void resetLoader(final String moviesUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIES_URL, moviesUrl);
        getSupportLoaderManager().restartLoader(MOVIES_LOADER, bundle, this);
    }
}
