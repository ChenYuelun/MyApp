package com.example.chenyuelun.myapp.modle.table;

/**
 * Created by chenyuelun on 2017/7/9.
 */

public class CartTable {

    public static final String TABLE_NAME = "cart";
    //商品id
    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_OWNER_NAME = "owner_name";
    //商品名
    public static final String COL_GOODS_NAME = "goods_name";

    //商品数量
    public static final String COL_GOODS_COUNT = "count";
    //商品价格
    public static final String COL_GOODS_PRICE = "price";
    //商品折扣价格
    public static final String COL_GOODS_DISCOUNT = "discount";
    //商品图片
    public static final String COL_GOODS_IMAGE = "image";
    //商品 品类单位 （一瓶  一条  颜色  等）
    public static final String COL_TYPE_NAME = "type_name";
    //选中的品类
    public static final String COL_TYPE = "type";
    //商品尺寸大小
    public static final String COL_SIZE_NAME = "size_name";
    //选择的大小
    public static final String COL_SIZE = "size";
    //是否是礼物
    public static final String COL_ISGIFT = "isgift";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COL_GOODS_ID + " text, "
            + COL_GOODS_NAME + " text, "
            + COL_OWNER_NAME + " text, "
            + COL_GOODS_COUNT + " integer, "
            + COL_GOODS_PRICE + " text, "
            + COL_GOODS_DISCOUNT + " text, "
            + COL_GOODS_IMAGE + " text, "
            + COL_TYPE_NAME + " text, "
            + COL_TYPE + " text, "
            + COL_SIZE_NAME + " text, "
            + COL_SIZE + " text, "
            + COL_ISGIFT + " integer, " +
            "primary key (" + COL_GOODS_ID +","+ COL_TYPE +"," + COL_SIZE + ")" +
            ")";


}
