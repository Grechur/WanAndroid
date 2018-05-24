package com.grechur.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.BaseFragment;
import com.grechur.wanandroid.contract.KnowledgeContract;
import com.grechur.wanandroid.model.entity.knowlege.Knowledge;
import com.grechur.wanandroid.presenter.KnowledgePresenter;
import com.grechur.wanandroid.utils.ToastUtils;
import com.grechur.wanandroid.view.KnowledgeAdapter;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zz on 2018/5/22.
 */

public class KnowledgeFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContract.IKnowledgeView{
    @BindView(R.id.knowledge_recycler_view)
    WrapRecyclerView knowledge_recycler_view;

    private KnowledgeAdapter mKnowledgeAdapter;
    private List<Knowledge> mKnowledge;

    Unbinder unbinder;
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
            public void onItemClick(int position) {
                ToastUtils.show("第"+position+"被点击");
            }
        });
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
    }

    @Override
    public void onSucceed(List<Knowledge> knowledges) {
        mKnowledge.addAll(knowledges);
        mKnowledgeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
