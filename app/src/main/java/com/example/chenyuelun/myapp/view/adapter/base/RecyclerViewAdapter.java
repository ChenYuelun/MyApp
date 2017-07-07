package com.example.chenyuelun.myapp.view.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<T> datas;

    public RecyclerViewAdapter(Context context, List<T> list){
        this.context = context;
        this.datas = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder();
    }

    protected abstract RecyclerView.ViewHolder getViewHolder();


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas == null? 0:datas.size();
    }

}
