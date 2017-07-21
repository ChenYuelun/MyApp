package com.example.chenyuelun.myapp.view.activity;

import android.app.Dialog;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.Modle;
import com.example.chenyuelun.myapp.modle.bean.UserInfo;
import com.example.chenyuelun.myapp.utils.SpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.mob.MobSDK;
import com.mob.tools.utils.ResHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import butterknife.BindView;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.layout.SendMsgDialogLayout;
import cn.smssdk.utils.SMSLog;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_login_back)
    ImageView ivLoginBack;
    @BindView(R.id.et_phont)
    EditText etPhont;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_getCode)
    TextView tvGetCode;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_morelogin)
    TextView tvMorelogin;
    private EventHandler handler;
    private boolean ready;
    private OnSendMessageHandler osmHandler;
    private Dialog pd;
    private CountDownTimer timer;

    @Override
    public void initView() {
        super.initView();
        handler = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.e("TAG", "验证码提交成功");
                        afterSubmit(result, data);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(handler); //注册短信回调
    }

    @Override
    public void initData() {
        MobSDK.init(LoginActivity.this, "1f5f691aeb954", "1e4c0422b1e5a61bf2497bca8543f188");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initListener() {
        super.initListener();
        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 请求发送短信验证码

                String phone = etPhont.getText().toString().trim().replaceAll("\\s*", "");
                String code = "+86";
                if (TextUtils.isEmpty(phone)) {
                    UiUtils.showToast("电话号码不能为空");
                    return;
                }
                showDialog(phone, code);


            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhont.getText().toString().trim();
                String ccode = "+86";
//                SMSSDK.submitVerificationCode(ccode,phone,code);
//                UiUtils.showToast("验证码已提交");
                // 提交验证码
                String verificationCode = etCode.getText().toString().trim();

                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(verificationCode)) {
                    UiUtils.showToast("手机号码和验证码不能为空");
                    return;
                }
                if (!TextUtils.isEmpty(ccode)) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    pd = CommonDialog.ProgressDialog(LoginActivity.this);
                    if (pd != null) {
                        pd.show();
                    }
                    SMSSDK.submitVerificationCode(ccode, phone, verificationCode);
                    Log.e("TAG", "验证码已提交");
                } else {
                    int resId = ResHelper.getStringRes(LoginActivity.this, "smssdk_write_identify_code");
                    if (resId > 0) {
                        Toast.makeText(LoginActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        ivLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 提交用户信息 保存到数据库
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        UiUtils.showToast("登陆成功");
        SpUtils.getSpUtils().put(SpUtils.IS_LOGIN, true);
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo.setImage(AVATARS[id % 12]);
        Modle.getInstance().loginSuccess(userInfo);
        finish();
    }


    //弹出对话框  发送验证码
    public void showDialog(final String phone, final String code) {
        int resId = ResHelper.getStyleRes(LoginActivity.this, "CommonDialog");
        if (resId > 0) {
            String phoneNum = code + " " + this.splitPhoneNum(phone);
            final Dialog dialog = new Dialog(LoginActivity.this, resId);
            LinearLayout layout = SendMsgDialogLayout.create(LoginActivity.this);
            if (layout != null) {
                dialog.setContentView(layout);
                ((TextView) dialog.findViewById(ResHelper.getIdRes(LoginActivity.this, "tv_phone"))).setText(phoneNum);
                TextView tv = (TextView) dialog.findViewById(ResHelper.getIdRes(LoginActivity.this, "tv_dialog_hint"));
                resId = ResHelper.getStringRes(LoginActivity.this, "smssdk_make_sure_mobile_detail");
                if (resId > 0) {
                    String text = LoginActivity.this.getString(resId);
                    tv.setText(Html.fromHtml(text));
                }

                ((Button) dialog.findViewById(ResHelper.getIdRes(LoginActivity.this, "btn_dialog_ok"))).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (LoginActivity.this.pd != null && LoginActivity.this.pd.isShowing()) {
                            LoginActivity.this.pd.dismiss();
                        }

                        LoginActivity.this.pd = CommonDialog.ProgressDialog(LoginActivity.this);
                        if (LoginActivity.this.pd != null) {
                            LoginActivity.this.pd.show();
                        }

                        SMSLog.getInstance().i("verification phone ==>>" + phone, new Object[0]);
                        SMSSDK.getVerificationCode(code, phone.trim());
                        Log.e("TAG", "验证码已获取");

                        CountDown();
                    }
                });
                ((Button) dialog.findViewById(ResHelper.getIdRes(LoginActivity.this, "btn_dialog_cancel"))).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        }

    }

    private void CountDown() {
        tvGetCode.setEnabled(false);
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(final long millisUntilFinished) {
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(timer != null) {
                            tvGetCode.setText(((millisUntilFinished) / 1000) + "");
                        }
                    }
                });
            }

            @Override
            public void onFinish() {
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(timer != null) {
                            tvGetCode.setEnabled(true);
                            tvGetCode.setText("获取验证码");
                        }
                    }
                });

            }
        }.start();
    }

    private String splitPhoneNum(String phone) {
        StringBuilder builder = new StringBuilder(phone);
        builder.reverse();
        int i = 4;

        for (int len = builder.length(); i < len; i += 5) {
            builder.insert(i, ' ');
        }

        builder.reverse();
        return builder.toString();
    }


    // 短信注册，随机产生头像
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };


    @Override
    protected void onResume() {
        super.onResume();
        SMSSDK.registerEventHandler(this.handler);
    }

    @Override
    protected void onDestroy() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        super.onDestroy();
        SMSSDK.unregisterEventHandler(this.handler);


    }


    /**
     * 提交验证码成功后的执行事件
     *
     * @param result
     * @param data
     */
    private void afterSubmit(final int result, final Object data) {
        Log.e("TAG", "提交验证码成功后续操作");
        UiUtils.runOnUiThread(new Runnable() {
            public void run() {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.e("TAG", "验证码正确");
                    UiUtils.showToast("登陆成功");
                    registerUser("", etPhont.getText().toString().trim());
                    finish();
                } else {
                    ((Throwable) data).printStackTrace();
                    // 验证码不正确
                    Log.e("TAG", "验证码错误");
                    String message = ((Throwable) data).getMessage();
                    int resId = 0;
                    try {
                        JSONObject json = new JSONObject(message);
                        int status = json.getInt("status");
                        resId = ResHelper.getStringRes(LoginActivity.this,
                                "smssdk_error_detail_" + status);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (resId == 0) {
                        resId = ResHelper.getStringRes(LoginActivity.this, "smssdk_virificaition_code_wrong");
                    }
                    if (resId > 0) {
                        Toast.makeText(LoginActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
