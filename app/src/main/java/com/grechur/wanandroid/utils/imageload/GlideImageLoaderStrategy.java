package com.grechur.wanandroid.utils.imageload;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.grechur.wanandroid.utils.NetworkUtils;

/**
 * Created by zz on 2018/8/20.
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(Context ctx, ImageLoader img) {
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
