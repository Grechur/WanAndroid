package com.grechur.wanandroid.presenter;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.contract.SearchListContract;
import com.grechur.wanandroid.model.SearchModel;
import com.grechur.wanandroid.model.entity.home.MainArticle;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zz on 2018/5/28.
 */

public class SearchPresenter extends BasePresenter<SearchListContract.ISearchView,SearchModel>
                    implements SearchListContract.ISearchPresenter{
    @Override
    public void getSearchList(int page, String key) {
        getView().onLoading();

        model.getSearchList(page, key).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DefaultObserver<BasicResponse<MainArticle>>() {
            @Override
            public void onSuccess(BasicResponse<MainArticle> response) {
                getView().onSuccess(response.getData());
            }

            @Override
            protected void onFail(String code, String msg) {
                getView().onError(code, msg);
            }
        });
    }
}
