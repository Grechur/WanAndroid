package com.grechur.wanandroid.presenter;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.contract.KnowledgeContract;
import com.grechur.wanandroid.model.KnowledgeModel;
import com.grechur.wanandroid.model.entity.knowlege.Knowledge;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zz on 2018/5/24.
 */

public class KnowledgePresenter extends BasePresenter<KnowledgeContract.IKnowledgeView,KnowledgeModel>
                                implements KnowledgeContract.IKnowledgePresenter{
    @Override
    public void getKnowledges() {
        //显示加载动画
        getView().onLoading();

        model.getKnowledges().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<Knowledge>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<Knowledge>> response) {
                        getView().onSucceed(response.getData());
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code,msg);
                    }
                });
    }
}
