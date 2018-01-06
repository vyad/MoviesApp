/*
 * Copyright (C) 2018 The Android Open Source Project
*/

package com.example.vyad.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * supplies the data and create the view for each item
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

    private Movies[] mMoviesData;

    private final MoviesClickListener mListener;

    private static final String MOVIE_THUMBNAIL_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    /**
     * construct an object of this object and set mListener data member
     *
     * @param listener listener object to listen onclick event
     */
    public MoviesAdapter(final MoviesClickListener listener) {
        mListener = listener;
    }

    /**
     * sets mMoviesData data member of the object
     *
     * @param moviesData list of movies data
     */
    public void setMoviesData(final Movies[] moviesData) {
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }

    @Override
    public MoviesAdapter.MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutForListItem = R.layout.movie_list_item;
        View view = inflater.inflate(layoutForListItem, parent, false);
        return new MoviesAdapter.MoviesHolder(view);

    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MoviesHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mMoviesData == null) {
            return 0;
        }
        return mMoviesData.length;
    }

    /**
     * create abstract method to handle onClick on any movie item
     */
    public interface MoviesClickListener {
        void onClickMovies(final Movies movies);
    }

    /**
     * View holder class for each movie item
     */
    class MoviesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView moviesIcon;

        final Context context;

        /**
         * constructs MoviesHolder object which holds a ImageView
         *
         * @param itemView ViewHolder view
         */
        public MoviesHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            moviesIcon = itemView.findViewById(R.id.tv_movie_poster);
            itemView.setOnClickListener(this);
        }

        /**
         * binds movie data with view, in this case it bind movie thumbnail with view.
         *
         * @param listIndex index of view
         */
        void bind(int listIndex) {
            Movies movies = mMoviesData[listIndex];
            String thumbnailUrl = MOVIE_THUMBNAIL_BASE_URL + movies.getPosterPath();
            Picasso.with(context).load(thumbnailUrl).into(moviesIcon);
        }

        @Override
        public void onClick(View view) {
            mListener.onClickMovies(mMoviesData[getAdapterPosition()]);
        }
    }

}
