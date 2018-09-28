package com.academy.fundamentals.Threads;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.academy.fundamentals.R;


public class ThreadsActivityFragment extends Fragment implements View.OnClickListener{

    public final static String FRAGMENT_TYPE = "fragment_type";
    private Button mBtnCreate;
    private Button mBtnStart;
    private Button mBtnCancel;
    private TextView mTxtValue;

    private IAsyncTaskEvents callbackListener;

    public ThreadsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_threads, container, false);

        mBtnCreate = rootView.findViewById(R.id.btnAsyncCreate);
        mBtnStart = rootView.findViewById(R.id.btnAsyncStart);
        mBtnCancel = rootView.findViewById(R.id.btnAsyncCancel);
        mTxtValue = rootView.findViewById(R.id.fullscreen_content);

        mBtnCreate.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        //UNPACK OUR DATA FROM OUR BUNDLE
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String fragmentText = this.getArguments().getString(FRAGMENT_TYPE).toString();
            mTxtValue.setText(fragmentText);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity hostActivity = getActivity();
        if (hostActivity != null && hostActivity instanceof IAsyncTaskEvents) {
            callbackListener = (IAsyncTaskEvents)hostActivity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackListener = null;
   }



    @Override
    public void onClick(View v) {
        if(!isAdded() || callbackListener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.btnAsyncCreate:
                callbackListener.createAsyncTask();
                break;
            case R.id.btnAsyncStart:
                callbackListener.startAsyncTask();
                break;
            case R.id.btnAsyncCancel:
                callbackListener.cancelAsyncTask();
                break;
        }
    }

    public void updateFragmentText(String text) {
        if(mTxtValue != null) {
            mTxtValue.setText(text);
        }
    }
}
