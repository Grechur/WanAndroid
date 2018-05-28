package com.grechur.wanandroid.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grechur.library.adapter.CommonRecyclerAdapter;
import com.grechur.library.adapter.MultiTypeSupport;
import com.grechur.library.adapter.ViewHolder;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.model.entity.home.History;
import com.grechur.wanandroid.view.adapter.FlowAdapter;

import java.util.List;

/**
 * Created by zhouzhu on 2018/5/27.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {

    private Context mContext;
    private List<History> mData;
    private OnItemClickListen onItemClickListen;

    public HistoryAdapter(Context context,List<History> data){
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 0){
            view = LayoutInflater.from(mContext).inflate(R.layout.navigation_flow_item,parent,false);
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.title_flow_item,parent,false);
        }
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.text.setText(TextUtils.isEmpty(mData.get(position).name)?"":mData.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mData.get(position).isTitle){
                    onItemClickListen.onItemClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).isTitle) return 1;
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
