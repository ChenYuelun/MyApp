package com.example.chenyuelun.myapp.view.activity;

import com.bumptech.glide.Glide;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;

import butterknife.BindView;

public class PhotoActivity extends BaseActivity {


    @BindView(R.id.photoview)
    com.github.chrisbanes.photoview.PhotoView photoview;

    @Override
    public void initData() {
        String image = getIntent().getStringExtra("image");
        photoview.setImageResource(R.drawable.magazine_lucency);

        Glide.with(this).load(image).into(photoview);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
    }
}
