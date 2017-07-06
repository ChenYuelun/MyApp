package com.example.chenyuelun.myapp.utils;

import android.content.Context;
import android.os.Process;
import android.view.View;

import com.example.chenyuelun.myapp.common.MyApplication;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public class UiUtils {

    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }

    public static Context getContext(){
        return MyApplication.getContext();
    }

    public static void runOnUiThread(Runnable runnable) {
       if(Process.myTid() == Process.myPid()) {
           runnable.run();
       }else {
           MyApplication.getHandler().post(runnable);
       }

    }
}
