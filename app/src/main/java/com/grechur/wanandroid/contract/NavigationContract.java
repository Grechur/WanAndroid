package com.grechur.wanandroid.contract;

import com.grechur.wanandroid.base.BaseModel;
import com.grechur.wanandroid.base.BaseView;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.navigation.NaviArticle;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/24.
 */

public class NavigationContract {
    public interface INavigationView extends BaseView{
        void onSucceed(List<NaviArticle> articles);
    }

    public interface INavigationModel extends BaseModel{
        Observable<BasicResponse<List<NaviArticle>>> getNavigation();
    }

    public interface INavigationPresent{
        void getNavigation();
    }
}
