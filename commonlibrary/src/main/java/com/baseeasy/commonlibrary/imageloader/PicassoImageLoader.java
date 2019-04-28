package com.baseeasy.commonlibrary.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class PicassoImageLoader implements ImageLoader {
    private Picasso mPicasso;

    public PicassoImageLoader(Context context) {
        mPicasso = Picasso.get();

    }

    @Override
    public void displayImage(ImageView iv, String imageUrl) {
        mPicasso.load(imageUrl).into(iv);
    }

    @Override
    public void displayImage(ImageView iv, String imageUrl, DisplayOption option) {
        RequestCreator requestCreator = mPicasso.load(imageUrl);
        if (option != null) {
            if (option.getPlaceHolderResId() != DisplayOption.NONE && option.getLoadErrorResId() != DisplayOption.NONE) {
                requestCreator.placeholder(option.getPlaceHolderResId())
                        .error(option.getLoadErrorResId());
                if (option.getWidth() != DisplayOption.NONE && option.getHeight() != DisplayOption.NONE) {
                    requestCreator.resize(option.getWidth(), option.getHeight());

                }
                requestCreator.into(iv);

            } else if (option.getPlaceHolderResId() != DisplayOption.NONE) {
                requestCreator.placeholder(option.getPlaceHolderResId());
                if (option.getWidth() != DisplayOption.NONE && option.getHeight() != DisplayOption.NONE) {
                    requestCreator .resize(option.getWidth(), option.getHeight());

                }
                requestCreator.into(iv);
            } else if (option.getLoadErrorResId() != DisplayOption.NONE) {
                requestCreator.error(option.getPlaceHolderResId());
                if (option.getWidth() != DisplayOption.NONE && option.getHeight() != DisplayOption.NONE) {
                    requestCreator .resize(option.getWidth(), option.getHeight());

                }
                requestCreator.into(iv);
            } else {
                requestCreator.into(iv);
            }

        } else {
            requestCreator.into(iv);

        }
    }
}