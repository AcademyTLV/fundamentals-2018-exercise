package com.academy.fundamentals.BackgroundServices;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.academy.fundamentals.R;

public class HardJobIntentService extends IntentService {

    private static final String TAG = "HardJobIntentService";
    private boolean isDestroyed;


    public HardJobIntentService() {
        super(TAG);
    }

    @Override protected void onHandleIntent(@Nullable Intent intent) {
        isDestroyed = false;
        showToast(getString(R.string.starting_intent_service_msg));
        for (int i = 0; i <= 100 && !isDestroyed; i++) {
            SystemClock.sleep(100);
            Intent broadcastIntent = new Intent(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
            broadcastIntent.putExtra(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_VALUE_KEY, i);
            sendBroadcast(broadcastIntent);
        }
        showToast(getString(R.string.finishing_intent_service_msg));
    }

    protected void showToast(final String msg) {
        Intent intent = new Intent(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
        intent.putExtra(BGServiceActivity.BackgroundProgressReceiver.SERVICE_STATUS, msg);
        sendBroadcast(intent);
    }

    @Override public void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }

}
