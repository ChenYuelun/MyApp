package com.example.chenyuelun.myapp.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.BrandInfosBean;
import com.example.chenyuelun.myapp.utils.HttpUtils;
import com.example.chenyuelun.myapp.view.adapter.BrandInfoAdapter;
import com.example.chenyuelun.myapp.view.view.MyTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class BrandInfoActivity extends BaseActivity {


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
    @BindView(R.id.iv_brand_bg)
    ImageView ivBrandBg;
    @BindView(R.id.iv_brand_logo)
    ImageView ivBrandLogo;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.rb_brand_story)
    RadioButton rbBrandStory;
    @BindView(R.id.rb_brand_goods)
    RadioButton rbBrandGoods;
    @BindView(R.id.rg_brand)
    RadioGroup rgBrand;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    @BindView(R.id.tv_brand_story)
    TextView tvBrandStory;
    private String brandInfoUrl;
    private BrandInfoAdapter brandInfoAdapter;

    @Override
    public void initData() {
        Intent intent = getIntent();
        int brand_id = intent.getIntExtra("brand_id", -1);
        Log.e("TAG", "brand_id" + brand_id);
        if (brand_id != -1) {
            brandInfoUrl = AppUrl.getBrandInfoUrl(brand_id);
            getDataFromNet();
        }

        String brand_name = intent.getStringExtra("brand_name");
        tvBrandName.setText(brand_name);
        String brand_logo = intent.getStringExtra("brand_logo");
        Picasso.with(this).load(brand_logo).transform(new MyTransformation()).into(ivBrandLogo);


        //设置适配器
        brandInfoAdapter = new BrandInfoAdapter(this);
        recyclerview.setAdapter(brandInfoAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(this,2));

    }

    private void getDataFromNet() {
        HttpUtils.getInstance().get(brandInfoUrl, new HttpUtils.OnHttpListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "品牌信息请求成功" + response);
                processData(response);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "品牌信息请求失败" + e.getMessage());
            }
        });
    }

    private void processData(String response) {
        BrandInfosBean brandInfosBean = JSON.parseObject(response, BrandInfosBean.class);
        if (brandInfosBean != null) {
            List<BrandInfosBean.DataBean.ItemsBean> items = brandInfosBean.getData().getItems();
            if (items != null && items.size() > 0) {
                BrandInfosBean.DataBean.ItemsBean.BrandInfoBean brand_info = items.get(0).getBrand_info();
                tvBrandStory.setText(brand_info.getBrand_desc());
                brandInfoAdapter.refresh(items);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_brand_info;
    }

    @Override
    public void initTitle() {
        super.initTitle();

    }

    @Override
    public void initListener() {
        super.initListener();
        rgBrand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case  R.id.rb_brand_story:
                        tvBrandStory.setVisibility(View.VISIBLE);
                        recyclerview.setVisibility(View.GONE);
                        break;
                    case  R.id.rb_brand_goods:
                        tvBrandStory.setVisibility(View.GONE);
                        recyclerview.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        rgBrand.check(R.id.rb_brand_goods);


        brandInfoAdapter.setOnItemClickListener(new BrandInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, BrandInfosBean.DataBean.ItemsBean itemsBean) {
                String goods_id = itemsBean.getGoods_id();
                Intent intent = new Intent(BrandInfoActivity.this, GoodsInfoActivity.class);
                intent.putExtra("goodsId", goods_id);
                startActivity(intent);
            }
        });
    }



}
