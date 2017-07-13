package com.example.chenyuelun.myapp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.Modle;
import com.example.chenyuelun.myapp.modle.bean.UserInfo;
import com.example.chenyuelun.myapp.utils.SpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.mob.MobSDK;

import java.util.HashMap;

import butterknife.BindView;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

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
    private EventHandler eventHandler;
    private boolean ready;
    private OnSendMessageHandler osmHandler;


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
                showDialog(phone, code);


            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhont.getText().toString().trim();
                String password = etCode.getText().toString().trim();
                if (isNoEmpty(phone, password)) {
                    UiUtils.showToast("登陆成功");
                    SpUtils.getSpUtils().put(SpUtils.IS_LOGIN, true);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setPhone(phone);
                    Modle.getInstance().loginSuccess(userInfo);
                    finish();
                    // 提交验证码
                    HashMap<String, Object> resp = new HashMap<String, Object>();
                    resp.put("country", password);
                    resp.put("phone", phone);
                    afterSubmit(resp);
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

    /**
     * 提交验证码成功后的执行事件
     *
     * @param
     * @param data
     */
    private void afterSubmit(final Object data) {
        UiUtils.runOnUiThread(new Runnable() {
            public void run() {
                HashMap<String, Object> res = new HashMap<String, Object>();
                res.put("res", true);
                res.put("page", 2);
                res.put("phone", data);
                HashMap<String, Object> phoneMap = (HashMap<String, Object>) res.get("phone");
                if (eventHandler != null) {
                    eventHandler.afterEvent(
                            SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE,
                            SMSSDK.RESULT_COMPLETE, phoneMap);
                }

            }
        });
    }

    private boolean isNoEmpty(String phone, String password) {

        if (TextUtils.isEmpty(phone)) {
            UiUtils.showToast("手机号不能为空！");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            UiUtils.showToast("验证码不能为空！");
            return false;
        }

        return true;
    }


    /**
     * 是否请求发送验证码，对话框
     */
    public void showDialog(final String phone, final String code) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("验证码")
                .setMessage("是否发送验证码到" + code + phone)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SMSSDK.getVerificationCode(code, phone.trim(), osmHandler);
                    }
                })
                .setNegativeButton("取消", null)
                .show();


    }


    //在oncreate中注册监听
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerSDK();
    }

    private void registerSDK() {
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
                    // 短信注册成功后，返回MainActivity,然后提示新好友
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        SpUtils.getSpUtils().put(SpUtils.IS_LOGIN, true);
                        UserInfo userInfo = new UserInfo();
                        userInfo.setPhone(etPhont.getText().toString().trim());
                        Modle.getInstance().loginSuccess(userInfo);
                        finish();
                        UiUtils.showToast("登录成功");
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                } else if (event == SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT){
                    if (result == SMSSDK.RESULT_COMPLETE) {
//                refreshViewCount(data);
//                gettingFriends = false;
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        };
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
        ready = true;
    }
}
