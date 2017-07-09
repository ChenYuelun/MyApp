package com.example.chenyuelun.myapp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.chenyuelun.myapp.base.BaseFragment;

import java.util.List;

/**
 * Created by chenyuelun on 2017/7/9.
 */

public class MagazineVpAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    String tab[] = {"分类","作者"};

    public MagazineVpAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }




    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tab[position];
    }
}
