package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.HomeBean;
import com.example.chenyuelun.myapp.view.activity.MainActivity;
import com.example.chenyuelun.myapp.view.fragment.store.StoreTypeDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/7.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Context context;
    @BindView(R.id.iv_hometype6)
    ImageView ivHometype1;


    private List<HomeBean.DataBean.ItemsBean.ListBeanX> datas = new ArrayList<>();

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        HomeBean.DataBean.ItemsBean.ListBeanX listBean = datas.get(position);
        int type = listBean.getHome_type();
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 1:
                viewHolder = new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.item_hometype1, parent, false));
                break;
            case 2:
                viewHolder = new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.item_hometype2, parent, false));
                break;
            case 4:
                viewHolder = new ViewHolder4(LayoutInflater.from(context).inflate(R.layout.item_hometype4, parent, false));
                break;
            case 6:
                viewHolder = new ViewHolder6(LayoutInflater.from(context).inflate(R.layout.item_hometype6, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseHolder = (BaseViewHolder) holder;
        baseHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void refresh(List<HomeBean.DataBean.ItemsBean.ListBeanX> list) {
        this.datas = list;
    }


    class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            HomeBean.DataBean.ItemsBean.ListBeanX listBean = datas.get(position);
        }
    }


    class ViewHolder1 extends BaseViewHolder {
        @BindView(R.id.iv_hometype6)
        ImageView ivHometype1;

        public ViewHolder1(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            super.setData(position);
            final HomeBean.DataBean.ItemsBean.ListBeanX listBean = datas.get(position);
            String pic_url = listBean.getOne().getPic_url();
            Picasso.with(context).load(pic_url).into(ivHometype1);
            ivHometype1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClicked(listBean.getOne().getTopic_url(), listBean.getOne().getTopic_name(), listBean.getOne().getContent_id());
                    }
                }
            });
        }
    }


    class ViewHolder2 extends BaseViewHolder {
        @BindView(R.id.iv1_hometype2)
        ImageView iv1Hometype2;
        @BindView(R.id.iv2_hometype2)
        ImageView iv2Hometype2;

        public ViewHolder2(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            super.setData(position);
            final HomeBean.DataBean.ItemsBean.ListBeanX listBean = datas.get(position);
            Picasso.with(context).load(listBean.getOne().getPic_url()).into(iv1Hometype2);
            Picasso.with(context).load(listBean.getTwo().getPic_url()).into(iv2Hometype2);


            iv1Hometype2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(listBean.getOne().getTopic_url(), listBean.getOne().getTopic_name(), listBean.getOne().getContent_id());
                }
            });

            iv2Hometype2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(listBean.getTwo().getTopic_url(), listBean.getTwo().getTopic_name(), listBean.getTwo().getContent_id());
                }
            });


        }
    }


    class ViewHolder4 extends BaseViewHolder {
        @BindView(R.id.iv_left_top)
        ImageView ivLeftTop;
        @BindView(R.id.iv_left_bot)
        ImageView ivLeftBot;
        @BindView(R.id.iv_right_top)
        ImageView ivRightTop;
        @BindView(R.id.iv_right_bot)
        ImageView ivRightBot;

        public ViewHolder4(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            super.setData(position);

            final HomeBean.DataBean.ItemsBean.ListBeanX listBean = datas.get(position);
            Picasso.with(context).load(listBean.getOne().getPic_url()).into(ivLeftTop);
            Picasso.with(context).load(listBean.getTwo().getPic_url()).into(ivLeftBot);
            Picasso.with(context).load(listBean.getThree().getPic_url()).into(ivRightTop);
            Picasso.with(context).load(listBean.getFour().getPic_url()).into(ivRightBot);

            ivLeftTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(listBean.getOne().getTopic_url(), listBean.getOne().getTopic_name(), listBean.getOne().getContent_id());
                }
            });

            ivLeftBot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(listBean.getTwo().getTopic_url(), listBean.getTwo().getTopic_name(), listBean.getTwo().getContent_id());
                }
            });

            ivRightTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(listBean.getThree().getTopic_url(), listBean.getThree().getTopic_name(), listBean.getThree().getContent_id());
                }
            });

            ivRightBot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(listBean.getFour().getTopic_url(), listBean.getFour().getTopic_name(), listBean.getFour().getContent_id());
                }
            });


        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public interface OnItemClickListener {
        void onItemClicked(String topic_url, String Topic_name, String content_id);
    }


    class ViewHolder6 extends BaseViewHolder {
        @BindView(R.id.iv_hometype6)
        ImageView ivHometype6;
        public ViewHolder6(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
            super.setData(position);
            final HomeBean.DataBean.ItemsBean.ListBeanX listBean = datas.get(position);
            String pic_url = listBean.getPic_url();
            Picasso.with(context).load(pic_url).into(ivHometype6);
            ivHometype6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        MainActivity mainActivity = (MainActivity) context;
                        StoreTypeDetailsFragment storeTypeDetailsFragment = new StoreTypeDetailsFragment(listBean.getHome_id()+"");
                        mainActivity.replaceFragment(storeTypeDetailsFragment);
                    }
                }
            });
        }
    }
}
