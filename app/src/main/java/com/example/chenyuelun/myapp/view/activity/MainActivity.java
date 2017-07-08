package com.example.chenyuelun.myapp.view.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.view.fragment.daren.DaRenFragment;
import com.example.chenyuelun.myapp.view.fragment.magazine.MagazineFragment;
import com.example.chenyuelun.myapp.view.fragment.self.SelfFragment;
import com.example.chenyuelun.myapp.view.fragment.share.ShareFragment;
import com.example.chenyuelun.myapp.view.fragment.store.StoreFragment;
import com.example.chenyuelun.myapp.view.fragment.store.StoreTypeDetailsFragment;

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
        fragments.add(new DaRenFragment());
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


    private BaseFragment tempFragment;

    public void replaceFragment(StoreTypeDetailsFragment storeTypeDetailsFragment) {
        if (fragments.size() == 6) {
            fragments.remove(5);
        }
        fragments.add(storeTypeDetailsFragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_right_in,R.anim.fragment_left_out);
        ft.hide(fragments.get(0));
        ft.add(R.id.fl_main,storeTypeDetailsFragment).commit();
        preFragment = storeTypeDetailsFragment;
        tempFragment =preFragment;
    }

    public void BackFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_left_in,R.anim.fragment_right_out);
        ft.remove(fragments.get(5));
        ft.show(fragments.get(0));
        ft.commit();
        fragments.remove(5);
        preFragment = fragments.get(0);
        tempFragment = null;
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
            if(position == 0 && tempFragment != null ) {
                position = 5;
            }

            switchFragment(position);
        }
    }


    private void switchFragment(int position) {
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DaRenFragment daRenFragment = (DaRenFragment) fragments.get(2);
            PopupWindow popuWindow = daRenFragment.getPopuWindow();
            if (popuWindow != null && popuWindow.isShowing()) {
                daRenFragment.showOrCloseMenu();
                return true;
            }

            if(fragments.size() == 6) {
                BackFragment();
                return true;
            }


        }
        return super.onKeyDown(keyCode, event);

    }


}
