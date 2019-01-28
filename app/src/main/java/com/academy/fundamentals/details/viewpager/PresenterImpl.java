package com.academy.fundamentals.details.viewpager;

import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.repositories.Listener;

import java.util.List;

class PresenterImpl implements Contract.Presenter {
  private final Interactor interactor;
  private final Contract.View view;

  PresenterImpl(Contract.View view) {
    this.interactor = new Interactor(view.getViewContext());
    this.view = view;

    this.interactor.retrieveMovies(new Listener<List<MovieModel>>() {
      @Override
      public void onSuccess(List<MovieModel> movieModels) {
        PresenterImpl.this.view.loadData(movieModels);
      }

      @Override
      public void onFailure() {
        PresenterImpl.this.view.onError();
      }
    });
  }
}
