package com.academy.fundamentals.details.innerfragment;

import android.content.Context;
import android.util.Log;

import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.repositories.Listener;
import com.academy.fundamentals.repositories.MovieRepository;

class Interactor {
  private final Context context;
  private MovieModel movieModel;
  private String videoKey;

  Interactor(Context context, MovieModel movieModel) {
    this.context = context;
    this.movieModel = movieModel;

    Log.d(Interactor.class.getSimpleName(), "movieModel: " + movieModel);
  }

  void fetchVideo(final Listener<String> listener) {
    if (videoKey == null) {
      MovieRepository.getVideos(context, movieModel.getMovieId(), new Listener<String>() {
        @Override
        public void onSuccess(String videoKey) {
          Interactor.this.videoKey = videoKey;
          listener.onSuccess(videoKey);
        }

        @Override
        public void onFailure() {
          listener.onFailure();
        }
      });
    } else {
      listener.onSuccess(videoKey);
    }
  }

  boolean isMovieModelValid() {
    return movieModel != null;
  }
}
