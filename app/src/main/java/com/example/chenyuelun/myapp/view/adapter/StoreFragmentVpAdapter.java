package com.example.chenyuelun.myapp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreFragmentVpAdapter extends FragmentPagerAdapter {
    private final List<Fragment> pagers;
    String[] titles = {"分类", "品牌", "首页","专题", "礼物"};
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public StoreFragmentVpAdapter(FragmentManager fm, List<Fragment> fragmentPagers) {
        super(fm);
        this.pagers =fragmentPagers;
    }

    @Override
    public Fragment getItem(int position) {
        return pagers.get(position);
    }

    @Override
    public int getCount() {
        return pagers == null? 0 : pagers.size();
    }
}
