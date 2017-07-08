package com.example.chenyuelun.myapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chenyuelun.myapp.common.MyApplication;
import com.example.chenyuelun.myapp.view.view.FixedSpeedScroller;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

/**
 * Created by chenyuelun on 2017/7/6.
 */

public class UiUtils {

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Process.myTid() == Process.myPid()) {
            runnable.run();
        } else {
            MyApplication.getHandler().post(runnable);
        }

    }

    public static void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private static FixedSpeedScroller mScroller = null;

    /**
     * 设置ViewPager的滑动时间
     *
     * @param context
     * @param viewpager      ViewPager控件
     * @param DurationSwitch 滑动延时
     */
    public static void controlViewPagerSpeed(Context context, ViewPager viewpager, int DurationSwitch) {
        try {
            Field mField;

            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            mScroller = new FixedSpeedScroller(context,
                    new AccelerateInterpolator());
            mScroller.setmDuration(DurationSwitch);
            mField.set(viewpager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImage(Context context, String url, ImageView view, int default_image) {
        if(default_image == 0) {
            Picasso.with(context).load(url).into(view);
        }else {
            Drawable drawable = context.getResources().getDrawable(default_image);
            Picasso.with(context).load(url).error(drawable).placeholder(drawable).into(view);
        }

    }
}
