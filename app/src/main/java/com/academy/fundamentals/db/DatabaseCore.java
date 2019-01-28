package com.academy.fundamentals.db;

import android.content.Context;

import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.model.VideoModel;

import java.util.List;

public class DatabaseCore {

  public static List<MovieModel> retrieveAllMovies(Context context) {
    return AppDatabase
            .getInstance(context)
            .movieDao()
            .getAll();
  }

  public static void saveMovies(Context context, List<MovieModel> movies) {
    deleteAllMovies(context);
    AppDatabase
            .getInstance(context)
            .movieDao()
            .insertAll(movies);
  }

  public static void deleteAllMovies(Context context) {
    AppDatabase
            .getInstance(context)
            .movieDao()
            .deleteAll();
  }

  public static VideoModel getVideo(Context context, int movieId) {
    return AppDatabase
            .getInstance(context)
            .videoDao()
            .getVideo(movieId);
  }

  public static void saveVideo(Context context, VideoModel video) {
    AppDatabase
            .getInstance(context)
            .videoDao()
            .insert(video);
  }
}
