package com.example.chenyuelun.myapp.cotroler.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.cotroler.fragment.BigGunFragment;
import com.example.chenyuelun.myapp.cotroler.fragment.MagazineFragment;
import com.example.chenyuelun.myapp.cotroler.fragment.ShareFragment;
import com.example.chenyuelun.myapp.cotroler.fragment.StoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private List<BaseFragment> fragments;
    private BaseFragment preFragment = null;
    private int position = 0;

    @Override
    public void initData() {
        initFragments();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new StoreFragment());
        fragments.add(new MagazineFragment());
        fragments.add(new BigGunFragment());
        fragments.add(new ShareFragment());
    }

    @Override
    public void initListener() {
        super.initListener();
        rgMain.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rgMain.check(R.id.rb_main_store);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rb_main_store:
                    position = 0;
                    break;
                case R.id.rb_main_magazine:
                    position = 1;
                    break;
                case R.id.rb_main_biggun:
                    position = 2;
                    break;
                case R.id.rb_main_share:
                    position = 3;
                    break;
            }

            BaseFragment currentFragment = fragments.get(position);
            switchFragment(currentFragment);
        }
    }

    private void switchFragment(BaseFragment currentFragment) {
        if (currentFragment != preFragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!currentFragment.isAdded()) {
                if (preFragment != null) {
                    ft.hide(preFragment);
                }
                ft.add(R.id.fl_main,currentFragment);
            }else {
                ft.hide(preFragment);
                ft.show(currentFragment);
            }
            ft.commit();
            preFragment = currentFragment;
        }
    }
}
