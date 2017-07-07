package com.example.chenyuelun.myapp.view.fragment.store;

import android.content.Intent;
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
import com.example.chenyuelun.myapp.modle.bean.GoodsDetailsBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.activity.GoodsInfoActivity;
import com.example.chenyuelun.myapp.view.activity.MainActivity;
import com.example.chenyuelun.myapp.view.adapter.GoodsDetailsAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public class StoreTypeDetailsFragment extends BaseFragment {


    private final String cart_id;
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
    @BindView(R.id.recyclerview)
    RecyclerView rvDetailsStore;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    @BindView(R.id.iv_title_faver)
    ImageView ivTitleFaver;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.ll_price_select)
    LinearLayout llPriceSelect;
    @BindView(R.id.tv_price_select)
    TextView tvPriceSelect;
    private GoodsDetailsAdapter goodsDetailsAdapter;
    private List<GoodsDetailsBean.DataBean.ItemsBean> datas;

    private boolean isGetMore = false;
    private int dataPager = 1;


    public StoreTypeDetailsFragment(String cart_id) {
        super();
        this.cart_id = cart_id;
    }

    @Override
    protected void setData(String response) {
        Log.e("TAG", "StoreTypeDetailsFragment:" + response);
        GoodsDetailsBean goodsDetailsBean = JSON.parseObject(response, GoodsDetailsBean.class);
        List<GoodsDetailsBean.DataBean.ItemsBean> itemsBeanList = goodsDetailsBean.getData().getItems();
        if (itemsBeanList != null && itemsBeanList.size() > 0) {
            if (isGetMore) {
                this.datas.addAll(itemsBeanList);
                refresh.finishRefreshLoadMore();
            } else {
                this.datas = itemsBeanList;
                refresh.finishRefresh();
            }
            goodsDetailsAdapter.refresh(this.datas);
        } else {
            refresh.finishRefreshLoadMore();
            UiUtils.showToast("没有更多了哦");
        }


    }

    @Override
    public void initData() {
        goodsDetailsAdapter = new GoodsDetailsAdapter(getActivity());
        rvDetailsStore.setAdapter(goodsDetailsAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvDetailsStore.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void initTitle() {
        super.initTitle();
        tvTitle.setText("商店");
        ivTitleBack.setVisibility(View.VISIBLE);
        ivTitleCart.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_details_type_store;
    }

    public String getUrl() {
        return AppUrl.getTypeGoodsUrl(cart_id, 1);
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

        //item点击事件
        goodsDetailsAdapter.setOnItemClickListener(new GoodsDetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, GoodsDetailsBean.DataBean.ItemsBean itemsBean) {
                Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                intent.putExtra("goodsId", itemsBean.getGoods_id());
                startActivity(intent);

            }
        });

        //标题栏返回点击事件
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("返回");
                if(popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchFragment(0);
            }
        });

        ivTitleCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("购物车");
            }
        });



        llPriceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHidePopu();
            }
        });
    }
    private PopupWindow popupWindow;
    private void showOrHidePopu() {
        if(popupWindow == null) {
            createPopu();
        }
        if(popupWindow.isShowing()) {
            popupWindow.dismiss();
        }else {
            popupWindow.showAsDropDown(llPriceSelect,0,0);
        }



    }

    private void createPopu() {
        LinearLayout view = (LinearLayout) View.inflate(getActivity(), R.layout.price_select_popu, null);
        RadioGroup rgPopu = (RadioGroup) view.getChildAt(0);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        rgPopu.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rgPopu.check(R.id.rb_all_popu);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHidePopu();
            }
        });
    }

    //加载更多数据
    private void getMoreData() {
        isGetMore = true;
        getDataFromNet(AppUrl.getTypeGoodsUrl(cart_id, ++dataPager));
    }

    //刷新数据
    private void refreshData() {
        isGetMore = false;
        getDataFromNet(AppUrl.getTypeGoodsUrl(cart_id, 1));
    }


    @Override
    public void getDataFromNet(String url) {
        super.getDataFromNet(url);

    }


    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rb_all_popu :
                    tvPriceSelect.setText("价格筛选");
                    break;
                case R.id.rb_200_popu :
                    tvPriceSelect.setText("0-200");
                    break;
                case R.id.rb_500_popu :
                    tvPriceSelect.setText("201-500");
                    break;
                case R.id.rb_1000_popu :
                    tvPriceSelect.setText("501-1000");
                    break;
                case R.id.rb_3000_popu :
                    tvPriceSelect.setText("1001-3000");
                    break;
                case R.id.rb_more_popu :
                    tvPriceSelect.setText("3000以上");
                    break;
            }
            showOrHidePopu();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }

    }
}
