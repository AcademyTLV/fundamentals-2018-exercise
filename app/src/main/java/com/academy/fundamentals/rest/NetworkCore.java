package com.academy.fundamentals.rest;

import android.support.annotation.NonNull;

import com.academy.fundamentals.model.MovieListResult;
import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.model.VideoModel;
import com.academy.fundamentals.model.VideoResult;
import com.academy.fundamentals.model.VideosListResult;
import com.academy.fundamentals.repositories.Listener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCore {

  public static void searchMovies(final Listener<List<MovieModel>> listener) {
    RestClientManager
            .getMovieServiceInstance()
            .searchMovies()
            .enqueue(new Callback<MovieListResult>() {
              @Override
              public void onResponse(@NonNull Call<MovieListResult> call, @NonNull Response<MovieListResult> response) {
                if (response.code() == 200 && response.body() != null) {
                  listener.onSuccess(MovieModelConverter.convertResult(response.body()));
                } else {
                  listener.onFailure();
                }
              }

              @Override
              public void onFailure(@NonNull Call<MovieListResult> call, Throwable t) {
                listener.onFailure();
              }
            });
  }

  public static void getVideos(int movieId, final Listener<VideoModel> listener) {
    RestClientManager
            .getMovieServiceInstance()
            .getVideos(movieId)
            .enqueue(new Callback<VideosListResult>() {
              @Override
              public void onResponse(@NonNull Call<VideosListResult> call, @NonNull Response<VideosListResult> response) {
                VideosListResult body = response.body();
                if (body != null) {
                  List<VideoResult> results = body.getResults();
                  if (results != null && !results.isEmpty()) {
                    VideoModel convertedVideoModel = MovieModelConverter.convertVideoResult(body);
                    if (convertedVideoModel != null) {
                      listener.onSuccess(convertedVideoModel);
                    } else {
                      listener.onFailure();
                    }
                  } else {
                    listener.onFailure();
                  }
                } else {
                  listener.onFailure();
                }
              }

              @Override
              public void onFailure(@NonNull Call<VideosListResult> call, Throwable t) {
                listener.onFailure();
              }
            });
  }
}
