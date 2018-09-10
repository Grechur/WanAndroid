package com.grechur.wanandroid.contract;

import com.grechur.wanandroid.base.BaseModel;
import com.grechur.wanandroid.base.BaseView;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.home.History;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhouzhu on 2018/5/27.
 */

public class HistoryContract {
    public interface IHistoryView extends BaseView{
        void onSuccessMost(List<History> data);
        void onSuccessHot(List<History> data);
    }

    public interface IHistoryModel extends BaseModel{
        Observable<BasicResponse<List<History>>> getMostUse();

        Observable<BasicResponse<List<History>>> getHotCode();
    }

    public interface IHistoryPresenter{
        void getMostUse();

        void getHotCode();
    }
}
