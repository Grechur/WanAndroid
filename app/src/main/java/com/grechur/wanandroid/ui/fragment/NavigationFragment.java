package com.grechur.wanandroid.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.NavigationContract;
import com.grechur.wanandroid.model.entity.navigation.NaviArticle;
import com.grechur.wanandroid.presenter.NavigationPresenter;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.view.adapter.NaviViewPagerAdapter;
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
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class NavigationFragment extends BaseFragment<NavigationPresenter> implements NavigationContract.INavigationView {

    public static final String TAG ="NavigationFragment";

    @BindView(R.id.tl_layout)
    TabLayout tl_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    private List<String> mList ;
    private ViewPagerAdapter mPagerAdapter;
    private List<Fragment> mFragmentList;
    private Unbinder unbind;
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
        unbind = ButterKnife.bind(this,view);
//        tl_layout = view.findViewById(R.id.tl_layout);
//        view_pager = view.findViewById(R.id.view_pager);
        view_pager.setOffscreenPageLimit(1);
        mList = new ArrayList<String>();
        mFragmentList = new ArrayList<>();
//        for (String title : titles) {
//            Bundle bundle = new Bundle();
//            bundle.putString(Constant.INTENT_ID, title);
//            Fragment fragment = NaviCommonFragment.newInstance(title);
//            fragment.setArguments(bundle);
//            mFragmentList.add(fragment);
//        }
//        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),mFragmentList,mList);
//        view_pager.setAdapter(mPagerAdapter);
//        tl_layout.setupWithViewPager(view_pager);
//        tl_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected NavigationPresenter createPresenter() {
        return new NavigationPresenter();
    }

    @Override
    protected void initData() {
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
            mList.add(article.name);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.INTENT_ID, article.name);
            Fragment fragment = NaviCommonFragment.newInstance(article.name,article.articles);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }
        view_pager.setCurrentItem(0);
        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),mFragmentList,mList);
        view_pager.setAdapter(mPagerAdapter);
        tl_layout.setupWithViewPager(view_pager);
        tl_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }
}
