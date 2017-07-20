package com.example.chenyuelun.myapp.view.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.utils.UiUtils;

import butterknife.BindView;

public class ERCodeActivity extends BaseActivity {


    @BindView(R.id.iv_ercode)
    ImageView ivErcode;

    @Override
    public void initData() {
        String goodsInfo = getIntent().getStringExtra("goodsInfo");
        Log.e("TAG", goodsInfo);
        Bitmap bitmap = UiUtils.getEncodeAsBitmap(goodsInfo, 400, 400);
        if(bitmap == null) {
            Log.e("TAG", "bitmap==null");
        }
        ivErcode.setImageBitmap(bitmap);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ercode;
    }
}
