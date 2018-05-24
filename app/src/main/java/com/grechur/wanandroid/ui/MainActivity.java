package com.grechur.wanandroid.ui;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.ui.fragment.HomeFragment;
import com.grechur.wanandroid.ui.fragment.KnowledgeFragment;
import com.grechur.wanandroid.ui.fragment.NavigationFragment;
import com.grechur.wanandroid.ui.fragment.ProjectFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    BottomNavigationBar bottomNavigationBar;
    TextView title;

    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mFragmentManager = getSupportFragmentManager();

        title = findViewById(R.id.title);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.home, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.knowledge, "知识体系"))
                .addItem(new BottomNavigationItem(R.mipmap.navigation, "导航"))
                .addItem(new BottomNavigationItem(R.mipmap.project, "项目"))
                .setBarBackgroundColor(R.color.white)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.selectTab(0);
    }


    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (position){
            case 0:
                title.setText("首页");
                if(mHomeFragment==null){
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_container,mHomeFragment);
                }
                transaction.replace(R.id.fragment_container,mHomeFragment);

                transaction.commit();
                break;
            case 1:
                title.setText("知识体系");
                if(mKnowledgeFragment==null){
                    mKnowledgeFragment = new KnowledgeFragment();
                    transaction.add(R.id.fragment_container,mKnowledgeFragment);
                }
                transaction.replace(R.id.fragment_container,mKnowledgeFragment);

                transaction.commit();
                break;
            case 2:
                title.setText("导航");
                if(mNavigationFragment==null){
                    mNavigationFragment = new NavigationFragment();
                    transaction.add(R.id.fragment_container,mNavigationFragment);
                }
                transaction.replace(R.id.fragment_container,mNavigationFragment);

                transaction.commit();
                break;
            case 3:
                title.setText("项目");
                if(mProjectFragment==null){
                    mProjectFragment = new ProjectFragment();
                    transaction.add(R.id.fragment_container,mProjectFragment);
                }
                transaction.replace(R.id.fragment_container,mProjectFragment);

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
}
