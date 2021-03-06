/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vyad.moviesapp.MoviesResource;
import com.example.vyad.moviesapp.R;
import com.example.vyad.moviesapp.ReviewsAdapter;
import com.example.vyad.moviesapp.ReviewsResource;
import com.example.vyad.moviesapp.TrailerAdapter;

import com.example.vyad.moviesapp.TrailerResource;
import com.example.vyad.moviesapp.services.MoviesService;
import com.example.vyad.moviesapp.util.FetchTaskUtils;
import com.example.vyad.moviesapp.util.MoviesUtils;
import com.example.vyad.moviesapp.util.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import com.example.vyad.moviesapp.data.MovieContract.MoviesEntry;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * shows details of a movie; original title, thumbnail etc.
 */
public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks,
        TrailerAdapter.TrailerClickListener, View.OnClickListener {

    private static final String TAG = DetailsActivity.class.getName();
    private static final String BUNDLE_RECYCLER_TRAILER = "recycler_trailer";
    private static final String BUNDLE_RECYCLER_REVIEWS = "recycler_review";
    private static final String REVIEWS = "reviews";
    private static final String TRAILER = "trailer";

    private static final int TRAILER_LOADER = 20;

    private static final int REVIEWS_LOADER = 21;

    private static final String TRAILER_URL = "trailer_url";

    private static final String REVIEWS_URL = "reviews_url";

    private MoviesResource.Movies mMovies;

    private TrailerResource.Trailer[] mTrailers;

    private ReviewsResource.Reviews[] mReviews;

    private TrailerAdapter mTrailerAdapter;

    private ReviewsAdapter mReviewsAdapter;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_trailer_recycler_view)
    RecyclerView mTrailerListView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_reviews_recycler_view)
    RecyclerView mReviewListView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.favorite)
    TextView mTextViewFavorite;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.header_image)
    ImageView mDisplayImage;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_release_date)
    TextView mDisplayReleaseYear;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_rating)
    TextView mDisplayRating;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.tv_synopsis)
    TextView mDisplayOverview;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //       Implementing Butter knife
        ButterKnife.bind(this);
        mMovies = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        setMoviesDetails();
        setToolBar();

        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mTrailerListView.setHasFixedSize(true);
        mTrailerListView.setLayoutManager(linearLayoutManager);
        mTrailerAdapter = new TrailerAdapter(this);
        mTrailerListView.setAdapter(mTrailerAdapter);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        mReviewListView.setHasFixedSize(true);
        mReviewListView.setLayoutManager(linearLayoutManager);
        mReviewsAdapter = new ReviewsAdapter();
        mReviewListView.setAdapter(mReviewsAdapter);

        mTextViewFavorite.setOnClickListener(this);


        if (savedInstanceState != null) {
            mTrailers = (TrailerResource.Trailer[]) savedInstanceState.getParcelableArray(TRAILER);
            mReviews = (ReviewsResource.Reviews[]) savedInstanceState.getParcelableArray(REVIEWS);
            setTrailerAdapter();
            setReviewsAdapter();

            Parcelable savedRecyclerTrailer = savedInstanceState.getParcelable(BUNDLE_RECYCLER_TRAILER);
            mTrailerListView.getLayoutManager().onRestoreInstanceState(savedRecyclerTrailer);

            Parcelable savedRecyclerReviews = savedInstanceState.getParcelable(BUNDLE_RECYCLER_REVIEWS);
            mReviewListView.getLayoutManager().onRestoreInstanceState(savedRecyclerReviews);
            return;
        }

        initOrRestartLoader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();

        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.setting_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share_trailer) {
            shareTrailer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMoviesDetails() {
        // sets thumbnail of the movie
        String thumbnailUrl = String.format(getString(R.string.thumbnail_url),
                mMovies.getBackdropPath());
        Picasso.with(this).load(thumbnailUrl).into(mDisplayImage);

        collapsingToolbarLayout.setTitle(mMovies.getOriginalTitle());

        //sets release data of the movie
        try {
            String dateYear = MoviesUtils.getMoviesDateYear(mMovies.getReleaseDate());
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

        setFavoriteTextView();
    }

    /**
     * Init or restart trailer and review loader
     */
    private void initOrRestartLoader() {
        LoaderManager loaderManager = getSupportLoaderManager();
        Bundle bundle = new Bundle();
        Loader loader;

        loader = loaderManager.getLoader(TRAILER_LOADER);
        String trailerUrl = NetworkUtils.getTrailerUrl(this, mMovies.getId());
        bundle.putString(TRAILER_URL, trailerUrl);
        if (loader == null) {
            loaderManager.initLoader(TRAILER_LOADER, bundle, this);
        } else {
            loaderManager.restartLoader(TRAILER_LOADER, bundle, this);
        }

        loader = loaderManager.getLoader(REVIEWS_LOADER);
        String reviewsUrl = NetworkUtils.getReviewsUrl(this, mMovies.getId());
        bundle.putString(REVIEWS_URL, reviewsUrl);
        if (loader == null) {
            loaderManager.initLoader(REVIEWS_LOADER, bundle, this);
        } else {
            loaderManager.restartLoader(REVIEWS_LOADER, bundle, this);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "inside onCreateLoader");
        switch (id) {
            case TRAILER_LOADER:
                return new FetchTaskUtils(this, getString(R.string.trailer), mMovies.getId());
            case REVIEWS_LOADER:
                return new FetchTaskUtils(this, getString(R.string.reviews), mMovies.getId());
            default:
                throw new IllegalArgumentException("Unknown Loader");
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        int loaderId = loader.getId();
        if (data == null) {
            Log.d(TAG, "data is null");
        } else if (loaderId == TRAILER_LOADER) {
            mTrailers = (TrailerResource.Trailer[]) data;
            setTrailerAdapter();
        } else if (loaderId == REVIEWS_LOADER) {
            Log.d(TAG, "reviews");
            mReviews = (ReviewsResource.Reviews[]) data;
            setReviewsAdapter();
        } else {
            throw new IllegalArgumentException("Illegal Object");
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    @Override
    public void click(TrailerResource.Trailer trailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri file = Uri.parse(String.format(getString(R.string.youtube_url), trailer.getKey()));
        intent.setData(file);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MoviesService.class);


        if (getString(R.string.favorite_label).equals(mTextViewFavorite.getText())) {
            ContentValues cv = new ContentValues();
            cv.put(MoviesEntry.COLUMN_MOVIE_ID, mMovies.getId());
            cv.put(MoviesEntry.COLUMN_POPULARITY, mMovies.getPopularity());
            cv.put(MoviesEntry.COLUMN_VOTE_AVERAGE, mMovies.getVoteAverage());
            cv.put(MoviesEntry.COLUMN_RELEASE_DATE, mMovies.getReleaseDate());
            cv.put(MoviesEntry.COLUMN_OVERVIEW, mMovies.getOverview());
            cv.put(MoviesEntry.COLUMN_TITLE, mMovies.getOriginalTitle());
            cv.put(MoviesEntry.COLUMN_POSTER_PATH, mMovies.getPosterPath());
            cv.put(MoviesEntry.COLUMN_BACKDROP_PATH, mMovies.getBackdropPath());
            intent.putExtra(Intent.EXTRA_TEXT, cv);
            intent.setAction(MoviesService.ADD_IN_FAVORITE_MOVIES_LIST);

            mTextViewFavorite.setText(getString(R.string.un_favorite_label));
            mTextViewFavorite.setBackground(getDrawable(R.color.colorAccent));
        } else {
            String moviesId = mMovies.getId();
            intent.putExtra(Intent.EXTRA_TEXT, moviesId);
            intent.setAction(MoviesService.REMOVE_FROM_FAVORITE_MOVIES_LIST);

            mTextViewFavorite.setText(getString(R.string.favorite_label));
            mTextViewFavorite.setBackground(getDrawable(R.color.titleColor));
        }

        startService(intent);
    }

    /**
     * Shares first trailer of movie
     */
    private void shareTrailer() {
        if (mTrailers == null) {
            return;
        }

        Uri file = Uri.parse(String.format(getString(R.string.youtube_url), mTrailers[0].getKey()));
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, file);
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
        startActivity(sendIntent);
    }

    /**
     * Set text of favorite text view.
     */
    private void setFavoriteTextView() {
        ContentResolver contentResolver = getContentResolver();
        String selection = MoviesEntry.COLUMN_MOVIE_ID + " = ?";
        String[] selectionArgs = new String[]{mMovies.getId()};
        Cursor cursor = contentResolver.query(MoviesEntry.CONTENT_URI, null,
                selection, selectionArgs, null);
        if (cursor == null) {
            Log.i(TAG, "Cursor is null");
            return;
        }

        Log.d(TAG, "no of data in cursor is " + cursor.getCount());
        if (cursor.getCount() > 0) {
            mTextViewFavorite.setText(getString(R.string.un_favorite_label));
            mTextViewFavorite.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            mTextViewFavorite.setText(getString(R.string.favorite_label));
            mTextViewFavorite.setBackgroundColor(ContextCompat.getColor(this, R.color.titleColor));
        }
        cursor.close();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setTrailerAdapter() {
        mTrailerAdapter.setTrailerData(mTrailers);
    }

    private void setReviewsAdapter() {
        mReviewsAdapter.setReviewsData(mReviews);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_TRAILER, mTrailerListView.getLayoutManager().onSaveInstanceState());
        outState.putParcelable(BUNDLE_RECYCLER_REVIEWS, mReviewListView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArray(TRAILER, mTrailers);
        outState.putParcelableArray(REVIEWS, mReviews);
    }
}
