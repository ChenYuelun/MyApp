package com.example.chenyuelun.myapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chenyuelun.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.loading_start).into(ivSplash);

        if(isFirstOpenApp()) {

        }else {
           countDown();
        }

    }

    private void countDown() {
        timer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                finish();
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }

    private boolean isFirstOpenApp() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
