package com.grechur.wanandroid.ui;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.ui.fragment.KnowArticleFragment;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.view.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class KnowledgeProcActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tl_activity_layout)
    TabLayout tl_activity_layout;
    @BindView(R.id.view_activity_pager)
    ViewPager view_activity_pager;

    private List<String> mList ;
    private ViewPagerAdapter mPagerAdapter;
    private List<Fragment> mFragmentList;

    private List<String> mIdList;
    private String mTitle;

    private Unbinder unbind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_proc);
        unbind = ButterKnife.bind(this);
        mList = new ArrayList<String>();
        mFragmentList = new ArrayList<>();
        mList = getIntent().getStringArrayListExtra("tab_title");
        mTitle = getIntent().getStringExtra("title");
        mIdList = getIntent().getStringArrayListExtra("tab_id");
        title.setText(mTitle);
        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_left);
        iv_back.setImageDrawable(drawable);

        view_activity_pager.setOffscreenPageLimit(1);

        for (String s : mIdList) {
            Fragment fragment=new KnowArticleFragment();
            Bundle bundle=new Bundle();
            bundle.putInt(Constant.INTENT_ID, Integer.parseInt(s));
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),mFragmentList,mList);
        view_activity_pager.setAdapter(mPagerAdapter);
        tl_activity_layout.setupWithViewPager(view_activity_pager);
        tl_activity_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @OnClick({R.id.iv_back})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }
}
