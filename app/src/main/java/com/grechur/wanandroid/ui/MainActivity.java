package com.grechur.wanandroid.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.aop.LoginCheck;
import com.grechur.wanandroid.ui.fragment.HistoryFragment;
import com.grechur.wanandroid.ui.fragment.HomeFragment;
import com.grechur.wanandroid.ui.fragment.KnowledgeFragment;
import com.grechur.wanandroid.ui.fragment.NavigationFragment;
import com.grechur.wanandroid.ui.fragment.ProjectFragment;
import com.grechur.wanandroid.utils.AccountMgr;
import com.grechur.wanandroid.utils.LogUtils;
import com.grechur.wanandroid.utils.ToastUtils;
import com.grechur.wanandroid.view.CommonAlertDialog;

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
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private TextView mUsTv;

    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private HistoryFragment mHistoryFragment;

    protected AccountMgr mAccountMgr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mFragmentManager = getSupportFragmentManager();
        mAccountMgr = new AccountMgr(this);
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

        initNavi();

    }

    private void initNavi() {
        mUsTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_login_tv);
        String username = mAccountMgr.getVal("username");
        if (!TextUtils.isEmpty(username)) {
            mUsTv.setText(username);
            mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(true);
        } else {
            mUsTv.setText("登陆");
            mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
        }

        mNavigationView.getMenu().findItem(R.id.nav_item_wan_android)
                .setOnMenuItemClickListener(item -> {
                    mDrawerLayout.closeDrawers();
                    return true;
                });
        mNavigationView.getMenu().findItem(R.id.nav_item_my_collect)
                .setOnMenuItemClickListener(item -> {
                    if (!TextUtils.isEmpty(username)) {
                        ToastUtils.show("收藏");
                        return true;
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                        return true;
                    }
                });
        mNavigationView.getMenu().findItem(R.id.nav_item_about_us)
                .setOnMenuItemClickListener(item -> {
                    startActivity(new Intent(this, AboutUsActivity.class));
                    return true;
                });
        mNavigationView.getMenu().findItem(R.id.nav_item_logout)
                .setOnMenuItemClickListener(item -> {
                    logout();
                    return true;
                });
        mNavigationView.getMenu().findItem(R.id.nav_item_setting)
                .setOnMenuItemClickListener(item -> {
//                    startSettingFragment();
                    ToastUtils.show("Setting");
                    return true;
                });
//        Glide.with(this).load("").into();
    }


    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (position){
            case 0:
                title.setText("首页");
                if(mHomeFragment==null){
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_container,mHomeFragment);
                }else{
                    transaction.show(mHomeFragment);
                }
//                transaction.replace(R.id.fragment_container,mHomeFragment);

                transaction.commit();
                break;
            case 1:
                title.setText("知识体系");
                if(mKnowledgeFragment==null){
                    mKnowledgeFragment = new KnowledgeFragment();
                    transaction.add(R.id.fragment_container,mKnowledgeFragment);
                }else{
                    transaction.show(mKnowledgeFragment);
                }
//                transaction.replace(R.id.fragment_container,mKnowledgeFragment);

                transaction.commit();
                break;
            case 2:
                title.setText("导航");
                if(mNavigationFragment==null){
                    mNavigationFragment = new NavigationFragment();
                    transaction.add(R.id.fragment_container,mNavigationFragment);
                }else{
                    transaction.show(mNavigationFragment);
                }

//                transaction.replace(R.id.fragment_container,mNavigationFragment);

                transaction.commit();
                break;
            case 3:
                title.setText("项目");
                if(mProjectFragment==null){
                    mProjectFragment = new ProjectFragment();
                    transaction.add(R.id.fragment_container,mProjectFragment);
                }else{
                    transaction.show(mProjectFragment);
                }
//                transaction.replace(R.id.fragment_container,mProjectFragment);

                transaction.commit();

                break;
        }
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if(mHomeFragment!=null){
            transaction.hide(mHomeFragment);
        }
        if(mKnowledgeFragment!=null){
            transaction.hide(mKnowledgeFragment);
        }
        if(mNavigationFragment!=null){
            transaction.hide(mNavigationFragment);
        }
        if(mProjectFragment!=null){
            transaction.hide(mProjectFragment);
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
                mHistoryFragment = new HistoryFragment();
                if (!mHistoryFragment.isAdded()) {
                    mHistoryFragment.show(transaction,"HistoryFragment");
                }

                break;
        }
    }


    private void logout() {
        CommonAlertDialog.newInstance().showDialog(
                this, getString(R.string.logout_tint),
                getString(R.string.ok),
                getString(R.string.no),
                v -> confirmLogout(),
                v -> CommonAlertDialog.newInstance().cancelDialog(true));
    }

    private void confirmLogout() {
        CommonAlertDialog.newInstance().cancelDialog(true);
        mNavigationView.getMenu().findItem(R.id.nav_item_logout).setVisible(false);
        mAccountMgr.clear();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //判断抽屉是否打开，打开就先关闭再进行两次连按检测
            if(mDrawerLayout.isDrawerOpen(mNavigationView)){
                mDrawerLayout.closeDrawer(mNavigationView);
                return true;
            }
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                ToastUtils.show("再按一次退出程序");
                exitTime = System.currentTimeMillis();
                return false;
            } else {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
