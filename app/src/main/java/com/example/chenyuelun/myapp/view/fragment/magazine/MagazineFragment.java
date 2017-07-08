package com.example.chenyuelun.myapp.view.fragment.magazine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseFragment;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.MagazineInfoBean;
import com.example.chenyuelun.myapp.view.activity.WebActivity;
import com.example.chenyuelun.myapp.view.adapter.MagazineAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenyuelun on 2017/7/5.
 */

public class MagazineFragment extends BaseFragment {
    @BindView(R.id.iv_title_search)
    ImageView ivTitleSearch;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_cart)
    ImageView ivTitleCart;
    @BindView(R.id.iv_title_menu)
    ImageView ivTitleList;
    @BindView(R.id.tv_title_date)
    TextView tvTitleDate;
    @BindView(R.id.iv_title_faver)
    ImageView ivTitleFaver;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private List<MagazineInfoBean> infoBeanList;
    private MagazineAdapter magazineAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_magazine;
    }

    @Override
    protected void setData(String response) {
        infoBeanList = processData(response);
        magazineAdapter.refresh(infoBeanList);
    }


    @Override
    public void initData() {
        magazineAdapter = new MagazineAdapter(getActivity());
        recyclerview.setAdapter(magazineAdapter);
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(gridLayoutManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initTitle() {
        tvTitle.setText("杂志");
        Drawable drawable = getResources().getDrawable(R.drawable.abc_spinner);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(null, null, drawable, null);
        tvTitleDate.setVisibility(View.VISIBLE);
        tvTitleDate.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void initListener() {
        super.initListener();
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = gridLayoutManager.findFirstVisibleItemPosition();
                if (position == 0) {
                    tvTitleDate.setText("Today");
                } else {
                    String date = infoBeanList.get(position).getDate();
                    tvTitleDate.setText(date);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        magazineAdapter.setOnItemClickListener(new MagazineAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(String topic_url,String topic_name) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("topic_url",topic_url);
                intent.putExtra("topic_name",topic_name);
                startActivity(intent);
            }
        });
    }

    @Override
    public String getUrl() {
        return AppUrl.MAGAZINE_LIANGCANG_URL;
    }


    private List<MagazineInfoBean> processData(String json) {
        List<MagazineInfoBean> datas = new ArrayList<>();
        try {
            JSONObject obj1 = new JSONObject(json);
            JSONObject data = obj1.getJSONObject("data");
            JSONObject items = data.getJSONObject("items");
            JSONArray keys = items.getJSONArray("keys");
            JSONObject infos = items.getJSONObject("infos");
            if (keys != null && keys.length() > 0) {
                for (int i = 0; i < keys.length(); i++) {
                    String date = (String) keys.get(i);
                    JSONArray info = infos.getJSONArray(date);
                    if (info != null && info.length() > 0) {
                        for (int j = 0; j < info.length(); j++) {
                            JSONObject obj2 = (JSONObject) info.get(j);
                            MagazineInfoBean infoBean = new MagazineInfoBean();
                            String topic_name = obj2.getString("topic_name");
                            infoBean.setTopic_name(topic_name);
                            String topic_url = obj2.getString("topic_url");
                            infoBean.setTopic_url(topic_url);
                            String cover_img_new = obj2.getString("cover_img_new");
                            infoBean.setCover_img_new(cover_img_new);
                            String cat_name = obj2.getString("cat_name");
                            infoBean.setCat_name(cat_name);
                            infoBean.setDate(date.substring(date.indexOf(".") + 1, date.length()));
                            datas.add(infoBean);
                        }
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datas;
    }

}
