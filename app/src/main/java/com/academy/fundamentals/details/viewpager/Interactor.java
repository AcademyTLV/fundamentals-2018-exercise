package com.academy.fundamentals.details.viewpager;

import android.content.Context;

import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.repositories.Listener;
import com.academy.fundamentals.repositories.MovieRepository;

import java.util.List;

class Interactor {
  private Context context;

  Interactor(Context context) {
    this.context = context;
  }

  void retrieveMovies(Listener<List<MovieModel>> listener) {
    MovieRepository.getMovies(context, listener);
  }
}
