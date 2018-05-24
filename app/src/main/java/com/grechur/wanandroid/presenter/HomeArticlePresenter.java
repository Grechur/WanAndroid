package com.grechur.wanandroid.presenter;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.contract.HomeArticleContract;
import com.grechur.wanandroid.model.HomeArticleModel;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zz on 2018/5/22.
 */

public class HomeArticlePresenter extends BasePresenter<HomeArticleContract.IArticlesView,HomeArticleModel> implements HomeArticleContract.IArticlesPresenter{
    @Override
    public void getArticles(int index) {
        getView().onLoading();

        model.getArticles(index)
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

    @Override
    public void getBanner() {
        model.getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<BannerItem>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<BannerItem>> response) {
                        getView().getBanner(response.getData());
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code,msg);
                    }
                });
    }
}
