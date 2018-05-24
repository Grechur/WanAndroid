package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.contract.UserInfoContract;
import com.grechur.wanandroid.model.entity.UserInfo;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/18.
 */

public class UserInfoModel implements UserInfoContract.IUserInfoModel{
    @Override
    public Observable<BasicResponse<UserInfo>> getUsers(String userName, String password) {
        // 获取数据
        Observable<BasicResponse<UserInfo>> observable = IdeaApi.getApiService().getLogin(userName,password);
        return observable;
    }
}
