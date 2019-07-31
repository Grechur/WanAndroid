package com.grechur.wanandroid.base;

/**
 * Created by zz on 2018/5/18.
 */

public interface BaseView {
    //加载动画
    void onLoading();
    //toast
    void showToast(String msg);
    //取消加载动画
    void unLoading();
    //请求出错
    void onError(String code,String msg);
}
