package com.example.chenyuelun.myapp.view.fragment.self;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.modle.bean.UserInfo;
import com.example.chenyuelun.myapp.utils.SpUtils;

import butterknife.BindView;

public class AccountSettingActivity extends BaseActivity {


    @BindView(R.id.tv_image)
    TextView tvImage;
    @BindView(R.id.iv_userimage)
    ImageView ivUserimage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_setname)
    LinearLayout llSetname;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.ll_set_info)
    LinearLayout llSetInfo;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    private UserInfo userInfo;

    @Override
    public void initData() {
        userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
        String userName = userInfo.getUserName();
        tvName.setText(TextUtils.isEmpty(userName)?"帅气的用户":userName);
        tvPhone.setText(userInfo.getPhone());
        tvInfo.setText(userInfo.getInfo());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_setting;
    }

    @Override
    public void initListener() {
        super.initListener();
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AccountSettingActivity.this)
                            .setMessage("确认退出吗")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SpUtils.getSpUtils().put(SpUtils.IS_LOGIN,false);
                                    SpUtils.getSpUtils().put(SpUtils.CURRENT_USER,"");
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
            }
        });
    }
}
