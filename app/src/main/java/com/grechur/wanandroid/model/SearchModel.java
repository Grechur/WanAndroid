package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.contract.SearchListContract;
import com.grechur.wanandroid.model.entity.home.MainArticle;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/28.
 */

public class SearchModel implements SearchListContract.ISearchModel{
    @Override
    public Observable<BasicResponse<MainArticle>> getSearchList(int page, String key) {
        return IdeaApi.getApiService().getSearchList(page, key);
    }
}
