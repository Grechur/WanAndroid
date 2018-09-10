package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.contract.HomeArticleContract;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/22.
 */

public class HomeArticleModel implements HomeArticleContract.IArticlesModel{
    @Override
    public Observable<BasicResponse<MainArticle>> getArticles(int index) {
        Observable<BasicResponse<MainArticle>> observable = IdeaApi.getApiService().getMainArticle(index);
        return observable;
    }

    @Override
    public Observable<BasicResponse<List<BannerItem>>> getBanner() {
        Observable<BasicResponse<List<BannerItem>>> observable = IdeaApi.getApiService().getBanner();
        return observable;
    }
}
