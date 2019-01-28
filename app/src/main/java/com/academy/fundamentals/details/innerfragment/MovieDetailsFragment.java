package com.academy.fundamentals.details.innerfragment;

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
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.academy.fundamentals.R;
import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.rest.MoviesService;
import com.squareup.picasso.Picasso;

/**
 * Created By Yamin on 21-09-2018
 */
public class MovieDetailsFragment extends Fragment implements View.OnClickListener, Contract.View {
  private static final String ARG_MOVIE = "MovieModel-data";
  private ImageView ivImage;
  private ImageView ivBackImage;
  private TextView tvTitle;
  private TextView tvReleaseDate;
  private TextView tvOverview;
  private Picasso picasso;
  private Button btnTrailer;
  private Contract.Presenter presenter;

  public MovieDetailsFragment() {
  }

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

    MovieModel movieModel = null;
    if (getArguments() != null) {
      movieModel = getArguments().getParcelable(ARG_MOVIE);
    }

    presenter = new PresenterImpl(this, movieModel);
  }

  @Override
  public void onClick(View view) {
    presenter.trailerButtonClick();
  }

  @Override
  public void loadMovieData(MovieModel movieModel) {
    picasso.load(movieModel.getImageUri())
            .into(ivImage);
    picasso.load(movieModel.getBackImageUri())
            .into(ivBackImage);

    tvTitle.setText(movieModel.getName());
    tvReleaseDate.setText(movieModel.getReleaseDate());
    tvOverview.setText(movieModel.getOverview());
  }

  @Override
  public void playTrailer(String videoKey) {
    String trailerUrl = MoviesService.YOUTUBE_BASE_URL + videoKey;
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
    startActivity(browserIntent);
  }

  @Override
  public void showLoading() {
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

  @Override
  public void hideLoading() {
    btnTrailer.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    btnTrailer.setText(R.string.details_trailer_text);
  }

  @Override
  public void onError() {
    Toast.makeText(getContext(), R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();
  }

  @Override
  public Context getViewContext() {
    return requireContext();
  }
}
