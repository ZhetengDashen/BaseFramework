package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.camera;

import android.graphics.SurfaceTexture;


/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：
 */
public interface ICameraProxy {
    /**
     * 打开摄像机
     */
    void openCamera();

    /**
     * 打开或者关闭闪光灯
     */
    void toggleFlash(boolean isOpen);

    int getStatus();

    void setSurfaceTexture(SurfaceTexture surfaceTexture);

    void setDisplayRotation(int degree);

    /**
     * 开启预览
     */
    void startPreview();

    /**
     * 直接开启预览
     */
    void startPreviewForce();

    /**
     * 停止预览
     */
    void stopPreview();

    /**
     * 关闭摄像头
     */
    void closeCamera();

    void switchSide();

    int[] getPreviewSize();

    void takePicture(CameraListener.TakePictureListener listener);

    void setEventListener(CameraListener.CommonListener commonListener);
}
