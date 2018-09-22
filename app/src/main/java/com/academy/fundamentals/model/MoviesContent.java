package com.academy.fundamentals.model;

import java.util.ArrayList;

/**
 * Created By Yamin on 22-09-2018
 */
public class MoviesContent {

    public static final ArrayList<MovieModel> MOVIES = new ArrayList<>();

    public static void clear() {
        MOVIES.clear();
    }

    public static void addMovie(MovieModel movie) {
        MOVIES.add(movie);
    }
}
