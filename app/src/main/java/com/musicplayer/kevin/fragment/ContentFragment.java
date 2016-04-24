package com.musicplayer.kevin.fragment;

import android.support.v7.widget.ViewUtils;
import android.view.View;

import com.musicplayer.kevin.musiciplayer.R;

/**
 * Created by Kevin on 2016/4/24.
 */
public class ContentFragment extends BaseFragment{
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content,null);

        return view;
    }
}
