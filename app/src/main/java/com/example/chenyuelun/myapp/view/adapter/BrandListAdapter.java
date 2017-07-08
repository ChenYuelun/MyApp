package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.BrandBean;
import com.example.chenyuelun.myapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/7.
 */

public class BrandListAdapter extends BaseAdapter {
    private final Context context;
    private List<BrandBean.DataBean.ItemsBean> datas = new ArrayList<>();

    public BrandListAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<BrandBean.DataBean.ItemsBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
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
            convertView = View.inflate(context, R.layout.item_list_brand, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BrandBean.DataBean.ItemsBean itemsBean = datas.get(position);
       // Glide.with(context).load(itemsBean.getBrand_logo()).into(viewHolder.ivBrandIcon);
        UiUtils.loadImage(context,itemsBean.getBrand_logo(),viewHolder.ivBrandIcon,R.drawable.brand_logo_empty);
        viewHolder.tvBrandname.setText(itemsBean.getBrand_name());


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_brand_icon)
        ImageView ivBrandIcon;
        @BindView(R.id.tv_brandname)
        TextView tvBrandname;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
