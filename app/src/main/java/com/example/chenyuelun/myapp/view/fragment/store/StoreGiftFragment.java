package com.example.chenyuelun.myapp.view.fragment.store;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreGiftFragment extends BaseFragment {

    @BindView(R.id.iv_liangcangjingxuan)
    ImageView ivLiangcangjingxuan;
    @BindView(R.id.iv_jieri)
    ImageView ivAiqing;
    @BindView(R.id.iv_loving)
    ImageView ivLoving;
    @BindView(R.id.iv_birthday)
    ImageView ivJieri;
    @BindView(R.id.iv_friend)
    ImageView ivFriend;
    @BindView(R.id.iv_child)
    ImageView ivChild;
    @BindView(R.id.iv_parent)
    ImageView ivParent;
    @BindView(R.id.ll_songlitixing)
    LinearLayout llSonglitixing;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gift_store;

    }

    @Override
    protected void setData(String response) {

    }

    @Override
    public void initData() {

    }




    @OnClick({R.id.iv_liangcangjingxuan, R.id.iv_jieri, R.id.iv_loving, R.id.iv_birthday, R.id.iv_friend, R.id.iv_child, R.id.iv_parent})
    public void onClick(View view) {
        int list_id = 0;
        switch (view.getId()) {
            case R.id.iv_liangcangjingxuan:
                list_id = 7;
                break;
            case R.id.iv_jieri:
                list_id = 1;
                break;
            case R.id.iv_loving:
                list_id = 2;
                break;
            case R.id.iv_birthday:
                list_id = 3;
                break;
            case R.id.iv_friend:
                list_id = 4;
                break;
            case R.id.iv_child:
                list_id = 5;
                break;
            case R.id.iv_parent:
                list_id = 6;
                break;

        }

        MainActivity mainActivity = (MainActivity) getActivity();
        StoreTypeDetailsFragment storeTypeDetailsFragment = new StoreTypeDetailsFragment(list_id +"");
        mainActivity.replaceFragment(storeTypeDetailsFragment);
    }
}
