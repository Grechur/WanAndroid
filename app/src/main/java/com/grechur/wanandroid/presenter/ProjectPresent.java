package com.grechur.wanandroid.presenter;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.contract.ProjectContract;
import com.grechur.wanandroid.model.ProjectModel;
import com.grechur.wanandroid.model.entity.project.ProjectBean;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhouzhu on 2018/5/24.
 */

public class ProjectPresent extends BasePresenter<ProjectContract.IProjectView,ProjectModel> implements ProjectContract.IProjectPresenter {
    @Override
    public void getProjects() {
        getView().onLoading();

        model.getProjects().subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<ProjectBean>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<ProjectBean>> response) {
                        getView().onSucceed(response.getData());
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code, msg);
                    }
                });
    }
}
