package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.StoreTypeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public class StoreTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    List<StoreTypeBean.DataBean.ItemsBean> items = new ArrayList<>();
    private OnItemClickListener listener;

    public StoreTypeAdapter(Context context) {
        this.context = context;

    }

    public void refresh(StoreTypeBean typeBean) {
        items.clear();
        items.addAll(typeBean.getData().getItems());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_type)
        ImageView ivItemType;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemClick(items.get(getLayoutPosition()));
                    }
                }
            });
        }

        public void setData(int position) {
            StoreTypeBean.DataBean.ItemsBean itemsBean = items.get(position);
            String new_cover_img = itemsBean.getNew_cover_img();
            Glide.with(context).load(new_cover_img).into(ivItemType);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(StoreTypeBean.DataBean.ItemsBean itemsBean);
    }

}
