package com.grechur.wanandroid.contract;

import com.grechur.wanandroid.base.BaseModel;
import com.grechur.wanandroid.base.BaseView;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.home.MainArticle;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/28.
 */

public class SearchListContract {

    public interface ISearchView extends BaseView{
        void onSuccess(MainArticle mainArticle);
    }

    public interface ISearchModel extends BaseModel{
        Observable<BasicResponse<MainArticle>> getSearchList(int page,String key);
    }

    public interface ISearchPresenter{
        void getSearchList(int page,String key);
    }
}
