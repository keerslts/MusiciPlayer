package com.musicplayer.kevin.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.musicplayer.kevin.base.BaseMenuDetailPager;
import com.musicplayer.kevin.base.BasePager;
import com.musicplayer.kevin.base.menuDetail.ChatMenuDetailPager;
import com.musicplayer.kevin.base.menuDetail.LookMenuDetailPager;
import com.musicplayer.kevin.base.menuDetail.ShakeMenuDetailPager;
import com.musicplayer.kevin.bean.ChatData;
import com.musicplayer.kevin.fragment.LeftFragment;
import com.musicplayer.kevin.musiciplayer.activity.MainActivity;
import com.musicplayer.kevin.musicplayer.global.GlobalContents;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/4/25.
 */
public class ChatPager extends BasePager {

    private ChatData myChatData;
    private ArrayList<BaseMenuDetailPager> detailPagers;

    public ChatPager(Activity myActivity) {
        super(myActivity);
    }

    @Override
    public void initData() {

        flContent.removeAllViews();

        tvTitle.setText("音乐吐槽");
        setSlidingMenuEnable(true);

//        TextView textView = new TextView(mActivity);
//        textView.setText("音乐吐槽11");
//        textView.setTextColor(Color.RED);
//        textView.setTextSize(25);
//        textView.setGravity(Gravity.CENTER);
//
//        flContent.addView(textView);

        getDataFromServer();
    }

    private void getDataFromServer() {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, GlobalContents.CATEGORIES_URL,
                new RequestCallBack<Object>() {

                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo) {

                        String result = (String) responseInfo.result;
                       // Log.i(GlobalContents.TAG, "chatPager:result " + result);
                        parseData(result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(mActivity, "请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void parseData(String result) {

        Gson gson = new Gson();
        myChatData = gson.fromJson(result, ChatData.class);


        //刷新侧边栏数据
        MainActivity mainUi = (MainActivity) mActivity;
        LeftFragment leftFragment = mainUi.getLeftFragment();
        leftFragment.setMenuData(myChatData);

        detailPagers = new ArrayList<BaseMenuDetailPager>();
        detailPagers.add(new ChatMenuDetailPager(mActivity,
                myChatData.data.get(0).children));
        detailPagers.add(new ShakeMenuDetailPager(mActivity,
                myChatData.data.get(1).children));
        detailPagers.add(new LookMenuDetailPager(mActivity,
                myChatData.data.get(2).children));

        setCurrentMenuDetailPager(1);
    }

    public void setCurrentMenuDetailPager(int position) {

        BaseMenuDetailPager baseMenuDetailPager = detailPagers.get(position);
        flContent.removeAllViews();
        flContent.addView(baseMenuDetailPager.myRootView);

        ChatData.ChatMenuData menuData = myChatData.data.get(position);
        tvTitle.setText(menuData.title);

        baseMenuDetailPager.initData();

    }
}
