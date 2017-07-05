package com.example.chenyuelun.myapp.cotroler.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class ShareFragment extends BaseFragment {
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTitle() {
        tvTitle.setText("分享");
        ivTitleSearch.setVisibility(View.VISIBLE);
        ivTitleMenu.setVisibility(View.VISIBLE);
    }

}