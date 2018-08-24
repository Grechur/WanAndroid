package com.grechur.wanandroid.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.grechur.wanandroid.model.entity.Article;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;
import com.grechur.wanandroid.presenter.HomeArticlePresenter;
import com.grechur.wanandroid.ui.WebViewActivity;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.ToastUtils;
import com.grechur.wanandroid.view.HomeFrgAdapter;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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

public class HomeFragment extends BaseFragment<HomeArticlePresenter> implements HomeArticleContract.IArticlesView,
        HomeFrgAdapter.OnItemClickListen, MZBannerView.BannerPageClickListener{

    //列表头部
    private View headerView;

    private MZBannerView mMZBanner;

    @BindView(R.id.wrawp_recycler_view)
    WrapRecyclerView mWrapRecyclerView;
    @BindView(R.id.smart_refresh)
    RefreshLayout smart_refresh;
    //适配器
    private HomeFrgAdapter mHomeFrgAdapter;
    //列表数据源
    private List<Article> mArticles;

    Unbinder unbinder;
    private List<BannerItem> mBanners;

    private int page = 0;

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this,view);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.banner_layout,null);
        mBanners = new ArrayList<>();
        mMZBanner = (MZBannerView) headerView.findViewById(R.id.banner);
//        mWrapRecyclerView = view.findViewById(R.id.wrawp_recycler_view);
        mWrapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mWrapRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
//                DividerItemDecoration.VERTICAL));
        mArticles = new ArrayList<>();
        mHomeFrgAdapter = new HomeFrgAdapter(getActivity(),mArticles,R.layout.recycle_item);
        mWrapRecyclerView.setAdapter(mHomeFrgAdapter);
        mWrapRecyclerView.addHeaderView(headerView);
        mWrapRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL,mArticles.get(position).link);
                intent.putExtra(Constant.INTENT_TITLE,mArticles.get(position).title);
                getActivity().startActivity(intent);
            }
        });

        mMZBanner.setBannerPageClickListener(this);
        mHomeFrgAdapter.setItemClickListen(this);

        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getPresenter().getArticles(page);
                getPresenter().getBanner();
            }
        });
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getPresenter().getArticles(page);
            }
        });
        //触发自动刷新
        smart_refresh.autoRefresh();

    }


    /**
     * 获取数据
     */
    @Override
    protected void initData() {
        getPresenter().getArticles(0);
        getPresenter().getBanner();
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
    public void onLoading() {

    }

    @Override
    public void onError(String code, String msg) {
        showToast(msg);
        smart_refresh.finishRefresh();
        smart_refresh.finishLoadMore();
    }

    @Override
    public void onSucceed(MainArticle article) {
        Log.e("TAG",mHomeFrgAdapter.toString()+mWrapRecyclerView.toString());
        if(page == 0) mArticles.clear();
        smart_refresh.finishRefresh();
        smart_refresh.finishLoadMore();
        if(article!=null){
            mArticles.addAll(article.datas);
        }
        mHomeFrgAdapter.notifyDataSetChanged();
    }

    @Override
    public void getBanner(List<BannerItem> bannerItem) {
        mBanners = bannerItem;
        // 设置数据
        mMZBanner.setPages(mBanners, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mMZBanner.start();
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
    public void onPageClick(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), WebViewActivity.class);
        intent.putExtra(Constant.INTENT_URL,mBanners.get(position).url);
        intent.putExtra(Constant.INTENT_TITLE,mBanners.get(position).title);
        getActivity().startActivity(intent);
    }

    public static class BannerViewHolder implements MZViewHolder<BannerItem> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, BannerItem data) {
            // 数据绑定
            Glide.with(context).load(data.imagePath).into(mImageView);
        }
    }

    @Override
    public void unLoading() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mBanners.clear();
        mBanners = null;
        mArticles.clear();
        mArticles = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();//暂停轮播
        mMZBanner.destroyDrawingCache();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
