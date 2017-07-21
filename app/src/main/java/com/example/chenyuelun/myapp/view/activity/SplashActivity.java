package com.example.chenyuelun.myapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };

        if (isFirstOpenApp()) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
        } else {
            Glide.with(this)
                    .load(R.drawable.loading_start)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<Integer, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            // 计算动画时长
                            int duration = 0;
                            GifDrawable drawable = (GifDrawable) resource;
                            GifDecoder decoder = drawable.getDecoder();
                            for (int i = 0; i < drawable.getFrameCount(); i++) {
                                duration += decoder.getDelay(i);
                            }
                            //发送延时消息，通知动画结束
                            //以下两个参数都是 int 型，记得如上的声明
                            handler.sendEmptyMessageDelayed(1,
                                    duration);

                            return false;
                        }
                    }).into(new GlideDrawableImageViewTarget(ivSplash, 1));
        }

    }

    private boolean isFirstOpenApp() {

        return (boolean) SpUtils.getSpUtils().get(SpUtils.FIRST_OPEN, true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
