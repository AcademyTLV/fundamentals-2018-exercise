package com.academy.fundamentals.details.innerfragment;

import com.academy.fundamentals.model.MovieModel;
import com.academy.fundamentals.repositories.Listener;

class PresenterImpl implements Contract.Presenter {
  private final Interactor interactor;
  private final Contract.View view;

  PresenterImpl(Contract.View view, MovieModel movieModel) {
    interactor = new Interactor(view.getViewContext(), movieModel);
    this.view = view;

    if (interactor.isMovieModelValid()) {
      this.view.loadMovieData(movieModel);
    } else {
      this.view.onError();
    }
  }

  @Override
  public void trailerButtonClick() {
    view.showLoading();
    interactor.fetchVideo(new Listener<String>() {
      @Override
      public void onSuccess(String movieKey) {
        view.hideLoading();
        view.playTrailer(movieKey);
      }

      @Override
      public void onFailure() {
        view.hideLoading();
        view.onError();
      }
    });
  }
}
