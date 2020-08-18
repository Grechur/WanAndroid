package com.grechur.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.KnowledgeContract;
import com.grechur.wanandroid.model.entity.knowlege.Children;
import com.grechur.wanandroid.model.entity.knowlege.Knowledge;
import com.grechur.wanandroid.presenter.KnowledgePresenter;
import com.grechur.wanandroid.ui.KnowledgeProcActivity;
import com.grechur.wanandroid.view.KnowledgeAdapter;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zz on 2018/5/22.
 */

public class KnowledgeFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContract.IKnowledgeView{
    @BindView(R.id.wrawp_recycler_view)
    WrapRecyclerView knowledge_recycler_view;
    @BindView(R.id.smart_refresh)
    RefreshLayout smart_refresh;

    private KnowledgeAdapter mKnowledgeAdapter;
    private List<Knowledge> mKnowledge;

    Unbinder unbinder;
    private int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this,view);
//        knowledge_recycler_view = view.findViewById(R.id.knowledge_recycler_view);

        mKnowledge = new ArrayList<>();
        mKnowledgeAdapter = new KnowledgeAdapter(getActivity(),mKnowledge,R.layout.knowledge_item);

        knowledge_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        knowledge_recycler_view.setAdapter(mKnowledgeAdapter);
        knowledge_recycler_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                List<Children> children = mKnowledge.get(position).children;
                ArrayList<String> sList = new ArrayList<>();
                ArrayList<String> iList = new ArrayList<>();
                for (Children child : children) {
                    sList.add(child.name);
                    iList.add(child.id+"");
                }
                Intent intent = new Intent(getActivity(), KnowledgeProcActivity.class);
                intent.putStringArrayListExtra("tab_title",sList);
                intent.putStringArrayListExtra("tab_id",iList);
                intent.putExtra("title",mKnowledge.get(position).name);
                getActivity().startActivity(intent);
            }
        });
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                getPresenter().getKnowledges();
            }
        });
        smart_refresh.setEnableLoadMore(false);
        //触发自动刷新
        smart_refresh.autoRefresh();
    }

    @Override
    protected KnowledgePresenter createPresenter() {
        return new KnowledgePresenter();
    }

    @Override
    protected void initData() {
        getPresenter().getKnowledges();
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
    }

    @Override
    public void onSucceed(List<Knowledge> knowledges) {
        mKnowledge.clear();
        mKnowledge.addAll(knowledges);
        mKnowledgeAdapter.notifyDataSetChanged();
        smart_refresh.finishRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mKnowledge.clear();
        mKnowledge = null;
    }
}
