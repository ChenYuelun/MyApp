package com.example.chenyuelun.myapp.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.chenyuelun.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.vp_guide)
    ViewPager vpGuide;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        bind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
