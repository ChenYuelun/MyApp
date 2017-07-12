package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.ShareDzBean;
import com.example.chenyuelun.myapp.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/11.
 */

public class ShareDzRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;

    private List<ShareDzBean.ListBean> datas = new ArrayList<>();
    private OnItemTextClickListener listenter;

    public ShareDzRvAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_share_text, parent, false));
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

    public void refresh(List<ShareDzBean.ListBean> list) {
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header_icon)
        ImageView ivHeaderIcon;
        @BindView(R.id.tv_header_name)
        TextView tvHeaderName;
        @BindView(R.id.iv_isvip)
        ImageView ivIsvip;
        @BindView(R.id.tv_passtime)
        TextView tvPasstime;
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.ll_zan)
        LinearLayout llZan;
        @BindView(R.id.tv_cai)
        TextView tvCai;
        @BindView(R.id.ll_cai)
        LinearLayout llCai;
        @BindView(R.id.tv_share)
        TextView tvShare;
        @BindView(R.id.ll_share)
        LinearLayout llShare;
        @BindView(R.id.tv_commend)
        TextView tvCommend;
        @BindView(R.id.ll_commend)
        LinearLayout llCommend;
        @BindView(R.id.ll_pinglun)
        LinearLayout llPinglun;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(ShareDzBean.ListBean listBean) {
            String text = listBean.getText();
            tvText.setText(text);
            ShareDzBean.ListBean.UBean u = listBean.getU();
            String headerImage = u.getHeader().get(0);
            UiUtils.loadImage(context, headerImage, ivHeaderIcon, 1);
            tvHeaderName.setText(u.getName());

            tvPasstime.setText(listBean.getPasstime());

            tvCommend.setText(listBean.getComment());
            tvZan.setText(listBean.getUp());
            tvCai.setText(listBean.getDown() + "");
            tvShare.setText(listBean.getForward() + "");
            List<ShareDzBean.ListBean.TopCommentsBean> top_comments = listBean.getTop_comments();
            if (top_comments != null) {
                llPinglun.setVisibility(View.VISIBLE);
                llPinglun.removeAllViews();
                for (int i = 0; i < top_comments.size(); i++) {
                    ShareDzBean.ListBean.TopCommentsBean topCommentsBean = top_comments.get(i);
                    String content = topCommentsBean.getContent();
                    String name = topCommentsBean.getU().getName();
                    TextView textView = new TextView(context);
                    String str = name + ":" + content;
                    SpannableStringBuilder mSpannable = new SpannableStringBuilder(str);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.novip)), 0, name.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    mSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), name.length() + 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    textView.setText(mSpannable);
                    llPinglun.addView(textView);


                    llPinglun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(listenter != null) {
                                listenter.onTextClicked(getLayoutPosition());
                            }
                        }
                    });

                    llCommend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(listenter != null) {
                                listenter.onTextClicked(getLayoutPosition());
                            }
                        }
                    });

                    tvText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(listenter != null) {
                                listenter.onTextClicked(getLayoutPosition());
                            }
                        }
                    });

                }
            }else {
                llPinglun.setVisibility(View.GONE);
            }
        }
    }

    public void setOnItemTextClickListener(OnItemTextClickListener listener){
        this.listenter = listener;
    }

    public interface OnItemTextClickListener {

        void onTextClicked(int position);
    }
}