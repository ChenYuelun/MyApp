package com.example.chenyuelun.myapp.view.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.StoreTypeBean;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.activity.MainActivity;
import com.example.chenyuelun.myapp.view.adapter.StoreTypeAdapter;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class StoreTypeFragment extends BaseFragment {

    @BindView(R.id.rv_type_store)
    RecyclerView rvTypeStore;
    private StoreTypeAdapter storeTypeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_type_store;

    }

    @Override
    protected void setData(String response) {
//        Log.e("TAG", "StoreTypeFragment:response" + response);
        StoreTypeBean storeTypeBean = JSON.parseObject(response, StoreTypeBean.class);
        storeTypeAdapter.refresh(storeTypeBean);
    }

    @Override
    public void initData() {
        storeTypeAdapter = new StoreTypeAdapter(getActivity());
        rvTypeStore.setAdapter(storeTypeAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rvTypeStore.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void initListener() {
        super.initListener();
        storeTypeAdapter.setOnItemClickListener(new StoreTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StoreTypeBean.DataBean.ItemsBean itemsBean) {

                MainActivity mainActivity = (MainActivity) getActivity();
//                BaseFragment currentFragment = mainActivity.getcurrentFragment();
//                FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
//
//                ft.setCustomAnimations(R.anim.fragment_left_in,R.anim.fragment_left_out);
//                ft.hide(currentFragment);
//                StoreTypeDetailsFragment storeTypeDetailsFragment = new StoreTypeDetailsFragment(detailsUrl);
//                ft.setCustomAnimations(R.anim.fragment_right_in,R.anim.fragment_right_out);
//                ft.add(R.id.fl_main,storeTypeDetailsFragment);
//
//                ft.commit();
                StoreTypeDetailsFragment storeTypeDetailsFragment = new StoreTypeDetailsFragment(itemsBean.getCat_id());
                mainActivity.addFragment(storeTypeDetailsFragment);
                mainActivity.switchFragment(5);
                UiUtils.showToast(itemsBean.getCat_name());
            }
        });
    }



    public String getCart_id() {
        return AppUrl.STORE_TYPE_URL;
    }
}
