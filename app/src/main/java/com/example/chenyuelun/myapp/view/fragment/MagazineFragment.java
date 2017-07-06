package com.example.chenyuelun.myapp.view.fragment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class MagazineFragment extends BaseFragment {
    @BindView(R.id.iv_title_search)
    ImageView ivTitleSearch;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_cart)
    ImageView ivTitleCart;
    @BindView(R.id.iv_title_menu)
    ImageView ivTitleList;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_magazine;
    }

    @Override
    protected void setData(String response) {

    }

    @Override
    public void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initTitle() {
        tvTitle.setText("杂志");
//        Drawable drawable= getResources().getDrawable(R.drawable.abc_spinner,null);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        tvTitle.setCompoundDrawables(null,null,drawable,null);
    }

}
