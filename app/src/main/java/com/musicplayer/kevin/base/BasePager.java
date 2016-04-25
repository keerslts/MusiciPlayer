package com.musicplayer.kevin.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.musicplayer.kevin.musiciplayer.R;
import com.musicplayer.kevin.musiciplayer.activity.MainActivity;


/**
 * Created by Kevin on 2016/4/25.
 */
public class BasePager {

    public View mBaseView;
    public Activity mActivity;
    public TextView tvTitle;
    public ImageButton btnMenu;
    public FrameLayout flContent;


    public BasePager(Activity myActivity) {

        mActivity = myActivity;
        initViews();
    }

    private void initViews() {

        mBaseView = View.inflate(mActivity, R.layout.base_pager,null);

        tvTitle = (TextView) mBaseView.findViewById(R.id.tv_content_title);
        btnMenu = (ImageButton) mBaseView.findViewById(R.id.btn_content_menu);
        flContent = (FrameLayout) mBaseView.findViewById(R.id.fl_content);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
    }

    private void toggleSlidingMenu() {

        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.toggle();
    }

    public void initData() {

    }

    public void setSlidingMenuEnable(boolean enable){
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();

        if(enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}
