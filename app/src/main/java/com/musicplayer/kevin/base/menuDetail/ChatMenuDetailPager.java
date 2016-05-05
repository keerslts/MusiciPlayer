package com.musicplayer.kevin.base.menuDetail;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.musicplayer.kevin.base.BaseMenuDetailPager;
import com.musicplayer.kevin.bean.ChatData;
import com.musicplayer.kevin.musiciplayer.R;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/5/4.
 */
public class ChatMenuDetailPager extends BaseMenuDetailPager
        implements ViewPager.OnPageChangeListener{

    private ArrayList<ChatData.ChatTabData> myChatTabData;
    private ViewPager mViewPager;

    public ChatMenuDetailPager(Activity activity,
                               ArrayList<ChatData.ChatTabData> children) {
        super(activity);
        myChatTabData = children;
    }

    @Override
    public View initViews() {
        View view = View.inflate(myActivity, R.layout.chat_menu_detail,null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_chat_menu_detail);

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
