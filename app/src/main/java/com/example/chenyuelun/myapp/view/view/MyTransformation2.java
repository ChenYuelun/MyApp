package com.example.chenyuelun.myapp.view.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Created by chenyuelun on 2017/6/26.
 */

public class MyTransformation2 implements Transformation {
    @Override public Bitmap transform(Bitmap source) {

        int width = source.getWidth();
        int height = width/2*3;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader =
                new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (width != 0 || height != 0) {
            // source isn't square, move viewport to center
            Matrix matrix = new Matrix();
            matrix.setTranslate(0, 0);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);
//        float r = size / 2f;
//        canvas.drawCircle(r, r, r, paint);
        canvas.drawRect(0,0,width,height,paint);
        source.recycle();

        return bitmap;
    }

    @Override public String key() {
        return "CropCircleTransformation()";
    }

}
