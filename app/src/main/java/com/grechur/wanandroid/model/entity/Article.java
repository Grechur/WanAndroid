package com.grechur.wanandroid.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zz on 2018/5/22.
 */
@Entity
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
    @Property(nameInDb = "author")
    public String author;
    @Property(nameInDb = "apkLink")
    public String apkLink;
    @Property(nameInDb = "chapterId")
    public int chapterId;
    @Property(nameInDb = "chapterName")
    public String chapterName;
    @Property(nameInDb = "collect")
    public boolean collect;
    @Property(nameInDb = "courseId")
    public int courseId;
    @Property(nameInDb = "desc")
    public String desc;
    @Property(nameInDb = "envelopePic")
    public String envelopePic;
    @Id
    public int id;
    @Property(nameInDb = "fresh")
    public boolean fresh;
    @Property(nameInDb = "link")
    public String link;
    @Property(nameInDb = "niceDate")
    public String niceDate;
    @Property(nameInDb = "origin")
    public String origin;
    @Property(nameInDb = "projectLink")
    public String projectLink;
    @Property(nameInDb = "publishTime")
    public long publishTime;
    @Property(nameInDb = "superChapterName")
    public String superChapterName;
    @Property(nameInDb = "superChapterId")
    public int superChapterId;
    @Property(nameInDb = "title")
    public String title;
//    public List<String> tags;
    @Property(nameInDb = "type")
    public int type;
    @Property(nameInDb = "userId")
    public int userId;
    @Property(nameInDb = "visible")
    public int visible;
    @Property(nameInDb = "name")
    public int zan;

    @Generated(hash = 1808346919)
    public Article(String author, String apkLink, int chapterId, String chapterName,
            boolean collect, int courseId, String desc, String envelopePic, int id,
            boolean fresh, String link, String niceDate, String origin,
            String projectLink, long publishTime, String superChapterName,
            int superChapterId, String title, int type, int userId, int visible,
            int zan) {
        this.author = author;
        this.apkLink = apkLink;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.collect = collect;
        this.courseId = courseId;
        this.desc = desc;
        this.envelopePic = envelopePic;
        this.id = id;
        this.fresh = fresh;
        this.link = link;
        this.niceDate = niceDate;
        this.origin = origin;
        this.projectLink = projectLink;
        this.publishTime = publishTime;
        this.superChapterName = superChapterName;
        this.superChapterId = superChapterId;
        this.title = title;
        this.type = type;
        this.userId = userId;
        this.visible = visible;
        this.zan = zan;
    }

    @Generated(hash = 742516792)
    public Article() {
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", apkLink='" + apkLink + '\'' +
                ", chapterId=" + chapterId +
                ", chapterName='" + chapterName + '\'' +
                ", collect=" + collect +
                ", courseId=" + courseId +
                ", desc='" + desc + '\'' +
                ", envelopePic='" + envelopePic + '\'' +
                ", id=" + id +
                ", fresh=" + fresh +
                ", link='" + link + '\'' +
                ", niceDate='" + niceDate + '\'' +
                ", origin='" + origin + '\'' +
                ", projectLink='" + projectLink + '\'' +
                ", publishTime=" + publishTime +
                ", superChapterName='" + superChapterName + '\'' +
                ", superChapterId=" + superChapterId +
                ", title='" + title + '\'' +
                '}';
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getApkLink() {
        return this.apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public int getChapterId() {
        return this.chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return this.chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean getCollect() {
        return this.collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return this.envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getFresh() {
        return this.fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return this.niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProjectLink() {
        return this.projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getSuperChapterName() {
        return this.superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public int getSuperChapterId() {
        return this.superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return this.visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return this.zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }
}
