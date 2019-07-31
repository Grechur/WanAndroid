package com.grechur.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.ProjectContract;
import com.grechur.wanandroid.model.entity.project.ProjectBean;
import com.grechur.wanandroid.presenter.ProjectPresent;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.view.KnowledgeAdapter;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.grechur.wanandroid.view.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zz on 2018/5/22.
 */

public class ProjectFragment extends BaseFragment<ProjectPresent> implements ProjectContract.IProjectView{
    @BindView(R.id.tl_project_layout)
    TabLayout tl_project_layout;
    @BindView(R.id.view_project_pager)
    ViewPager view_project_pager;
    private List<String> mList ;
    private ViewPagerAdapter mPagerAdapter;
    private List<Fragment> mFragmentList;
    private Unbinder unbind;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view) {
        unbind = ButterKnife.bind(this,view);
        mList = new ArrayList<String>();
        mFragmentList = new ArrayList<>();
        view_project_pager.setOffscreenPageLimit(1);
        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),mFragmentList,mList);
        view_project_pager.setAdapter(mPagerAdapter);
        tl_project_layout.setupWithViewPager(view_project_pager);
        tl_project_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected ProjectPresent createPresenter() {
        return new ProjectPresent();
    }

    @Override
    protected void initData() {
        getPresenter().getProjects();
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
        for (ProjectBean article : articles) {
            mList.add(article.name);
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.INTENT_PID, article.id);
            bundle.putString(Constant.INTENT_TITLE, article.name);
            Fragment fragment = ProjectDataFragment.newInstance(article.id,article.name);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }

        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }
}
