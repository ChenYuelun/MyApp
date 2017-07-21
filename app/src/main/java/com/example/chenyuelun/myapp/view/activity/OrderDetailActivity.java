package com.example.chenyuelun.myapp.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chaychan.library.ExpandableLinearLayout;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.Modle;
import com.example.chenyuelun.myapp.modle.bean.CartBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.utils.pay.AuthResult;
import com.example.chenyuelun.myapp.utils.pay.OrderInfoUtil2_0;
import com.example.chenyuelun.myapp.utils.pay.PayKeys;
import com.example.chenyuelun.myapp.utils.pay.PayResult;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_djq)
    TextView tvDjq;
    @BindView(R.id.tv_pack)
    TextView tvPack;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_jiesheng)
    TextView tvJiesheng;
    @BindView(R.id.bt_pay)
    Button btPay;
    @BindView(R.id.tv_choice_addr)
    TextView tvChoiceAddr;
    @BindView(R.id.ell_product)
    ExpandableLinearLayout ellProduct;
    private ArrayList<CartBean> goods;

    @Override
    public void initData() {
        goods = (ArrayList<CartBean>) getIntent().getSerializableExtra("goods");
        if (goods != null && goods.size() > 0) {
            ellProduct.setVisibility(View.VISIBLE);
            ListData();
        } else {
            ellProduct.setVisibility(View.GONE);
        }


        double totalPrice = getTotalPrice();
        tvPrice.setText("￥:" + totalPrice);
        double totalDis = getTotalDis();
        tvDiscount.setText("-￥:" + totalDis);
        tvTotalPrice.setText("总计:￥" + (totalPrice - totalDis));
        tvJiesheng.setText("已节省:￥" + totalDis);
    }

    private void ListData() {
        for (int i = 0; i < goods.size(); i++) {
            View view = View.inflate(this, R.layout.item_lv_order, null);
            ViewHolder viewHolder = new ViewHolder(this,view);
            viewHolder.setData(goods.get(i));
            ellProduct.addItem(view);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initTitle() {
        super.initTitle();
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("订单详情");
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

        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pay();
                payV2();
                Modle.getInstance().getCartDao().deleteGoods(goods);
            }
        });

        tvChoiceAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderDetailActivity.this, EditAddressActivity.class));
            }
        });

    }


    //获取购物车选中商品的总原价
    private double getTotalPrice() {
        double totalPrice = 0.00;
        if (goods != null && goods.size() > 0) {
            for (int i = 0; i < goods.size(); i++) {
                CartBean cartBean = goods.get(i);
                if (cartBean.isCheck()) {
                    totalPrice += cartBean.getCount() * Double.parseDouble(cartBean.getPrice());
                }

            }
        }
        return totalPrice;
    }

    //获取总折扣价格
    private double getTotalDis() {
        double totalDis = 0.00;
        if (goods != null && goods.size() > 0) {
            for (int i = 0; i < goods.size(); i++) {
                CartBean cartBean = goods.get(i);
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


//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = 0;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, View.MeasureSpec.makeMeasureSpec(0,
//                    View.MeasureSpec.UNSPECIFIED));
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }


    static class ViewHolder {
        private final Context context;
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

        ViewHolder(Context context, View view) {
            this.context = context;
            ButterKnife.bind(this, view);
        }
        public void setData(CartBean cartBean){
            tvCount.setText("x" + cartBean.getCount());
            tvGoodsname.setText(cartBean.getGoods_name());
            UiUtils.loadImage(context, cartBean.getImage(), ivImage, 0);
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
        }
    }


    /*********************************************************************************/
    // 支付宝支付
    public static final String APPID = PayKeys.APPID;
    public static final String PID = PayKeys.PID;
    //商户PID
    public static final String PARTNER = PayKeys.DEFAULT_PARTNER;
    //商户收款账号
    public static final String SELLER = PayKeys.DEFAULT_SELLER;
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = PayKeys.PRIVATE;
    public static final String RSA2_PRIVATE = "";
    //支付宝公钥
    public static final String RSA_PUBLIC = PayKeys.PUBLIC;


//    private static final int SDK_PAY_FLAG = 1;
//
//    private static final int SDK_CHECK_FLAG = 2;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
//

//
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((String) msg.obj);
//
//                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//                    String resultInfo = payResult.getResult();
//
//                    String resultStatus = payResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        Toast.makeText(OrderDetailActivity.this, "支付成功",
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        // 判断resultStatus 为非“9000”则代表可能支付失败
//                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(OrderDetailActivity.this, "支付结果确认中",
//                                    Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            Toast.makeText(OrderDetailActivity.this, "支付失败",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                    break;
//                }
//                case SDK_CHECK_FLAG: {
//                    Toast.makeText(OrderDetailActivity.this, "检查结果为：" + msg.obj,
//                            Toast.LENGTH_SHORT).show();
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//
//        ;
//    };
//
//
//    /**
//     * call alipay sdk pay. 调用SDK支付
//     */
//    public void pay() {
//        // 订单
//        String orderInfo = getOrderInfo("测试的商品", goods.get(0).getGoods_name() + "等" + goods.size() + "件商品", "0.01");
//
//        // 对订单做RSA 签名
//        String sign = sign(orderInfo);
//        try {
//            // 仅需对sign 做URL编码
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        // 完整的符合支付宝参数规范的订单信息
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//                + getSignType();
//
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask alipay = new PayTask(OrderDetailActivity.this);
//                // 调用支付接口，获取支付结果
//                String result = alipay.pay(payInfo);
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    /**
//     * create the order info. 创建订单信息
//     */
//    public String getOrderInfo(String subject, String body, String price) {
//        // 签约合作者身份ID
//        String orderInfo = "partner=" + "\"" + PARTNER + "\"";
//
//        // 签约卖家支付宝账号
//        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
//
//        // 商户网站唯一订单号
//        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
//
//        // 商品名称
//        orderInfo += "&subject=" + "\"" + subject + "\"";
//
//        // 商品详情
//        orderInfo += "&body=" + "\"" + body + "\"";
//
//        // 商品金额
//        orderInfo += "&total_fee=" + "\"" + price + "\"";
//
//        // 服务器异步通知页面路径
//        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
//                + "\"";
//
//        // 服务接口名称， 固定值
//        orderInfo += "&service=\"mobile.securitypay.pay\"";
//
//        // 支付类型， 固定值
//        orderInfo += "&payment_type=\"1\"";
//
//        // 参数编码， 固定值
//        orderInfo += "&_input_charset=\"utf-8\"";
//
//        // 设置未付款交易的超时时间
//        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//        // 取值范围：1m～15d。
//        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//        // 该参数数值不接受小数点，如1.5h，可转换为90m。
//        orderInfo += "&it_b_pay=\"30m\"";
//
//        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
//
//        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//        orderInfo += "&return_url=\"m.alipay.com\"";
//
//        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//        // orderInfo += "&paymethod=\"expressGateway\"";
//
//        return orderInfo;
//    }
//
//
//    /**
//     * sign the order info. 对订单信息进行签名
//     *
//     * @param content 待签名订单信息
//     */
//    public String sign(String content) {
//        return SignUtils.sign(content, RSA_PRIVATE);
//    }
//
//    /**
//     * get the sign type we use. 获取签名方式
//     */
//    public String getSignType() {
//        return "sign_type=\"RSA\"";
//    }
//
//    /**
//     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
//     */
//    public String getOutTradeNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//                Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
//        return key;
//    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(OrderDetailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OrderDetailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(OrderDetailActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(OrderDetailActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void payV2() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, false);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderDetailActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
