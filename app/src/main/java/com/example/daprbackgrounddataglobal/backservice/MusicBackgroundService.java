package com.example.daprbackgrounddataglobal.backservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.daprbackgrounddataglobal.R;

public class MusicBackgroundService extends Service {
    private static final String TAG = "BackgroundSoundService";
    MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.samplemusic);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate() , service started...");

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
     //   UtilClass.Companion.notifyUI(getApplicationContext());
        player.start();
        return Service.START_STICKY;    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        Toast.makeText(this, "Service stopped...", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate() , service stopped...");
    }


}
