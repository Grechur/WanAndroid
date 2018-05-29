package com.grechur.wanandroid.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class SearchListActivity extends BaseMvpActivity<SearchPresenter> implements SearchListContract.ISearchView {

    @BindView(R.id.search_recycle_view)
    WrapRecyclerView search_recycle_view;
    @BindView(R.id.search_tv)
    TextView search_tv;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;

    private Unbinder unbinder;
    private String mKey;
    private Long mId;
    
    private HomeFrgAdapter mHomeFrgAdapter;
    private List<Article> mData;
    private List<History> mHistory;
    View loadView = null;

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

        if(loadView!=null)
            search_recycle_view.addLoadingView(loadView);

        search_recycle_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(SearchListActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL,mData.get(position).link);
                intent.putExtra(Constant.INTENT_TITLE,mData.get(position).title);
                startActivity(intent);
            }
        });
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getSearchList(0,mKey);
            }
        });
        sw_refresh.setRefreshing(true);
    }

    @Override
    protected void initData() {
        getPresenter().getSearchList(0,mKey);
        setHistory(mKey,mId);
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
        if(sw_refresh.isRefreshing()) {
            sw_refresh.setRefreshing(false);
            mData.clear();
        }
    }

    @Override
    public void onError(String code, String msg) {
        showToast(msg);
    }

    @Override
    public void onSuccess(MainArticle mainArticle) {
        unLoading();
        List<Article> articles = mainArticle.datas;
        if(articles!=null&&articles.size()>0){
            mData.addAll(articles);
            mHomeFrgAdapter.notifyDataSetChanged();
        }


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
}
