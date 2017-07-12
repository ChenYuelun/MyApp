package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
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
import com.example.chenyuelun.myapp.modle.bean.ShareDetailBean;
import com.example.chenyuelun.myapp.utils.UiUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/12.
 */

public class ShareDetailRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;

    private ShareDetailBean shareDetailBean;
    private List<ShareDetailBean.HotBean.ListBean> hotlist = new ArrayList<>();
    private List<ShareDetailBean.NormalBean.ListBeanX> normalList = new ArrayList<>();

    public ShareDetailRvAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder viewHolder = null;
        if (hotlist.size() > 0 && (viewType == 0 || viewType == hotlist.size())) {
            viewHolder = new HeadHolder(LayoutInflater.from(context).inflate(R.layout.item_share_detail_header, parent, false));
        } else {
            viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_share_detail, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolder viewHolder = (BaseHolder) holder;
        viewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (hotlist != null && hotlist.size() > 0) {
            count += hotlist.size();
        }

        if (normalList != null && normalList.size() > 0) {
            count += normalList.size();
        }
        return count;
    }

    public void refresh(ShareDetailBean shareDetailBean) {
        this.shareDetailBean = shareDetailBean;
        ShareDetailBean.HotBean hot = shareDetailBean.getHot();
        hotlist = hot.getList();
        ShareDetailBean.NormalBean normal = shareDetailBean.getNormal();
        normalList = normal.getList();
        notifyDataSetChanged();


    }

    class ViewHolder extends BaseHolder {
        @BindView(R.id.iv_header_icon)
        ImageView ivHeaderIcon;
        @BindView(R.id.tv_like_count)
        TextView tvLikeCount;
        @BindView(R.id.iv_sex)
        ImageView ivSex;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.tv_cai)
        TextView tvCai;
        @BindView(R.id.tv_username2)
        TextView tvUsername2;
        @BindView(R.id.tv_zan_2)
        TextView tvZan2;
        @BindView(R.id.tv_cai_2)
        TextView tvCai2;
        @BindView(R.id.tv_zhuiping)
        TextView tvZhuiping;
        @BindView(R.id.ll_zhuiping)
        LinearLayout llZhuiping;
        @BindView(R.id.tv_pinglun)
        TextView tvPinglun;
        @BindView(R.id.iv_pinglun)
        ImageView ivPinglun;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            if (hotlist.size()>0 &&position < hotlist.size()+1) {
                //有热评的时候
                ShareDetailBean.HotBean.ListBean listBean = hotlist.get(position-1);
                ShareDetailBean.HotBean.ListBean.UserBean user = listBean.getUser();
                //加载头像
                UiUtils.loadImage(context, user.getProfile_image(), ivHeaderIcon, 1);
                //设置喜欢数量
                String total_cmt_like_count = user.getTotal_cmt_like_count();
                //根据不同的喜欢数量设置不同的背景
                double totallike = Double.parseDouble(total_cmt_like_count);
                DecimalFormat df = new DecimalFormat("######0.00");
                if (totallike < 1000) {
                    tvLikeCount.setBackgroundResource(R.color.bg0_999);
                    tvLikeCount.setText(total_cmt_like_count);
                } else if (totallike > 1000 && totallike < 10000) {
                    tvLikeCount.setBackgroundResource(R.color.bg1k_10k);
                    tvLikeCount.setText(df.format(totallike / 1000) + " k");
                } else if (totallike > 10000) {
                    tvLikeCount.setBackgroundResource(R.color.bg10k_0);
                    tvLikeCount.setText(df.format(totallike / 1000) + " k");
                }


                //根据性别设置名字
                String sex = user.getSex();
                if (sex.equals("m")) {
                    String str = "♂ " + user.getUsername();
                    SpannableStringBuilder mSpannable = new SpannableStringBuilder(str);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.sex_m)), 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.novip)), 2, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    tvUsername.setText(str);
                } else {

                    String str = "♀ " + user.getUsername();
                    SpannableStringBuilder mSpannable = new SpannableStringBuilder(str);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.sex_f)), 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.novip)), 2, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    tvUsername.setText(str);
                }
                llZhuiping.setVisibility(View.GONE);
                tvZan.setText(listBean.getLike_count() + "");
                tvCai.setText(listBean.getHate_count() + "");
                tvPinglun.setText(listBean.getContent());
                if(listBean.getType().equals("image")) {
                    ivPinglun.setVisibility(View.VISIBLE);
                    UiUtils.loadImage(context,listBean.getImage().getImages().get(0),ivPinglun,0);
                }else {
                    ivPinglun.setVisibility(View.GONE);
                }
            } else {
//                ---------------------------------------------------------------------
                //没有热评或者不是热评的时候
                ShareDetailBean.NormalBean.ListBeanX listBeanX = normalList.get(position - hotlist.size());
                ShareDetailBean.NormalBean.ListBeanX.UserBeanX user = listBeanX.getUser();
                //加载头像
                UiUtils.loadImage(context, user.getProfile_image(), ivHeaderIcon, 1);
                //设置喜欢数量
                double totallike = Double.parseDouble(user.getTotal_cmt_like_count());
                DecimalFormat df = new DecimalFormat("######0.00");
                if (totallike < 1000) {
                    tvLikeCount.setBackgroundResource(R.color.bg0_999);
                    tvLikeCount.setText(user.getTotal_cmt_like_count());
                } else if (totallike > 1000 && totallike < 10000) {
                    tvLikeCount.setBackgroundResource(R.color.bg1k_10k);
                    tvLikeCount.setText(df.format(totallike / 1000) + " k");
                } else if (totallike > 10000) {
                    tvLikeCount.setBackgroundResource(R.color.bg10k_0);
                    tvLikeCount.setText(df.format(totallike / 1000) + " k");
                }
                //根据性别设置名字
                String sex = user.getSex();
                if (sex.equals("m")) {
                    String str = "♂ " + user.getUsername();
                    SpannableStringBuilder mSpannable = new SpannableStringBuilder(str);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.sex_m)), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.novip)), 2, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    tvUsername.setText(str);
                } else {

                    String str = "♀ " + user.getUsername();
                    SpannableStringBuilder mSpannable = new SpannableStringBuilder(str);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.sex_f)), 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.novip)), 2, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    tvUsername.setText(str);
                }
                tvZan.setText(listBeanX.getLike_count() + "");
                tvCai.setText(listBeanX.getHate_count() + "");
                tvPinglun.setText(listBeanX.getContent());
                if(listBeanX.getType().equals("image")) {
                    ivPinglun.setVisibility(View.VISIBLE);
                    UiUtils.loadImage(context,listBeanX.getImage().getImages().get(0),ivPinglun,0);
                }else {
                    ivPinglun.setVisibility(View.GONE);
                }

//----------------------------------------------------------------------------------------
                //如果有追评  就设置
                List<ShareDetailBean.NormalBean.ListBeanX.precmts> precmts = listBeanX.getPrecmts();
                if (precmts != null && precmts.size() > 0) {
                    llZhuiping.setVisibility(View.VISIBLE);
                    ShareDetailBean.NormalBean.ListBeanX.precmts precmts1 = precmts.get(0);
                    ShareDetailBean.NormalBean.ListBeanX.precmts.UserBean user1 = precmts1.getUser();
                    String sex1 = user1.getSex();
                    tvUsername2.setText(user1.getUsername());
                    tvZan2.setText(precmts1.getLike_count() + "");
                    tvCai2.setText(precmts1.getHate_count() + "");
                    tvZhuiping.setText(precmts1.getContent());

                } else {
                    llZhuiping.setVisibility(View.GONE);
                }
            }
        }

    }

    class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {

        }

    }

    class HeadHolder extends BaseHolder {
        @BindView(R.id.header)
        TextView header;

        public HeadHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            if (hotlist.size() > 0) {
                if (position == 0) {
                    header.setText("热门评论");
                }
            }
            if (position == hotlist.size()) {
                header.setText("最新评论");
            }

        }
    }
}
