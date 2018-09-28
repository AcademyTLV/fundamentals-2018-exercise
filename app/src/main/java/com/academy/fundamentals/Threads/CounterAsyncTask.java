package com.academy.fundamentals.Threads;

import android.os.AsyncTask;
import android.os.SystemClock;

public class CounterAsyncTask extends AsyncTask<Integer, Integer, Void> {


    private IAsyncTaskEvents mIAsyncTaskEvents;

    public CounterAsyncTask(IAsyncTaskEvents iAsyncTaskEvents) {
        mIAsyncTaskEvents = iAsyncTaskEvents;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        int length = 0;
        if (integers.length == 1) {
            length = integers[0].intValue();
        } else {
            length = 10;
        }

        for (int i = 0; i <= length; i++) {
            if(isCancelled()) {
                return null;
            }
            publishProgress(i);
            SystemClock.sleep(500);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mIAsyncTaskEvents != null) {
            mIAsyncTaskEvents.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (mIAsyncTaskEvents != null) {
            mIAsyncTaskEvents.onPostExecute();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (mIAsyncTaskEvents != null) {
            mIAsyncTaskEvents.onProgressUpdate(values[0]);
        }
    }

}
