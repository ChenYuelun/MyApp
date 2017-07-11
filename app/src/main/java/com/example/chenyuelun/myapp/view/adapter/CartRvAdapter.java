package com.example.chenyuelun.myapp.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.modle.bean.CartBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.view.AddSubView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyuelun on 2017/7/10.
 */

public class CartRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;


    private List<CartBean> datas = new ArrayList<>();
    private OnItemClickListener listener;
    private boolean isEdit;

    public CartRvAdapter(Context context) {
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
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

    public void refresh(List<CartBean> allCartData, boolean isEdit) {
        this.datas.clear();
        this.datas.addAll(allCartData);
        this.isEdit = isEdit;
        notifyDataSetChanged();

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cb_check)
        CheckBox cbCheck;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_goodsname)
        TextView tvGoodsname;
        @BindView(R.id.tv_type_size)
        TextView tvTypeSize;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ll_noEdit)
        LinearLayout llNoEdit;
        @BindView(R.id.cb_check_edit)
        CheckBox cbCheckEdit;
        @BindView(R.id.iv_image_edit)
        ImageView ivImageEdit;
        @BindView(R.id.add_sub_view)
        AddSubView addSubView;
        @BindView(R.id.tv_goodsname_edit)
        TextView tvGoodsnameEdit;
        @BindView(R.id.tv_price_edit)
        TextView tvPriceEdit;
        @BindView(R.id.tv_delete_edit)
        TextView tvDeleteEdit;
        @BindView(R.id.ll_edit)
        LinearLayout llEdit;
        private CartBean cartBean;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final CartBean cartBean) {
            if (!isEdit) {
                //非编辑状态显示的布局设置
                llEdit.setVisibility(View.GONE);
                llNoEdit.setVisibility(View.VISIBLE);
                this.cartBean = cartBean;
                if (cartBean.isCheck()) {
                    cbCheck.setChecked(true);
                } else {
                    cbCheck.setChecked(false);
                }
                UiUtils.loadImage(context, cartBean.getImage(), ivImage, 0);
                tvGoodsname.setText(cartBean.getOwner_name() + cartBean.getGoods_name());
                String type_name = cartBean.getType_name();
                String type = cartBean.getType();
                String size_name = cartBean.getSize_name();
                String size = cartBean.getSize();
                if (TextUtils.isEmpty(size_name)) {
                    tvTypeSize.setText(type_name + ":" + type);
                } else {
                    tvTypeSize.setText(type_name + ":" + type + ";" + size_name + ":" + size);
                }
                String discount = cartBean.getDiscount();
                if (TextUtils.isEmpty(discount)) {
                    tvPrice.setText("￥" + cartBean.getPrice());
                    tvOldPrice.setVisibility(View.GONE);
                } else {
                    tvPrice.setText("￥" + cartBean.getDiscount());
                    tvOldPrice.setText("￥" + cartBean.getPrice());
                    tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                tvCount.setText("x" + cartBean.getCount());

                cbCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemChecked(getLayoutPosition(), cbCheck.isChecked());
                        }
                    }
                });
                ivImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemImageClicked(getLayoutPosition());
                        }
                    }
                });
            } else {
                //编辑状态的属性设置
                addSubView.setValue(cartBean.getCount());
                llEdit.setVisibility(View.VISIBLE);
                llNoEdit.setVisibility(View.GONE);
                if (cartBean.isCheck()) {
                    cbCheckEdit.setChecked(true);
                } else {
                    cbCheckEdit.setChecked(false);
                }
                UiUtils.loadImage(context, cartBean.getImage(), ivImageEdit, 0);
                tvGoodsnameEdit.setText(cartBean.getOwner_name() + cartBean.getGoods_name());
                String discount = cartBean.getDiscount();
                if (TextUtils.isEmpty(discount)) {
                    tvPriceEdit.setText("￥" + cartBean.getPrice());

                } else {
                    tvPriceEdit.setText("￥" + cartBean.getDiscount());
                }
                cbCheckEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemChecked(getLayoutPosition(), cbCheckEdit.isChecked());
                    }
                });

                tvDeleteEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener!= null) {
                            listener.onDeleteClicked(getLayoutPosition());
                        }
                    }
                });

                addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
                    @Override
                    public void onNumberChange(int number) {
                        if(listener != null) {
                            listener.onItemCountChanged(getLayoutPosition(),addSubView.getValue());
                        }
                    }
                });

            }


        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemChecked(int layoutPosition, boolean isChecked);

        void onItemImageClicked(int position);

        void onDeleteClicked(int position);

        void onItemCountChanged(int layoutPosition, int newValue);
    }


}
