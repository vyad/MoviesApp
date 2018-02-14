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

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder>{

    private TrailerResource.Trailer[] mTrailer;

    private final TrailerClickListener mListener;

    public TrailerAdapter(final TrailerClickListener clickListener) {
      mListener = clickListener;
    }

    public interface TrailerClickListener {
         void click(final TrailerResource.Trailer trailer);
    }

    @Override
    public TrailerAdapter.TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerAdapter.TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mTrailer == null) {
            return 0;
        }
        return mTrailer.length;
    }

    class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mTrailerLabel;
        public TrailerHolder(View itemView) {
            super(itemView);
            mTrailerLabel = itemView.findViewById(R.id.trailer_label);
            itemView.setOnClickListener(this);
        }

        public void bind(final int position) {
            mTrailerLabel.setText(String.format("Trailer %s", position + 1));
        }

        @Override
        public void onClick(View view) {
            mListener.click(mTrailer[getAdapterPosition()]);
        }
    }

    public void setTrailerData(final TrailerResource.Trailer[] trailers) {
        mTrailer = trailers;
        notifyDataSetChanged();
    }
}
