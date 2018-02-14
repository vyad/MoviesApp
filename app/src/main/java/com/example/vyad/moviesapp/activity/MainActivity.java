/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Parcelable;
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

import com.example.vyad.moviesapp.MoviesAdapter;
import com.example.vyad.moviesapp.MoviesResource;
import com.example.vyad.moviesapp.R;
import com.example.vyad.moviesapp.util.FetchTaskUtils;
import com.example.vyad.moviesapp.data.MovieContract.MoviesEntry;
import com.example.vyad.moviesapp.util.MoviesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Launches a movie app with list of movies in grid layout, provides a way to sort movies
 * by highest rated or most popular movie and also provide onclick listener on the movies poster
 * to open movies details.
 */
public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesClickListener,
        LoaderCallbacks<MoviesResource.Movies[]> {

    private MoviesAdapter mMoviesAdapter;

    private static final String TAG = MainActivity.class.getName();

    private static final String BUNDLE_RECYCLER_LAYOUT = "bundle";
    private static final String MOVIES = "movies";

    //  Loader id to uniquely this loader
    private static final int MOVIES_LOADER = 22;

    private static final String MOVIES_TYPE = "movies_url";

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_recycler_view)
    RecyclerView mMoviesList;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_display_error_message)
    TextView mDisplayError;

    private MenuItem mSortByPopularity;

    private MenuItem mSortByRating;

    private MenuItem mFavorite;

    private String mPopularMoviesUrl;
    private String mRatedMoviesUrl;

    private MoviesResource.Movies[] mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Implementing Butter knife
        ButterKnife.bind(this);

//        Setting different for portrait and landscape mode
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mMoviesList.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mMoviesList.setLayoutManager(new GridLayoutManager(this, 3));
        }

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

        if (savedInstanceState != null) {
            mMovies = (MoviesResource.Movies[]) savedInstanceState.getParcelableArray(MOVIES);
            loadMoviesData(mMovies);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mMoviesList.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            return;
        }

        initOrStartLoader();
    }

    private void initOrStartLoader() {
        //        Initializing the movies popular and highest rated movies url
        mPopularMoviesUrl = getString(R.string.popular);
        mRatedMoviesUrl = getString(R.string.top_rated);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<MoviesResource.Movies[]> loader = loaderManager.getLoader(MOVIES_LOADER);
        Bundle bundle = new Bundle();
        bundle.putString(MOVIES_TYPE, mPopularMoviesUrl);

        //If loader is not created then create the loader else restart the same loader for current
        // bundle and callbacks
        if (loader == null) {
            loaderManager.initLoader(MOVIES_LOADER, bundle, MainActivity.this);
        } else {
            loaderManager.restartLoader(MOVIES_LOADER, bundle, MainActivity.this);
        }
    }

    @Override
    public void onClickMovies(MoviesResource.Movies movies) {
        Intent intentToStartDetailsActivity = new Intent(this, DetailsActivity.class);
        intentToStartDetailsActivity.putExtra(Intent.EXTRA_TEXT, movies);
        startActivity(intentToStartDetailsActivity);
    }

    @Override
    public Loader<MoviesResource.Movies[]> onCreateLoader(int id, final Bundle args) {
        return new FetchTaskUtils(this, args.getString(MOVIES_TYPE), null);
    }

    @Override
    public void onLoadFinished(Loader<MoviesResource.Movies[]> loader, MoviesResource.Movies[] data) {
        if (data != null) {
            mMovies = data;
            loadMoviesData(mMovies);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<MoviesResource.Movies[]> loader) {

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

        mFavorite = menu.findItem(R.id.favorite_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /* If sort by popularity menu item is clicked, then hides it and show sort by highest rating
        * menu and favorite and load movies data sorted by popularity and vice versa*/
        if (id == R.id.sort_popularity) {
            resetLoader(mPopularMoviesUrl);
            mSortByPopularity.setVisible(false);
            mSortByRating.setVisible(true);
            mFavorite.setVisible(true);
            return true;
        } else if (id == R.id.sort_ratings) {
            resetLoader(mRatedMoviesUrl);
            mSortByRating.setVisible(false);
            mSortByPopularity.setVisible(true);
            mFavorite.setVisible(true);
            return true;
        } else if (id == R.id.favorite_menu) {
            loadFavoriteMovies();
            mFavorite.setVisible(false);
            mSortByPopularity.setVisible(true);
            mSortByRating.setVisible(true);

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
    private void loadMoviesData(final MoviesResource.Movies[] movies) {
        showMoviesData();
        mMoviesAdapter.setMoviesData(movies);
    }

    /**
     * Restart the loader to fetch latest movies data
     *
     * @param moviesUrl movies url
     */
    private void resetLoader(final String moviesUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIES_TYPE, moviesUrl);
        getSupportLoaderManager().restartLoader(MOVIES_LOADER, bundle, this);
    }

    private void loadFavoriteMovies() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MoviesEntry.CONTENT_URI, null,
                null, null, null);

        if ((cursor == null) || (cursor.getCount() == 0)) {
            Log.d(TAG, "There is no favorite movies");
            mDisplayError.setText(getString(R.string.no_favorite_movies));
            showErrorMessage();
            return;
        }
        mMovies = MoviesUtils.getMoviesObjectFromCursor(cursor);
        mMoviesAdapter.setMoviesData(mMovies);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mMoviesList.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArray(MOVIES, mMovies);
    }
}
