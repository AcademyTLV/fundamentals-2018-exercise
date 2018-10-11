package com.academy.fundamentals.model;

import com.academy.fundamentals.rest.MoviesService;

import java.util.ArrayList;
import java.util.List;

public class MovieModelConverter {

    public static ArrayList<MovieModel> convertResult(MovieListResult movieListResult) {

        ArrayList<MovieModel> result = new ArrayList<>();
        for (MovieResult movieResult : movieListResult.getResults()) {
            result.add(new MovieModel(movieResult.getId(), movieResult.getTitle(), MoviesService.POSTER_BASE_URL + movieResult.getPosterPath(),
                    MoviesService.BACKDROP_BASE_URL + movieResult.getBackdropPath(), movieResult.getOverview(),
                    movieResult.getReleaseDate(), movieResult.getPopularity()));
        }

        return result;
    }

    public static VideoModel convertVideoResult(VideosListResult videosListResult) {
        List<VideoResult> results = videosListResult.getResults();
        if (results != null && !results.isEmpty()) {
            VideoResult videoResult = results.get(0);
            return new VideoModel(videosListResult.getId(), videoResult.getId(), videoResult.getKey());

        }
        return null;
    }
}
