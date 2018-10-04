package com.academy.fundamentals.model;

import com.academy.fundamentals.rest.MovieListResult;
import com.academy.fundamentals.rest.MovieResult;
import com.academy.fundamentals.rest.MoviesService;

import java.util.ArrayList;

public class MovieModelConverter {

    public static ArrayList<MovieModel> convertResult(MovieListResult movieListResult) {

        ArrayList<MovieModel> result = new ArrayList<>();
        for (MovieResult movieResult : movieListResult.getResults()) {
            result.add(new MovieModel(movieResult.getId(), movieResult.getTitle(), MoviesService.POSTER_BASE_URL + movieResult.getPosterPath(),
                    MoviesService.BACKDROP_BASE_URL + movieResult.getBackdropPath(), movieResult.getOverview(),
                    movieResult.getReleaseDate()));
        }

        return result;
    }
}
