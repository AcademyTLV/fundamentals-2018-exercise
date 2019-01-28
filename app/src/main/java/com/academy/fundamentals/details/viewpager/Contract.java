package com.academy.fundamentals.details.viewpager;

import android.content.Context;

import com.academy.fundamentals.model.MovieModel;

import java.util.List;

interface Contract {
  interface View {
    void loadData(List<MovieModel> movies);
    void onError();
    Context getViewContext();
  }
  interface Presenter {

  }
}
