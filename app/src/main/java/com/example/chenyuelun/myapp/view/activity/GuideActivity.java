package com.example.chenyuelun.myapp.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.utils.SpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    @BindView(R.id.bt_open)
    Button btOpen;
    private Unbinder bind;

    int[] ids = {R.drawable.feature1, R.drawable.feature2, R.drawable.feature3, R.drawable.feature4, R.drawable.feature5};
    private CountDownTimer timer;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        bind = ButterKnife.bind(this);
        vpGuide.setAdapter(new MyPagerAdapter());
        UiUtils.controlViewPagerSpeed(this, vpGuide, 300);//设置你想要的时间
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == ids.length - 1) {
                    timer = new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            btOpen.setVisibility(View.VISIBLE);
                        }
                    }.start();

                }else {
                    btOpen.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtils.getSpUtils().put(SpUtils.FIRST_OPEN,false);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        timer.cancel();
        timer = null;
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(ids[position]);
            ViewGroup.LayoutParams parmas = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(parmas);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
