package com.musicplayer.kevin.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.musicplayer.kevin.base.BasePager;
import com.musicplayer.kevin.base.impl.ChatPager;
import com.musicplayer.kevin.base.impl.LocalMusicPager;
import com.musicplayer.kevin.base.impl.NetMusicPager;
import com.musicplayer.kevin.musiciplayer.R;

import java.util.ArrayList;


/**
 * Created by Kevin on 2016/4/24.
 */
public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.rg_content)
    private RadioGroup radioGroup;
    @ViewInject(R.id.noScroll_vp_content)
    private ViewPager mViewPager;

    private ArrayList<BasePager> mPagerList;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        radioGroup.check(R.id.rb_localMusic);
        mPagerList = new ArrayList<BasePager>();

        mPagerList.add(new LocalMusicPager(mActivity));
        mPagerList.add(new NetMusicPager(mActivity));
        mPagerList.add(new ChatPager(mActivity));

        mViewPager.setAdapter(new ContentAdapter());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_localMusic:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_netMusic:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_chat:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    default:
                        break;
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mPagerList.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPagerList.get(0).initData();
    }

    class ContentAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            BasePager basePager = mPagerList.get(position);
            container.addView(basePager.mBaseView);

            return basePager.mBaseView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
