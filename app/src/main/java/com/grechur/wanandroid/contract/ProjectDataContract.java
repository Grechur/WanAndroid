package com.grechur.wanandroid.contract;

import com.grechur.wanandroid.base.BaseModel;
import com.grechur.wanandroid.base.BaseView;
import com.grechur.wanandroid.base.BasicResponse;
import com.grechur.wanandroid.model.entity.project.ProjectData;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/25.
 */

public class ProjectDataContract {

    public interface IProjectDataView extends BaseView{
        void onSuccess(ProjectData projectData);
    }

    public interface IProjectDataModel extends BaseModel{
        Observable<BasicResponse<ProjectData>> getProjectData(int index,int id);
    }

    public interface IProjectDataPresenter{
        void getProjectData(int index,int id);
    }

}
