package com.academy.fundamentals.repositories;

public interface Listener<Data> {
  void onSuccess(Data data);

  void onFailure();
}
