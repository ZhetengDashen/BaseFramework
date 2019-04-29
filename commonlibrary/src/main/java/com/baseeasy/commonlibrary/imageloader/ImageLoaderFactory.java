package com.baseeasy.commonlibrary.imageloader;

import android.content.Context;
/**
 * 作者：WangZhiQiang
 * 时间：2019/4/28
 * 邮箱：sos181@163.com
 * 描述：
 */
public class ImageLoaderFactory {
    private static volatile ImageLoader mImageLoader;

    public static void init(ImageLoader imageLoader) {
        if (mImageLoader == null) {
            synchronized (ImageLoaderFactory.class) {
                if (mImageLoader == null) {
                    mImageLoader = imageLoader;
                }
            }
        }
    }

    public static ImageLoader getInstance() {
        if (mImageLoader == null) {
            throw new NullPointerException("you should call method init() first in application onCreate() method");
        }
        return mImageLoader;
    }

}