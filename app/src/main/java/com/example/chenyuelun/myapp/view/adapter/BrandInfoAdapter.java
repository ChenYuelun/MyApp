package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.BrandInfosBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/7.
 */

public class BrandInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;

    private List<BrandInfosBean.DataBean.ItemsBean> datas = new ArrayList<>();
    private OnItemClickListener listener;

    public BrandInfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details_good_store, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHoder viewHoder = (ViewHoder) holder;
        viewHoder.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void refresh(List<BrandInfosBean.DataBean.ItemsBean> items) {
        this.datas.clear();
        this.datas.addAll(items);
        notifyDataSetChanged();
    }

    class ViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_brandname)
        TextView tvBrandName;
        @BindView(R.id.tv_like_count)
        TextView tvLikeCount;
        @BindView(R.id.tv_discount_price)
        TextView tvDiscountPrice;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        public ViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemClick(getLayoutPosition(),datas.get(getLayoutPosition()));
                    }
                }
            });
        }

        public void setData(int position) {
            BrandInfosBean.DataBean.ItemsBean itemsBean = datas.get(position);
            Glide.with(context).load(itemsBean.getGoods_image()).into(ivGoods);
            tvGoodsName.setText(itemsBean.getGoods_name());
            tvBrandName.setText(itemsBean.getBrand_info().getBrand_name());
            tvLikeCount.setText(itemsBean.getLike_count());
            String discount_price = itemsBean.getDiscount_price();
            if (!TextUtils.isEmpty(discount_price)) {
                tvDiscountPrice.setText("￥" + discount_price);
                tvPrice.setText("￥" + itemsBean.getPrice());
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tvDiscountPrice.setText("￥" + itemsBean.getPrice());
                tvPrice.setVisibility(View.GONE);
            }
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position,BrandInfosBean.DataBean.ItemsBean itemsBean);
    }
}
