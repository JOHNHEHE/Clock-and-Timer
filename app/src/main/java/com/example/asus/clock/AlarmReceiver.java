package com.example.asus.clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;

import java.io.IOException;

/**
 * Created by ASUS on 2018/2/22.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(10000);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        audioManager.setStreamVolume(AudioManager.RINGER_MODE_NORMAL, 5, 0);
        String p=intent.getStringExtra("raw");
        if(p.equals("a")) {
            MediaPlayer mp=MediaPlayer.create(context,R.raw.watch);
            mp.start();
        }
        if(p.equals("b")) {
            MediaPlayer mp=MediaPlayer.create(context,R.raw.tidal);
            mp.start();
        }
        if(p.equals("c")) {
            MediaPlayer mp=MediaPlayer.create(context,R.raw.runaway);
            mp.start();
        }
        if(p.equals("d")) {
            MediaPlayer mp=MediaPlayer.create(context,R.raw.watch);
            mp.start();
        }
    }
}
