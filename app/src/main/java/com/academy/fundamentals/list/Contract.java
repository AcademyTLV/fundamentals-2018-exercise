package com.academy.fundamentals.list;

import android.content.Context;

import com.academy.fundamentals.model.MovieModel;

import java.util.List;

interface Contract {
  interface View {
    void loadMovies(List<MovieModel> movies);

    void openMovieDetails(int position);

    void showLoading();

    void hideLoading();

    void clearAdapter();

    Context getViewContext();

    void onError();
  }

  interface Presenter {
    void onMovieClicked(int position);

    void onMenuDeleteClicked();
  }
}
