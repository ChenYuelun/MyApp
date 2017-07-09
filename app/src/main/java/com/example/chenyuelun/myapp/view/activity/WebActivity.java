package com.example.chenyuelun.myapp.view.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.utils.UiUtils;

import butterknife.BindView;

public class WebActivity extends BaseActivity {

    @BindView(R.id.iv_title_search)
    ImageView ivTitleSearch;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_cart)
    ImageView ivTitleCart;
    @BindView(R.id.iv_title_menu)
    ImageView ivTitleMenu;
    @BindView(R.id.iv_title_faver)
    ImageView ivTitleFaver;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    public void initData() {
        Intent intent = getIntent();
        final String topic_url = intent.getStringExtra("topic_url");
        String topic_name = intent.getStringExtra("topic_name");
        tvTitle.setText(topic_name);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放


        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        webview.loadUrl(topic_url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ivTitleBack.setVisibility(View.VISIBLE);
        ivTitleFaver.setVisibility(View.VISIBLE);
        ivTitleShare.setVisibility(View.VISIBLE);

    }

    @Override
    public void initListener() {
        super.initListener();
        ivTitleShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("喜欢");
            }
        });
        ivTitleShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("分享");
            }
        });

        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
