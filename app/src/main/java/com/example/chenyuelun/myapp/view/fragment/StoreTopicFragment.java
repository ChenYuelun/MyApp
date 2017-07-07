package com.example.chenyuelun.myapp.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.TopicBean;
import com.example.chenyuelun.myapp.view.activity.WebActivity;
import com.example.chenyuelun.myapp.view.adapter.TopicAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreTopicFragment extends BaseFragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private TopicAdapter topicAdapter;
    private List<TopicBean.DataBean.ItemsBean> datas;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_topic_store;

    }

    @Override
    protected void setData(String response) {
        TopicBean topicBean = JSON.parseObject(response, TopicBean.class);
        if(topicBean != null) {
            List<TopicBean.DataBean.ItemsBean> items = topicBean.getData().getItems();
            if(items != null  && items.size() > 0) {
                if(isGetMore) {
                    this.datas.addAll(items);
                    refresh.finishRefreshLoadMore();
                }else {
                    this.datas =items;
                    refresh.finishRefresh();
                }
                topicAdapter.refresh(datas);
            }



        }

    }

    @Override
    public void initData() {
        topicAdapter = new TopicAdapter(getActivity());
        recyclerview.setAdapter(topicAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public String getUrl() {
        return AppUrl.TOPIC_URL;
    }

    @Override
    public void initListener() {
        super.initListener();
        topicAdapter.setOnItemClickListener(new TopicAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(String topic_url, String topic_name) {
                if(!TextUtils.isEmpty(topic_url)) {
                    Intent intent = new Intent(getActivity(),WebActivity.class);
                    intent.putExtra("topic_url",topic_url);
                    intent.putExtra("Topic_name",topic_name);

                    startActivity(intent);
                }
            }
        });

        refresh.setLoadMore(true);
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }



            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                getMoreData();
            }
        });
    }
    private boolean isGetMore = false;
    private int page = 1;
    private void getMoreData() {
        isGetMore = true;
        getDataFromNet(AppUrl.getTopicUrl(++page));
    }

    private void refreshData() {
        isGetMore = false;
        getDataFromNet(AppUrl.TOPIC_URL);
    }
}
