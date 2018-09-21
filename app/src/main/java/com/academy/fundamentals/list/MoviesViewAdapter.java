package com.academy.fundamentals.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.R;
import com.academy.fundamentals.model.MovieModel;

import java.util.List;

public class MoviesViewAdapter extends RecyclerView.Adapter<MoviesViewAdapter.ViewHolder> {

    private final List<MovieModel> movies;
    private OnMovieClickListener movieClickListener;

    public MoviesViewAdapter(List<MovieModel> items, OnMovieClickListener listener) {
        movies = items;
        movieClickListener = listener;
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
            ivImage.setImageResource(movieModel.getImageRes());
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
