package com.grechur.wanandroid.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.base.WanApplication;
import com.grechur.wanandroid.contract.NavigationContract;
import com.grechur.wanandroid.model.entity.Article;
import com.grechur.wanandroid.model.entity.ArticleDao;
import com.grechur.wanandroid.model.entity.DaoMaster;
import com.grechur.wanandroid.model.entity.DaoSession;
import com.grechur.wanandroid.model.entity.navigation.NaviArticle;
import com.grechur.wanandroid.model.entity.navigation.NaviArticleDao;
import com.grechur.wanandroid.presenter.NavigationPresenter;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.view.adapter.ViewPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zz on 2018/5/22.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.INavigationView {

    private TabLayout tl_layout;
    private ViewPager view_pager;
    private List<String> mList ;
    private ViewPagerAdapter mPagerAdapter;
    private List<Fragment> mFragmentList;
    private NaviArticleDao mNaviDao;
    private ArticleDao mArticleDao;

    private static String[] titles={
            "常用网站","个人博客","公司博客","开发社区","常用工具","在线学习",
            "开放平台","互联网资讯","求职招聘","应用加固","三方支付","推送平台",
            "三方分享","地图平台","直播SDK","IM即时通讯","Bug管理","后端云",
            "WebView内核","创意&素材","互联网统计","快速开发","应用发布"
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @Override
    protected void initView(View view) {
        tl_layout = view.findViewById(R.id.tl_layout);
        view_pager = view.findViewById(R.id.view_pager);
        view_pager.setOffscreenPageLimit(0);
        mList = new ArrayList<String>(Arrays.asList(titles));
        mFragmentList = new ArrayList<>();
        for (String title : titles) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.INTENT_ID, title);
            Fragment fragment = new NaviCommonFragment();
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }
        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),mFragmentList,mList);
        view_pager.setAdapter(mPagerAdapter);
        tl_layout.setupWithViewPager(view_pager);
        tl_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tl_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected NavigationPresenter createPresenter() {
        return new NavigationPresenter();
    }

    @Override
    protected void initData() {

        mNaviDao = GreenDaoHelper.getDaoSession().getNaviArticleDao();
        mArticleDao = GreenDaoHelper.getDaoSession().getArticleDao();
        getPresenter().getNavigation();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void unLoading() {

    }

    @Override
    public void onError(String code, String msg) {
        showToast(msg);
    }

    @Override
    public void onSucceed(List<NaviArticle> articles) {
        for (NaviArticle article : articles) {
//            mNaviDao.insert(article);
//            for (Article article1 : article.articles) {
//                mArticleDao.insert(article1);
//            }
        }
    }
}
