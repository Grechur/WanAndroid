package com.grechur.wanandroid.presenter;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.contract.HistoryContract;
import com.grechur.wanandroid.model.HistoryModel;
import com.grechur.wanandroid.model.entity.home.History;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouzhu on 2018/5/27.
 */

public class HistoryPresenter extends BasePresenter<HistoryContract.IHistoryView,HistoryModel> implements HistoryContract.IHistoryPresenter{
    @Override
    public void getMostUse() {
        getView().onLoading();
        model.getMostUse().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<History>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<History>> response) {
                        getView().onSuccessMost(response.getData());
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code, msg);
                    }
                });
    }

    @Override
    public void getHotCode() {
        model.getHotCode().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<History>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<History>> response) {
                        getView().onSuccessHot(response.getData());
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code, msg);
                    }
                });
    }
}
