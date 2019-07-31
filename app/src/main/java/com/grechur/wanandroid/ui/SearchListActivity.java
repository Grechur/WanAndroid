package com.grechur.wanandroid.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.aop.LoginCheck;
import com.grechur.wanandroid.base.BaseMvpActivity;
import com.grechur.wanandroid.contract.SearchListContract;
import com.grechur.wanandroid.model.entity.Article;
import com.grechur.wanandroid.model.entity.home.History;
import com.grechur.wanandroid.model.entity.home.HistoryDao;
import com.grechur.wanandroid.model.entity.home.MainArticle;
import com.grechur.wanandroid.presenter.SearchPresenter;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.utils.ToastUtils;
import com.grechur.wanandroid.view.HomeFrgAdapter;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchListActivity extends BaseMvpActivity<SearchPresenter> implements SearchListContract.ISearchView,HomeFrgAdapter.OnItemViewClickListen {

    @BindView(R.id.wrawp_recycler_view)
    WrapRecyclerView search_recycle_view;
    @BindView(R.id.search_tv)
    TextView search_tv;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.smart_refresh)
    RefreshLayout smart_refresh;

    //butterknife的Unbinder
    private Unbinder unbinder;
    //搜索关键字
    private String mKey;
    //数据库的关键字id
    private Long mId;
    //适配器
    private HomeFrgAdapter mHomeFrgAdapter;
    //列表数据
    private List<Article> mData;
    //数据库数据
    private List<History> mHistory;
    View loadView = null;
    private int page;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_search_list);
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @SuppressLint("NewApi")
    @Override
    protected void initView() {
        unbinder = ButterKnife.bind(this);
        mKey = getIntent().getStringExtra(Constant.INTENT_KEY);
        mId = getIntent().getLongExtra(Constant.INTENT_ID,-1);
        search_edit.setText(mKey);

        loadView = LayoutInflater.from(this).inflate(R.layout.loading_view,null);
        search_recycle_view.setLayoutManager(new LinearLayoutManager(this));

        mData = new ArrayList<>();
        mHomeFrgAdapter = new HomeFrgAdapter(this,mData,R.layout.recycle_item);
        
        search_recycle_view.setAdapter(mHomeFrgAdapter);

        mHomeFrgAdapter.setItemClickListen(this);

        if(loadView!=null)
            search_recycle_view.addLoadingView(loadView);
        //点击事件
        search_recycle_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                Intent intent = new Intent();
                intent.setClass(SearchListActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL,mData.get(position).link);
                intent.putExtra(Constant.INTENT_TITLE,mData.get(position).title);
                startActivity(intent);
            }
        });
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getPresenter().getSearchList(page,mKey);
            }
        });
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getPresenter().getSearchList(page,mKey);
            }
        });
        //触发自动刷新
        smart_refresh.autoRefresh();
    }

    /**
     * 获取数据
     */
    @Override
    protected void initData() {
        getPresenter().getSearchList(0,mKey);
        setHistory(mKey,mId);//数据库
        getHistory();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void unLoading() {

    }

    @Override
    public void onError(String code, String msg) {
        showToast(msg);
        smart_refresh.finishRefresh();
        smart_refresh.finishLoadMore();
    }

    @Override
    public void onSuccess(MainArticle mainArticle) {
        if(page == 0) mData.clear();
        List<Article> articles = mainArticle.datas;
        if(articles!=null&&articles.size()>0){
            mData.addAll(articles);
            mHomeFrgAdapter.notifyDataSetChanged();
        }

        smart_refresh.finishRefresh();
        smart_refresh.finishLoadMore();
    }
    @OnClick({R.id.search_tv,R.id.search_back_ib})
    void onClick(View view){
        switch (view.getId()){
            case R.id.search_tv:
                String key = search_edit.getText().toString().trim();
                if(!key.equals(mKey)){
                    getPresenter().getSearchList(0,key);
                    mKey = key;
                }
                mId = getIdByKey(key);
                setHistory(key,mId);
                break;
            case R.id.search_back_ib:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mData.clear();
        mData = null;
    }

    public void setHistory(String key,Long id){
        if(!TextUtils.isEmpty(key)){
            History history = new History();
            history.name = key;

            if(id != -1) history.id = id;

            GreenDaoHelper.insertHistory(history).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                        }
                    });
        }

    }

    public Long getIdByKey(String key){
        Long id = Long.valueOf(-1);
        for (History history : mHistory) {
            if (key.equals(history.name)) {
                id = history.id;
                break;
            }
        }
        return id;
    }
    public void getHistory(){
        GreenDaoHelper.queryHistory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<History>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<History> histories) {
                        mHistory = histories;
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @LoginCheck
    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.iv_zan:
                ToastUtils.show("jinlaile");
                break;
        }
    }



}
