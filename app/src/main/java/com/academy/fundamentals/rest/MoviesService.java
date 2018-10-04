package com.academy.fundamentals.rest;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    String POPULAR_QUERY_PATH = POPULAR + keyQuery;

    String MOVIE_ID_KEY = "movie_id";
    String VIDEOS = "movie/{" + MOVIE_ID_KEY + "}/videos";

    String VIDEOS_QUERY_PATH = VIDEOS + keyQuery;

    @GET(POPULAR_QUERY_PATH)
    Call<MovieListResult> searchImage();

    @GET(VIDEOS_QUERY_PATH)
    Call<VideosListResult> getVideos(@Path(MOVIE_ID_KEY) int movieId);
}


