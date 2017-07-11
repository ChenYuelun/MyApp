package com.example.chenyuelun.myapp.modle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chenyuelun.myapp.modle.table.AccountTable;

/**
 * Created by chenyuelun on 2017/7/10.
 */

public class AccountDB extends SQLiteOpenHelper {
    public AccountDB(Context context, String name) {
        super(context, name, null, 1);
        Log.e("TAG", "账户信息数据库已建立");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AccountTable.CREATE_TABLE);
        Log.e("TAG", "账户信息表已建立");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
