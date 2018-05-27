package com.grechur.wanandroid.view;

import android.content.Context;

import com.grechur.library.adapter.CommonRecyclerAdapter;
import com.grechur.library.adapter.MultiTypeSupport;
import com.grechur.library.adapter.ViewHolder;
import com.grechur.wanandroid.model.entity.home.History;

import java.util.List;

/**
 * Created by zhouzhu on 2018/5/27.
 */

public class HistoryAdapter extends CommonRecyclerAdapter<History> {
    public HistoryAdapter(Context context, List<History> data){
        super(context, data, new MultiTypeSupport<History>() {
            @Override
            public int getLayoutId(History history, int i) {
                return 0;
            }
        });
    }
    @Override
    public void convert(ViewHolder viewHolder, History history) {

    }
}
