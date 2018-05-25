package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.contract.ProjectDataContract;
import com.grechur.wanandroid.model.entity.project.ProjectData;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/25.
 */

public class ProjectDataModel implements ProjectDataContract.IProjectDataModel {
    @Override
    public Observable<BasicResponse<ProjectData>> getProjectData(int index, int id) {
        return IdeaApi.getApiService().getProjectData(index,id);
    }
}
