package com.grechur.wanandroid.model;

/**
 * Created by zz on 2018/5/22.
 */

public class BasicResponse<T> {
    private String errorCode;
    private String errorMsg;
    private T data;

    public String getCode() {
        return errorCode;
    }

    public void setCode(String code) {
        this.errorCode = code;
    }

    public String getErrmsg() {
        return errorMsg;
    }

    public void setErrmsg(String errmsg) {
        this.errorMsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
