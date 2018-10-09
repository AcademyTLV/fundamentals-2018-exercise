package com.academy.fundamentals.BackgroundServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.academy.fundamentals.R;

import java.util.Locale;

import static com.academy.fundamentals.BackgroundServices.BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION;

public class BGServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mProgressValue;
    private BackgroundProgressReceiver mBackgroundProgressReceiver;
    private boolean mIsServiceStarted;
    private boolean mIsIntentServiceStarted;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgservice);

        mProgressValue = findViewById(R.id.tv_progress_value);
        findViewById(R.id.btn_start_service).setOnClickListener(this);
        findViewById(R.id.btn_start_intent_service).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeForProgressUpdates();
    }


    @Override
    public void onPause() {
        if(mBackgroundProgressReceiver != null) {
            unregisterReceiver(mBackgroundProgressReceiver);
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                if (mIsIntentServiceStarted) {
                    stopService(new Intent(this, HardJobIntentService.class));
                    mIsIntentServiceStarted = false;
                }
                if(!mIsServiceStarted) {
                    mIsServiceStarted = true;
                    startService(new Intent(this, HardJobService.class));
                }
                break;

            case R.id.btn_start_intent_service:
                if (mIsServiceStarted) {
                    stopService(new Intent(this, HardJobService.class));
                    mIsServiceStarted = false;
                }
                if (!mIsIntentServiceStarted) {
                    mIsIntentServiceStarted = true;
                    startService(new Intent(this, HardJobIntentService.class));
                }
                break;
        }
    }

    private void subscribeForProgressUpdates() {
        if (mBackgroundProgressReceiver == null) {
            mBackgroundProgressReceiver = new BackgroundProgressReceiver();
        }
        IntentFilter progressUpdateActionFilter = new IntentFilter(PROGRESS_UPDATE_ACTION);
        registerReceiver(mBackgroundProgressReceiver, progressUpdateActionFilter);
    }

    public class BackgroundProgressReceiver extends BroadcastReceiver {
        public static final String PROGRESS_UPDATE_ACTION = "PROGRESS_UPDATE_ACTION";
        public static final String PROGRESS_VALUE_KEY = "PROGRESS_VALUE_KEY";
        public static final String SERVICE_STATUS = "SERVICE_STATUS";

        @Override public void onReceive(Context context, Intent intent) {

            int progress = intent.getIntExtra(PROGRESS_VALUE_KEY, -1);
            if (progress >= 0) {
                String text;
                if (progress == 100) {
                    text = "Done!";
                    mIsIntentServiceStarted = false;
                    mIsServiceStarted = false;
                } else {
                    text = String.format(Locale.getDefault(), "%d%%", progress);
                }
                mProgressValue.setText(text);
            }

            String msg = intent.getStringExtra(SERVICE_STATUS);
            if(msg != null) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                mToast.show();
            }

        }
    }

    @Override
    public void onDestroy() {
        if (isFinishing()) {
            if (mIsIntentServiceStarted) {
                stopService(new Intent(this, HardJobIntentService.class));
            }
            if (mIsServiceStarted) {
                stopService(new Intent(this, HardJobService.class));
            }
            if (mToast != null) {
                mToast.cancel();
            }
        }
        super.onDestroy();
    }

}
