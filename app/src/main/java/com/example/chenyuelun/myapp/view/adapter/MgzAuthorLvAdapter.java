package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class MgzAuthorLvAdapter extends BaseAdapter {
    private final Context context;
    private List<MagazineAuthorListBean.DataBean.ItemsBean> datas = new ArrayList<>();

    public MgzAuthorLvAdapter(Context context) {
        this.context = context;
    }

    public void refrsh(List<MagazineAuthorListBean.DataBean.ItemsBean> itemsBeanList) {
        this.datas.clear();
        this.datas.addAll(itemsBeanList);
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
            convertView = View.inflate(context, R.layout.item_mgz_author_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MagazineAuthorListBean.DataBean.ItemsBean itemsBean = datas.get(position);
        viewHolder.tvAuthorName.setText(itemsBean.getAuthor_name());
        viewHolder.tvAuthorNote.setText(itemsBean.getNote());
        UiUtils.loadImage(context,itemsBean.getThumb(),viewHolder.ivAuthor,1);
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_author)
        ImageView ivAuthor;
        @BindView(R.id.tv_author_name)
        TextView tvAuthorName;
        @BindView(R.id.tv_author_note)
        TextView tvAuthorNote;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
