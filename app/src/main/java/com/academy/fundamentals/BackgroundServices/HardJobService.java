package com.academy.fundamentals.BackgroundServices;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;

import com.academy.fundamentals.R;

public class HardJobService extends Service {

    private static final String TAG = "HardJobService";

    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    private boolean isDestroyed=false;

    public HardJobService() {
    }


    @Override public void onCreate() {
        // To avoid cpu-blocking, we create a background handler to run our service
        HandlerThread thread = new HandlerThread(TAG, Process.THREAD_PRIORITY_BACKGROUND);
        // start the new handler thread
        thread.start();

        mServiceLooper = thread.getLooper();
        // start the service using the background handler
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        isDestroyed = false;
        showToast(getString(R.string.starting_hardjob_service_msg));

        // call a new service handler. The service ID can be used to identify the service
        Message message = mServiceHandler.obtainMessage();
        message.arg1 = startId;
        mServiceHandler.sendMessage(message);

        return START_STICKY;
    }

    protected void showToast(final String msg) {
        Intent intent = new Intent(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
        intent.putExtra(BGServiceActivity.BackgroundProgressReceiver.SERVICE_STATUS, msg);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Object responsible for
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override public void handleMessage(Message msg) {
            // Well calling mServiceHandler.sendMessage(message);
            // from onStartCommand this method will be called.

            // Add your cpu-blocking activity here
            for (int i = 0; i <= 100 && !isDestroyed; i++) {
                SystemClock.sleep(100);
                Intent intent = new Intent(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_UPDATE_ACTION);
                intent.putExtra(BGServiceActivity.BackgroundProgressReceiver.PROGRESS_VALUE_KEY, i);
                sendBroadcast(intent);
            }
            showToast(getString(R.string.finishing_hardjob_service_msg, msg.arg1));
            // the msg.arg1 is the startId used in the onStartCommand,
            // so we can track the running service here.
            stopSelf(msg.arg1);
        }
    }

    @Override public void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }


}
