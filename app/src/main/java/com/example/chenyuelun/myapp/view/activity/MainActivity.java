package com.example.chenyuelun.myapp.view.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.view.fragment.BigGunFragment;
import com.example.chenyuelun.myapp.view.fragment.MagazineFragment;
import com.example.chenyuelun.myapp.view.fragment.SelfFragment;
import com.example.chenyuelun.myapp.view.fragment.ShareFragment;
import com.example.chenyuelun.myapp.view.fragment.StoreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private List<BaseFragment> fragments;
    private BaseFragment preFragment = null;
    private int position = 0;
    private BaseFragment currentFragment;

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
        fragments.add(new SelfFragment());
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

    public BaseFragment getcurrentFragment() {
        return currentFragment;
    }

    public void addFragment(BaseFragment baseFragment) {
        if(fragments.size() == 6) {
            fragments.remove(5);
        }
        fragments.add(baseFragment);
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
                case R.id.rb_main_myselef:
                    position = 4;
                    break;
            }


            switchFragment(position);
        }
    }

    public void switchFragment(int position) {
        this.position = position;
        currentFragment = fragments.get(position);
        if (currentFragment != preFragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (!currentFragment.isAdded()) {
                if (preFragment != null) {
                    ft.hide(preFragment);
                }
                ft.add(R.id.fl_main, currentFragment);
            } else {
                ft.hide(preFragment);
                ft.show(currentFragment);
            }
            ft.commit();
            preFragment = currentFragment;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {

            if(position != 0) {
                rgMain.check(R.id.rb_main_store);
                position = 0;
                switchFragment(position);
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);

    }
}
