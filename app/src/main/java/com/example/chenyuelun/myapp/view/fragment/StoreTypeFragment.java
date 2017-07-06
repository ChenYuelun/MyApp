package com.example.chenyuelun.myapp.view.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.StoreTypeBean;
import com.example.chenyuelun.myapp.view.adapter.StoreTypeAdapter;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreTypeFragment extends BaseFragment {

    @BindView(R.id.rv_type_store)
    RecyclerView rvTypeStore;
    private StoreTypeAdapter storeTypeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_type_store;

    }

    @Override
    protected void setData(String response) {
        Log.e("TAG", "StoreTypeFragment:response" + response);
        StoreTypeBean storeTypeBean = JSON.parseObject(response, StoreTypeBean.class);
        storeTypeAdapter = new StoreTypeAdapter(getActivity(), storeTypeBean);
        rvTypeStore.setAdapter(storeTypeAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rvTypeStore.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void initData() {

    }

    @Override
    public String getDataUrl() {
        return AppUrl.STORE_TYPE_URL;
    }
}
