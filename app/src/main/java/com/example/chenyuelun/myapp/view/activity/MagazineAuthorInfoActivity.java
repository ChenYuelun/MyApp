package com.example.chenyuelun.myapp.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.MagazineInfoBean;
import com.example.chenyuelun.myapp.utils.HttpUtils;
import com.example.chenyuelun.myapp.view.adapter.MagazineAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class MagazineAuthorInfoActivity extends BaseActivity {


    @BindView(R.id.tv_title_date)
    TextView tvTitleDate;
    @BindView(R.id.iv_title_search)
    ImageView ivTitleSearch;
    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_cart)
    ImageView ivTitleCart;
    @BindView(R.id.iv_title_menu)
    ImageView ivTitleMenu;
    @BindView(R.id.iv_title_faver)
    ImageView ivTitleFaver;
    @BindView(R.id.iv_title_share)
    ImageView ivTitleShare;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private GridLayoutManager gridLayoutManager;
    private MagazineAdapter magazineAdapter;

    @Override
    public void initData() {
        magazineAdapter = new MagazineAdapter(this);
        recyclerview.setAdapter(magazineAdapter);
        gridLayoutManager = new GridLayoutManager(this, 1);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(gridLayoutManager);
        getDataFromNet();
    }

    private void getDataFromNet() {
        HttpUtils.get(AppUrl.MGZ_AUTHOR_ITEM, new HttpUtils.OnHttpListener() {
            @Override
            public void onResponse(String response, int id) {
                List<MagazineInfoBean> magazineInfoBeen = processData(response);
                refresh.finishRefresh();
                if(magazineInfoBeen != null && magazineInfoBeen.size() > 0) {
                    magazineAdapter.refresh(magazineInfoBeen);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }
        });
    }

    @Override
    public void initTitle() {
        super.initTitle();
        String author_name = getIntent().getStringExtra("author_name");
        tvTitle.setText("杂志·" + author_name);
        Drawable drawable = getResources().getDrawable(R.drawable.abc_spinner);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(null, null, drawable, null);
        ivTitleBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void initListener() {
        super.initListener();
        ivTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MagazineAuthorInfoActivity.this,MagazineListActivity.class));
            }
        });

        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataFromNet();
            }
        });


        magazineAdapter.setOnItemClickListener(new MagazineAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(String topic_url,String topic_name) {
                Intent intent = new Intent(MagazineAuthorInfoActivity.this, WebActivity.class);
                intent.putExtra("topic_url",topic_url);
                intent.putExtra("topic_name",topic_name);
                startActivity(intent);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_magazine_author_info;
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
