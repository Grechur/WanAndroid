package com.grechur.wanandroid.model.entity;

/**
 * Created by zz on 2018/5/18.
 */

public class UserInfo {
    public String username;
    public String password;
    public int id;
    public String email;
    public String icon;

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
