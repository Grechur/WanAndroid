package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.contract.KnowArticleContract;
import com.grechur.wanandroid.model.entity.home.MainArticle;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/22.
 */

public class KnowArticleModel implements KnowArticleContract.IArticlesModel{
    @Override
    public Observable<BasicResponse<MainArticle>> getArticles(int index,int id) {
        Observable<BasicResponse<MainArticle>> observable = IdeaApi.getApiService().getKnowArticle(index,id);
        return observable;
    }

}
