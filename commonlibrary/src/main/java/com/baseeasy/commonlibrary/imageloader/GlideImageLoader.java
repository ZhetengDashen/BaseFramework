package com.baseeasy.commonlibrary.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * 作者：WangZhiQiang
 * 时间：2019/4/28
 * 邮箱：sos181@163.com
 * 描述：
 */
public class GlideImageLoader implements ImageLoader {
    private RequestManager mRequestManager;


    public GlideImageLoader(Context context) {
        mRequestManager = Glide.with(context);

    }


    @Override
    public void displayImage(ImageView iv, String imageUrl) {
        displayImage(iv, imageUrl, null);
    }

    @Override
    public void displayImage(ImageView iv, String imageUrl, DisplayOption option) {
        RequestBuilder requestBuilder=  mRequestManager.load(imageUrl);
        if (option != null) {
            requestBuilder.skipMemoryCache(option.getSkipMemoryCache());
            if (null!=option.getNeedHaveCache()&& option.getNeedHaveCache()){
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE);
            }
            if (option.getPlaceHolderResId() != DisplayOption.NONE && option.getLoadErrorResId() != DisplayOption.NONE ) {
                requestBuilder.placeholder(option.getPlaceHolderResId())
                        .error(option.getLoadErrorResId());
                if(option.getWidth() != DisplayOption.NONE && option.getHeight() != DisplayOption.NONE){
                    requestBuilder .override(option.getWidth(), option.getHeight());
                }
                requestBuilder.into(iv);

            } else if (option.getPlaceHolderResId() != DisplayOption.NONE) {
                requestBuilder .placeholder(option.getPlaceHolderResId());
                if (option.getWidth() != DisplayOption.NONE && option.getHeight() != DisplayOption.NONE) {
                    requestBuilder  .override(option.getWidth(), option.getHeight());
                }
                requestBuilder.into(iv);
            } else if (option.getLoadErrorResId() != DisplayOption.NONE) {
                requestBuilder .error(option.getPlaceHolderResId());
                if (option.getWidth() != DisplayOption.NONE && option.getHeight() != DisplayOption.NONE) {
                    requestBuilder.override(option.getWidth(), option.getHeight());
                }
                requestBuilder.into(iv);
            } else {
                requestBuilder.into(iv);
            }
        } else {
            requestBuilder.into(iv);
        }
    }
}