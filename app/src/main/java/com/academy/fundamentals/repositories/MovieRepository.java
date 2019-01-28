package com.academy.fundamentals.repositories;

import android.content.Context;

import com.academy.fundamentals.db.DatabaseCore;
import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.model.VideoModel;
import com.academy.fundamentals.rest.NetworkCore;

import java.util.List;

public class MovieRepository {
  public static void getMovies(final Context context, final Listener<List<MovieModel>> listener) {
    final List<MovieModel> movies = DatabaseCore.retrieveAllMovies(context);
    if (!movies.isEmpty()) {
      listener.onSuccess(movies);
    } else {
      NetworkCore.searchMovies(new Listener<List<MovieModel>>() {
        @Override
        public void onSuccess(List<MovieModel> movieModels) {
          DatabaseCore.saveMovies(context, movieModels);
          listener.onSuccess(movieModels);
        }

        @Override
        public void onFailure() {
          listener.onFailure();
        }
      });
    }
  }

  public static void getVideos(final Context context, int movieId, final Listener<String> listener) {
    VideoModel video = DatabaseCore.getVideo(context, movieId);
    if (video != null) {
      listener.onSuccess(video.getKey());
    } else {
      NetworkCore.getVideos(movieId, new Listener<VideoModel>() {
        @Override
        public void onSuccess(VideoModel videoModel) {
          DatabaseCore.saveVideo(context, videoModel);
          listener.onSuccess(videoModel.getKey());
        }

        @Override
        public void onFailure() {
          listener.onFailure();
        }
      });
    }
  }
}
