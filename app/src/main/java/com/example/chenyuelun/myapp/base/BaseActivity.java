package com.example.chenyuelun.myapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initView();
        initData();
        initListener();

    }

    public void initListener() {

    }

    public void initView() {

    }

    public abstract void initData();

    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
