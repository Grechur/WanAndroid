package com.grechur.wanandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.grechur.wanandroid.utils.AccountMgr;

/**
 * Created by zz on 2018/5/18.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{
    private P mPresenter;
    protected AccountMgr mAccountMgr;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountMgr = new AccountMgr(this);
        boolean enableNightMode = mAccountMgr.getBool("isNight",false);
        if(!enableNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        setContentView();
        
        mPresenter = createPresenter();
        mPresenter.attach(this);

        initView();
        
        initData();
    }

    protected abstract void setContentView();

    protected abstract P createPresenter() ;

    protected abstract void initView();

    protected abstract void initData();


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public P getPresenter() {
        return mPresenter;
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
