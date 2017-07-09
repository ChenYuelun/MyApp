package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.MagazineAuthorListBean;
import com.example.chenyuelun.myapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/9.
 */

public class MgzCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;

    private List<MagazineAuthorListBean.DataBean.ItemsBean> datas = new ArrayList<>();
    private OnItemCliclkListener listener;

    public MgzCategoryAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<MagazineAuthorListBean.DataBean.ItemsBean> items) {
        this.datas.clear();
        this.datas.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_magazine_category, parent, false));
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
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemClicked(getLayoutPosition(),datas.get(getLayoutPosition()));
                    }
                }
            });
        }

        public void setData(MagazineAuthorListBean.DataBean.ItemsBean itemsBean) {
            tvName.setText(itemsBean.getAuthor_name());
            UiUtils.loadImage(context,itemsBean.getThumb(),ivIcon,0);
        }
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener){
        this.listener = listener;
    }

    public interface OnItemCliclkListener{
        void onItemClicked(int position, MagazineAuthorListBean.DataBean.ItemsBean itemsBean);
    }
}
