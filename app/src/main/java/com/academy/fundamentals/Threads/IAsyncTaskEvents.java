package com.academy.fundamentals.Threads;

public interface IAsyncTaskEvents {

    void createAsyncTask();
    void startAsyncTask();
    void cancelAsyncTask();

    void onPreExecute();
    void onPostExecute();
    void onProgressUpdate(Integer integer);
    void onCancel();
}
