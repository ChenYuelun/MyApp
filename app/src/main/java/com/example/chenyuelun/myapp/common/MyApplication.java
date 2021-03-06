package com.example.chenyuelun.myapp.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.chenyuelun.myapp.utils.SpUtils;
import com.mob.MobSDK;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public class MyApplication extends Application {

    private static Context context;

    private static Handler handler;

    public static Handler getHandler() {
        return handler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        handler = new Handler();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        SpUtils.getSpUtils().init(context,"liangcang");
        Modle.getInstance().init(context);
        MobSDK.init(context);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


    }

    public static Context getContext() {
        return context;
    }
}
