package com.musicplayer.kevin.musiciplayer.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.musicplayer.kevin.musiciplayer.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private final static int[] mImagesIds = new int[]{
            R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};
    private ViewPager viewPager;
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout linearPointGroup;
    private View viewRedPoint;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        linearPointGroup = (LinearLayout)
                findViewById(R.id.linear_guide_pointGroup);
        viewRedPoint = findViewById(R.id.view_guide_redPoint);
        button = (Button) findViewById(R.id.btn_guide);
        initViews();
        viewPager.setAdapter(new GuideAdapter());

    }

    private void initViews() {

        mImageViewList = new ArrayList<ImageView>();

        for (int i = 0; i < mImagesIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImagesIds[i]);
            mImageViewList.add(image);
        }
    }


    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImagesIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
