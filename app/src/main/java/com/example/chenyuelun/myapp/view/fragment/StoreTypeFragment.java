package com.example.chenyuelun.myapp.view.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.StoreTypeBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
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
                mainActivity.addFragment(storeTypeDetailsFragment);
                mainActivity.switchFragment(5);
                UiUtils.showToast(itemsBean.getCat_name());
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


}
