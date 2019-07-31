package com.grechur.wanandroid.model.entity.home;

import com.grechur.wanandroid.model.entity.Article;

import java.util.List;

/**
 * Created by zz on 2018/5/22.
 */

public class MainArticle {
//    "offset": 0,
//            "over": false,
//            "pageCount": 65,
//            "size": 20,
//            "total": 1299
    public int curPage;
    public int pageCount;
    public int size;
    public int total;
    public List<Article> datas;
    public int offset;
    public boolean over;

    @Override
    public String toString() {
        return "MainArticle{" +
                "curPage=" + curPage +
                ", pageCount=" + pageCount +
                ", size=" + size +
                ", total=" + total +
                ", datas=" + datas +
                ", offset=" + offset +
                ", over=" + over +
                '}';
    }
}
