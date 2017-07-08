package com.example.chenyuelun.myapp.view.fragment.store;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.HomeBean;
import com.example.chenyuelun.myapp.view.activity.WebActivity;
import com.example.chenyuelun.myapp.view.adapter.HomeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreHomeFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    Unbinder unbinder;
    private HomeAdapter homeAdapteer;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_store;

    }

    //联网成功后重写的方法
    @Override
    protected void setData(String response) {
        Log.e("TAG", "首页数据获取成功" + response);
        refresh.finishRefresh();
        HomeBean homeBean = JSON.parseObject(response, HomeBean.class);
        if(homeBean != null) {
            List<HomeBean.DataBean.ItemsBean.ListBean> list = homeBean.getData().getItems().getList();
            if(list != null && list.size() > 0) {
                homeAdapteer.refresh(list);
            }
        }

    }

    @Override
    public void initData() {
        homeAdapteer = new HomeAdapter(getActivity());
        recyclerview.setAdapter(homeAdapteer);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public void initListener() {
        super.initListener();
        homeAdapteer.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(String topic_url, String topic_name, String content_id) {
                if(TextUtils.isEmpty(topic_url)) {
                    return;
                }
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("topic_url",topic_url);
                intent.putExtra("topic_name",topic_name);

                startActivity(intent);
            }

            
        });

        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataFromNet(AppUrl.HOME_URL);
            }
        });
    }

    //返回联网的地址
    @Override
    public String getUrl() {
        return AppUrl.HOME_URL;
    }


}
