package com.grechur.wanandroid.model;

import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.contract.KnowledgeContract;
import com.grechur.wanandroid.model.entity.knowlege.Knowledge;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by zz on 2018/5/24.
 */

public class KnowledgeModel implements KnowledgeContract.IKnowledgeModel {
    @Override
    public Observable<BasicResponse<List<Knowledge>>> getKnowledges() {

        return IdeaApi.getApiService().getKnowledge();
    }
}
