package com.grechur.wanandroid.model.entity.home;

/**
 * Created by zz on 2018/5/22.
 */

public class BannerItem {
//    {
//        "desc": "最新项目上线啦~",
//            "id": 13,
//            "imagePath": "http://www.wanandroid.com/blogimgs/5ae04af4-72b9-4696-81cb-1644cdcd2d29.jpg",
//            "isVisible": 1,
//            "order": 0,
//            "title": "最新项目上线啦~",
//            "type": 0,
//            "url": "http://www.wanandroid.com/pindex"
//    }
    public String desc;
    public String imagePath;
    public int id;
    public int isVisible;
    public int order;
    public String title;
    public int type;
    public String url;

    @Override
    public String toString() {
        return "BannerItem{" +
                "desc='" + desc + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", id=" + id +
                ", isVisible=" + isVisible +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }
}
