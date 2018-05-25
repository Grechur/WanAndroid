package com.grechur.wanandroid.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.model.entity.Article;
import com.grechur.wanandroid.utils.DateUtil;
import com.grechur.wanandroid.view.adapter.FlowAdapter;

import java.util.List;

/**
 * Created by zz on 2018/5/23.
 */

public class HomeFrgAdapter extends RecyclerView.Adapter<HomeFrgAdapter.MyViewHolder> {
    private List<Article> mDatas;
    private LayoutInflater mLayoutInflater;
    private int mLayoutId;
    private FlowAdapter.OnItemClickListen onItemClickListen;
    public HomeFrgAdapter(Context context, List<Article> data, int layoutId) {
        this.mDatas = data;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mLayoutId,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Article article = mDatas.get(position);
        holder.tv_title.setText(article.title==null?"":article.title);
        holder.tv_author.setText(article.author==null?"":"作者："+article.author);
        holder.tv_type.setText(article.superChapterName==null?"":"tv_time"+article.superChapterName);
        long time = article.publishTime;
        String date = DateUtil.getDay(time);
        holder.tv_time.setText("时间："+article.niceDate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListen.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView iv_head;
        TextView tv_title;
        TextView tv_author;
        TextView tv_type;
        TextView tv_time;

        public MyViewHolder(View view)
        {
            super(view);
            iv_head = view.findViewById(R.id.iv_head);
            tv_title = view.findViewById(R.id.tv_title);
            tv_author = view.findViewById(R.id.tv_author);
            tv_type = view.findViewById(R.id.tv_type);
            tv_time = view.findViewById(R.id.tv_time);

        }
    }
    public void setItemClickListen(FlowAdapter.OnItemClickListen onItemClickListen){
        this.onItemClickListen = onItemClickListen;
    }

    public interface OnItemClickListen{
        void onItemClick(View view, int position);
    }
}
