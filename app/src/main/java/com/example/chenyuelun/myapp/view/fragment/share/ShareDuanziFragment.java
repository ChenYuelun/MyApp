package com.example.chenyuelun.myapp.view.fragment.share;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.ShareDzBean;
import com.example.chenyuelun.myapp.view.adapter.ShareDzRvAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/11.
 */

public class ShareDuanziFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private ShareDzRvAdapter shareDzRvAdapter;
    private List<ShareDzBean.ListBean> list;

    @Override
    protected void setData(String response) {
        ShareDzBean shareDzBean = JSON.parseObject(response, ShareDzBean.class);
        if(shareDzBean != null) {
            list = shareDzBean.getList();
            if(list != null && list.size() > 0) {
                shareDzRvAdapter.refresh(list);
            }
        }
        refresh.finishRefresh();
    }

    @Override
    public void initData() {
        shareDzRvAdapter = new ShareDzRvAdapter(getActivity());
        recyclerview.setAdapter(shareDzRvAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share_dz;
    }

    @Override
    public void initListener() {
        super.initListener();
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataFromNet(AppUrl.SHARE_DZ);
            }
        });


        shareDzRvAdapter.setOnItemTextClickListener(new ShareDzRvAdapter.OnItemTextClickListener() {
            @Override
            public void onTextClicked(int position) {
                Intent intent = new Intent(getActivity(),ShareDetailsActivity.class);
                String id = list.get(position).getId();
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    @Override
    public String getUrl() {
        return AppUrl.SHARE_DZ;
    }
}
