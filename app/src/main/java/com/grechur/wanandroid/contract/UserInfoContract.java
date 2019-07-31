package com.grechur.wanandroid.contract;

import com.grechur.wanandroid.base.BaseModel;
import com.grechur.wanandroid.base.BaseView;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.UserInfo;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/18.
 */

public class UserInfoContract {
    // user View 层
    public interface IUserInfoView extends BaseView{
        void onSucceed(UserInfo userInfo);
    }

    // user presenter 层
    public interface IUserInfoPresenter {
        void getUsers(String userName,String password);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    public interface IUserInfoModel extends BaseModel{
        Observable<BasicResponse<UserInfo>> getUsers(String userName, String password);
    }

}
