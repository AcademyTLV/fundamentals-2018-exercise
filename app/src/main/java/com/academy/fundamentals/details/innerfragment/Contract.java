package com.academy.fundamentals.details.innerfragment;

import android.content.Context;

import com.academy.fundamentals.model.MovieModel;

interface Contract {
  interface View {
    void loadMovieData(MovieModel movieModel);

    void playTrailer(String videoKey);

    void showLoading();

    void hideLoading();

    void onError();

    Context getViewContext();
  }

  interface Presenter {
    void trailerButtonClick();
  }
}
