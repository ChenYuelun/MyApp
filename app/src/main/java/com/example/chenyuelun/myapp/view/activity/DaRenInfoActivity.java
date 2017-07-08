package com.example.chenyuelun.myapp.view.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.chenyuelun.myapp.R;
import com.example.chenyuelun.myapp.base.BaseActivity;
import com.example.chenyuelun.myapp.common.AppUrl;
import com.example.chenyuelun.myapp.modle.bean.DaRenInfoBean;
import com.example.chenyuelun.myapp.utils.HttpUtils;
import com.example.chenyuelun.myapp.utils.UiUtils;
import com.example.chenyuelun.myapp.view.adapter.DarenInfoAdapter;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class DaRenInfoActivity extends BaseActivity {
    //状态判断  用来确定recyclerview显示哪个链接的数据
    public static final int STATE_LIKE = 1;
    public static final int STATE_RECOMMEND = 2;
    public static final int STATE_ATTENTION = 3;
    public static final int STATE_FANS = 4;

    //当前显示的状态 显示哪个链接的数据  喜欢 推荐 关注  粉丝 ，也用来获取Url时的参数
    private int CURRENT_STATE = STATE_RECOMMEND;
    //uid 用来确定链接地址
    private String uid;


    //布局管理器 用来设置布局显示效果
    private GridLayoutManager gridLayoutManager;


    private boolean isGetMore = false;
    private int page = 1;

    //适配器通过此方法获取当前状态  确定创建哪个类型的ViewHolder
    public int getCurrentState() {
        return CURRENT_STATE;
    }

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    private DarenInfoAdapter darenInfoAdapter;
    private DaRenInfoBean.DataBean.ItemsBean datas;

    @Override
    public void initData() {
        darenInfoAdapter = new DarenInfoAdapter(DaRenInfoActivity.this);
        recyclerview.setAdapter(darenInfoAdapter);
        gridLayoutManager = new GridLayoutManager(DaRenInfoActivity.this, 2);

        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(gridLayoutManager);

        uid = getIntent().getStringExtra("uid");
        Log.e("TAG", "达人详情uid==" + uid);
        String url = AppUrl.getDaRenInfoUrl(uid, 1);
        Log.e("TAG", "达人详情url==" + url);
        getDataFromNet(url);
    }

    //此方法用来联网获取数据
    private void getDataFromNet(String url) {
        HttpUtils.get(url, new HttpUtils.OnHttpListener() {
            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "达人详情数据请求成功" + response);
                processData(response);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "达人详情数据请求失败" + e.getMessage());
                UiUtils.showToast("联网失败啦！请检查网络");
            }
        });
    }

    //解析数据
    private void processData(String response) {
        DaRenInfoBean daRenInfoBean = JSON.parseObject(response, DaRenInfoBean.class);
        if (daRenInfoBean != null) {
            //items 包含该达人的信息：名字 介绍 头像 邮箱等
            DaRenInfoBean.DataBean.ItemsBean items = daRenInfoBean.getData().getItems();
            if (isGetMore) {
                isGetMore = false;
                List<DaRenInfoBean.DataBean.ItemsBean.GoodsBean> goods = items.getGoods();
                List<DaRenInfoBean.DataBean.ItemsBean.UsersBean> users = items.getUsers();
                if (goods != null && goods.size() > 0) {
                    List<DaRenInfoBean.DataBean.ItemsBean.GoodsBean> goods1 = datas.getGoods();
                    if(goods1 != null) {
                        goods1.addAll(goods);
                        datas.setGoods(goods1);
                    }
                } else if (users != null && users.size() > 0) {
                    List<DaRenInfoBean.DataBean.ItemsBean.UsersBean> users1 = datas.getUsers();
                    if(users != null) {
                        users1.addAll(users);
                    }
                    datas.setUsers(users1);
                }else {
                    UiUtils.showToast("没有更多了");
                }
                refresh.finishRefreshLoadMore();
            } else {
                this.datas = items;
                refresh.finishRefresh();
            }
        }
        refrshData();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_da_ren_info;
    }


    @Override
    public void initListener() {
        super.initListener();
        darenInfoAdapter.setOnRadiouGroupCheckChangeListener(new DarenInfoAdapter.OnRadiouGroupCheckChangeListener() {
            @Override
            public void onChecked(int state) {
                CURRENT_STATE = state;
                datas.setGoods(null);
                datas.setUsers(null);
                refrshData();
                getDataFromNet(AppUrl.getDarenUrlForState(CURRENT_STATE, uid, 1));
            }
        });
        refresh.setLoadMore(true);
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                getMoreData();
            }
        });


        darenInfoAdapter.setOnItemClickListener(new DarenInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(DaRenInfoBean.DataBean.ItemsBean datas, int position) {
                if(CURRENT_STATE == STATE_LIKE || CURRENT_STATE == STATE_RECOMMEND) {
                    DaRenInfoBean.DataBean.ItemsBean.GoodsBean goodsBean = datas.getGoods().get(position);
                    int is_outer = Integer.parseInt(goodsBean.getIs_outter());
                    if(is_outer == 0) {
                        String goods_id = goodsBean.getGoods_id();
                        Intent intent = new Intent(DaRenInfoActivity.this,GoodsInfoActivity.class);
                        intent.putExtra("goodsId",goods_id);
                        startActivity(intent);
                    }else if(is_outer ==1) {
                        UiUtils.showToast("这是别人家的东西");
                    }

                }

                if(CURRENT_STATE == STATE_ATTENTION || CURRENT_STATE == STATE_FANS) {
                    DaRenInfoBean.DataBean.ItemsBean.UsersBean usersBean = datas.getUsers().get(position);
                    String user_id = usersBean.getUser_id();
                    Intent intent = new Intent(DaRenInfoActivity.this,DaRenInfoActivity.class);
                    intent.putExtra("uid",user_id);
                    startActivity(intent);
                }
            }
        });
    }

    //下拉加载更多数据
    private void getMoreData() {
        isGetMore = true;
        getDataFromNet(AppUrl.getDarenUrlForState(CURRENT_STATE,uid,++page));
    }

    //下拉刷新数据
    private void refreshData() {
        page =1;
        isGetMore = false;
        //更具当前状态获取第一页的数据链接
        getDataFromNet(AppUrl.getDarenUrlForState(CURRENT_STATE, uid, 1));
    }


    //切换状态时刷新数据
    private void refrshData() {
        int count = 2;
        if (CURRENT_STATE == STATE_FANS || CURRENT_STATE == STATE_ATTENTION) {
            count = 3;
        } else {
            count = 2;
        }
        gridLayoutManager.setSpanCount(count);
        final int finalCount = count;
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return finalCount;
                }
                return 1;
            }
        });
        darenInfoAdapter.refresh(datas);
    }
}
