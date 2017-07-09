package com.example.chenyuelun.myapp.view.fragment.magazine;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.MagazineAuthorListBean;
import com.example.chenyuelun.myapp.view.activity.MagazineAuthorInfoActivity;
import com.example.chenyuelun.myapp.view.adapter.MgzCategoryAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/9.
 */

public class MagazineCategoryFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private MgzCategoryAdapter mgzCategoryAdapter;

    @Override
    protected void setData(String response) {
        MagazineAuthorListBean magazineAuthorListBean = JSON.parseObject(response, MagazineAuthorListBean.class);
        List<MagazineAuthorListBean.DataBean.ItemsBean> items = magazineAuthorListBean.getData().getItems();
        if(items !=null && items.size()>0) {
            mgzCategoryAdapter.refresh(items);

        }
    }

    @Override
    public void initData() {
        mgzCategoryAdapter = new MgzCategoryAdapter(getActivity());
        recyclerview.setAdapter(mgzCategoryAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

    @Override
    public int getLayoutId() {
        return R.layout.ftagment_magazine_category;
    }

    @Override
    public String getUrl() {
        return AppUrl.MGZ_AUTHOR_LIST;
    }

    @Override
    public void initListener() {
        super.initListener();
        mgzCategoryAdapter.setOnItemCliclkListener(new MgzCategoryAdapter.OnItemCliclkListener() {
            @Override
            public void onItemClicked(int position, MagazineAuthorListBean.DataBean.ItemsBean itemsBean) {
                Intent intent = new Intent(getActivity(),MagazineAuthorInfoActivity.class);
                intent.putExtra("author_id",itemsBean.getAuthor_id());
                intent.putExtra("author_name",itemsBean.getAuthor_name());
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
