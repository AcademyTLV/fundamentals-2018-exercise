package com.academy.fundamentals.list;

import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.repositories.Listener;

import java.util.List;

class PresenterImpl implements Contract.Presenter {
  private final Interactor interactor;
  private final Contract.View view;

  PresenterImpl(Contract.View view) {
    this.interactor = new Interactor(view.getViewContext());
    this.view = view;

    loadData();
  }

  private void loadData() {
    view.showLoading();
    this.interactor.retrieveMovies(new Listener<List<MovieModel>>() {
      @Override
      public void onSuccess(List<MovieModel> movieModels) {
        view.hideLoading();
        view.loadMovies(movieModels);
      }

      @Override
      public void onFailure() {
        view.hideLoading();
        view.onError();
      }
    });
  }

  @Override
  public void onMovieClicked(int position) {
    if (interactor.isItemPositionCorrect(position)) {
      view.openMovieDetails(position);
    }
  }

  @Override
  public void onMenuDeleteClicked() {
    view.clearAdapter();
  }
}
