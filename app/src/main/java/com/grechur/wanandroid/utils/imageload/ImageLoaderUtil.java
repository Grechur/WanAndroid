package com.grechur.wanandroid.utils.imageload;

import android.content.Context;

/**
 * Created by zz on 2018/8/20.
 */

public class ImageLoaderUtil {

    public static final int PIC_SMALL = 0;
    public static final int LOAD_STRATEGY_NORMAL = 1;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 2;
    private static ImageLoaderUtil mInstance;
    private BaseImageLoaderStrategy mStrategy;

    private ImageLoaderUtil(){
        mStrategy =new GlideImageLoaderStrategy();
    }

    //single instance
    public static ImageLoaderUtil getInstance(){
        if(mInstance ==null){
            synchronized (ImageLoaderUtil.class){
                if(mInstance == null){
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }


    public void loadImage(Context context, ImageLoader img){
        mStrategy.loadImage(context,img);
    }

    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy){
        mStrategy =strategy;
    }
}
