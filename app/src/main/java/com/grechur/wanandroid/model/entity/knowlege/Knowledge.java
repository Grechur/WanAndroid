package com.grechur.wanandroid.model.entity.knowlege;

import java.util.List;

/**
 * Created by zz on 2018/5/24.
 */

public class Knowledge {

    public List<Children> children;
    public int courseId;
    public int id;
    public String name;
    public int order;
    public int parentChapterId;
    public int visible;

    @Override
    public String toString() {
        return "Knowledge{" +
                "children=" + children +
                ", courseId=" + courseId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId=" + parentChapterId +
                ", visible=" + visible +
                '}';
    }
}
