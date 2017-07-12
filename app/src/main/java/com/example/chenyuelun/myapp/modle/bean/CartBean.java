package com.example.chenyuelun.myapp.modle.bean;

import java.io.Serializable;

/**
 * Created by chenyuelun on 2017/7/10.
 */

public class CartBean implements Serializable{
    private String goods_id;
    private String owner_name;
    private String goods_name;
    private int count;
    private String price;
    private String discount;
    private String image;
    private String type_name;
    private String type;
    private String size_name;
    private String size = "00";
    private int isgift;
    private boolean isCheck = true;
    public CartBean(){}

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getIsgift() {
        return isgift;
    }

    public void setIsgift(int isgift) {
        this.isgift = isgift;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "goods_id='" + goods_id + '\'' +
                ", owner_name='" + owner_name + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", count=" + count +
                ", price='" + price + '\'' +
                ", discount='" + discount + '\'' +
                ", image='" + image + '\'' +
                ", type_name='" + type_name + '\'' +
                ", type='" + type + '\'' +
                ", size_name='" + size_name + '\'' +
                ", size='" + size + '\'' +
                ", isgift=" + isgift +
                '}' ;
    }


}
