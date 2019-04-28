package com.baseeasy.commonlibrary.imageloader;

import android.widget.ImageView;

public interface ImageLoader {

    public void displayImage(ImageView iv, String imageUrl);

    public void displayImage(ImageView iv, String imageUrl, DisplayOption option);


}