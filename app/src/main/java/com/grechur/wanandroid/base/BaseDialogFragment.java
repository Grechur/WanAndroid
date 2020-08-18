package com.grechur.wanandroid.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.grechur.wanandroid.utils.ToastUtils;

/**
 * Created by zz on 2018/5/22.
 */

public abstract class BaseDialogFragment<P extends BasePresenter> extends DialogFragment implements BaseView {

    public View rootView;
    public LayoutInflater inflater;
    private P mPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(this.getLayoutId(), container, false);
            init(savedInstanceState);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initView(rootView);

        mPresenter = createPresenter();
        mPresenter.attach(this);
        initData();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }



    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initView(View view);

    protected abstract P createPresenter();

    protected abstract void initData();

    @Override
    public void showToast(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    public P getPresenter() {
        return mPresenter;
    }
}
