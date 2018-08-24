package com.grechur.wanandroid.utils.imageload;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.grechur.wanandroid.utils.NetworkUtils;
import com.grechur.wanandroid.utils.imageload.hasprogress.ProgressInterceptor;
import com.grechur.wanandroid.utils.imageload.hasprogress.ProgressListener;

/**
 * Created by zz on 2018/8/20.
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(Context ctx, ImageLoader img) {
        if(img.getListener()!=null){
            loadImageWithProgress(ctx,img);
            return;
        }
        boolean flag= true;
        //如果不是在wifi下加载图片，直接加载
        if(!flag){
            loadNormal(ctx,img);
            return;
        }

        int strategy =img.getStrategy();
        if(strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI){
            NetworkUtils.NetworkType netType = NetworkUtils.getNetworkType();
            //如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
            if(netType == NetworkUtils.NetworkType.NETWORK_WIFI) {
                loadNormal(ctx, img);
            } else {
                //当前网络不是wifi，加载缓存
                loadCache(ctx, img);
            }
        }else{
            //如果不是在wifi下才加载图片
            loadNormal(ctx,img);
        }

    }

    public void loadImageWithProgress(Context ctx, ImageLoader img) {
        RequestOptions options = getCommonOptions(img);
        if(img.getListener()!=null){
            ProgressInterceptor.addListener(img.getUrl(),img.getListener());

            options.skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        Glide.with(img.getImgView().getContext())
                .load(img.getUrl())
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressInterceptor.removeListener(img.getUrl());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressInterceptor.removeListener(img.getUrl());
                        return false;
                    }
                })
                .into(img.getImgView());

    }

    /**
     * load image with Glide
     */
    private void loadNormal(Context ctx, ImageLoader img) {
        RequestOptions options = getCommonOptions(img);

        Glide.with(ctx).load(img.getUrl()).apply(options).into(img.getImgView());
    }


    /**
     *load cache image with Glide
     */
    private void loadCache(Context ctx, ImageLoader img) {
        RequestOptions options = getCommonOptions(img);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(ctx).load(img.getUrl()).apply(options).into(img.getImgView());
    }

    public RequestOptions getCommonOptions(ImageLoader img){
        RequestOptions options = new RequestOptions()
                .override(img.getWidth(),img.getHeight())
                .placeholder(img.getPlaceHolder())	//加载成功之前占位图
                .error(img.getPlaceHolder());//加载错误之后的错误图;
        if(img.getIsCircle()){
            options.circleCrop();
        }else{
            if(img.getTransformation()!=null) options.transform(img.getTransformation());
        }
        return options;
    }

}
