package com.grechur.wanandroid.presenter;

import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.base.BasePresenter;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.contract.ProjectDataContract;
import com.grechur.wanandroid.model.ProjectDataModel;
import com.grechur.wanandroid.model.entity.project.ProjectData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zz on 2018/5/25.
 */

public class ProjectDataPresenter extends BasePresenter<ProjectDataContract.IProjectDataView,ProjectDataModel>
        implements ProjectDataContract.IProjectDataPresenter {
    @Override
    public void getProjectData(int index, int id) {
        getView().onLoading();

        model.getProjectData(index, id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ProjectData>>() {
                    @Override
                    public void onSuccess(BasicResponse<ProjectData> response) {
                        getView().onSuccess(response.getData());
                    }

                    @Override
                    protected void onFail(String code, String msg) {
                        getView().onError(code, msg);
                    }
                });

    }
}
