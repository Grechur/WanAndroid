package com.grechur.wanandroid.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zz on 2018/5/22.
 */
public class Article {
    //        {
//            "apkLink": "",
//                "author": "鸿洋公众号",
//                "chapterId": 121,
//                "chapterName": "ViewPager",
//                "collect": false,
//                "courseId": 13,
//                "desc": "",
//                "envelopePic": "",
//                "fresh": true,
//                "id": 2942,
//                "link": "https://mp.weixin.qq.com/s/JlKtnVU_DqUwYAlQXXyniQ",
//                "niceDate": "7小时前",
//                "origin": "",
//                "projectLink": "",
//                "publishTime": 1526950846000,
//                "superChapterId": 26,
//                "superChapterName": "常用控件",
//                "tags": [],
//            "title": "ViewPager 刷新无效?",
//                "type": 0,
//                "userId": -1,
//                "visible": 1,
//                "zan": 0
//        }
    public String author;
    public String apkLink;
    public int chapterId;
    public String chapterName;
    public boolean collect;
    public int courseId;
    public String desc;
    public String envelopePic;
    public Long id;
    public boolean fresh;
    public String link;
    public String niceDate;
    public String origin;
    public String projectLink;
    public long publishTime;
    public String superChapterName;
    public int superChapterId;
    public String title;
    public int type;
    public int userId;
    public int visible;
    public int zan;

}
