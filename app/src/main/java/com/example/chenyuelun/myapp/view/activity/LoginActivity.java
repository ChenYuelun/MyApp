package com.example.chenyuelun.myapp.view.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.common.Modle;
import com.example.chenyuelun.myapp.modle.bean.UserInfo;
import com.example.chenyuelun.myapp.utils.HttpUtils;
import com.example.chenyuelun.myapp.utils.SpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;

import butterknife.BindView;
import okhttp3.Call;

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

    @Override
    public void initData() {

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
                String phone = etPhont.getText().toString().trim();
                String password = etCode.getText().toString().trim();
                if (!isNoEmpty(phone, password)) {
                    HttpUtils.post(AppUrl.REGISTER_URl, phone, password, new HttpUtils.OnHttpListener() {
                        @Override
                        public void onResponse(String response, int id) {
                            UiUtils.showToast("注册联网成功：" + response.toString());
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("TAG", "注册联网失败:" + e.getMessage());
                        }
                    });
                }
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
}
