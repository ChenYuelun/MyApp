package com.example.chenyuelun.myapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chenyuelun.myapp.common.MyApplication;
import com.example.chenyuelun.myapp.view.view.FixedSpeedScroller;
import com.example.chenyuelun.myapp.view.view.MyTransformation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
        if (default_image == 0) {
            Picasso.with(context).load(url).into(view);
        } else if (default_image == 1) {
            Picasso.with(context).load(url).transform(new MyTransformation()).into(view);
        } else {
            Drawable drawable = context.getResources().getDrawable(default_image);
            Picasso.with(context).load(url).error(drawable).placeholder(drawable).into(view);
        }

    }


    //生成二维码
    /**
     * 生成二维码的代码
     * String str 生成的二维码的内容
     * width 生成的bitmap的宽
     * height 生成的bitmap的高
     */
    public static Bitmap getEncodeAsBitmap(String str, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(str, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 在二维码中间添加Logo图案
     */
    public static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

}
