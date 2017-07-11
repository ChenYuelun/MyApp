package com.example.chenyuelun.myapp.modle.table;

/**
 * Created by chenyuelun on 2017/7/10.
 */

public class AccountTable {

    public static final String TABLE_NAME = "userInfo";
    public static final String COL_USERNAME="userName";
    public static final String COL_INFO="info";
    public static final String COL_PHONE="phone";
    public static final String COL_IMAGE="image";
    public static final String COL_QQ="qq";
    public static final String COL_WEINXIN="weinxin";
    public static final String COL_WEIBO="weibo";
    public static final String COL_DOUBAN="douban";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COL_PHONE +" text primary key,"
            + COL_USERNAME +" text,"
            + COL_INFO +" text,"
            + COL_IMAGE +" text,"
            + COL_QQ +" text,"
            + COL_WEINXIN +" text,"
            + COL_WEIBO +" text,"
            + COL_DOUBAN +" text,"
            + COL_PHONE +" text,"
            + COL_PHONE +" text)"
            ;


}
