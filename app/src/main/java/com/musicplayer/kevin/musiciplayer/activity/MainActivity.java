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
import android.widget.Toast;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.musicplayer.kevin.fragment.ContentFragment;
import com.musicplayer.kevin.fragment.LeftFragment;
import com.musicplayer.kevin.inter.ControllerInterface;
import com.musicplayer.kevin.musiciplayer.R;
import com.musicplayer.kevin.musicplayer.global.GlobalContents;
import com.musicplayer.kevin.service.MusicService;
import com.musicplayer.kevin.utils.LoadMusic;


/**
 * Created by Kevin on 2016/4/24.
 */
public class MainActivity extends SlidingFragmentActivity{

    public ControllerInterface ci;




    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // Log.i(GlobalContents.TAG, "endflag  outside: "+LoadMusic.getEnd_flag());


        while(LoadMusic.getEnd_flag()==0){
            Toast.makeText(MainActivity.this,"正在加载本地音乐，请稍后！",Toast.LENGTH_SHORT);
            Log.i(GlobalContents.TAG, "toast");

        }

        //设置左侧边栏和布局文件绑定
        setBehindContentView(R.layout.left_menu);
        //创建左侧边栏
        SlidingMenu slidingMenu = getSlidingMenu();
        //设置全屏滑动唤出侧边栏
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置侧边栏伸出长度
        slidingMenu.setBehindOffset(500);


        initFragment();



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


}
