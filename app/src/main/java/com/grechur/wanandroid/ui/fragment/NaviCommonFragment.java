package com.grechur.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.model.entity.Article;
import com.grechur.wanandroid.model.entity.ArticleDao;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.grechur.wanandroid.view.adapter.FlowAdapter;
import com.grechur.wanandroid.view.adapter.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz on 2018/5/24.
 */

public class NaviCommonFragment extends Fragment {
    private View rootView;
    //自定义的recycleview
    private WrapRecyclerView common_recycler_view;
    //从activity传过来的数据
    private String productId;
    //Article的本地数据库的dao
    private ArticleDao mArticleDao;
    //查询到的Article
    private Article article;
    //列表的Article
    private List<Article> mArticles;
    //流布局的adapter
    private FlowAdapter mFlowAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_navi_common,container,false);
        common_recycler_view = rootView.findViewById(R.id.common_recycler_view);
        //FlowLayoutManager自定义的流布局manager
        common_recycler_view.setLayoutManager(new FlowLayoutManager());
        mArticles = new ArrayList<>();

        mFlowAdapter = new FlowAdapter(getActivity(),mArticles);

        common_recycler_view.setAdapter(mFlowAdapter);

        mArticleDao = GreenDaoHelper.getDaoSession().getArticleDao();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        productId = getArguments().getString(Constant.INTENT_ID);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mArticles!=null&&mArticles.size()>0){
            mArticles.clear();
        }
        article = mArticleDao.queryBuilder().where(ArticleDao.Properties.ChapterName.eq(productId)).unique();
        if(article!=null){
            mArticles.add(article);
        }

        if(mArticles.size()>0) mFlowAdapter.notifyDataSetChanged();
    }
}