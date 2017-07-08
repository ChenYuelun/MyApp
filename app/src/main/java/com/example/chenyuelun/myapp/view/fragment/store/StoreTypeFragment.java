package com.example.chenyuelun.myapp.view.fragment.store;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.StoreTypeBean;
import com.example.chenyuelun.myapp.view.activity.MainActivity;
import com.example.chenyuelun.myapp.view.adapter.StoreTypeAdapter;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreTypeFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView rvTypeStore;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private StoreTypeAdapter storeTypeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_type_store;

    }

    @Override
    protected void setData(String response) {
        Log.e("TAG", "StoreTypeFragment:response" + response);
        StoreTypeBean storeTypeBean = JSON.parseObject(response, StoreTypeBean.class);
        storeTypeAdapter.refresh(storeTypeBean);
        refresh.finishRefresh();
    }

    @Override
    public void initData() {
        storeTypeAdapter = new StoreTypeAdapter(getActivity());
        rvTypeStore.setAdapter(storeTypeAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvTypeStore.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void initListener() {
        super.initListener();
        storeTypeAdapter.setOnItemClickListener(new StoreTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StoreTypeBean.DataBean.ItemsBean itemsBean) {

                MainActivity mainActivity = (MainActivity) getActivity();
                StoreTypeDetailsFragment storeTypeDetailsFragment = new StoreTypeDetailsFragment(itemsBean.getCat_id());
                mainActivity.replaceFragment(storeTypeDetailsFragment);

            }
        });


        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataFromNet(AppUrl.STORE_TYPE_URL);
            }
        });




    }



    public String getUrl() {
        return AppUrl.STORE_TYPE_URL;
    }


    //生命周期
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("TAG1", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG1", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("TAG1", "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("TAG1", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("TAG1", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG1", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG1", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG1", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TAG1", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG1", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG1", "onDetach");
    }


}
