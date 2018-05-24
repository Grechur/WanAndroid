package com.grechur.wanandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.HomeArticleContract;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;
import com.grechur.wanandroid.presenter.HomeArticlePresenter;

import java.util.List;

/**
 * Created by zz on 2018/5/22.
 */

public class ProjectFragment extends BaseFragment<HomeArticlePresenter> implements HomeArticleContract.IArticlesView{


    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected HomeArticlePresenter createPresenter() {
        return new HomeArticlePresenter();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String code, String msg) {

    }

    @Override
    public void onSucceed(MainArticle article) {

    }

    @Override
    public void getBanner(List<BannerItem> bannerItem) {

    }

    @Override
    public void unLoading() {

    }
}
