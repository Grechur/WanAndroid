package com.grechur.wanandroid.view.adapter;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zz on 2018/5/24.
 */

public class NaviViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> tList;
    public NaviViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public NaviViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tList) {
        super(fm);
        this.fragments = fragments;
        this.tList = tList;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tList.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        return fragment;
    }
    private int mChildCount = 0;


}