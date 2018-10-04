package com.academy.fundamentals.rest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesService {
    
    String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
    String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";
    String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    String BASE_URL = "https://api.themoviedb.org";
    /* base search image url */
    String BASE_API_URL = BASE_URL + "/3/";

    String POPULAR = "movie/popular";

    /* api key */
    String apiKey = "f453f49d458a7818caaec0a67158d0ee";
    String keyQuery= "?api_key=" + apiKey;

    String QUERY_PATH = POPULAR + keyQuery;

    @GET(QUERY_PATH)
    Call<MovieListResult> searchImage();
}


