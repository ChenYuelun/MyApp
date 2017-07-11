package com.example.chenyuelun.myapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.GoodsInfosBean;
import com.example.chenyuelun.myapp.utils.HttpUtils;
import com.example.chenyuelun.myapp.utils.SpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class GoodsInfoActivity extends BaseActivity {


    String goodsUrl = "";
    @BindView(R.id.iv_goodsinfo_back)
    ImageView ivGoodsinfoBack;
    @BindView(R.id.iv_goodsinfo_cart)
    ImageView ivGoodsinfoCart;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_brandname)
    TextView tvBrandname1;
    @BindView(R.id.tv_goodsname)
    TextView tvGoodsname;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.tv_promotion_note)
    TextView tvPromotionNote;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_goodsprice)
    TextView tvGoodsprice;
    @BindView(R.id.tv_oldprice)
    TextView tvOldprice;
    @BindView(R.id.tv_free)
    TextView tvFree;
    @BindView(R.id.tv_realgood)
    TextView tvRealgood;
    @BindView(R.id.tv_preslod)
    TextView tvPreslod;
    @BindView(R.id.tv_selected)
    TextView tvSelected;
    @BindView(R.id.ll_choicegoods)
    LinearLayout llChoicegoods;
    @BindView(R.id.iv_heading)
    ImageView ivHeading;
    @BindView(R.id.tv_brandname2)
    TextView tvBrandname2;
    @BindView(R.id.ll_brandinfo)
    LinearLayout llHeading;
    @BindView(R.id.rb_goods_info)
    RadioButton rbGoodsInfo;
    @BindView(R.id.rb_goods_notice)
    RadioButton rbGoodsNotice;
    @BindView(R.id.rg_select_show)
    RadioGroup rgSelectShow;
    @BindView(R.id.ll_images)
    LinearLayout llImages;
    @BindView(R.id.tv_textInfo)
    TextView tvTextInfo;
    @BindView(R.id.ll_brandInfo)
    LinearLayout llBrandInfo;
    @BindView(R.id.tv_ownername)
    TextView tvOwnername;
    @BindView(R.id.tv_brandinfo)
    TextView tvBrandinfo;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.rl_goodsInfo)
    RelativeLayout rlGoodsInfo;
    @BindView(R.id.tv_goodsnotice)
    TextView tvGoodsnotice;
    @BindView(R.id.ll_goodsnotice)
    LinearLayout llGoodsnotice;
    @BindView(R.id.sv_goodsinfo)
    ScrollView svGoodsinfo;
    @BindView(R.id.id_goodsinfo_service)
    ImageButton idGoodsinfoService;
    @BindView(R.id.bt_add_in_cart)
    Button btAddInCart;
    @BindView(R.id.bt_buy)
    Button btBuy;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    private GoodsInfosBean.DataBean.ItemsBean goodsinfo;


    @Override
    public void initData() {
        String goodsId = getIntent().getStringExtra("goodsId");
        goodsUrl = AppUrl.GOODSINFO_HEAD + goodsId + AppUrl.GOODSINFO_FOOT;
        Log.e("TAG", "商品信息Url:" + goodsUrl);
        getDataFromNet();

    }


    private void getDataFromNet() {
        HttpUtils.getInstance().get(goodsUrl, new HttpUtils.OnHttpListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "商品信息请求成功" + response);
                processData(response);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "商品信息请求失败" + e.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void processData(String response) {
        GoodsInfosBean goodsInfosBean = JSON.parseObject(response, GoodsInfosBean.class);
        //banner图片集合
        goodsinfo = goodsInfosBean.getData().getItems();
        List<String> images_item = goodsinfo.getImages_item();
        //设置banner
        initBanner(images_item);

        //商品信息
        //商品名称
        tvGoodsname.setText(goodsinfo.getGoods_name());
        //收藏数量
        tvLikeCount.setText(goodsinfo.getLike_count());
        //折扣价格 如果有 就设置
        String discount_price = goodsinfo.getDiscount_price();
        if (!TextUtils.isEmpty(discount_price)) {
            tvGoodsprice.setText("￥" + discount_price);
            tvOldprice.setText("￥" + goodsinfo.getPrice());
            tvOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvGoodsprice.setText("￥" + goodsinfo.getPrice());
            tvOldprice.setVisibility(View.GONE);
        }
        //广告语
        tvPromotionNote.setText(goodsinfo.getPromotion_note());
        //发货时间
        tvPreslod.setText(goodsinfo.getShipping_str());

        List<GoodsInfosBean.DataBean.ItemsBean.GoodsInfoBean> goods_info = goodsinfo.getGoods_info();

        //商品详情的图片
        List<String> imageUrls = new ArrayList<>();

        //商品详情的文本介绍
        String textInfo = "";
        if (goods_info != null) {
            for (int i = 0; i < goods_info.size(); i++) {
                GoodsInfosBean.DataBean.ItemsBean.GoodsInfoBean goodsInfoBean = goods_info.get(i);
                int type = goodsInfoBean.getType();
                if (type == 1) {
                    //是图片
                    imageUrls.add(goodsInfoBean.getContent().getImg());
                    Log.e("TAG", "imageUrls==" + goodsInfoBean.getContent().getImg());
                } else if (type == 0) {
                    //是文本
                    String text = goodsInfoBean.getContent().getText();
                    textInfo = textInfo + "\n\n " + text;
                    Log.e("TAG", "texts==" + goodsInfoBean.getContent().getText());
                } else if (type == 2) {
                    String text = goodsInfoBean.getContent().getText();
                    textInfo = textInfo + "\n\n " + text;
                }

            }
        }

        //如果商品详情界面的图片集合部位空 就往商品详情页面填充这些图片
        if (imageUrls != null && imageUrls.size() > 0) {
            for (int i = 0; i < imageUrls.size(); i++) {
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setCropToPadding(true);
                imageView.setAdjustViewBounds(true);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                llImages.addView(imageView);
                Glide.with(this).load(imageUrls.get(i)).into(imageView);
            }
        }
        textInfo = textInfo + "\n\n" + goodsinfo.getGoods_desc();
        //文本介绍
        tvTextInfo.setText(textInfo);


        //品牌信息
        GoodsInfosBean.DataBean.ItemsBean.BrandInfoBean brand_info = goodsinfo.getBrand_info();
        tvBrandname1.setText(brand_info.getBrand_name());
        tvOwnername.setText(brand_info.getBrand_name());
        tvBrandname2.setText(brand_info.getBrand_name());
        tvBrandinfo.setText(brand_info.getBrand_desc());
        Glide.with(this).load(brand_info.getBrand_logo()).into(ivHeading);

        //购买须知
        tvGoodsnotice.setText(goodsinfo.getGood_guide().getContent());

        //良仓推荐
        tvRecommend.setText(goodsinfo.getRec_reason());


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_info;
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }

    private void initBanner(List<String> images) {
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.stopAutoPlay();
    }

    @Override
    public void initListener() {
        super.initListener();
        rgSelectShow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_goods_info:
                        rlGoodsInfo.setVisibility(View.VISIBLE);
                        llGoodsnotice.setVisibility(View.GONE);
                        break;

                    case R.id.rb_goods_notice:
                        rlGoodsInfo.setVisibility(View.GONE);
                        llGoodsnotice.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        rgSelectShow.check(R.id.rb_goods_info);

        ivGoodsinfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivGoodsinfoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean islogin = (boolean) SpUtils.getSpUtils().get(SpUtils.IS_LOGIN, false);
                if (islogin) {
                    UiUtils.showToast("购物车");
                    startActivity(new Intent(GoodsInfoActivity.this, ShoppingCratActivity.class));
                } else {
                    UiUtils.showToast("请先登录");
                    Intent intent = new Intent(GoodsInfoActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        tvBrandname2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBrandInfo();
            }
        });
        ivHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBrandInfo();
            }
        });

        btAddInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InToCart(goodsinfo, 0);
            }
        });

        btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InToCart(goodsinfo, 1);
            }
        });

        tvSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InToCart(goodsinfo, 0);
            }
        });

    }

    private void InToCart(GoodsInfosBean.DataBean.ItemsBean goodsinfo, int value) {
        Intent intent = new Intent(GoodsInfoActivity.this, JoinCartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goodsInfo", goodsinfo);
        intent.putExtras(bundle);
        intent.putExtra("is_buy", value);
        startActivity(intent);
    }

    //打开品牌详情页面
    private void toBrandInfo() {
        Intent intent = new Intent(GoodsInfoActivity.this, BrandInfoActivity.class);
        GoodsInfosBean.DataBean.ItemsBean.BrandInfoBean brand_info = goodsinfo.getBrand_info();
        intent.putExtra("brand_id",Integer.parseInt(brand_info.getBrand_id()));
        Log.e("TAG", "brand_id");
        intent.putExtra("brand_name",brand_info.getBrand_name());
        intent.putExtra("brand_logo",brand_info.getBrand_logo());
        startActivity(intent);
    }
}
