package com.grechur.wanandroid.ui;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.ui.fragment.HistoryFragment;
import com.grechur.wanandroid.ui.fragment.HomeFragment;
import com.grechur.wanandroid.ui.fragment.KnowledgeFragment;
import com.grechur.wanandroid.ui.fragment.NavigationFragment;
import com.grechur.wanandroid.ui.fragment.ProjectFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.iv_search)
    ImageView iv_search;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private HistoryFragment mHistoryFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mFragmentManager = getSupportFragmentManager();

//        title = findViewById(R.id.title);
        iv_search.setImageDrawable(getResources().getDrawable(R.mipmap.search));
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.home, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.knowledge, "知识体系"))
                .addItem(new BottomNavigationItem(R.mipmap.navigation, "导航"))
                .addItem(new BottomNavigationItem(R.mipmap.project, "项目"))
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setActiveColor(R.color.blue)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.selectTab(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = mDrawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
//                float endScale = 0.8f + scale * 0.2f;
//                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
//                drawerView.setScaleX(startScale);
//                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
//                mContent.setScaleX(endScale);
//                mContent.setScaleY(endScale);
            }
        };
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);

        mHistoryFragment = new HistoryFragment();

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

    @OnClick({R.id.iv_back,R.id.iv_search})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_search:
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if(mHistoryFragment==null){
                    mHistoryFragment = new HistoryFragment();
                }
                mHistoryFragment.show(transaction,"SearchDialogFragment");
                break;
        }
    }

}
