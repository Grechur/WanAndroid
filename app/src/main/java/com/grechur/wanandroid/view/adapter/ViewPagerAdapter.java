package com.grechur.wanandroid.view.adapter;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.grechur.wanandroid.ui.fragment.NaviCommonFragment;
import com.grechur.wanandroid.utils.Constant;

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