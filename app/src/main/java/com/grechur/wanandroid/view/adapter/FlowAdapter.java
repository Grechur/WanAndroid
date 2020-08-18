package com.grechur.wanandroid.view.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.grechur.wanandroid.R;
import com.grechur.wanandroid.model.entity.Article;

import java.util.List;

/**
 * Created by Zc on 2018/2/26.
 */

public class FlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> list;
    private Context context;
    private OnItemClickListen onItemClickListen;
    public FlowAdapter(Context context,List<Article> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.navigation_flow_item,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView textView = ((MyHolder) holder).text;
        if(list.get(position)!=null){
            textView.setText(TextUtils.isEmpty(list.get(position).title)?"":list.get(position).title);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListen.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.flow_text);
        }
    }

    public void setItemClickListen(OnItemClickListen onItemClickListen){
        this.onItemClickListen = onItemClickListen;
    }

    public interface OnItemClickListen{
        void onItemClick(View view, int position);
    }

}
