package com.grechur.wanandroid.view;

import android.content.Context;
import android.view.View;

import com.grechur.library.adapter.CommonRecyclerAdapter;
import com.grechur.library.adapter.MultiTypeSupport;
import com.grechur.library.adapter.ViewHolder;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.model.entity.knowlege.Children;
import com.grechur.wanandroid.model.entity.knowlege.Knowledge;

import java.util.List;

/**
 * Created by zz on 2018/5/24.
 */

public class KnowledgeAdapter extends CommonRecyclerAdapter<Knowledge>{
    private List<Knowledge> mData;
    private Context mContext;
    public KnowledgeAdapter(Context context, List<Knowledge> data, int layoutId) {
        super(context, data, layoutId);
        this.mData = data;
        this.mContext = context;
    }


    @Override
    public void convert(ViewHolder viewHolder, Knowledge knowledge) {

        viewHolder.setText(R.id.tv_knowledge_title,knowledge.name);

        List<Children> childrens = knowledge.children;
        String label = "";
        if(childrens!=null&&childrens.size()>0){
            for (Children children : childrens) {
                label += children.name+"    ";
            }
        }
        viewHolder.setText(R.id.tv_knowledge_label,label);

    }
}
