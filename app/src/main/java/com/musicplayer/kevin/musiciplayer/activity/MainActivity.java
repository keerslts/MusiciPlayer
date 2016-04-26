package com.musicplayer.kevin.musiciplayer.activity;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.musicplayer.kevin.fragment.ContentFragment;
import com.musicplayer.kevin.fragment.LeftFragment;
import com.musicplayer.kevin.inter.ControllerInterface;
import com.musicplayer.kevin.musiciplayer.R;
import com.musicplayer.kevin.musicplayer.global.GlobalContents;
import com.musicplayer.kevin.service.MusicService;




/**
 * Created by Kevin on 2016/4/24.
 */
public class MainActivity extends SlidingFragmentActivity{

//    private static SeekBar sk_bar;
//    public ControllerInterface ci;
//    public String path;


//    public static Handler handler = new Handler(){
//        public void handleMessage(Message message){
//            Bundle bundle = message.getData();
//            int duration = bundle.getInt("duration");
//            int currentPosition = bundle.getInt("currentPosition");
//            sk_bar.setMax(duration);
//            sk_bar.setProgress(currentPosition);
//        }
//    };


    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置左侧边栏和布局文件绑定
        setBehindContentView(R.layout.left_menu);
        //创建左侧边栏
        SlidingMenu slidingMenu = getSlidingMenu();
        //设置全屏滑动唤出侧边栏
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置侧边栏伸出长度
        slidingMenu.setBehindOffset(500);

        initFragment();

        //sk_bar = (SeekBar) findViewById(R.id.music_seekBar);

//        sk_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                //获取sk_bar的当前进度，然后设置给音乐服务的播放进度
//                ci.seekTo(seekBar.getProgress());
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

//        Intent intent = new Intent(this,MusicService.class);
//
//        intent.putExtra("path",path);
//        startService(intent);
//        bindService(intent, new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                ci = (ControllerInterface) service;
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        }, BIND_AUTO_CREATE);

    }

    private void initFragment() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.activity_main,
                new ContentFragment(), FRAGMENT_CONTENT);
        fragmentTransaction.replace(R.id.left_menu,
                new LeftFragment(),FRAGMENT_LEFT_MENU);

        fragmentTransaction.commit();
    }

//    public void play(View v){
//        ci.play();
//    }
//    public void pause(View v){
//        ci.pause();
//    }
//    public void continuePlay(View v){
//        ci.continuePlay();
//    }

}
