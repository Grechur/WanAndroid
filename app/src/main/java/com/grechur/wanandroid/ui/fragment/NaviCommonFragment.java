package com.grechur.wanandroid.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.model.entity.Article;
import com.grechur.wanandroid.ui.WebViewActivity;
import com.grechur.wanandroid.utils.Constant;
//import com.grechur.wanandroid.utils.GreenDaoHelper;
import com.grechur.wanandroid.view.OnItemClickListener;
import com.grechur.wanandroid.view.WrapRecyclerView;
import com.grechur.wanandroid.view.adapter.DividerGridItemDecoration;
import com.grechur.wanandroid.view.adapter.FlowAdapter;
import com.grechur.wanandroid.view.adapter.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zz on 2018/5/24.
 */
@SuppressLint("ValidFragment")
public class NaviCommonFragment extends Fragment {
    private View rootView;
    //自定义的recycleview
    @BindView(R.id.common_recycler_view)
    WrapRecyclerView common_recycler_view;
    //从activity传过来的数据
    private String mProductId;

    //列表的Article
    private List<Article> mArticles;
    //流布局的adapter
    private FlowAdapter mFlowAdapter;

    Unbinder unbinder;
    boolean isVisiable = false;

    private static Map<String,List<Article>> mArticleMap = new ConcurrentHashMap<>();

    public static Fragment newInstance(String url,List<Article> datas){
        mArticleMap.put(url,datas);
        Fragment fragment=new NaviCommonFragment();
        Bundle bundle=new Bundle();
        bundle.putString(Constant.INTENT_ID,url);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_navi_common,container,false);
//        common_recycler_view = rootView.findViewById(R.id.common_recycler_view);
        unbinder = ButterKnife.bind(this,rootView);
        mProductId = getArguments().getString(Constant.INTENT_ID);
        //ChipsLayoutManager自定义的流布局manager
        ChipsLayoutManager layoutManager = ChipsLayoutManager.newBuilder(getContext()).build();
        common_recycler_view.setLayoutManager(layoutManager);
        common_recycler_view.addItemDecoration(new DividerGridItemDecoration(getActivity(),R.drawable.line_trans_drawable));
        mArticles = new ArrayList<>();

        mFlowAdapter = new FlowAdapter(getActivity(),mArticles);

        common_recycler_view.setAdapter(mFlowAdapter);
        common_recycler_view.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL,mArticles.get(position).link);
                intent.putExtra(Constant.INTENT_TITLE,mArticles.get(position).title);
                getActivity().startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisiable = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
            if(mArticles!=null&&mArticles.size()>0){
                mArticles.clear();
            }
            List<Article> list = mArticleMap.get(mProductId);
            if(list!=null&&list.size()>0) mArticles.addAll(list);
            if(mArticles.size()>0) mFlowAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mArticles.clear();
        mArticles = null;
    }
}
