package com.academy.fundamentals.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.academy.fundamentals.R;
import com.academy.fundamentals.details.viewpager.DetailsActivity;
import com.academy.fundamentals.model.MovieModel;

import java.util.List;

public class MoviesActivity extends AppCompatActivity implements OnMovieClickListener, Contract.View {

  private RecyclerView recyclerView;
  private View progressBar;
  private MoviesViewAdapter adapter;

  private Contract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);
    recyclerView = findViewById(R.id.movies_rv_list);
    progressBar = findViewById(R.id.main_progress);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    presenter = new PresenterImpl(this);
  }

  @Override
  public void onMovieClicked(int itemPosition) {
    presenter.onMovieClicked(itemPosition);
  }

  @Override
  public void loadMovies(List<MovieModel> movies) {
    adapter = new MoviesViewAdapter(movies, MoviesActivity.this);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void openMovieDetails(int position) {
    Intent intent = new Intent(this, DetailsActivity.class);
    intent.putExtra(DetailsActivity.EXTRA_ITEM_POSITION, position);
    startActivity(intent);
  }

  @Override
  public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    progressBar.setVisibility(View.GONE);
  }

  @Override
  public void clearAdapter() {
    adapter.clearData();
  }

  @Override
  public Context getViewContext() {
    return this;
  }

  @Override
  public void onError() {
    Toast.makeText(MoviesActivity.this, R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_delete:
        presenter.onMenuDeleteClicked();
        break;
    }

    return true;
  }
}
