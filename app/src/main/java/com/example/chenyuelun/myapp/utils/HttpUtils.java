package com.example.chenyuelun.myapp.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public class HttpUtils {

    private static HttpUtils httpUtils = new HttpUtils();
    private HttpUtils(){}

    public static HttpUtils getInstance(){
        return httpUtils;
    }

    public void get(String url,OnHttpListener listener){
        this.listener = listener;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallBack());
    }

    public class StringCallBack extends StringCallback{

        @Override
        public void onError(Call call, Exception e, int id) {
            if(listener != null) {
                listener.onError(call,e,id);
            }
        }

        @Override
        public void onResponse(String response, int id) {
            if(listener != null) {
                listener.onResponse(response,id);
            }
        }
    }
    public OnHttpListener listener;

    public interface OnHttpListener{
        void onResponse(String response, int id);
        void onError(Call call, Exception e, int id);
    }

}