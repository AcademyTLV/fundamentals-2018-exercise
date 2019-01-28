package com.academy.fundamentals.list;

import android.content.Context;

import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.repositories.Listener;
import com.academy.fundamentals.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

class Interactor {
  private List<MovieModel> movies;
  private Context context;

  Interactor(Context context) {
    this.context = context;
  }

  boolean isItemPositionCorrect(int itemPosition) {
    return itemPosition >= 0 || itemPosition < movies.size();
  }

  void retrieveMovies(final Listener<List<MovieModel>> listener) {
    MovieRepository.getMovies(context, new Listener<List<MovieModel>>() {
      @Override
      public void onSuccess(List<MovieModel> movieModels) {
        if (movieModels != null) {
          Interactor.this.movies = movieModels;
        } else {
          Interactor.this.movies = new ArrayList<>();
        }
        listener.onSuccess(Interactor.this.movies);
      }

      @Override
      public void onFailure() {
        listener.onFailure();
      }
    });
  }
}
