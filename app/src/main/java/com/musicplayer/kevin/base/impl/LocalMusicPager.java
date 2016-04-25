package com.musicplayer.kevin.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.musicplayer.kevin.base.BasePager;

/**
 * Created by Kevin on 2016/4/25.
 */
public class LocalMusicPager extends BasePager {
    public LocalMusicPager(Activity myActivity) {
        super(myActivity);
    }


    @Override
    public void initData() {
        tvTitle.setText("本地音乐");
        setSlidingMenuEnable(true);

        initView();
        
        TextView textView = new TextView(mActivity);
        textView.setText("本地音乐11");
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);

        flContent.addView(textView);
    }

    private void initView() {
    }
}
