package com.example.vyad.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder> {

    private Reviews[] mReviews;
    private static final String TAG = ReviewsAdapter.class.getName();

    public ReviewsAdapter() {
        Log.d(TAG, "ReviewsAdapter constructor is called");
    }

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

    class ReviewsHolder extends RecyclerView.ViewHolder {
        Reviews mSpecificReviews;
        final TextView mAuthorTextView;
        final TextView mContentTextView;
        public ReviewsHolder(View itemView) {
            super(itemView);
            mAuthorTextView = itemView.findViewById(R.id.author);
            mContentTextView = itemView.findViewById(R.id.review);
        }

        public void bind(final int position) {
            mSpecificReviews = mReviews[position];
            mAuthorTextView.setText(mSpecificReviews.getAuthor());
            mContentTextView.setText(mSpecificReviews.getContent());
//            Log.d(TAG, "author name is " + mSpecificReviews.getAuthor());
//            Log.d(TAG, "content is " + mSpecificReviews.getContent());
        }
    }

    public void setReviewsData(final Reviews[] reviews) {
        Log.d(TAG, "inside set review");
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
