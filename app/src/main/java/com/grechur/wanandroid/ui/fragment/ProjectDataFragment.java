package com.grechur.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseLazyFragment;
import com.grechur.wanandroid.contract.ProjectDataContract;
import com.grechur.wanandroid.model.entity.project.ProjectData;
import com.grechur.wanandroid.model.entity.project.ProjectInfo;
import com.grechur.wanandroid.presenter.ProjectDataPresenter;
import com.grechur.wanandroid.ui.WebViewActivity;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.LogUtils;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.ProjectDataAdapter;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zz on 2018/5/25.
 */

public class ProjectDataFragment extends BaseLazyFragment<ProjectDataPresenter> implements ProjectDataContract.IProjectDataView{
    @BindView(R.id.wrawp_recycler_view)
    WrapRecyclerView project_recycler_view;
    @BindView(R.id.smart_refresh)
    RefreshLayout smart_refresh;
    private int mCId;

    private String mProductId;


    private ProjectDataAdapter mProjectDataAdapter;
    private List<ProjectInfo> mProjectInfo;
    private Unbinder unbinder;
    private int page = 1;

    public static Fragment newInstance(int id,String name){
        Fragment fragment=new ProjectDataFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constant.INTENT_PID,id);
        bundle.putString(Constant.INTENT_TITLE, name);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_data;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this,view);
        mCId = getArguments().getInt(Constant.INTENT_PID);
        mProductId = getArguments().getString(Constant.INTENT_TITLE);
        LogUtils.e(mCId+"");
        mProjectInfo = new ArrayList<>();
        mProjectDataAdapter = new ProjectDataAdapter(getActivity(),mProjectInfo,R.layout.project_data_item);

        project_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        project_recycler_view.setAdapter(mProjectDataAdapter);
        project_recycler_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
//                ToastUtils.show("第"+position+"被点击");
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL,mProjectInfo.get(position).link);
                intent.putExtra(Constant.INTENT_TITLE,mProjectInfo.get(position).title);
                getActivity().startActivity(intent);
            }
        });
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getPresenter().getProjectData(page,mCId);
            }
        });
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getPresenter().getProjectData(page,mCId);
            }
        });

    }

    @Override
    protected ProjectDataPresenter createPresenter() {
        return new ProjectDataPresenter();
    }

    @Override
    protected void initData() {
        //触发自动刷新
        smart_refresh.autoRefresh();
        getPresenter().getProjectData(1,mCId);
    }

    @Override
    public void onLoading() {

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
    public void onSuccess(ProjectData projectData) {
        if(page==1) mProjectInfo.clear();
        mProjectInfo.addAll(projectData.datas);
        mProjectDataAdapter.notifyDataSetChanged();
        smart_refresh.finishRefresh();
        smart_refresh.finishLoadMore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mProjectInfo.clear();
        mProjectInfo = null;
    }

    @Override
    protected void onInvisible() {

    }
}
