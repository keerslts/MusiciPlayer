package com.musicplayer.kevin.musiciplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.musicplayer.kevin.musiciplayer.R;
import com.musicplayer.kevin.utils.PrefUtils;

public class SplashActivity extends Activity {

    private ImageView imageView;
    private RelativeLayout rl_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = (ImageView) findViewById(R.id.iv_splash);
        rl_splash = (RelativeLayout) findViewById(R.id.rl_splash);

        startAnim();

    }


    /**
     * 设置闪屏动画效果
     * 旋转+放大+透明
     */
    private void startAnim() {

        //imageView.setAlpha(0.75f);

        //把两个效果合在一起
        AnimationSet set = new AnimationSet(true);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //设置时间
        rotateAnimation.setDuration(2000);
        //保持动画状态
        rotateAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.
                RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2500);
        scaleAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);

        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);


        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //rl_splash.setAlpha(1f);
                jumpGuidePage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView.startAnimation(set);
    }



    private void jumpGuidePage() {
        boolean userGuide = PrefUtils.getBoolean(this, "user_guide_showed", false);
        if (!userGuide) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }

        finish();
    }

}
