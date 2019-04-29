package com.baseeasy.commonlibrary.imageloader;

import android.widget.ImageView;
/**
 * 作者：WangZhiQiang
 * 时间：2019/4/28
 * 邮箱：sos181@163.com
 * 描述：
 */
public interface ImageLoader {

    public void displayImage(ImageView iv, String imageUrl);

    public void displayImage(ImageView iv, String imageUrl, DisplayOption option);


}