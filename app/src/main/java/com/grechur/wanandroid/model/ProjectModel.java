package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.contract.ProjectContract;
import com.grechur.wanandroid.model.entity.project.ProjectBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhouzhu on 2018/5/24.
 */

public class ProjectModel implements ProjectContract.IProjectModel{
    @Override
    public Observable<BasicResponse<List<ProjectBean>>> getProjects() {
        return IdeaApi.getApiService().getProject();
    }
}
