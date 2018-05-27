package com.grechur.wanandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.HistoryContract;
import com.grechur.wanandroid.presenter.HistoryPresenter;
import com.grechur.wanandroid.view.WrapRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouzhu on 2018/5/27.
 */

public class HistoryFragment extends BaseFragment<HistoryPresenter> implements HistoryContract.IHistoryPresenter{

    @BindView(R.id.history_recycle_view)
    WrapRecyclerView history_recycle_view;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.search_tv)
    TextView search_tv;
    @BindView(R.id.search_back_ib)
    ImageView search_back_ib;

    @Override
    protected int getLayoutId() {
        return R.layout.pop_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onLoading() {

    }

    @Override
    public void unLoading() {

    }

    @Override
    public void onError(String code, String msg) {

    }

    @Override
    public void getMostUse() {

    }

    @Override
    public void getHotCode() {

    }
}
