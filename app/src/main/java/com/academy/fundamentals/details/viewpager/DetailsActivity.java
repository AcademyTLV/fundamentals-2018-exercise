package com.academy.fundamentals.details.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.academy.fundamentals.R;
import com.academy.fundamentals.model.MovieModel;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements Contract.View {

  public static final String EXTRA_ITEM_POSITION = "init-position-data";
  private Contract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);

    presenter = new PresenterImpl(this);
  }

  @Override
  public void loadData(List<MovieModel> movies) {
    SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), movies);
    ViewPager viewPager = findViewById(R.id.details_vp_container);
    viewPager.setAdapter(mSectionsPagerAdapter);

    int startPosition = getIntent().getIntExtra(EXTRA_ITEM_POSITION, 0);
    viewPager.setCurrentItem(startPosition, false);
  }

  @Override
  public void onError() {
    Toast.makeText(this, R.string.something_went_wrong_text, Toast.LENGTH_SHORT).show();
  }

  @Override
  public Context getViewContext() {
    return this;
  }
}
