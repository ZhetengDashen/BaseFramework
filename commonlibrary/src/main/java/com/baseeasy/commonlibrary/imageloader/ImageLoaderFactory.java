package com.baseeasy.commonlibrary.imageloader;

import android.content.Context;

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