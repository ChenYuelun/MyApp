package com.example.chenyuelun.myapp.view.fragment.daren;

import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.DaRenBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.adapter.DaRenAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class DaRenFragment extends BaseFragment {
    @BindView(R.id.iv_title_search)
    ImageView ivTitleSearch;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_cart)
    ImageView ivTitleCart;
    @BindView(R.id.iv_title_menu)
    ImageView ivTitleMenu;
    @BindView(R.id.iv_title_faver)
    ImageView ivTitleFaver;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;

    private int currentState = AppUrl.DAREN_DEFAULT;
    private DaRenAdapter daRenAdapter;
    private List<DaRenBean.DataBean.ItemsBean> datas;
    private PopupWindow popupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_daren;
    }

    @Override
    protected void setData(String response) {
        Log.e("TAG", "达人数据获取成功" + response);
        DaRenBean daRenBean = JSON.parseObject(response, DaRenBean.class);
        if (daRenBean != null) {
            List<DaRenBean.DataBean.ItemsBean> items = daRenBean.getData().getItems();
            if (items != null && items.size() > 0) {
                if(isGetMore) {
                    this.datas.addAll(items);
                    refresh.finishRefreshLoadMore();
                }else {
                    this.datas = items;
                    refresh.finishRefresh();
                }

                daRenAdapter.refresh(datas);
            }
        }
    }

    @Override
    public void initData() {
        daRenAdapter = new DaRenAdapter(getActivity());
        recyclerview.setAdapter(daRenAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    @Override
    public void initTitle() {
        tvTitle.setText("达人");
        ivTitleSearch.setVisibility(View.VISIBLE);
        ivTitleMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public String getUrl() {
        return AppUrl.getDaRenListUrl(currentState, 1);
    }

    @Override
    public void initListener() {
        super.initListener();
        //标题 搜索
        ivTitleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("搜索");
            }
        });
        //刷新
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

        //标题栏 菜单
        ivTitleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrCloseMenu();
            }
        });

        daRenAdapter.setOnItemClickListener(new DaRenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DaRenBean.DataBean.ItemsBean itemsBean) {

            }
        });

    }

    public void showOrCloseMenu() {
        if(popupWindow == null) {
            createMenuView();
        }
        if(popupWindow.isShowing()) {
            popupWindow.dismiss();
            ivTitleMenu.setBackgroundResource(R.drawable.actionbar_navigation_menu);
        }else {
            popupWindow.showAsDropDown(tvTitle,0,40);
            ivTitleMenu.setBackgroundResource(R.drawable.close);

        }
    }

    private void createMenuView() {
            LinearLayout view = (LinearLayout) View.inflate(getActivity(), R.layout.menu_popu, null);
            RadioGroup rgPopu = (RadioGroup) view.getChildAt(0);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

            rgPopu.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
            rgPopu.check(R.id.rb_default_popu);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrCloseMenu();
                }
            });


    }



    private boolean isGetMore = false;
    private int page = 1;
    private void getMoreData() {
        isGetMore = true;
        getDataFromNet(AppUrl.getDaRenListUrl(currentState,++page));
    }

    private void refreshData() {
        isGetMore = false;
        getDataFromNet(AppUrl.getDaRenListUrl(currentState,1));
    }

    private int newState;
    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

            switch (checkedId){
                case R.id.rb_default_popu:
                    newState = AppUrl.DAREN_DEFAULT;
                    break;
                case R.id.rb_most_popu:
                    newState =AppUrl.DAREN_MOST;
                    break;
                case R.id.rb_welcome_popu:
                    newState = AppUrl.DAREN_WELCOME;
                    break;
                case R.id.rb_new_popu:
                    newState = AppUrl.DAREN_NEW;
                    break;
                case R.id.rb_join_popu:
                    newState = AppUrl.DAREN_NEWJOIN;
                    break;
            }
            
            if(newState != currentState) {
                currentState = newState;
                refreshData();
                showOrCloseMenu();
            }
        }
    }

    public PopupWindow getPopuWindow() {
        return popupWindow;
    }


}
