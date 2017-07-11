package com.example.chenyuelun.myapp.common;

import android.content.Context;

import com.example.chenyuelun.myapp.modle.bean.UserInfo;
import com.example.chenyuelun.myapp.modle.dao.CartDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenyuelun on 2017/7/10.
 */

public class Modle {
    private Context context;
    private CartDao cartDao;

    private Modle(){}
    private static Modle modle = new Modle();
    public static Modle getInstance(){
        return modle;
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public void init(Context context){
        cartDao = new CartDao(context,"123");

    }

    public void loginSuccess(UserInfo userinfo){

    }



    //全局线程池--缓存型线程池
    private ExecutorService service = Executors.newCachedThreadPool();

    public ExecutorService getThread(){
        return service;
    }


}
