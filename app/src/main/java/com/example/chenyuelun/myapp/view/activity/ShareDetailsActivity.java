package com.example.chenyuelun.myapp.view.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.ShareDetailBean;
import com.example.chenyuelun.myapp.modle.bean.ShareRecBean;
import com.example.chenyuelun.myapp.utils.HttpUtils;
import com.example.chenyuelun.myapp.view.adapter.ShareDetailRvAdapter;

import butterknife.BindView;
import okhttp3.Call;

public class ShareDetailsActivity extends BaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private String id;
    private ShareDetailRvAdapter shareDetailRvAdapter;

    @Override
    public void initData() {

        ShareRecBean.ListBean headData = (ShareRecBean.ListBean) getIntent().getSerializableExtra("bean");
        id = headData.getId();
        String type = headData.getType();
        Log.e("TAG", "type==" + type);
        shareDetailRvAdapter = new ShareDetailRvAdapter(this,headData);
        recyclerview.setAdapter(shareDetailRvAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerview.setLayoutManager(gridLayoutManager);
        getDataFromNet();
    }

    private void getDataFromNet() {
        HttpUtils.get(AppUrl.getShareDetailUrl(id), new HttpUtils.OnHttpListener() {
            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "不得姐详情页面数据请求成功");
                ShareDetailBean shareDetailBean = JSON.parseObject(response, ShareDetailBean.class);
                if(shareDetailBean != null) {
                    shareDetailRvAdapter.refresh(shareDetailBean);
                }
                refresh.finishRefresh();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "不得姐详情页面数据请求失败");
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_share_details;
    }


    @Override
    public void initListener() {
        super.initListener();
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataFromNet();
            }
        });
    }
}
