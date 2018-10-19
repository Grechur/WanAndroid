package com.grechur.wanandroid.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseMvpActivity;
import com.grechur.wanandroid.contract.UserInfoContract;
import com.grechur.wanandroid.model.entity.UserInfo;
import com.grechur.wanandroid.presenter.UserInfoPresenter;
import com.grechur.wanandroid.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity  extends BaseMvpActivity<UserInfoPresenter> implements UserInfoContract.IUserInfoView {
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_up)
    Button btn_up;
    Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this);

    }

    @Override
    protected void initData() {

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
//        text.setText(userInfo.toString());
        mAccountMgr.putString("username",userInfo.username);
        mAccountMgr.putString("password",userInfo.password);
    }

    @OnClick({R.id.btn_login,R.id.btn_up})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                String username = et_username.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                getPresenter().getUsers(username,pwd);
                break;
            case R.id.btn_up:
                ToastUtils.show("这里就懒得做了");
                break;
        }
    }

    @Override
    public void unLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
