package com.example.chenyuelun.myapp.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.BrandBean;
import com.example.chenyuelun.myapp.view.adapter.BrandListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreBrandFragment extends BaseFragment {


    @BindView(R.id.lv_brand)
    ListView lvBrand;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    Unbinder unbinder;
    private BrandListAdapter brandListAdapter;
    private List<BrandBean.DataBean.ItemsBean> datas;
    private boolean isGetMore =false;
    private int moreDataPager = 1;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_brand_store;

    }

    @Override
    protected void setData(String response) {
        BrandBean brandBean = JSON.parseObject(response, BrandBean.class);
        List<BrandBean.DataBean.ItemsBean> itemsBeanList = brandBean.getData().getItems();
        if (itemsBeanList != null  && itemsBeanList.size() > 0) {
            if(isGetMore) {
                this.datas.addAll(itemsBeanList);
                refresh.finishRefreshLoadMore();
            }else {
                this.datas = itemsBeanList;
                refresh.finishRefresh();
            }

            brandListAdapter.refresh(datas);
        }

    }

    @Override
    public void initData() {
        brandListAdapter = new BrandListAdapter(getActivity());
        lvBrand.setAdapter(brandListAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        refresh.setLoadMore(true);
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //上拉加载更多
                getMoreData();
            }
        });


        lvBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BrandInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getMoreData() {
        isGetMore = true;
        getDataFromNet(AppUrl.getBrandUrl(++moreDataPager));
    }

    private void refreshData() {
        isGetMore = false;
        getDataFromNet(AppUrl.getBrandUrl(1));
    }

    @Override
    public String getUrl() {
        return AppUrl.getBrandUrl(1);
    }


    //父类方法 加载更多或者刷新时调用获取数据
    @Override
    public void getDataFromNet(String url) {
        super.getDataFromNet(url);
    }
}
