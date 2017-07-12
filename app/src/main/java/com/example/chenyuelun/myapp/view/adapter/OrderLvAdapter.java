package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.CartBean;
import com.example.chenyuelun.myapp.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/12.
 */

public class OrderLvAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<CartBean> datas;

    public OrderLvAdapter(Context context, ArrayList<CartBean> goods) {
        this.context = context;
        this.datas = goods;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_lv_order, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CartBean cartBean = datas.get(position);
        viewHolder.tvCount.setText("x" + cartBean.getCount());
        viewHolder.tvGoodsname.setText(cartBean.getGoods_name());
        UiUtils.loadImage(context, cartBean.getImage(), viewHolder.ivImage, 0);
        String type_name = cartBean.getType_name();
        String type = cartBean.getType();
        String size_name = cartBean.getSize_name();
        String size = cartBean.getSize();
        if (TextUtils.isEmpty(size_name)) {
            viewHolder.tvTypeSize.setText(type_name + ":" + type);
        } else {
            viewHolder.tvTypeSize.setText(type_name + ":" + type + ";" + size_name + ":" + size);
        }

        String discount = cartBean.getDiscount();
        if (TextUtils.isEmpty(discount)) {
            viewHolder.tvPrice.setText("￥" + cartBean.getPrice());
            viewHolder.tvOldPrice.setVisibility(View.GONE);
        } else {
            viewHolder.tvPrice.setText("￥" + cartBean.getDiscount());
            viewHolder.tvOldPrice.setText("￥" + cartBean.getPrice());
            viewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_goodsname)
        TextView tvGoodsname;
        @BindView(R.id.tv_type_size)
        TextView tvTypeSize;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ll_noEdit)
        LinearLayout llNoEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
