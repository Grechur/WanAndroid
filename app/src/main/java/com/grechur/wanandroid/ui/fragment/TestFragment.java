package com.grechur.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.view.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestFragment extends Fragment {

    private Unbinder mUnbinder;

    @BindView(R.id.tl_layout)
    TabLayout tl_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private ViewPagerAdapter mPagerAdapter;
    private List<Fragment> mFragmentList;

    private TestItemFragment itemFragment;
    private TestItemFragment testItemFragment;

    private static String[] titles={"常用网站","个人博客"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view_pager.setOffscreenPageLimit(1);
        mFragmentList = new ArrayList<>();
        itemFragment = new TestItemFragment();
        testItemFragment = new TestItemFragment();
        mFragmentList.add(itemFragment);
        mFragmentList.add(testItemFragment);
        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),mFragmentList, Arrays.asList(titles));
        view_pager.setAdapter(mPagerAdapter);
        tl_layout.setupWithViewPager(view_pager);
    }
}
