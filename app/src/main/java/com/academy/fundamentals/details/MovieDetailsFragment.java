package com.academy.fundamentals.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.academy.fundamentals.R;
import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.rest.MoviesService;
import com.academy.fundamentals.rest.VideoResult;
import com.academy.fundamentals.rest.VideosListResult;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created By Yamin on 21-09-2018
 */
public class MovieDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MovieDetailsFragment";
    private static final String ARG_MOVIE = "MovieModel-data";
    private ImageView ivImage;
    private ImageView ivBackImage;
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private MovieModel movieModel;
    private Picasso picasso;
    private MoviesService moviesService;

    public MovieDetailsFragment() { }

    public static MovieDetailsFragment newInstance(MovieModel movieModel) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movieModel);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picasso = Picasso.get();
        if (getArguments() != null) {
            movieModel = getArguments().getParcelable(ARG_MOVIE);
        }
        Log.d(TAG, "movieModel: "+movieModel);

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(MoviesService.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesService = retrofit.create(MoviesService.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivImage = view.findViewById(R.id.details_iv_image);
        ivBackImage = view.findViewById(R.id.details_iv_back);
        tvTitle = view.findViewById(R.id.details_tv_title);
        tvReleaseDate = view.findViewById(R.id.details_tv_released_date);
        tvOverview = view.findViewById(R.id.details_tv_overview_text);

        Button btnTrailer = view.findViewById(R.id.details_btn_trailer);
        btnTrailer.setOnClickListener(this);

        setMovie();
    }

    private void setMovie() {
        if (movieModel == null) return;

        picasso.load(movieModel.getImageUri())
                .into(ivImage);
        picasso.load(movieModel.getBackImageUri())
                .into(ivBackImage);

        tvTitle.setText(movieModel.getName());
        tvReleaseDate.setText(movieModel.getReleaseDate());
        tvOverview.setText(movieModel.getOverview());
    }

    @Override
    public void onClick(View view) {
        if (movieModel == null) return;

        moviesService.getVideos(movieModel.getMovieId())
                .enqueue(new Callback<VideosListResult>() {

                    @Override
                    public void onResponse(Call<VideosListResult> call, Response<VideosListResult> response) {
                        VideosListResult body = response.body();
                        if (body != null) {
                            List<VideoResult> results = body.getResults();
                            if (results != null && !results.isEmpty()) {
                                String trailerUrl = MoviesService.YOUTUBE_BASE_URL + results.get(0).getKey();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                                startActivity(browserIntent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VideosListResult> call, Throwable t) {

                    }
                });

    }
}
