package com.example.chenyuelun.myapp.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.adapter.StoreFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreFragment extends BaseFragment {
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
    @BindView(R.id.tabview)
    TabLayout tabview;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    private List<Fragment> fragmentPagers = null;
    private StoreFragmentAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    protected void setData(String response) {

    }

    @Override
    public void initData() {
        initFragmentPagers();
        tabview.setupWithViewPager(vpMain);
        adapter = new StoreFragmentAdapter(getFragmentManager(), fragmentPagers);
        vpMain.setAdapter(adapter);
    }

    private void initFragmentPagers() {
        fragmentPagers = new ArrayList<>();
        fragmentPagers.add(new StoreTypeFragment());
        fragmentPagers.add(new StoreBrandFragment());
        fragmentPagers.add(new StoreHomeFragment());
        fragmentPagers.add(new StoreTopicFragment());
        fragmentPagers.add(new StoreGiftFragment());
    }


    @Override
    public void initTitle() {
        tvTitle.setText("商店");
        ivTitleSearch.setVisibility(View.VISIBLE);
        ivTitleCart.setVisibility(View.VISIBLE);
    }

    @Override
    public void initListener() {
        super.initListener();
        ivTitleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("搜索商品");
            }
        });

        ivTitleCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("购物车");
            }
        });
    }
}
