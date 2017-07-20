package com.example.chenyuelun.myapp.view.fragment.store;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomScanActivity extends BaseActivity implements DecoratedBarcodeView.TorchListener {

        // 添加一个按钮用来控制闪光灯，同时添加两个按钮表示其他功能，先用Toast表示

        @BindView(R.id.btn_switch)
        Button btnSwitch;
        @BindView(R.id.btn_hint1)
        Button btnHint1;
        @BindView(R.id.btn_hint2)
        Button btnHint2;
        @BindView(R.id.dbv_custom)
        DecoratedBarcodeView mDBV;

        private CaptureManager captureManager;
        private boolean isLightOn = false;
    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBV.setTorchListener((DecoratedBarcodeView.TorchListener) this);


        // 如果没有闪光灯功能，就去掉相关按钮
        if (!hasFlash()) {
            btnSwitch.setVisibility(View.GONE);
        }

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_scan;
    }

    //手电筒状态的监听
    @Override
    public void onTorchOn() {
        Toast.makeText(this, "torch on", Toast.LENGTH_LONG).show();
        isLightOn = true;
    }

    //手电筒状态的监听
    @Override
    public void onTorchOff() {
        Toast.makeText(this, "torch off", Toast.LENGTH_LONG).show();
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
    // 点击切换闪光灯
    @OnClick({R.id.btn_switch, R.id.btn_hint1, R.id.btn_hint2, R.id.dbv_custom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_switch:
                if(isLightOn){
                    mDBV.setTorchOff();
                }else{
                    mDBV.setTorchOn();
                }
                break;
            case R.id.btn_hint1:
                break;
            case R.id.btn_hint2:
                break;
            case R.id.dbv_custom:
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
