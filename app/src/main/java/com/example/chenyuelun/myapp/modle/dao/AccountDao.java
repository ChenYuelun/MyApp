package com.example.chenyuelun.myapp.modle.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chenyuelun.myapp.modle.bean.UserInfo;
import com.example.chenyuelun.myapp.modle.db.AccountDB;
import com.example.chenyuelun.myapp.modle.table.AccountTable;

/**
 * Created by chenyuelun on 2017/7/10.
 */

public class AccountDao {

    private final AccountDB accountDB;

    public AccountDao(Context context, String name){
        accountDB = new AccountDB(context, name + ".db");
    }


    //增 存放用户数据
    public void addUser(UserInfo userInfo){
        if(getUser(userInfo.getPhone()) != null) {
            return;
        }
        SQLiteDatabase database = accountDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AccountTable.COL_PHONE,userInfo.getPhone());
        values.put(AccountTable.COL_USERNAME,userInfo.getUserName());
        values.put(AccountTable.COL_INFO,userInfo.getInfo());
        values.put(AccountTable.COL_IMAGE,userInfo.getImage());
        values.put(AccountTable.COL_QQ,userInfo.getQq());
        values.put(AccountTable.COL_WEINXIN,userInfo.getWeinxin());
        values.put(AccountTable.COL_WEIBO,userInfo.getWeibo());
        values.put(AccountTable.COL_DOUBAN,userInfo.getWeinxin());
        database.replace(AccountTable.TABLE_NAME,null, values);
    }


    //删


    //改 用户更改信息

    public void updataUser(UserInfo userInfo){

    }

    //用来验证用户是否存在于数据库
    public UserInfo getUser(String phone){
        boolean isHanve = false;
        SQLiteDatabase database = accountDB.getWritableDatabase();
        String sql = "select * from " + AccountTable.TABLE_NAME + " where " + AccountTable.COL_PHONE + "=?";
        Cursor cursor = database.rawQuery(sql, new String[]{phone});
        UserInfo userInfo = new UserInfo();
        if(cursor.moveToNext()) {
            userInfo.setPhone(cursor.getString(cursor.getColumnIndex(AccountTable.COL_PHONE)));
            userInfo.setUserName(cursor.getString(cursor.getColumnIndex(AccountTable.COL_USERNAME)));
            userInfo.setInfo(cursor.getString(cursor.getColumnIndex(AccountTable.COL_INFO)));
            userInfo.setImage(cursor.getString(cursor.getColumnIndex(AccountTable.COL_IMAGE)));
            userInfo.setQq(cursor.getString(cursor.getColumnIndex(AccountTable.COL_QQ)));
            userInfo.setWeinxin(cursor.getString(cursor.getColumnIndex(AccountTable.COL_WEINXIN)));
            userInfo.setWeibo(cursor.getString(cursor.getColumnIndex(AccountTable.COL_WEIBO)));
            userInfo.setDouban(cursor.getString(cursor.getColumnIndex(AccountTable.COL_DOUBAN)));
        }
        return userInfo;
    }
}
