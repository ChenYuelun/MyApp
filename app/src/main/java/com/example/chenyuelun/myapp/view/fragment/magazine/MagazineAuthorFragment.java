package com.example.chenyuelun.myapp.view.fragment.magazine;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.MagazineAuthorListBean;
import com.example.chenyuelun.myapp.view.activity.MagazineAuthorInfoActivity;
import com.example.chenyuelun.myapp.view.adapter.MgzAuthorLvAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/9.
 */

public class MagazineAuthorFragment extends BaseFragment {
    @BindView(R.id.lv_author)
    ListView lvAuthor;
    private MgzAuthorLvAdapter mgzAuthorLvAdapter;
    private List<MagazineAuthorListBean.DataBean.ItemsBean> items;

    @Override
    protected void setData(String response) {
        List<MagazineAuthorListBean.DataBean.ItemsBean> itemsBeanList = processData(response);
        if(itemsBeanList != null) {
            mgzAuthorLvAdapter.refrsh(itemsBeanList);
        }

    }

    private List<MagazineAuthorListBean.DataBean.ItemsBean> processData(String response) {
        MagazineAuthorListBean authorListBean = JSON.parseObject(response, MagazineAuthorListBean.class);
        if(authorListBean != null) {
            items = authorListBean.getData().getItems();
            return items;
        }
       return null;
    }


    @Override
    public void initListener() {
        super.initListener();
        lvAuthor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),MagazineAuthorInfoActivity.class);
                intent.putExtra("author_id",items.get(position).getAuthor_id());
                intent.putExtra("author_name",items.get(position).getAuthor_name());
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {
        mgzAuthorLvAdapter = new MgzAuthorLvAdapter(getActivity());
        lvAuthor.setAdapter(mgzAuthorLvAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_magazine_author;
    }

    @Override
    public String getUrl() {
        return AppUrl.MGZ_AUTHOR_LIST;
    }

}
