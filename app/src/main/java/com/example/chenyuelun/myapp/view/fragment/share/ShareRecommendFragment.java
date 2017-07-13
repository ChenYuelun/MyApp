package com.example.chenyuelun.myapp.view.fragment.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.ShareRecBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.activity.ShareDetailsActivity;
import com.example.chenyuelun.myapp.view.adapter.ShareRecRvAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/11.
 */

public class ShareRecommendFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private ShareRecRvAdapter shareRecRvAdapter;
    private List<ShareRecBean.ListBean> list;

    @Override
    protected void setData(String response) {
        ShareRecBean shareRecBean = JSON.parseObject(response, ShareRecBean.class);
        list = shareRecBean.getList();
        for(int i = 0; i < list.size(); i++) {
            String type = list.get(i).getType();
            Log.e("TAG", type);
        }
        if(list != null && list.size() > 0) {
            shareRecRvAdapter.refresh(list);

        }
        refresh.finishRefresh();
    }

    @Override
    public void initData() {
        shareRecRvAdapter = new ShareRecRvAdapter(getActivity());
        recyclerview.setAdapter(shareRecRvAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share_rec;
    }

    @Override
    public String getUrl() {
        return AppUrl.SHARE_REC;
    }

    @Override
    public void initListener() {
        super.initListener();
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataFromNet(AppUrl.SHARE_REC);
            }
        });


        shareRecRvAdapter.setOnItemDetailClickListener(new ShareRecRvAdapter.OnItemDetailClickListener() {
            @Override
            public void onItemDetailClicked(int position, ShareRecBean.ListBean listBean) {
                if(listBean.getType().equals("html")) {
                    UiUtils.showToast("这是html");
                }else {
                    Intent intent = new Intent(getActivity(),ShareDetailsActivity.class);
                    String id = list.get(position).getId();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", listBean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }
}
