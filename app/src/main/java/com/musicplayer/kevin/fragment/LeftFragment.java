package com.musicplayer.kevin.fragment;

import android.view.View;

import com.musicplayer.kevin.musiciplayer.R;

/**
 * Created by Kevin on 2016/4/24.
 */
public class LeftFragment extends BaseFragment{
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_left,null);

        return view;
    }
}
