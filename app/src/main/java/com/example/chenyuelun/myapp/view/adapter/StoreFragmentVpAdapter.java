package com.example.chenyuelun.myapp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.chenyuelun.myapp.base.BaseFragment;

import java.util.List;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreFragmentVpAdapter extends FragmentPagerAdapter {
    private final List<BaseFragment> pagers;
    String[] titles;
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public StoreFragmentVpAdapter(FragmentManager fm, List<BaseFragment> fragmentPagers, String[] titles) {
        super(fm);
        this.pagers =fragmentPagers;
        this.titles = titles;
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
