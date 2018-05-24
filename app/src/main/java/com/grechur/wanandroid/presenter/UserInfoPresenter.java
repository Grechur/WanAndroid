package com.grechur.wanandroid.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.contract.UserInfoContract;
import com.grechur.wanandroid.model.UserInfoModel;
import com.grechur.wanandroid.model.entity.UserInfo;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import static com.grechur.wanandroid.contract.UserInfoContract.*;

/**
 * Created by zz on 2018/5/18.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoContract.IUserInfoView, UserInfoModel>
        implements IUserInfoPresenter{

    public static final String TAG ="UserInfoPresenter";

    @Override
    public void getUsers(String userName,String password) {

        getView().onLoading();

        if(model == null){
            return;
        }
        model.getUsers(userName,password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<UserInfo>>() {
                    @Override
                    public void onSuccess(BasicResponse<UserInfo> response) {
                        getView().onSucceed(response.getData());
                        getView().unLoading();
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code,msg);
                        getView().unLoading();
                    }
                });
    }
}
