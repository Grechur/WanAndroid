package com.grechur.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zz on 2018/5/18.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{
    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
