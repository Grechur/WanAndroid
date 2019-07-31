package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.contract.NavigationContract;
import com.grechur.wanandroid.model.entity.navigation.NaviArticle;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/24.
 */

public class NavigationModel implements NavigationContract.INavigationModel {
    @Override
    public Observable<BasicResponse<List<NaviArticle>>> getNavigation() {
        return IdeaApi.getApiService().getNavigation();
    }
}
