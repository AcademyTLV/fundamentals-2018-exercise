package com.academy.fundamentals.download;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.academy.fundamentals.R;
import com.academy.fundamentals.model.MovieModel;

public class DownloadActivity extends AppCompatActivity {

    public static final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final int PERMISSIONS_REQUEST_CODE = 42;
    public static final String ARG_FILE_PATH = "Image-File-Path";
    private static final String ARG_MOVIE_MODEL = "Movie-Model-Data";
    private BroadcastReceiver broadcastReceiver;

    public static void startActivity(Context context, MovieModel movieModel) {
        Intent intent = new Intent(context, DownloadActivity.class);
        intent.putExtra(ARG_MOVIE_MODEL, movieModel);
        startActivity(context, intent);
    }

    private static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String filePath = intent.getStringExtra(ARG_FILE_PATH);
                Log.d("TAG", "DownloadActivity # onReceive, filePath: " + filePath);
                if (!TextUtils.isEmpty(filePath)) {
                    showImage(filePath);
                }
            }
        };

        if (isPermissionGranted()) {
            startDownloadService();
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // register local broadcast
        IntentFilter filter = new IntentFilter(DownloadService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    private void showImage(String filePath) {
        ImageView imageView = findViewById(R.id.download_iv_big_image);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        imageView.setImageBitmap(bitmap);
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, PERMISSION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)) {
            // Show an explanation to the user.
            // After the user sees the explanation, try again to request the permission.
            showExplainingRationaleDialog();
        } else {
            // No explanation needed, we can request the permission.
            requestWritePermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the contacts-related task you need to do.
                Log.d("TAG", "DownloadActivity # onRequestPermissionsResult, Permission granted");
                startDownloadService();
            } else {
                // permission denied, boo! Disable the functionality that depends on this permission.
                Log.d("TAG", "DownloadActivity # onRequestPermissionsResult, Permission denied");
            }
        }
    }

    private void requestWritePermission() {
        ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, PERMISSIONS_REQUEST_CODE);
        // PERMISSIONS_REQUEST_CODE is an app-defined int constant.
        // The callback method gets the result of the request.
    }

    private void showExplainingRationaleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.download_dialog_title);
        builder.setMessage(R.string.download_dialog_message);
        builder.setPositiveButton(R.string.download_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestWritePermission();
            }
        });
        builder.setNegativeButton(R.string.download_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // no Permission - finish activity
                DownloadActivity.this.finish();
            }
        });
        builder.create().show();
    }

    private void startDownloadService() {
        MovieModel movieModel = getIntent().getParcelableExtra(ARG_MOVIE_MODEL);
        Log.d("TAG", "DownloadActivity # onCreate, movieModel: " + movieModel);
        if (movieModel == null) return;

        Log.d("TAG", "DownloadActivity # onRequestPermissionsResult, startDownloadService");
        DownloadService.startService(this, movieModel.getBackImageUri());
    }
}
