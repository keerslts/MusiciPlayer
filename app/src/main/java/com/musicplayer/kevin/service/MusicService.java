package com.musicplayer.kevin.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.musicplayer.kevin.base.impl.LocalMusicPager;
import com.musicplayer.kevin.inter.ControllerInterface;
import com.musicplayer.kevin.musiciplayer.activity.MainActivity;
import com.musicplayer.kevin.musicplayer.global.GlobalContents;

import java.io.IOException;
import java.util.Timer;

/**
 * Created by Kevin on 2016/4/26.
 */
public class MusicService extends Service {
    MediaPlayer player;
    private Timer timer;
    private static String change;

    public static void setChange(String change) {
        MusicService.change = change;
    }

    private String path;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();

        path = bundle.getString("path");
        Log.i(GlobalContents.TAG, "onBind:传来的intent值 " + path);
        return new MusicController();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
       // Log.i(GlobalContents.TAG, "here service ");
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if (change != path) {
                        Log.i(GlobalContents.TAG, "run: "+ change);
                        path = change;
                        play();
                    }

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
        player.release();

        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }


    class MusicController extends Binder implements ControllerInterface {
        @Override
        public void play() {
            MusicService.this.play();
        }

        @Override
        public void pause() {
            MusicService.this.pause();
        }

        @Override
        public void continuePlay() {
            MusicService.this.continuePlay();
        }

        @Override
        public void seekTo(int progress) {
            MusicService.this.seekTo(progress);
        }
    }

    private void seekTo(int progress) {
    }

    private void continuePlay() {
    }

    private void pause() {
    }

    private void play() {
        player.reset();



        try {
            player.setDataSource(path);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    addTimer();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }



        new Thread(){

            @Override
            public void run() {

                for(int i=0;i<100;i++) {
                    try {
                        sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = LocalMusicPager.handler.obtainMessage();
                    Bundle data = new Bundle();
                    data.putInt("progress", i);
                    msg.setData(data);
                    LocalMusicPager.handler.sendMessage(msg);

                }
            }
        }.start();

    }

    private void addTimer() {
    }
}
