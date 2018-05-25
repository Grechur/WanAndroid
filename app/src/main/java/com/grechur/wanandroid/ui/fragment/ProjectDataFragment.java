package com.grechur.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.ProjectDataContract;
import com.grechur.wanandroid.model.entity.project.ProjectBean;
import com.grechur.wanandroid.model.entity.project.ProjectData;
import com.grechur.wanandroid.model.entity.project.ProjectInfo;
import com.grechur.wanandroid.presenter.ProjectDataPresenter;
import com.grechur.wanandroid.ui.WebViewActivity;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.utils.LogUtils;
import com.grechur.wanandroid.utils.ToastUtils;
import com.grechur.wanandroid.view.KnowledgeAdapter;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.ProjectDataAdapter;
import com.grechur.wanandroid.view.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zz on 2018/5/25.
 */

public class ProjectDataFragment extends BaseFragment<ProjectDataPresenter> implements ProjectDataContract.IProjectDataView{
    @BindView(R.id.project_recycler_view)
    WrapRecyclerView project_recycler_view;

    private int mCId;


    private ProjectDataAdapter mProjectDataAdapter;
    private List<ProjectInfo> mProjectInfo;
    private Unbinder unbinder;
    public static Fragment newInstance(int id){
        Fragment fragment=new ProjectDataFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constant.INTENT_PID,id);
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
        LogUtils.e(mCId+"");
        mProjectInfo = new ArrayList<>();
        mProjectDataAdapter = new ProjectDataAdapter(getActivity(),mProjectInfo,R.layout.project_data_item);

        project_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        project_recycler_view.setAdapter(mProjectDataAdapter);
        project_recycler_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                ToastUtils.show("第"+position+"被点击");
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL,mProjectInfo.get(position).link);
                intent.putExtra(Constant.INTENT_TITLE,mProjectInfo.get(position).title);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected ProjectDataPresenter createPresenter() {
        return new ProjectDataPresenter();
    }

    @Override
    protected void initData() {
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
    }

    @Override
    public void onSuccess(ProjectData projectData) {
        mProjectInfo.addAll(projectData.datas);
        mProjectDataAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
