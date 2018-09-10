package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.contract.HistoryContract;
import com.grechur.wanandroid.model.entity.home.History;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhouzhu on 2018/5/27.
 */

public class HistoryModel implements HistoryContract.IHistoryModel {
    @Override
    public Observable<BasicResponse<List<History>>> getMostUse() {
        return IdeaApi.getApiService().getMostUse();
    }

    @Override
    public Observable<BasicResponse<List<History>>> getHotCode() {
        return IdeaApi.getApiService().getHotCode();
    }
}
