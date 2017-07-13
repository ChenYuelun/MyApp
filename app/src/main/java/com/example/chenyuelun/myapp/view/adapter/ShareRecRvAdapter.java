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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.ShareRecBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.view.ExpandTextView;
import com.example.chenyuelun.myapp.view.view.MyTransformation2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by chenyuelun on 2017/7/11.
 */

public class ShareRecRvAdapter extends RecyclerView.Adapter {
    private final int VIDEO = 1;
    private final int IMAGE = 2;
    private final int TEXT = 3;
    private final int GIF = 4;
    private final int HTML = 5;

    private final Context context;
    int itemType = 0;


    private List<ShareRecBean.ListBean> datas = new ArrayList<>();
    private OnItemDetailClickListener listener;


    public ShareRecRvAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<ShareRecBean.ListBean> list) {
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        String type = datas.get(position).getType();
        switch (type) {
            case "video":
                itemType = VIDEO;
                break;
            case "image":
                itemType = IMAGE;
                break;
            case "gif":
                itemType = GIF;
                break;
            case "text":
                itemType = TEXT;
                break;
            case "html":
                itemType = HTML;
        }

        return itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_share_rec, parent, false));
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
        @BindView(R.id.iv_header_icon)
        ImageView ivHeaderIcon;
        @BindView(R.id.tv_header_name)
        TextView tvHeaderName;
        @BindView(R.id.iv_isvip)
        ImageView ivIsvip;
        @BindView(R.id.tv_passtime)
        TextView tvPasstime;
        @BindView(R.id.tv_text)
        ExpandTextView  tvText;
        @BindView(R.id.videoplayer)
        JCVideoPlayerStandard videoplayer;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_bigImage)
        TextView tvBigImage;
        @BindView(R.id.rl_bigImage)
        RelativeLayout rlBigImage;
        @BindView(R.id.iv_gift)
        ImageView ivGift;
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

        public void setData(final ShareRecBean.ListBean listBean) {
            String text = listBean.getText();
            tvText.setText(text);
            ShareRecBean.ListBean.UBean u = listBean.getU();
            String headerImage = u.getHeader().get(0);
            UiUtils.loadImage(context, headerImage, ivHeaderIcon, 1);
            tvHeaderName.setText(u.getName());
            if (u.isIs_v()) {
                tvHeaderName.setTextColor(context.getResources().getColor(R.color.vip));
                ivIsvip.setVisibility(View.VISIBLE);
            } else {
                tvHeaderName.setTextColor(context.getResources().getColor(R.color.novip));
                ivIsvip.setVisibility(View.GONE);
            }
            tvPasstime.setText(listBean.getPasstime());

            tvCommend.setText(listBean.getComment());
            tvZan.setText(listBean.getUp());
            tvCai.setText(listBean.getDown() + "");
            tvShare.setText(listBean.getForward() + "");
            List<ShareRecBean.ListBean.TopCommentsBean> top_comments = listBean.getTop_comments();
            llPinglun.removeAllViews();
            if (top_comments != null) {
                llPinglun.setVisibility(View.VISIBLE);
                for (int i = 0; i < top_comments.size(); i++) {
                    ShareRecBean.ListBean.TopCommentsBean topCommentsBean = top_comments.get(i);
                    String content = topCommentsBean.getContent();
                    String name = topCommentsBean.getU().getName();
                    TextView textView = new TextView(context);
                    String str = name + ":" + content;
                    SpannableStringBuilder mSpannable = new SpannableStringBuilder(str);
                    mSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.novip)), 0, name.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    mSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), name.length() + 1, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    textView.setText(mSpannable);
                    llPinglun.addView(textView);
                }
            }else {
                llPinglun.setVisibility(View.GONE);
            }

            if (itemType == VIDEO) {
                //如果当前iten是video
                videoplayer.setVisibility(View.VISIBLE);
                rlBigImage.setVisibility(View.GONE);
                ivGift.setVisibility(View.GONE);


                videoplayer.setUp(listBean.getVideo().getVideo().get(0), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                String image = listBean.getVideo().getThumbnail().get(0);
                UiUtils.loadImage(context, image, videoplayer.thumbImageView, 0);
            }

            if (itemType == IMAGE) {
                videoplayer.setVisibility(View.GONE);
                rlBigImage.setVisibility(View.VISIBLE);
                ivGift.setVisibility(View.GONE);

//                UiUtils.loadImage(context, listBean.getImage().getThumbnail_small().get(0), ivImage, 0);
                List<String> big = listBean.getImage().getBig();
                Picasso.with(context).load(big.get(1)).transform(new MyTransformation2()).into(ivImage);
            }

            if (itemType == TEXT) {
                videoplayer.setVisibility(View.GONE);
                rlBigImage.setVisibility(View.GONE);
                ivGift.setVisibility(View.GONE);
            }


            if (itemType == GIF ) {
                videoplayer.setVisibility(View.GONE);
                rlBigImage.setVisibility(View.GONE);
                ivGift.setVisibility(View.VISIBLE);
                Glide.with(context).load(listBean.getGif().getImages().get(0)).into(ivGift);
            }
            if (itemType == HTML) {
                //如果是Html 这是地址
                String source_url = listBean.getHtml().getSource_url();
                videoplayer.setVisibility(View.GONE);
                rlBigImage.setVisibility(View.GONE);
                ivGift.setVisibility(View.VISIBLE);
                String s = listBean.getHtml().getThumbnail().get(0);
                Glide.with(context).load(s).into(ivGift);
            }
            tvCommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemDetailClicked(getLayoutPosition(), datas.get(getLayoutPosition()));
                    }
                }
            });

            llPinglun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemDetailClicked(getLayoutPosition(), datas.get(getLayoutPosition()));
                    }
                }
            });

            rlBigImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemDetailClicked(getLayoutPosition(), datas.get(getLayoutPosition()));
                    }
                }
            });
            ivGift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemDetailClicked(getLayoutPosition(), datas.get(getLayoutPosition()));
                    }
                }
            });

            tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemDetailClicked(getLayoutPosition(),datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public void setOnItemDetailClickListener(OnItemDetailClickListener listener){
        this.listener = listener;
    }

    public interface OnItemDetailClickListener{
        void onItemDetailClicked(int position, ShareRecBean.ListBean listBean);
    }
}
