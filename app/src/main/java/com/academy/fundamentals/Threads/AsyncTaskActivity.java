package com.academy.fundamentals.Threads;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.academy.fundamentals.R;

public class AsyncTaskActivity extends AppCompatActivity implements IAsyncTaskEvents {

    private static FragmentManager mFragmentManager;
    private CounterFragment mThreadsFragment;
    private CounterAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynctask_activity);

        mFragmentManager = getSupportFragmentManager();//Get Fragment Manager
        if (mThreadsFragment == null) {
            mThreadsFragment = new CounterFragment();//Get Fragment Instance
            Bundle data = new Bundle();//Use bundle to pass data
            data.putString(CounterFragment.FRAGMENT_TYPE, getString(R.string.async_task_activity));//put string, int, etc in bundle with a key value
            mThreadsFragment.setArguments(data);//Finally set argument bundle to fragment
            mFragmentManager.beginTransaction().replace(R.id.fragment, mThreadsFragment).commit();//now replace the argument fragment
        }
    }


    /***
     // IAsyncTaskEvent's methods - start:
     ***/

    @Override
    public void createAsyncTask() {
        Toast.makeText(this, getString(R.string.msg_oncreate), Toast.LENGTH_SHORT).show();
        mAsyncTask = new CounterAsyncTask(this);
    }

    public void startAsyncTask() {
        if ((mAsyncTask == null) || (mAsyncTask.isCancelled())) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.msg_onstart), Toast.LENGTH_SHORT).show();
            mAsyncTask.execute(10);
        }
    }

    public void cancelAsyncTask() {
        if (mAsyncTask == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            mAsyncTask.cancel(true);
        }
    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show();
        mThreadsFragment.updateFragmentText(getString(R.string.done));
        mAsyncTask = null;
    }

    @Override
    public void onProgressUpdate(Integer integer) {
        mThreadsFragment.updateFragmentText(String.valueOf(integer));
    }

    @Override
    public void onCancel() {
        Toast.makeText(this, getString(R.string.msg_oncancel), Toast.LENGTH_SHORT).show();
    }

    /***
     //  IAsyncTaskEvent's methods - end
     ***/


    @Override
    protected void onDestroy() {
        if (mAsyncTask != null) {
            mAsyncTask.cancel(false);
            mAsyncTask = null;
        }
        super.onDestroy();
    }
}
