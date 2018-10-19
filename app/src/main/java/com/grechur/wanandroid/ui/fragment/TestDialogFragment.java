package com.grechur.wanandroid.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.api.DefaultObserver;
import com.grechur.wanandroid.api.IdeaApi;
import com.grechur.wanandroid.model.BasicResponse;
import com.grechur.wanandroid.model.entity.home.History;
import com.grechur.wanandroid.ui.SearchListActivity;
import com.grechur.wanandroid.ui.WebViewActivity;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.utils.ToastUtils;
import com.grechur.wanandroid.view.HistoryAdapter;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.grechur.wanandroid.view.adapter.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zz on 2018/5/29.
 */

public class TestDialogFragment extends DialogFragment {
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pop_search,container, false);
        ButterKnife.bind(this,view);
        ChipsLayoutManager layoutManager = ChipsLayoutManager.newBuilder(getActivity()).build();
        history_recycle_view.setLayoutManager(layoutManager);
        history_recycle_view.addItemDecoration(new DividerGridItemDecoration(getActivity(),R.drawable.line_drawable));
        mData = new ArrayList<>();
        mHistoryAdapter = new HistoryAdapter(getActivity(),mData);

        history_recycle_view.setAdapter(mHistoryAdapter);
        history_recycle_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                ToastUtils.show(position+"被点击");

                Intent intent = new Intent();
                if(TextUtils.isEmpty(mData.get(position).link)){
                    intent.setClass(getActivity(), SearchListActivity.class);
                    intent.putExtra(Constant.INTENT_KEY,mData.get(position).name);
                }else{
                    intent.setClass(getActivity(), WebViewActivity.class);
                    intent.putExtra(Constant.INTENT_URL,mData.get(position).link);
                    intent.putExtra(Constant.INTENT_TITLE,mData.get(position).name);
                }
                getActivity().startActivity(intent);
            }
        });
        initData();
        return view;
    }

    private void initData() {
        getHistory();
        getHotCode();
        getMostUse();
    }
    public History getTitleHistory(String s){
        History history = new History();
        history.isTitle = true;
        history.name = s;
        return history;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
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
                break;
            case R.id.search_back_ib:
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        ToastUtils.show("onDismiss");
        dismissAllowingStateLoss();
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

    public void getHotCode(){
        IdeaApi.getApiService().getHotCode().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<History>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<History>> response) {
                        History history = getTitleHistory("热搜");

                        if(response.getData() !=null){
                            response.getData().add(0,history);
                            mData.addAll(response.getData());
                            mHistoryAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onFail(String code, String msg) {

                    }
                });
    }
    public void getMostUse(){
        IdeaApi.getApiService().getMostUse().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<History>>>() {
                    @Override
                    public void onSuccess(BasicResponse<List<History>> response) {
                        History history = getTitleHistory("常用网站");

                        if(response.getData() !=null){
                            response.getData().add(0,history);
                            mData.addAll(response.getData());
                            mHistoryAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onFail(String code, String msg) {

                    }
                });
    }
}
