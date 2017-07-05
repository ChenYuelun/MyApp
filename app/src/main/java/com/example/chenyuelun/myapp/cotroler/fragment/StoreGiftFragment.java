package com.example.chenyuelun.myapp.cotroler.fragment;

import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreGiftFragment extends BaseFragment {
    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_type_store;

    }

    @Override
    public void initData() {
        tvTest.setText("StoreGiftFragment");
    }

}
