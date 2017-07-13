package com.example.chenyuelun.myapp.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.Modle;
import com.example.chenyuelun.myapp.modle.bean.CartBean;
import com.example.chenyuelun.myapp.modle.bean.GoodsInfosBean;
import com.example.chenyuelun.myapp.utils.SpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.view.AddSubView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class JoinCartActivity extends BaseActivity {

    @BindView(R.id.covor)
    ImageView covor;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_ownername)
    TextView tvOwnername;
    @BindView(R.id.tv_goodsname)
    TextView tvGoodsname;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.flowlayout2)
    TagFlowLayout flowlayout2;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.add_sub_view)
    AddSubView addSubView;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.iv_goodsImage)
    ImageView ivGoodsImage;
    private GoodsInfosBean.DataBean.ItemsBean goodsInfo;
    private int is_buy;
    private MyTagAdapter myTagAdapter;
    private List<String> goodsTypes;
    private List<GoodsInfosBean.DataBean.ItemsBean.SkuInvBean> sku_inv;
    private int typeSelectedPos;
    private List<GoodsInfosBean.DataBean.ItemsBean.SkuInfoBean> sku_info;

    //存放商品尺寸大小等信息（衣服、鞋子大小等）
    private List<GoodsInfosBean.DataBean.ItemsBean.SkuInfoBean.AttrListBean> sizes;

    //包含商品规格的对象（规格、颜色、是否套装等信息）
    private List<GoodsInfosBean.DataBean.ItemsBean.SkuInfoBean.AttrListBean> attrList_type;
    private GoodsInfosBean.DataBean.ItemsBean.SkuInfoBean skuInfoBean_type;
    private int sizeSlectedPos;
    private MyTagAdapter myTagAdapter2;

    @Override
    public void initData() {
        Intent intent = getIntent();
        goodsInfo = (GoodsInfosBean.DataBean.ItemsBean) intent.getSerializableExtra("goodsInfo");
        //0 表示加入购物车  1 表示直接购买
        is_buy = intent.getIntExtra("is_buy", 0);
        //进入页面 加载一些信息
        tvOwnername.setText(goodsInfo.getOwner_name());
        tvGoodsname.setText(goodsInfo.getGoods_name());
        tvPrice.setText(TextUtils.isEmpty(goodsInfo.getDiscount_price()) ? "￥" + goodsInfo.getPrice() : "￥" + goodsInfo.getDiscount_price());



        //这个里面存放的是不同规格各个价格
        sku_inv = goodsInfo.getSku_inv();
        if(sku_inv != null && sku_inv.size()>0) {
            String price = sku_inv.get(0).getPrice();
            String discount_price = sku_inv.get(0).getDiscount_price();
            tvPrice.setText(TextUtils.isEmpty(discount_price) ? "￥" + price : "￥" + discount_price);
        }else {
            tvPrice.setText(TextUtils.isEmpty(goodsInfo.getDiscount_price()) ? "￥" + goodsInfo.getPrice() : "￥" + goodsInfo.getDiscount_price());
        }

        //里面一般最多存在两个集合  第一个是规格属性  第二个是大小尺寸
        sku_info = goodsInfo.getSku_info();

        //获取商品规格
        skuInfoBean_type = sku_info.get(0);

        tvType.setText(skuInfoBean_type.getType_name());
        attrList_type = skuInfoBean_type.getAttrList();

        //默认显示第一个的图片
        if(!TextUtils.isEmpty(attrList_type.get(0).getImg_path())) {
            UiUtils.loadImage(JoinCartActivity.this, attrList_type.get(0).getImg_path(), ivGoodsImage, 0);
        }else {
            UiUtils.loadImage(this, goodsInfo.getGoods_image(), ivGoodsImage, 0);
        }

        //下面的集合用来放规格品类
        goodsTypes = new ArrayList<>();
        for (int i = 0; i < attrList_type.size(); i++) {
            goodsTypes.add(attrList_type.get(i).getAttr_name());
        }
        //第一个流式布局显示规格颜色等
        myTagAdapter = new MyTagAdapter(goodsTypes);
        flowlayout.setAdapter(myTagAdapter);
        myTagAdapter.setSelectedList(0);


        //如果有大小尺寸的信息  就设置第二个流式布局
        if (sku_info.size() > 1) {
            GoodsInfosBean.DataBean.ItemsBean.SkuInfoBean skuInfoBean1 = sku_info.get(1);
            tvSize.setVisibility(View.VISIBLE);
            flowlayout2.setVisibility(View.VISIBLE);
            tvSize.setText(skuInfoBean1.getType_name());
            sizes = skuInfoBean1.getAttrList();
            List<String> size_s = new ArrayList<>();
            for (int i = 0; i < sizes.size(); i++) {
                size_s.add(sizes.get(i).getAttr_name());
            }
            myTagAdapter2 = new MyTagAdapter(size_s);
            flowlayout2.setAdapter(myTagAdapter2);
            myTagAdapter2.setSelectedList(0);
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_join_cart;
    }

    @Override
    public void initListener() {
        super.initListener();

        //流式布局点击事件  点击时更改价格显示 图片显示
        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                GoodsInfosBean.DataBean.ItemsBean.SkuInvBean skuInvBean = sku_inv.get(position);
                typeSelectedPos =position;
                if(!TextUtils.isEmpty(attrList_type.get(position).getImg_path())) {
                    UiUtils.loadImage(JoinCartActivity.this, attrList_type.get(position).getImg_path(), ivGoodsImage, 0);
                }

                tvPrice.setText(TextUtils.isEmpty(skuInvBean.getDiscount_price()) ? "￥" + skuInvBean.getPrice() : "￥" + skuInvBean.getDiscount_price());
//                UiUtils.showToast(goodsTypes.get(position));
                return true;
            }
        });



        flowlayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                sizeSlectedPos = position;
                UiUtils.showToast(sizes.get(position).getAttr_name());
                return true;
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.anim_down_out);
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean islogin = (boolean) SpUtils.getSpUtils().get(SpUtils.IS_LOGIN, false);
                if (!islogin) {
                    UiUtils.showToast("请先登录");
                    Intent intent = new Intent(JoinCartActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {

                    CartBean cartBean = new CartBean();
                    cartBean.setGoods_id(goodsInfo.getGoods_id());
                    cartBean.setCount(addSubView.getValue());
                    cartBean.setGoods_name(goodsInfo.getGoods_name());
                    cartBean.setOwner_name(goodsInfo.getOwner_name());
                    cartBean.setPrice(goodsInfo.getPrice());
                    cartBean.setDiscount(goodsInfo.getDiscount_price());
                    cartBean.setImage(TextUtils.isEmpty(attrList_type.get(typeSelectedPos).getImg_path()) ? goodsInfo.getGoods_image() : attrList_type.get(typeSelectedPos).getImg_path());
                    cartBean.setType_name(sku_info.get(0).getType_name());
                    cartBean.setType(attrList_type.get(typeSelectedPos).getAttr_name());
                    if (sku_info.size() > 1) {
                        cartBean.setSize_name(sku_info.get(1).getType_name());
                        cartBean.setSize(sizes.get(sizeSlectedPos).getAttr_name());
                    }
                    cartBean.setIsgift(Integer.parseInt(goodsInfo.getIs_gift()));

                    Log.e("TAG", "cartBean" + cartBean.toString());
                    Modle.getInstance().getCartDao().add(cartBean);
                    UiUtils.showToast("已加入购物车");
                    finish();
                    overridePendingTransition(0, R.anim.anim_down_out);
                }
            }
        });


    }

    private class MyTagAdapter extends TagAdapter<String> {

        public MyTagAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(JoinCartActivity.this).inflate(R.layout.goods_type_tv, flowlayout, false);
            tv.setText(s);
            return tv;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(0, R.anim.anim_down_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
