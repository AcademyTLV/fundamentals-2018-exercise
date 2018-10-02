package com.academy.fundamentals.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.academy.fundamentals.R;
import com.academy.fundamentals.details.DetailsActivity;
import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.model.MoviesContent;
import com.academy.fundamentals.rest.MoviesService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    private Retrofit retrofit;
    private MoviesService moviesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        RecyclerView recyclerView = findViewById(R.id.movies_rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        createNetworkService();

        loadMovies();

        recyclerView.setAdapter(new MoviesViewAdapter(MoviesContent.MOVIES, this));
    }

    private void createNetworkService() {
        retrofit = new Retrofit.Builder().
                baseUrl(MoviesService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesService = retrofit.create(MoviesService.class);
    }

    @Override
    public void onMovieClicked(int itemPosition) {
        if (itemPosition < 0 || itemPosition >= MoviesContent.MOVIES.size()) return;

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_ITEM_POSITION, itemPosition);
        startActivity(intent);
    }

    private void loadMovies() {
        MoviesContent.clear();

        final Call<JsonObject> jsonObjectCall = moviesService.searchImage();
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                jsonObjectCall.cancel();
                Log.i("response", "response");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("failure", "failure");

            }
        });
    }
}
