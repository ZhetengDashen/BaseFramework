package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.camera;

import android.graphics.Bitmap;

/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：
 */
public class CameraListener {
    public interface CommonListener {
        void onSwitchCamera();
    }

    /**
     * 获取照片，实际是获取预览缓存的照片，变通的进行取景
     */
    public interface TakePictureListener {
        void onTakenPicture(Bitmap bitmap);
    }
}
