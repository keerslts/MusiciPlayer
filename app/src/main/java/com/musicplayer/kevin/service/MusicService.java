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
import java.util.TimerTask;

/**
 * Created by Kevin on 2016/4/26.
 */
public class MusicService extends Service {
    MediaPlayer player;
    private Timer timer;
    private static String change;
    private static int flag_run = 1;

    public static int getFlag_run() {
        return flag_run;
    }

    public static void setFlag_run(int flag_run) {
        MusicService.flag_run = flag_run;
    }

    public static void setChange(String change) {
        MusicService.change = change;
    }

    private String path;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Bundle bundle = intent.getExtras();

        path = bundle.getString("path");
       // Log.i(GlobalContents.TAG, "onBind:传来的intent值 " + path);
        return new MusicController();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        // Log.i(GlobalContents.TAG, "here service ");
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (change != path) {
                        Log.i(GlobalContents.TAG, "run: " + change);
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

        if (timer != null) {
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
        player.seekTo(progress);
    }

    private void continuePlay() {
        flag_run++;
        Log.i(GlobalContents.TAG, "conti: " + flag_run);
        player.start();

    }

    private void pause() {
        flag_run++;
        Log.i(GlobalContents.TAG, "pause: " + flag_run);
        player.pause();

    }

    private void play() {
        flag_run = 1;
        Log.i(GlobalContents.TAG, "play: " + flag_run);
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

    }

    private void addTimer() {
        if (timer == null) {
            timer = new Timer();
            //设计计时任务
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //获取播放总时长
                    int duration = player.getDuration();
                    //获取当前播放进度
                    int currentPosition = player.getCurrentPosition();

                    Message msg = LocalMusicPager.handler.obtainMessage();

                    //把数据封装至消息对象
                    Bundle data = new Bundle();
                    data.putInt("duration", duration);
                    data.putInt("currentPosition", currentPosition);
                    msg.setData(data);

                    LocalMusicPager.handler.sendMessage(msg);
                }
            }, 5, 500);//计时任务开始5毫秒后，run方法执行，每500毫秒执行一次
        }
    }
}
