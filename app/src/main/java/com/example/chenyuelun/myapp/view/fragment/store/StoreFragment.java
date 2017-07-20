package com.example.chenyuelun.myapp.view.fragment.store;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.utils.SpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.activity.LoginActivity;
import com.example.chenyuelun.myapp.view.activity.ShoppingCratActivity;
import com.example.chenyuelun.myapp.view.adapter.StoreFragmentVpAdapter;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreFragment extends BaseFragment {
    @BindView(R.id.iv_title_search)
    ImageView ivTitleSearch;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_cart)
    ImageView ivTitleCart;
    @BindView(R.id.tabview)
    TabLayout tabview;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    private List<BaseFragment> fragmentPagers = null;
    private StoreFragmentVpAdapter adapter;
    String[] titles = {"分类", "品牌", "首页","专题", "礼物"};

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
        adapter = new StoreFragmentVpAdapter(getFragmentManager(), fragmentPagers,titles);
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
                customScan();

            }
        });

        ivTitleCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean islogin = (boolean) SpUtils.getSpUtils().get(SpUtils.IS_LOGIN, false);
                if (islogin) {
                    UiUtils.showToast("购物车");
                    startActivity(new Intent(getActivity(), ShoppingCratActivity.class));
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    public void customScan() {
        new IntentIntegrator(getActivity())
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }



}
