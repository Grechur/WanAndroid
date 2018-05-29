package com.grechur.wanandroid.model.entity.home;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhouzhu on 2018/5/27.
 */
@Entity
public class History {
    public String icon;
    @Id
    public Long id;
    public String link;
    public String name;
    public int order;
    public int visible;
    public boolean isTitle;

    @Override
    public String toString() {
        return "History{" +
                "icon='" + icon + '\'' +
                ", id=" + id +
                ", link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", visible=" + visible +
                ", isTitle=" + isTitle +
                '}';
    }

    @Generated(hash = 49438406)
    public History(String icon, Long id, String link, String name, int order,
            int visible, boolean isTitle) {
        this.icon = icon;
        this.id = id;
        this.link = link;
        this.name = name;
        this.order = order;
        this.visible = visible;
        this.isTitle = isTitle;
    }
    @Generated(hash = 869423138)
    public History() {
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public int getVisible() {
        return this.visible;
    }
    public void setVisible(int visible) {
        this.visible = visible;
    }
    public boolean getIsTitle() {
        return this.isTitle;
    }
    public void setIsTitle(boolean isTitle) {
        this.isTitle = isTitle;
    }

}
