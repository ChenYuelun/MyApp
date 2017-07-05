package com.example.chenyuelun.myapp.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),getLayoutId(),null);
        bind = ButterKnife.bind(this, view);
        return view;

    }

    public abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitle();
        initView();
        initData();
        initListener();

    }

    public void initListener() {
    }

    public void initView() {

    }

    public abstract void initData();

    public  void initTitle(){};


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
