package com.grechur.wanandroid.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.aop.LoginCheck;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.HomeArticleContract;
import com.grechur.wanandroid.contract.KnowArticleContract;
import com.grechur.wanandroid.model.entity.Article;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;
import com.grechur.wanandroid.presenter.HomeArticlePresenter;
import com.grechur.wanandroid.presenter.KnowArticlePresenter;
import com.grechur.wanandroid.ui.KnowledgeProcActivity;
import com.grechur.wanandroid.ui.WebViewActivity;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.ToastUtils;
import com.grechur.wanandroid.view.HomeFrgAdapter;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zz on 2018/5/22.
 */

public class KnowArticleFragment extends BaseFragment<KnowArticlePresenter> implements KnowArticleContract.IArticlesView
,HomeFrgAdapter.OnItemViewClickListen{

    @BindView(R.id.wrawp_recycler_view)
    WrapRecyclerView mWrapRecyclerView;

    private HomeFrgAdapter mHomeFrgAdapter;
    private List<Article> mArticles;

    Unbinder unbinder;
    private int mCid;

    public static Fragment getInstance(int id){
        Fragment fragment=new KnowArticleFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constant.INTENT_ID,id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this,view);
        mWrapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArticles = new ArrayList<>();
        mHomeFrgAdapter = new HomeFrgAdapter(getActivity(),mArticles,R.layout.recycle_item);
        mWrapRecyclerView.setAdapter(mHomeFrgAdapter);
        mHomeFrgAdapter.setItemClickListen(this);
        mWrapRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL,mArticles.get(position).link);
                intent.putExtra(Constant.INTENT_TITLE,mArticles.get(position).title);
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }
    @LoginCheck
    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.iv_zan:
                ToastUtils.show("jinlaile");
                break;
        }
    }
    @Override
    protected void initData() {
        mCid = getArguments().getInt(Constant.INTENT_ID);
        getPresenter().getArticles(0,mCid);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected KnowArticlePresenter createPresenter() {
        return new KnowArticlePresenter();
    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String code, String msg) {
        showToast(msg);
    }

    @Override
    public void onSucceed(MainArticle article) {
        Log.e("TAG",mHomeFrgAdapter.toString()+mWrapRecyclerView.toString());
        if(article!=null){
            mArticles.addAll(article.datas);
        }
        mHomeFrgAdapter.notifyDataSetChanged();
    }


    @Override
    public void unLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mArticles.clear();
        mArticles = null;
    }
}
