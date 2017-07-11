package com.example.chenyuelun.myapp.modle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chenyuelun.myapp.modle.table.CartTable;

/**
 * Created by chenyuelun on 2017/7/9.
 */

public class CartDB extends SQLiteOpenHelper {
    public CartDB(Context context, String name) {
        super(context, name, null, 1);
        Log.e("TAG2", "购物车数据库已建立");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("TAG2", "建表语句：===" + CartTable.CREATE_TABLE);
        db.execSQL(CartTable.CREATE_TABLE);
        Log.e("TAG2", "购物车商品详情表已建立");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
