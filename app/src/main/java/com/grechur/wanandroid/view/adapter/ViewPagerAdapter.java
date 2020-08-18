package com.grechur.wanandroid.view.adapter;




import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zz on 2018/5/24.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> tList;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragments,List<String> tList) {
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


}