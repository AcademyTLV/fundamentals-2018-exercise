package com.academy.fundamentals.details.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.academy.fundamentals.details.innerfragment.MovieDetailsFragment;
import com.academy.fundamentals.model.MovieModel;

import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
  private List<MovieModel> movies;

  public SectionsPagerAdapter(FragmentManager fm, List<MovieModel> movies) {
    super(fm);
    this.movies = movies;
  }

  @Override
  public Fragment getItem(int position) {
    return MovieDetailsFragment.newInstance(movies.get(position));
  }

  @Override
  public int getCount() {
    return movies.size();
  }
}
