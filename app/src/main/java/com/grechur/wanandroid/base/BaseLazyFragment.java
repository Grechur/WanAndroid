package com.grechur.wanandroid.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grechur.wanandroid.utils.ToastUtils;

/**
 * Created by zz on 2018/7/9.
 */

public abstract class BaseLazyFragment<P extends BasePresenter> extends Fragment implements BaseView{

    protected View rootView;
    protected Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
//    private boolean isFirst = true;
    private LayoutInflater inflater;
    private P mPresenter;

    //--------------------system method callback------------------------//

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            isVisible = true;
            lazyLoad();
        }else{
            isVisible = false;
            onInvisible();
        }
        super.setUserVisibleHint(isVisibleToUser);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        return rootView;
    }

    //--------------------------------method---------------------------//

    /**
     * 懒加载
     */
    protected void lazyLoad(){
        if(!isPrepared || !isVisible || rootView == null){//|| !isFirst
            return;
        }
        initData();
//        isFirst = false;
    }

    //--------------------------abstract method------------------------//

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

    /**
     * fragment被设置为不可见时调用
     */
    protected abstract void onInvisible();

    /**
     * 这里获取数据，刷新界面
     */
    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initView(View view);

    protected abstract P createPresenter();

    protected abstract void initData();

}