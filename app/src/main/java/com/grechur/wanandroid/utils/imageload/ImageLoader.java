package com.grechur.wanandroid.utils.imageload;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.grechur.wanandroid.R;
import com.grechur.wanandroid.utils.imageload.hasprogress.ProgressListener;


/**
 * Created by zz on 2018/8/20.
 */

public class ImageLoader {
    private int type;  //类型 (大图，中图，小图)
    private String url; //需要解析的url
    private int placeHolder; //当没有成功加载的时候显示的图片
    private ImageView imgView; //ImageView的实例
    private int wifiStrategy;//加载策略，是否在wifi下才加载
    private boolean isCircle;//是否是圆形图片
    private BitmapTransformation transformation;//是否是圆形图片
    private int width;//设置加载宽
    private int height;//设置加载高
    private ProgressListener listener;//添加进度监听

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
        this.isCircle = builder.isCircle;
        this.transformation = builder.transformation;
        this.width = builder.width;
        this.height = builder.height;
        this.listener = builder.listener;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getStrategy() {
        return wifiStrategy;
    }

    public boolean getIsCircle() {
        return isCircle;
    }

    public BitmapTransformation getTransformation(){return transformation;}

    public int getWidth(){return width;}

    public int getHeight(){return height;}

    public ProgressListener getListener() {
        return listener;
    }

    public static class Builder{
        private int type;  //类型 (大图，中图，小图)
        private String url; //需要解析的url
        private int placeHolder; //当没有成功加载的时候显示的图片
        private ImageView imgView; //ImageView的实例
        private int wifiStrategy;//加载策略，是否在wifi下才加载
        private boolean isCircle;//是否是圆形图片
        private BitmapTransformation transformation;//图片形状
        private int width;//设置加载宽
        private int height;//设置加载高
        private ProgressListener listener;//添加进度监听
        public Builder() {
            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = "";
            this.placeHolder = R.mipmap.ic_launcher;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
            this.isCircle = false;
            this.transformation = null;
            this.width = imgView!=null?imgView.getWidth():0;
            this.height = imgView!=null?imgView.getHeight():0;
            this.listener = null;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public Builder isCircle(boolean isCircle){
            this.isCircle = isCircle;
            return this;
        }

        public Builder transformation(BitmapTransformation transformation){
            this.transformation = transformation;
            return this;
        }

        public Builder width(int width){
            this.width = width;
            return this;
        }

        public Builder height(int height){
            this.height = height;
            return this;
        }

        public Builder listener(ProgressListener listener){
            this.listener = listener;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }
}
