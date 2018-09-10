package com.grechur.wanandroid.contract;

import com.grechur.wanandroid.base.BaseModel;
import com.grechur.wanandroid.base.BaseView;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/22.
 */

public class HomeArticleContract {
    // user View 层
    public interface IArticlesView extends BaseView {

        void onSucceed(MainArticle article);
        void getBanner(List<BannerItem> bannerItems);

    }

    // user presenter 层
    public interface IArticlesPresenter {
        void getArticles(int index);
        void getBanner();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    public interface IArticlesModel extends BaseModel{
        //获取文字列表
        Observable<BasicResponse<MainArticle>> getArticles(int index);
        //获取广告栏
        Observable<BasicResponse<List<BannerItem>>> getBanner();
    }
}
