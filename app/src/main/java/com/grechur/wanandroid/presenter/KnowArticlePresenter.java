package com.grechur.wanandroid.presenter;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.contract.HomeArticleContract;
import com.grechur.wanandroid.contract.KnowArticleContract;
import com.grechur.wanandroid.model.HomeArticleModel;
import com.grechur.wanandroid.model.KnowArticleModel;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zz on 2018/5/22.
 */

public class KnowArticlePresenter extends BasePresenter<KnowArticleContract.IArticlesView,KnowArticleModel> implements KnowArticleContract.IArticlesPresenter{
    @Override
    public void getArticles(int index,int id) {
        getView().onLoading();

        model.getArticles(index,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<MainArticle>>() {
                    @Override
                    public void onSuccess(BasicResponse<MainArticle> response) {
                        getView().onSucceed(response.getData());
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code,msg);
                    }
                });
    }

}