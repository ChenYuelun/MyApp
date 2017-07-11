package com.example.chenyuelun.myapp.view.fragment.self;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.Modle;
import com.example.chenyuelun.myapp.modle.bean.UserInfo;
import com.example.chenyuelun.myapp.utils.SpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.activity.LoginActivity;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class SelfFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @BindView(R.id.iv_userimage)
    ImageView ivUserimage;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.fl_myHeart)
    FrameLayout flMyHeart;
    @BindView(R.id.fl_myMsg)
    FrameLayout flMyMsg;
    @BindView(R.id.fl_myAddress)
    FrameLayout flMyAddress;
    @BindView(R.id.fl_service)
    FrameLayout flService;
    private boolean isLogin;
    private UserInfo userInfo;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_self;
    }

    @Override
    protected void setData(String response) {

    }

    @Override
    public void initData() {
        isLogin = (boolean) SpUtils.getSpUtils().get(SpUtils.IS_LOGIN,false);
        if(isLogin) {
            String userPhone = (String) SpUtils.getSpUtils().get(SpUtils.CURRENT_USER, "");
            userInfo = Modle.getInstance().getAccountDao().getUser(userPhone);
            String userName = userInfo.getUserName();
            tvLogin.setText(TextUtils.isEmpty(userName) ? "帅气的用户" : userName);
            String image = userInfo.getImage();
            if(image != null) {
                UiUtils.loadImage(getContext(),image,ivUserimage,1);
            }

        }else {
            tvLogin.setText("登录/注册");
            ivUserimage.setImageResource(R.drawable.ic_self_avatar);
        }



    }

    @Override
    public void initListener() {
        super.initListener();

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOrSetting();
            }
        });

        ivTitleSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginOrSetting();
            }
        });
        flMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("地址管理");
            }
        });
        flMyHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("我的心愿清单");
            }
        });
        flMyMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("我的消息");
            }
        });
        flService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtils.showToast("良仓客服");
            }
        });
    }

    //进入登录或者用户信息设置界面
    private void loginOrSetting() {
        if(!isLogin) {
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getActivity(),AccountSettingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("userInfo",userInfo);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void initTitle() {
        tvTitle.setText("我的账户");
        ivTitleSetting.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
