package com.musicplayer.kevin.musiciplayer.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.musicplayer.kevin.musiciplayer.R;
import com.musicplayer.kevin.musicplayer.global.GlobalContents;
import com.musicplayer.kevin.utils.PrefUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private final static int[] mImagesIds = new int[]{
            R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};
    private ViewPager viewPager;
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout linearPointGroup;
    private View viewRedPoint;
    private int redPointWidth;
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
        viewPager.addOnPageChangeListener(new GuideChangeListener());
    }

    private void initViews() {

        /**
         * 加载引导页的图片
         */
        mImageViewList = new ArrayList<ImageView>();
        for (int i = 0; i < mImagesIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImagesIds[i]);
            mImageViewList.add(image);
        }

        /**
         * 初始化引导页中的圆点（灰色底色及个数）
         */
        for (int i = 0; i < mImagesIds.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    10, 10);
            if (i > 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            linearPointGroup.addView(point);
        }

        /**
         * 计算两个圆点的间距
         */
        linearPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        linearPointGroup.getViewTreeObserver().
                                removeOnGlobalLayoutListener(this);
                        redPointWidth = linearPointGroup.getChildAt(1).getLeft()
                                - linearPointGroup.getChildAt(0).getLeft();
                        Log.i(GlobalContents.TAG, "距离: " + redPointWidth);
                    }
                }
        );
    }

    public void startMusic(View view) {
        PrefUtils.setBoolean(GuideActivity.this,"user_guide_showed",true);
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }


    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImagesIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
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

    class GuideChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            int length = (int) (positionOffset * redPointWidth + position * redPointWidth);
            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
            params.leftMargin = length;
            viewRedPoint.setLayoutParams(params);

        }

        @Override
        public void onPageSelected(int position) {

            if (position == mImagesIds.length - 1) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
