package com.example.chenyuelun.myapp.view.fragment.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.ShareRecBean;
import com.example.chenyuelun.myapp.view.adapter.ShareRecRvAdapter;

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

    private List<ShareRecBean.ListBean> list;
    private ShareRecRvAdapter shareDzRvAdapter;


    @Override
    protected void setData(String response) {
        ShareRecBean shareRecBean = JSON.parseObject(response, ShareRecBean.class);
        if(shareRecBean != null) {
            list = shareRecBean.getList();
            if(list != null && this.list.size() > 0) {
                shareDzRvAdapter.refresh(this.list);
            }
        }
        refresh.finishRefresh();
    }

    @Override
    public void initData() {
        shareDzRvAdapter = new ShareRecRvAdapter(getActivity());
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

        shareDzRvAdapter.setOnItemDetailClickListener(new ShareRecRvAdapter.OnItemDetailClickListener() {
            @Override
            public void onItemDetailClicked(int position, ShareRecBean.ListBean listBean) {
                Intent intent = new Intent(getActivity(),ShareDetailsActivity.class);
                String id = list.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", listBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }

    @Override
    public String getUrl() {
        return AppUrl.SHARE_DZ;
    }
}
