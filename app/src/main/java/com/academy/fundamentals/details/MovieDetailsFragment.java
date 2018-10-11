package com.academy.fundamentals.details;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.RotateDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.academy.fundamentals.R;
import com.academy.fundamentals.db.AppDatabase;
import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.model.MovieModelConverter;
import com.academy.fundamentals.model.VideoModel;
import com.academy.fundamentals.model.VideoResult;
import com.academy.fundamentals.model.VideosListResult;
import com.academy.fundamentals.rest.MoviesService;
import com.academy.fundamentals.rest.RestClientManager;
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
    private Button btnTrailer;

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

        btnTrailer = view.findViewById(R.id.details_btn_trailer);
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
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        final Context context = activity.getApplicationContext();
        if (context == null) {
            return;
        }

        final VideoModel videoModel = AppDatabase.getInstance(context).videoDao().getVideo(movieModel.getMovieId());
        if (videoModel != null) {
            playTrailer(videoModel.getKey());
            return;
        }
        setButtonLoadingStatus();
        MoviesService moviesService = RestClientManager.getMovieServiceInstance();
        moviesService.getVideos(movieModel.getMovieId())
                .enqueue(new Callback<VideosListResult>() {

                    @Override
                    public void onResponse(Call<VideosListResult> call, Response<VideosListResult> response) {
                        VideosListResult body = response.body();
                        if (body != null) {
                            List<VideoResult> results = body.getResults();
                            if (results != null && !results.isEmpty()) {
                                VideoModel convertedVideoModel = MovieModelConverter.convertVideoResult(body);
                                if (convertedVideoModel != null) {
                                    AppDatabase.getInstance(context).videoDao().insert(convertedVideoModel);
                                    String key = convertedVideoModel.getKey();
                                    playTrailer(key);
                                }
                            }
                        }
                        resetButtonStatus();
                    }

                    @Override
                    public void onFailure(Call<VideosListResult> call, Throwable t) {
                        Toast.makeText(getContext(), R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();
                        resetButtonStatus();
                    }
                });

    }

    private void playTrailer(String key) {
        String trailerUrl = MoviesService.YOUTUBE_BASE_URL + key;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(browserIntent);
    }

    private void setButtonLoadingStatus() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        RotateDrawable rotateDrawable = (RotateDrawable) ContextCompat.getDrawable(context, R.drawable.progress_animation);
        ObjectAnimator anim = ObjectAnimator.ofInt(rotateDrawable, "level", 0, 10000);
        anim.setDuration(1000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();
        btnTrailer.setText(R.string.details_loading_trailer_text);
        btnTrailer.setCompoundDrawablesWithIntrinsicBounds(rotateDrawable, null, null, null);
    }

    private void resetButtonStatus() {
        btnTrailer.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        btnTrailer.setText(R.string.details_trailer_text);
    }
}
