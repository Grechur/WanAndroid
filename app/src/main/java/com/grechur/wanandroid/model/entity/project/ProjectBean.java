package com.grechur.wanandroid.model.entity.project;

/**
 * Created by zhouzhu on 2018/5/24.
 */

public class ProjectBean {
    public int courseId;
    public int id;
    public String name;
    public int order;
    public int parentChapterId;
    public int visible;

    @Override
    public String toString() {
        return "ProjectBean{" +
                "courseId=" + courseId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId=" + parentChapterId +
                ", visible=" + visible +
                '}';
    }
}
