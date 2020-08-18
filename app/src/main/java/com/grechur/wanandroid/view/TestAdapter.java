package com.grechur.wanandroid.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grechur.wanandroid.R;

import java.util.List;

/**
 * Created by zhouzhu on 2018/5/27.
 */

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Integer> mData;
    private OnItemClickListener onItemClickListen;

    public TestAdapter(Context context, List<Integer> data){
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        //根据类型不同加载不同页面
        view = LayoutInflater.from(mContext).inflate(R.layout.flow_item,parent,false);

        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder mholder = (MyHolder)holder;
        mholder.text.setText(mData.get(position)+"");

        mholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListen.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
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

    public void setItemClickListen(OnItemClickListener onItemClickListen){
        this.onItemClickListen = onItemClickListen;
    }


}
