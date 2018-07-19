package com.grechur.wanandroid.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
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
import com.grechur.wanandroid.ui.MainActivity;
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
    WrapRecyclerView history_recycle_view;//自定义RecyclerView
    @BindView(R.id.search_edit)
    EditText search_edit;
    @BindView(R.id.search_tv)
    TextView search_tv;
    @BindView(R.id.search_back_ib)
    ImageView search_back_ib;//返回按钮


    //适配器
    private HistoryAdapter mHistoryAdapter;
    //数据
    private List<History> mData;
    //数据库中的数据
    private List<History> mHistory;
    //列表标题集合
    private List<String> mTitles;
    //得到常用网站的下标，-1还没加载，0第一个加载完成，大于0说明数据库数据先拿
    private int index = -1;
    //butterknife
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
        //初始化布局管理器
        ChipsLayoutManager layoutManager = ChipsLayoutManager.newBuilder(getActivity()).build();
        //recycleview的设置
        history_recycle_view.setLayoutManager(layoutManager);
        history_recycle_view.addItemDecoration(new DividerGridItemDecoration(getActivity(),R.drawable.line_drawable));
        //数组初始化
        mData = new ArrayList<>();
        mHistory= new ArrayList<>();
        mTitles = new ArrayList<>();
        //适配器初始化
        mHistoryAdapter = new HistoryAdapter(getActivity(),mData);

        history_recycle_view.setAdapter(mHistoryAdapter);
        history_recycle_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                ToastUtils.show(position+"被点击");
                //不是标题才能点击
                if(!mData.get(position).isTitle) {
                    Intent intent = new Intent();
                    //数据是否有链接
                    if (TextUtils.isEmpty(mData.get(position).link)) {
                        Long id = getIdByKey(mData.get(position).name);
                        intent.setClass(getActivity(), SearchListActivity.class);
                        intent.putExtra(Constant.INTENT_KEY, mData.get(position).name);//搜素字段
                        intent.putExtra(Constant.INTENT_ID, id);//数据库中该搜索字段的id -1不存在

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                                Pair.create(search_back_ib,"share"),
                                Pair.create(search_tv,"share1"),
                                Pair.create(search_edit,"share2"));
                        getActivity().startActivity(intent,options.toBundle());
                    } else {
                        intent.setClass(getActivity(), WebViewActivity.class);
                        intent.putExtra(Constant.INTENT_URL, mData.get(position).link);//跳转到网页
                        intent.putExtra(Constant.INTENT_TITLE, mData.get(position).name);
                        getActivity().startActivity(intent);
                    }

                }
            }
        });
    }

    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter();
    }

    @Override
    protected void initData() {
        //获取热词
        getPresenter().getHotCode();
        //获取常用网站
        getPresenter().getMostUse();
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取数据库数据，回来时能及时更新
        getHistory();
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
                    Long id = getIdByKey(key);

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SearchListActivity.class);
                    intent.putExtra(Constant.INTENT_KEY,key);
                    if(id!=-1){
                        intent.putExtra(Constant.INTENT_ID,id);
                    }
                    getActivity().startActivity(intent);
                }
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
            index = mData.indexOf(history);
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccessHot(List<History> data) {
        History history = getTitleHistory("热搜");

        if(data !=null){
            data.add(0,history);
            if(index>=0) mData.addAll(index,data);
            if(index == -1) mData.addAll(data);
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取数据库数据
     */
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
                        if(!mTitles.contains("历史记录")) {
                            History history = getTitleHistory("历史记录");
                            histories.add(0, history);
                            mData.addAll(0, histories);
                            mHistoryAdapter.notifyDataSetChanged();
                        }
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

    /**
     * 设置标题项
     * @param s
     * @return
     */
    public History getTitleHistory(String s){
        History history = new History();
        history.isTitle = true;
        history.name = s;
        mTitles.add(s);
        return history;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    /**
     * 通过文字查找数据是否在数据库
     * @param key
     * @return
     */
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
}
