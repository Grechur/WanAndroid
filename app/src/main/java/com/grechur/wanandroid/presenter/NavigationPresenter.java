package com.grechur.wanandroid.presenter;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.contract.NavigationContract;
import com.grechur.wanandroid.model.NavigationModel;
import com.grechur.wanandroid.model.entity.navigation.NaviArticle;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zz on 2018/5/24.
 */

public class NavigationPresenter extends BasePresenter<NavigationContract.INavigationView,NavigationModel> implements NavigationContract.INavigationPresent {
    @Override
    public void getNavigation() {
        getView().onLoading();

        model.getNavigation().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<NaviArticle>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<NaviArticle>> response) {
                        getView().onSucceed(response.getData());
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code, msg);
                    }
                });
    }
}
