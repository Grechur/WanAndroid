package com.grechur.wanandroid.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.grechur.library.adapter.CommonRecyclerAdapter;
import com.grechur.library.adapter.MultiTypeSupport;
import com.grechur.library.adapter.ViewHolder;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.model.entity.knowlege.Children;
import com.grechur.wanandroid.model.entity.knowlege.Knowledge;
import com.grechur.wanandroid.model.entity.project.ProjectInfo;

import java.util.List;

/**
 * Created by zz on 2018/5/24.
 */

public class ProjectDataAdapter extends CommonRecyclerAdapter<ProjectInfo>{
    private List<ProjectInfo> mData;
    private Context mContext;
    public ProjectDataAdapter(Context context, List<ProjectInfo> data, int layoutId) {
        super(context, data, layoutId);
        this.mData = data;
        this.mContext = context;
    }

    public ProjectDataAdapter(Context context, List<ProjectInfo> data, MultiTypeSupport<ProjectInfo> multiTypeSupport) {
        super(context, data, multiTypeSupport);
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder viewHolder, ProjectInfo projectInfo) {
        viewHolder.setImageByUrl(R.id.iv_project_img, new ViewHolder.HolderImageLoader(projectInfo.envelopePic) {
            @Override
            public void displayImage(Context context, ImageView imageView, String s) {
                Glide.with(context).load(s).into(imageView);
            }
        });
        viewHolder.setText(R.id.tv_project_title,projectInfo.title);
        viewHolder.setText(R.id.tv_project_desc,projectInfo.desc);
        viewHolder.setText(R.id.tv_project_time,projectInfo.niceDate+"   "+projectInfo.author);
    }
}
