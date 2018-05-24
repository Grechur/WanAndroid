package com.grechur.wanandroid.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.HomeArticleContract;
import com.grechur.wanandroid.model.entity.Article;
import com.grechur.wanandroid.model.entity.home.BannerItem;
import com.grechur.wanandroid.model.entity.home.MainArticle;
import com.grechur.wanandroid.presenter.HomeArticlePresenter;
import com.grechur.wanandroid.view.HomeFrgAdapter;
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

public class HomeFragment extends BaseFragment<HomeArticlePresenter> implements HomeArticleContract.IArticlesView{

    private View headerView;

    private MZBannerView mMZBanner;

    @BindView(R.id.wrawp_recycler_view)
    WrapRecyclerView mWrapRecyclerView;

    private HomeFrgAdapter mHomeFrgAdapter;
    private List<Article> mArticles;

    Unbinder unbinder;
    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this,view);
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.banner_layout,null);

        mMZBanner = (MZBannerView) headerView.findViewById(R.id.banner);
//        mWrapRecyclerView = view.findViewById(R.id.wrawp_recycler_view);
        mWrapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mWrapRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
//                DividerItemDecoration.VERTICAL));
        mArticles = new ArrayList<>();
        mHomeFrgAdapter = new HomeFrgAdapter(getActivity(),mArticles,R.layout.recycle_item);
        mWrapRecyclerView.setAdapter(mHomeFrgAdapter);
        mWrapRecyclerView.addHeaderView(headerView);
    }
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
    public void getBanner(List<BannerItem> bannerItem) {
        // 设置数据
        mMZBanner.setPages(bannerItem, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mMZBanner.start();
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
        unbinder.unbind();
    }
}
