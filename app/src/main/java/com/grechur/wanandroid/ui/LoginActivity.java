package com.grechur.wanandroid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseMvpActivity;
import com.grechur.wanandroid.contract.UserInfoContract;
import com.grechur.wanandroid.model.entity.UserInfo;
import com.grechur.wanandroid.presenter.UserInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity  extends BaseMvpActivity<UserInfoPresenter> implements UserInfoContract.IUserInfoView {
    @BindView(R.id.text)
    TextView text;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        getPresenter().getUsers("Grechur","zz124578");
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(String code, String msg) {
        showToast(msg);
    }


    @Override
    public void onSucceed(UserInfo userInfo) {
        text.setText(userInfo.toString());
    }

    @Override
    public void unLoading() {

    }
}
