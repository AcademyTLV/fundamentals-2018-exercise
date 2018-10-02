package com.academy.fundamentals.model;

import com.academy.fundamentals.rest.MovieListResult;
import com.academy.fundamentals.rest.MovieResult;

import java.util.ArrayList;

public class MovieModelConverter {

    public static ArrayList<MovieModel> convertResult(MovieListResult movieListResult) {

        ArrayList<MovieModel> result = new ArrayList<>();
        for (MovieResult movieResult : movieListResult.getResults()) {
            result.add(new MovieModel(movieResult.getTitle(), 0, 0, movieResult.getOverview(), movieResult.getReleaseDate(), movieResult.getPosterPath()));
        }

        return result;
    }
}
