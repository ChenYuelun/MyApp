package com.example.chenyuelun.myapp.view.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.utils.HttpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;

import okhttp3.Call;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public abstract class LoadingView extends FrameLayout {

    private View errorView;
    private View loadingView;
    private View successView;

    private static final int STATE_ERROR = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_SUCCESS = 2;

    private int current_state = STATE_LOADING;
    private LayoutParams params;

    public LoadingView(@NonNull Context context) {
        super(context);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        if (errorView == null) {
            errorView = UiUtils.inflate(R.layout.pager_error);
            this.addView(errorView, params);
        }
        if (loadingView == null) {
            loadingView = UiUtils.inflate(R.layout.pager_loading);
            this.addView(loadingView, params);
        }

        showSafeView();
    }

    private void showSafeView() {
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startShowView();
            }
        });
    }

    private void startShowView() {
        errorView.setVisibility(current_state == STATE_ERROR? View.VISIBLE:View.GONE);
        loadingView.setVisibility(current_state == STATE_LOADING ? View.VISIBLE : View.GONE);
        if (successView == null) {
            successView = getSuccessView();
            this.addView(successView, params);
        }
        successView.setVisibility(current_state == STATE_SUCCESS ? View.VISIBLE : View.GONE);
    }


    public void getData(){
        String url = getUrl();
        if (TextUtils.isEmpty(url)) {
            current_state = STATE_SUCCESS;
            showSafeView();
            return;
        }
        HttpUtils.getInstance().get(url, new HttpUtils.OnHttpListener() {
            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "网络请求成功");
                current_state = STATE_SUCCESS;
                showSafeView();
                setSeccessData(response);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "网络请求失败");
                current_state = STATE_ERROR;
                showSafeView();
            }
        });
    }


    public void getNewData(String url){

        HttpUtils.getInstance().get(url, new HttpUtils.OnHttpListener() {
            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "网络请求成功");
                setSeccessData(response);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "网络请求失败");
            }
        });
    }

    protected abstract void setSeccessData(String response);

    protected abstract String getUrl();

    protected abstract View getSuccessView();




}
