package com.example.chenyuelun.myapp.view.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private List<BaseFragment> fragments;
    private BaseFragment preFragment = null;
    private int position = 0;
    private BaseFragment currentFragment;
    private StoreFragment storeFragment;
    private EventHandler eventHandler;
    private AlertDialog exitDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        initFragments();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        storeFragment = new StoreFragment();
        fragments.add(storeFragment);
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

        tempFragment = storeTypeDetailsFragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_right_in, R.anim.fragment_left_out);
        ft.hide(preFragment);
        ft.add(R.id.fl_main, tempFragment).commit();

    }

    public void BackFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_left_in,R.anim.fragment_right_out);
        ft.show(fragments.get(0));
        ft.remove(tempFragment);
        ft.commit();
        tempFragment = null;
        preFragment = fragments.get(0);

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

            Log.e("TAG1", "Position" + position);
            switchFragment(position);
        }
    }


    private void switchFragment(int position) {
        currentFragment = fragments.get(position);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(tempFragment != null  && position != 0) {
            if(tempFragment.isAdded()) {
                ft.hide(tempFragment);
            }
        }

        if (position == 0 && tempFragment != null) {
            if (tempFragment.isAdded()) {
                ft.hide(preFragment);
                ft.show(tempFragment);
                ft.commit();
                preFragment = tempFragment;
                return;
            }
        }
        if (currentFragment != preFragment) {

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

            if (position == 0 && tempFragment != null) {
                BackFragment();
                return true;
            }

            if(exitDialog != null && exitDialog.isShowing()) {
                exitDialog.dismiss();
                exitDialog = null;
                return true;
            }
            showExitDialog();
            return true;


        }
        return super.onKeyDown(keyCode, event);

    }

    private void showExitDialog() {
        if(exitDialog == null) {
            LinearLayout view  = (LinearLayout) View.inflate(this, R.layout.exit_dialog,null);
            LinearLayout view2 = (LinearLayout) view.findViewById(R.id.ll_view2);
            Button confirm = (Button) view2.findViewById(R.id.bt_comfirm);
            Button cancel = (Button) view2.findViewById(R.id.bt_cancel);
            exitDialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .show();
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDestroy();
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exitDialog.dismiss();
                    exitDialog = null;
                }
            });
        }



    }


    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
