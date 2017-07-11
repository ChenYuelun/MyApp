package com.example.chenyuelun.myapp.view.fragment.share;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.view.adapter.StoreFragmentVpAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class ShareFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tablyout)
    TabLayout tablyout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private List<BaseFragment> fragments;
    String[] titles= {"推荐","段子"};
    private StoreFragmentVpAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    protected void setData(String response) {

    }

    @Override
    public void initData() {
        initFragment();

        adapter = new StoreFragmentVpAdapter(getFragmentManager(), fragments,titles);
        viewpager.setAdapter(adapter);
        tablyout.setupWithViewPager(viewpager);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new ShareRecommendFragment());
        fragments.add(new ShareDuanziFragment());
    }

    @Override
    public void initTitle() {
        tvTitle.setText("分享");
    }

}
