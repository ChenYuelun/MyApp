package com.example.chenyuelun.myapp.modle.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chenyuelun.myapp.modle.bean.CartBean;
import com.example.chenyuelun.myapp.modle.db.CartDB;
import com.example.chenyuelun.myapp.modle.table.CartTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyuelun on 2017/7/9.
 */

public class CartDao {

    private final CartDB dbHelper;

    public CartDao(Context context, String name) {
        dbHelper = new CartDB(context, name + ".db");
    }


    //数据存入
    public void add(CartBean cartBean) {
        if (cartBean == null) {
            throw new NullPointerException("存入数据库的商品信息不能为空！");
        }
        CartBean data = getData(cartBean);
        if (data != null) {
            cartBean.setCount(cartBean.getCount() + data.getCount());
        }
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartTable.COL_GOODS_ID, cartBean.getGoods_id());
        values.put(CartTable.COL_OWNER_NAME, cartBean.getOwner_name());
        values.put(CartTable.COL_GOODS_NAME, cartBean.getGoods_name());
        values.put(CartTable.COL_GOODS_COUNT, cartBean.getCount());
        values.put(CartTable.COL_GOODS_PRICE, cartBean.getPrice());
        values.put(CartTable.COL_GOODS_DISCOUNT, cartBean.getDiscount());
        values.put(CartTable.COL_GOODS_IMAGE, cartBean.getImage());
        values.put(CartTable.COL_TYPE_NAME, cartBean.getType_name());
        values.put(CartTable.COL_TYPE, cartBean.getType());
        values.put(CartTable.COL_SIZE_NAME, cartBean.getSize_name());
        values.put(CartTable.COL_SIZE, cartBean.getSize());
        values.put(CartTable.COL_ISGIFT, cartBean.getIsgift());
        database.replace(CartTable.TABLE_NAME, null, values);
    }

    //数据存入  集合数据更新  在购物车操作编辑保存之后
    public void replaceList(List<CartBean> list) {
        for (int i = 0; i < list.size(); i++) {
            update(list.get(i));
        }
    }

    //更新商品数量
    public void update(CartBean cartBean){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String where = CartTable.COL_GOODS_ID + "=? and " + CartTable.COL_TYPE + "=? and " + CartTable.COL_SIZE + "=?";
        ContentValues values = new ContentValues();
        values.put(CartTable.COL_GOODS_COUNT, cartBean.getCount());
        database.update(CartTable.TABLE_NAME,values,where,new String[]{cartBean.getGoods_id(), cartBean.getType(), cartBean.getSize()});
    }



    //数据读取
    public List<CartBean> getAllCartData() {
        List<CartBean> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = "select * from " + CartTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            CartBean cartBean = new CartBean();
            cartBean.setGoods_id(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_ID)));
            cartBean.setOwner_name(cursor.getString(cursor.getColumnIndex(CartTable.COL_OWNER_NAME)));
            cartBean.setGoods_name(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_NAME)));
            cartBean.setCount(cursor.getInt(cursor.getColumnIndex(CartTable.COL_GOODS_COUNT)));
            cartBean.setPrice(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_PRICE)));
            cartBean.setDiscount(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_DISCOUNT)));
            cartBean.setImage(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_IMAGE)));
            cartBean.setType_name(cursor.getString(cursor.getColumnIndex(CartTable.COL_TYPE_NAME)));
            cartBean.setType(cursor.getString(cursor.getColumnIndex(CartTable.COL_TYPE)));
            cartBean.setSize_name(cursor.getString(cursor.getColumnIndex(CartTable.COL_SIZE_NAME)));
            cartBean.setSize(cursor.getString(cursor.getColumnIndex(CartTable.COL_SIZE)));
            cartBean.setIsgift(cursor.getInt(cursor.getColumnIndex(CartTable.COL_ISGIFT)));
            list.add(cartBean);
        }
        return list;
    }


    //数据删除
    public void delete(CartBean cartBean) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String where = CartTable.COL_GOODS_ID + "=? and " + CartTable.COL_TYPE + "=? and " + CartTable.COL_SIZE + "=?";
        database.delete(CartTable.TABLE_NAME, where, new String[]{cartBean.getGoods_id(), cartBean.getType(), cartBean.getSize()});

    }

    public CartBean getData(CartBean cartBean) {
        CartBean cartBean1 = new CartBean();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = "select * from " + CartTable.TABLE_NAME
                + " where " + CartTable.COL_GOODS_ID + "=? and "
                + CartTable.COL_TYPE + "=? and "
                + CartTable.COL_SIZE + "=?";
        Cursor cursor = database.rawQuery(sql, new String[]{cartBean.getGoods_id(), cartBean.getType(), cartBean.getSize()});
        if (cursor.moveToNext()) {

            cartBean1.setGoods_id(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_ID)));
            cartBean1.setOwner_name(cursor.getString(cursor.getColumnIndex(CartTable.COL_OWNER_NAME)));
            cartBean1.setGoods_name(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_NAME)));
            cartBean1.setCount(cursor.getInt(cursor.getColumnIndex(CartTable.COL_GOODS_COUNT)));
            cartBean1.setPrice(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_PRICE)));
            cartBean1.setDiscount(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_DISCOUNT)));
            cartBean1.setImage(cursor.getString(cursor.getColumnIndex(CartTable.COL_GOODS_IMAGE)));
            cartBean1.setType_name(cursor.getString(cursor.getColumnIndex(CartTable.COL_TYPE_NAME)));
            cartBean1.setType(cursor.getString(cursor.getColumnIndex(CartTable.COL_TYPE)));
            cartBean1.setSize_name(cursor.getString(cursor.getColumnIndex(CartTable.COL_SIZE_NAME)));
            cartBean1.setSize(cursor.getString(cursor.getColumnIndex(CartTable.COL_SIZE)));
            cartBean1.setIsgift(cursor.getInt(cursor.getColumnIndex(CartTable.COL_ISGIFT)));
        }
        return cartBean1;

    }
}
