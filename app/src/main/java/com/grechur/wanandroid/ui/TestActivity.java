package com.grechur.wanandroid.ui;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.ui.fragment.TestFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private FragmentManager mFragmentManager;
    private TestFragment mHomeFragment;

    private TestFragment mKnowledgeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.home, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.knowledge, "知识体系"))
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setActiveColor(R.color.blue)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.selectTab(0);
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new TestFragment();
                    transaction.add(R.id.fragment_container,mHomeFragment);
                } else{
                    transaction.show(mHomeFragment);
                }

                transaction.commit();
                break;
            case 1:
                if (mKnowledgeFragment == null) {
                    mKnowledgeFragment = new TestFragment();
                    transaction.add(R.id.fragment_container,mKnowledgeFragment);
                } else{
                    transaction.show(mKnowledgeFragment);
                }

                transaction.commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if(mHomeFragment!=null){
            transaction.hide(mHomeFragment);
        }
        if(mKnowledgeFragment!=null){
            transaction.hide(mKnowledgeFragment);
        }

    }
}
