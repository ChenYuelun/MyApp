package com.example.chenyuelun.myapp.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.Modle;
import com.example.chenyuelun.myapp.modle.bean.CartBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.adapter.CartRvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShoppingCratActivity extends BaseActivity {


    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cart_edit)
    TextView tvCartEdit;
    @BindView(R.id.rv_goodsList)
    RecyclerView rvGoodsList;
    @BindView(R.id.tv_rea)
    TextView tvRea;
    @BindView(R.id.tv_reach)
    TextView tvReach;
    @BindView(R.id.tv_dis)
    TextView tvDis;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_p)
    TextView tvP;
    @BindView(R.id.tv_pack_no)
    TextView tvPackNo;
    @BindView(R.id.tv_pack)
    TextView tvPack;
    @BindView(R.id.tv_s)
    TextView tvS;
    @BindView(R.id.tv_ship_no)
    TextView tvShipNo;
    @BindView(R.id.tv_ship)
    TextView tvShip;
    @BindView(R.id.cb_check_all)
    CheckBox cbCheckAll;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.bt_pay)
    Button btPay;
    private CartRvAdapter cartRvAdapter;
    private List<CartBean> datas;
    private boolean isEdit = false;
    private AlertDialog show;

    @Override
    public void initData() {
        cartRvAdapter = new CartRvAdapter(ShoppingCratActivity.this);
        rvGoodsList.setAdapter(cartRvAdapter);
        rvGoodsList.setLayoutManager(new LinearLayoutManager(ShoppingCratActivity.this, LinearLayoutManager.VERTICAL, false));
        readDataFromDB();


    }

    private void readDataFromDB() {
        Modle.getInstance().getThread().execute(new Runnable() {
            @Override
            public void run() {
                datas = Modle.getInstance().getCartDao().getAllCartData();
                if (datas != null && datas.size() > 0) {
                    UiUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cartRvAdapter.refresh(datas, isEdit);
                            tvDiscount.setText("-￥:" + getTotalDis());
                            tvTotalPrice.setText("￥:" + getTotalPrice());
                            isCheckedAllOrNo();
                        }
                    });

                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shopping_crat;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        tvTitle.setText("购物车");
        ivTitleBack.setVisibility(View.VISIBLE);
        tvCartEdit.setVisibility(View.VISIBLE);

    }

    @Override
    public void initListener() {
        super.initListener();
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cbCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = cbCheckAll.isChecked();
                chckedAll(checked);
                tvDiscount.setText("-￥:" + getTotalDis());
                tvTotalPrice.setText("￥:" + getTotalPrice());
            }
        });
        cartRvAdapter.setOnItemClickListener(new CartRvAdapter.OnItemClickListener() {
            @Override
            public void onItemChecked(int position, boolean isChecked) {
                //当某个Item选中状态改变的时候  重新计算总价 折扣 是否全选
                datas.get(position).setCheck(isChecked);
                isCheckedAllOrNo();
                tvDiscount.setText("￥:-" + getTotalDis());
                tvTotalPrice.setText("￥:" + getTotalPrice());
            }

            @Override
            public void onItemImageClicked(int position) {
                String goods_id = datas.get(position).getGoods_id();
                Intent intent = new Intent(ShoppingCratActivity.this, GoodsInfoActivity.class);
                intent.putExtra("goodsId", goods_id);
                startActivity(intent);
            }

            @Override
            public void onDeleteClicked(final int position) {
                final LinearLayout dialog = (LinearLayout) View.inflate(ShoppingCratActivity.this, R.layout.dialog, null);
                RelativeLayout rel = (RelativeLayout) dialog.getChildAt(0);
                ImageView close = (ImageView) rel.getChildAt(0);
                TextView confirm = (TextView) dialog.getChildAt(3);
                show = new AlertDialog.Builder(ShoppingCratActivity.this)
                        .setView(dialog)
                        .show();

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                        show = null;
                    }
                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteGoods(position);
                        show.dismiss();
                        show = null;
                    }
                });
            }

            @Override
            public void onItemCountChanged(int position, int newValue) {
                CartBean cartBean = datas.get(position);
                cartBean.setCount(newValue);
            }
        });

        tvCartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果不是编辑状态  就进入编辑状态
                editAndConfirmCart();
            }
        });


        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("结算");
                ArrayList<CartBean> list = new ArrayList<CartBean>();
                for (int i = 0; i < datas.size(); i++) {
                    CartBean cartBean = datas.get(i);
                    if(cartBean.isCheck()) {
                        list.add(cartBean);
                    }
                }
                Intent intent = new Intent(ShoppingCratActivity.this, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods",list);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void editAndConfirmCart() {
        if (!isEdit) {
            isEdit = true;
            tvCartEdit.setText("完成");
            readDataFromDB();
            cartRvAdapter.refresh(datas, isEdit);

        } else {
            isEdit = false;
            tvCartEdit.setText("编辑");
            cartRvAdapter.refresh(datas, isEdit);
            Modle.getInstance().getThread().execute(new Runnable() {
                @Override
                public void run() {
                    Modle.getInstance().getCartDao().replaceList(datas);
                }
            });

        }
    }

    //从购物车删除商品
    private void deleteGoods(int position) {
        Modle.getInstance().getCartDao().delete(datas.get(position));
        datas.remove(position);
        cartRvAdapter.refresh(datas,isEdit);
//        readDataFromDB();
    }


    //获取购物车选中商品的总价
    private double getTotalPrice() {
        double totalPrice = 0.00;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                CartBean cartBean = datas.get(i);
                if (cartBean.isCheck()) {
                    String discount = cartBean.getDiscount();
                    if (TextUtils.isEmpty(discount)) {
                        totalPrice += cartBean.getCount() * Double.parseDouble(cartBean.getPrice());
                    } else {
                        totalPrice += cartBean.getCount() * Double.parseDouble(discount);
                    }
                }

            }
        }
        return totalPrice;
    }

    //获取总折扣价格
    private double getTotalDis() {
        double totalDis = 0.00;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                CartBean cartBean = datas.get(i);
                if (cartBean.isCheck()) {
                    String discount = cartBean.getDiscount();
                    if (!TextUtils.isEmpty(discount)) {
                        totalDis += cartBean.getCount() * (Double.parseDouble(cartBean.getPrice()) - Double.parseDouble(discount));
                    }
                }
            }
        }
        return totalDis;
    }

    //验证是否全选
    public void isCheckedAllOrNo() {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                CartBean cartBean = datas.get(i);
                //只要有一个的选中状态不是true 就取消全选的选中
                if (!cartBean.isCheck()) {
                    cbCheckAll.setChecked(false);
                    return;
                }
            }
            //能执行到这就说明全部选中
            cbCheckAll.setChecked(true);
        }
    }

    //点击全选的时候调用此方法 将集合内的备案对象的属性全部变为true或者fales 然后刷新适配器
    public void chckedAll(boolean checked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setCheck(checked);
            }
            cartRvAdapter.refresh(datas, isEdit);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isEdit) {
                editAndConfirmCart();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        readDataFromDB();
    }
}
