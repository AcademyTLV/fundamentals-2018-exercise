package com.academy.fundamentals.rest;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {

    /* base search image url */
    String BASE_URL = "https://api.themoviedb.org/3/";

    String POPULAR = "movie/popular";

    /* api key */
    String apiKey = "f453f49d458a7818caaec0a67158d0ee";
    String keyQuery= "?api_key=" + apiKey;

    String QUERY_PATH = POPULAR + keyQuery;

    @GET(QUERY_PATH)
    Call<MovieListResult> searchImage();
}


