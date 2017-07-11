package com.example.chenyuelun.myapp.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.chenyuelun.myapp.modle.bean.UserInfo;
import com.example.chenyuelun.myapp.modle.dao.AccountDao;
import com.example.chenyuelun.myapp.modle.dao.CartDao;
import com.example.chenyuelun.myapp.utils.SpUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenyuelun on 2017/7/10.
 */

public class Modle {
    private Context context;
    private CartDao cartDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    private AccountDao accountDao;

    private Modle(){}
    private static Modle modle = new Modle();
    public static Modle getInstance(){
        return modle;
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public void init(Context context){
        this.context= context;
        accountDao = new AccountDao(context, "account.db");
        String phone = (String) SpUtils.getSpUtils().get(SpUtils.CURRENT_USER, "");
        //初始化  如果已经登录过 就打开数据库连接
        if(!TextUtils.isEmpty(phone)) {
            Log.e("TAG", "初始化，发现已登录用户，自动创建购物车数据库连接");
            cartDao = new CartDao(context,phone);
        }
    }

    public void loginSuccess(UserInfo userinfo){
        SpUtils.getSpUtils().put(SpUtils.CURRENT_USER,userinfo.getPhone());
        accountDao.addUser(userinfo);
        //用户登录成功后创建后打开购物车数据库
        cartDao = new CartDao(context,userinfo.getPhone());
        Log.e("TAG", "登陆成功购物车数据库连接");
    }



    //全局线程池--缓存型线程池
    private ExecutorService service = Executors.newCachedThreadPool();

    public ExecutorService getThread(){
        return service;
    }


}
