package com.grechur.wanandroid.contract;

import com.grechur.wanandroid.base.BaseModel;
import com.grechur.wanandroid.base.BaseView;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.knowlege.Knowledge;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/24.
 */

public class KnowledgeContract {
    public interface IKnowledgeView extends BaseView{
        void onSucceed(List<Knowledge> knowledges);
    }

    public interface IKnowledgeModel extends BaseModel{
        Observable<BasicResponse<List<Knowledge>>> getKnowledges();
    }

    public interface IKnowledgePresenter {
        void getKnowledges();
    }
}
