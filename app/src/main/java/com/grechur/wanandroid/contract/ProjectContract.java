package com.grechur.wanandroid.contract;

import com.grechur.wanandroid.base.BaseModel;
import com.grechur.wanandroid.base.BaseView;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.project.ProjectBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zhouzhu on 2018/5/24.
 */

public class ProjectContract {
    public interface IProjectView extends BaseView{
        void onSucceed(List<ProjectBean> articles);
    }

    public interface IProjectModel extends BaseModel{
        Observable<BasicResponse<List<ProjectBean>>> getProjects();
    }

    public interface IProjectPresenter{
        void getProjects();
    }
}
