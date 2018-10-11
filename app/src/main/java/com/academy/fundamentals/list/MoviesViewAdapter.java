package com.academy.fundamentals.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.R;
import com.academy.fundamentals.model.MovieModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {

    private final List<MovieModel> movies;
    private OnMovieClickListener movieClickListener;
    private Picasso picasso;

    public MoviesViewAdapter(List<MovieModel> items, OnMovieClickListener listener) {
        movies = new ArrayList<>(items);
        movieClickListener = listener;
        picasso = Picasso.get();
    }

    public void setData(List<MovieModel> items) {
        movies.clear();
        movies.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView ivImage;
        public final TextView tvTitle;
        public final TextView tvOverview;

        public ViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.item_movie_iv);
            tvTitle = view.findViewById(R.id.item_movie_tv_title);
            tvOverview = view.findViewById(R.id.item_movie_tv_overview);
            view.setOnClickListener(this);
        }

        public void bind(MovieModel movieModel) {
            picasso.load(movieModel.getImageUri())
                    .into(ivImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.i("onSuccess", "onSuccess");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("onError", "onError() called with: e = [" + e + "]");
                        }
                    });
            tvTitle.setText(movieModel.getName());
            tvOverview.setText(movieModel.getOverview());
        }

        @Override
        public void onClick(View view) {
            if (movieClickListener == null) return;
            movieClickListener.onMovieClicked(getAdapterPosition());
        }
    }
}
