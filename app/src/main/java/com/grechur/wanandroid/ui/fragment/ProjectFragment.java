package com.grechur.wanandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.HomeArticleContract;
import com.grechur.wanandroid.contract.ProjectContract;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;
import com.grechur.wanandroid.model.entity.project.ProjectBean;
import com.grechur.wanandroid.presenter.HomeArticlePresenter;
import com.grechur.wanandroid.presenter.ProjectPresent;
import com.grechur.wanandroid.view.KnowledgeAdapter;
import com.grechur.wanandroid.view.WrapRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zz on 2018/5/22.
 */

public class ProjectFragment extends BaseFragment<ProjectPresent> implements ProjectContract.IProjectView{


    @BindView(R.id.project_recycler_view)
    WrapRecyclerView project_recycler_view;


    private KnowledgeAdapter mKnowledgeAdapter;
    private List<ProjectBean> mKnowledge;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected ProjectPresent createPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void unLoading() {

    }

    @Override
    public void onError(String code, String msg) {

    }

    @Override
    public void onSucceed(List<ProjectBean> articles) {

    }
}
