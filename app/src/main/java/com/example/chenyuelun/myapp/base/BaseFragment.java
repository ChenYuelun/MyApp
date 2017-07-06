package com.example.chenyuelun.myapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chenyuelun.myapp.view.view.LoadingView;

import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public abstract class BaseFragment extends Fragment {
    private LoadingView loadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getLayoutId() == 0) {
            TextView textView = new TextView(getActivity());
            textView.setText("布局编号请不要返回0");
            return textView;
        }
        loadingView = new LoadingView(getContext()) {
            @Override
            protected void setSeccessData(String response) {
                setData(response);
            }

            @Override
            protected String getUrl() {
                return getDataUrl();
            }

            @Override
            protected View getSuccessView() {
                View view = View.inflate(getActivity(),getLayoutId(),null);
                ButterKnife.bind(BaseFragment.this,view);
                return view;
            }
        };




        return loadingView;

    }

    protected abstract void setData(String response);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitle();
        initView();
        initData();
        initListener();
        if(getLayoutId() != 0) {
            loadingView.getData();
        }

    }

    public void initListener() {

    }

    public abstract void initData();

    public void initView() {

    }

    public void initTitle() {

    }


    public String getDataUrl() {
        return "";
    }


    public abstract int getLayoutId();
}
