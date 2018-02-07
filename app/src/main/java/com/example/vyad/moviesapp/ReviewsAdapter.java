/*
 * Copyright (C) 2018 The Android Open Source Project
*/
package com.example.vyad.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This class is responsible for showing review on the screen.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder> {

    // List of reviews
    private Reviews[] mReviews;

    private static final String TAG = ReviewsAdapter.class.getName();

    @Override
    public ReviewsAdapter.ReviewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewsAdapter.ReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mReviews == null) {
            return 0;
        }

        return mReviews.length;
    }

    /**
     * Responsible to add review data in a view holder.
     */
    class ReviewsHolder extends RecyclerView.ViewHolder {

//        Current review in adapter
        Reviews mSpecificReviews;

//        TextView to show the author name
        final TextView mAuthorTextView;

//        TextView to show content of review
        final TextView mContentTextView;

        /**
         * Initializes the text views
         * @param itemView current view of holder
         */
        public ReviewsHolder(View itemView) {
            super(itemView);
            mAuthorTextView = itemView.findViewById(R.id.author);
            mContentTextView = itemView.findViewById(R.id.review);
        }

        /**
         * sets the review data
         * @param position current position in adapter
         */
        public void bind(final int position) {
            mSpecificReviews = mReviews[position];
            mAuthorTextView.setText(mSpecificReviews.getAuthor());
            mContentTextView.setText(mSpecificReviews.getContent());
        }
    }

    /**
     * Updates mReviews, list of review of the class
     * @param reviews current review data
     */
    public void setReviewsData(final Reviews[] reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
