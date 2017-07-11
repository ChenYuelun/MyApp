package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.DaRenBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/7.
 */

public class DaRenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;

    private List<DaRenBean.DataBean.ItemsBean> datas = new ArrayList<>();
    private OnItemClickListener listener;

    public DaRenAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<DaRenBean.DataBean.ItemsBean> items) {
        this.datas.clear();
        this.datas.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daren, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user)
        ImageView ivUser;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_duty)
        TextView tvDuty;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemClick(datas.get(getLayoutPosition()));
                    }
                }
            });
        }

        public void setData(DaRenBean.DataBean.ItemsBean itemsBean) {

            Picasso.with(context)
                    .load(itemsBean.getUser_images()
                     .getOrig())
                    .error(R.drawable.brand_logo_empty)
                    .placeholder(R.drawable.brand_logo_empty)
                    .into(ivUser);

            tvUsername.setText(itemsBean.getUsername());
            tvDuty.setText(itemsBean.getDuty());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(DaRenBean.DataBean.ItemsBean itemsBean);
    }
}
