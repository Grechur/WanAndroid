package com.grechur.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseDialogFragment;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.HistoryContract;
import com.grechur.wanandroid.model.entity.home.History;
import com.grechur.wanandroid.model.entity.home.HistoryDao;
import com.grechur.wanandroid.presenter.HistoryPresenter;
import com.grechur.wanandroid.ui.SearchListActivity;
import com.grechur.wanandroid.ui.WebViewActivity;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.utils.ToastUtils;
import com.grechur.wanandroid.view.HistoryAdapter;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.grechur.wanandroid.view.adapter.DividerGridItemDecoration;
import com.grechur.wanandroid.view.adapter.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Created by zhouzhu on 2018/5/27.
 */

public class HistoryFragment extends BaseDialogFragment<HistoryPresenter> implements HistoryContract.IHistoryView{

    @BindView(R.id.history_recycle_view)
    WrapRecyclerView history_recycle_view;
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.search_tv)
    TextView search_tv;
    @BindView(R.id.search_back_ib)
    ImageView search_back_ib;

    private HistoryAdapter mHistoryAdapter;
    private List<History> mData;

    private HistoryDao mHistoryDao;
    private Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.pop_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this,view);
        ChipsLayoutManager layoutManager = ChipsLayoutManager.newBuilder(getContext()).build();
        history_recycle_view.setLayoutManager(layoutManager);
        history_recycle_view.addItemDecoration(new DividerGridItemDecoration(getActivity(),R.drawable.line_drawable));
        mHistoryDao = GreenDaoHelper.getDaoSession().getHistoryDao();
        mData = new ArrayList<>();
        mHistoryAdapter = new HistoryAdapter(getActivity(),mData);

        history_recycle_view.setAdapter(mHistoryAdapter);
        history_recycle_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.show(position+"被点击");
                Intent intent = new Intent();
                if(TextUtils.isEmpty(mData.get(position).link)){
                    intent.setClass(getActivity(), SearchListActivity.class);
                    intent.putExtra(Constant.INTENT_KEY,mData.get(position).name);
                    setHistory(mData.get(position).name);
                }else{
                    intent.setClass(getActivity(), WebViewActivity.class);
                    intent.putExtra(Constant.INTENT_URL,mData.get(position).link);
                    intent.putExtra(Constant.INTENT_TITLE,mData.get(position).name);
                }
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter();
    }

    @Override
    protected void initData() {
        getHistory();
        getPresenter().getHotCode();
        getPresenter().getMostUse();
    }


    @Override
    public void onLoading() {

    }

    @Override
    public void unLoading() {

    }

    @Override
    public void onError(String code, String msg) {
        ToastUtils.show(msg);
    }


    @OnClick({R.id.search_tv,R.id.search_back_ib})
    void onClick(View view){
        switch (view.getId()){
            case R.id.search_tv:
                String key = search_edit.getText().toString().trim();
                if(!TextUtils.isEmpty(key)){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SearchListActivity.class);
                    intent.putExtra(Constant.INTENT_KEY,key);
                    getActivity().startActivity(intent);
                }
                setHistory(key);
                break;
            case R.id.search_back_ib:
                dismiss();
                break;
        }
    }

    @Override
    public void onSuccessMost(List<History> data) {
        History history = getTitleHistory("常用网站");

        if(data !=null){
            data.add(0,history);
            mData.addAll(data);
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccessHot(List<History> data) {
        History history = getTitleHistory("热搜");

        if(data !=null){
            data.add(0,history);
            mData.addAll(data);
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

    public void setHistory(String key){
        History history = new History();
        history.id = new Long(2);
        history.name = key;

        insertHistory(history).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                    }
                });
    }

    public void getHistory(){
        queryHistory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<History>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<History> histories) {
                        History history = getTitleHistory("历史记录");

                        histories.add(0,history);
                        mData.addAll(0,histories);
                        mHistoryAdapter.notifyDataSetChanged();
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

    public History getTitleHistory(String s){
        History history = new History();
        history.isTitle = true;
        history.name = s;
        return history;
    }

    public Observable<List<History>> queryHistory(){

        return Observable.create(new ObservableOnSubscribe<List<History>>() {
            @Override
            public void subscribe(ObservableEmitter<List<History>> e) throws Exception {
                List<History> historyList = mHistoryDao.queryBuilder().list();
                if(historyList!=null&&historyList.size()>0){
                    e.onNext(historyList);
                    e.onComplete();
                }else{
                    e.onError(new Throwable("数据库中没数据"));
                }
            }
        });
    }

    public Observable<Boolean> insertHistory(History history){
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                if(history!=null){
                    Long id = mHistoryDao.insertOrReplace(history);
                    if(id==history.id){
                        e.onNext(true);
                    }else {
                        e.onNext(false);
                    }
                    e.onComplete();
                }else{
                    e.onNext(false);
                    e.onComplete();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
