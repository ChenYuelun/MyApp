package com.example.chenyuelun.myapp.modle.bean;

import java.io.Serializable;

/**
 * Created by chenyuelun on 2017/7/10.
 */

public class UserInfo implements Serializable {
    private String userName;
    private String info;
    private String phone;
    private String image;
    private String qq;
    private String weinxin;
    private String weibo;
    private String douban;

    public UserInfo() {
    }

    public UserInfo(String userName, String info, String phone, String image, String qq, String weinxin, String weibo, String douban) {
        this.userName = userName;
        this.info = info;
        this.phone = phone;
        this.image = image;
        this.qq = qq;
        this.weinxin = weinxin;
        this.weibo = weibo;
        this.douban = douban;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeinxin() {
        return weinxin;
    }

    public void setWeinxin(String weinxin) {
        this.weinxin = weinxin;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getDouban() {
        return douban;
    }

    public void setDouban(String douban) {
        this.douban = douban;
    }
}
