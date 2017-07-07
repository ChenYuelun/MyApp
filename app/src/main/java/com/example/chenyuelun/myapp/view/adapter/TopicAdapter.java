package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.TopicBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/7.
 */

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<TopicBean.DataBean.ItemsBean> datas = new ArrayList<>();
    private OnItemClickListener listener;

    public TopicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null? 0 :datas.size();
    }

    public void refresh(List<TopicBean.DataBean.ItemsBean> items) {
        this.datas.clear();
        this.datas.addAll(items);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_topic)
        ImageView ivTopic;
        @BindView(R.id.tv_topicInfo)
        TextView tvTopicInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        TopicBean.DataBean.ItemsBean itemsBean = datas.get(getLayoutPosition());
                        listener.onItemClicked(itemsBean.getTopic_url(),itemsBean.getTopic_name());
                    }
                }
            });
        }

        public void setData(TopicBean.DataBean.ItemsBean itemsBean) {
            Picasso.with(context).load(itemsBean.getCover_img_new()).into(ivTopic);
            tvTopicInfo.setText(itemsBean.getTopic_name());

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    public interface OnItemClickListener{
        void onItemClicked(String topic_url,String topic_name);
    }
}
