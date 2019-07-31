package com.grechur.wanandroid.utils.imageload;

import android.content.Context;

import com.grechur.wanandroid.utils.imageload.hasprogress.ProgressListener;

/**
 * Created by zz on 2018/8/20.
 */

public interface BaseImageLoaderStrategy {
    void loadImage(Context ctx, ImageLoader img);

}