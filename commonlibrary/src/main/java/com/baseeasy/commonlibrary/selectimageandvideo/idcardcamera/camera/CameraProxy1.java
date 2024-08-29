package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.camera;

import static android.hardware.Camera.getCameraInfo;
import static android.hardware.Camera.getNumberOfCameras;


import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util.AutoTrigger.cancelAutoFocusTimer;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util.AutoTrigger.createAutoFocusTimerTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;


import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util.IDCardImageUtil;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util.UiLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ruanshimin on 2018/4/16.
 */

public class CameraProxy1 implements ICameraProxy {

    public static final int STATUS_INIT = 0;

    public static final int STATUS_OPENED = 1;

    public static final int STATUS_PREVIEWING = 2;

    public static final int TEXTURE_STATUS_INITED = 0;

    private static final int TEXTURE_STATUS_READY = 1;

    private SurfaceTexture mSurfaceTexture;

    private boolean isBack = true;

    private Camera mCamera;

    private int status;
    private int textureStatus;

    private int layoutWidth;
    private int layoutHeight;

    private int[] previewSize = new int[2];

    private CameraListener.CommonListener mCameraListener;

    private int minPreviewCandidateRatio = 1;

    private int previewRotation = 90;
    private int displayRotation = 0;

    private int previewCaptureRotation = 0;

    private Camera.Parameters cameraParameters;

    private byte[] currentFrameData;

    /**
     * 初始化方法
     * @param width 预期预览窗口宽度
     * @param height 预期预览窗口高度
     */
    public CameraProxy1(int width, int height) {
        layoutWidth = width;
        layoutHeight = height;
        status = STATUS_INIT;
        textureStatus = TEXTURE_STATUS_INITED;
    }

    @Override
    public int getStatus() {
        return status;
    }

    /**
     * 获取预览长宽
     * @return
     */
    public int[] getPreviewSize() {
        return previewSize;
    }

    /**
     * 挑选一个长宽比合适的
     * 根据界面特点，这里需要宽度占满后高度大于界面高度
     *
     * @param list
     * @return
     */
    private Camera.Size getPreviewSize(List<Camera.Size> list) {
        ArrayList validSizeList = new ArrayList<Camera.Size>();
        float ratio = (float) layoutHeight / layoutWidth;

        // minPreviewCandidateRatio是符合预览界面宽度的最小比值
        for (Camera.Size size : list) {
            if ((float) size.width / size.height >= ratio
                    && size.width > (layoutWidth * minPreviewCandidateRatio)) {
                validSizeList.add(size);
            }
        }

        // 没有符合条件的,直接返回第一个,有风险
        if (validSizeList.size() == 0) {
            Log.e("baseeasyMF","found no valid preview size");
            return list.get(0);
        }

        // 选出符合候选中最小的一个预览尺寸
        Camera.Size size = (Camera.Size) Collections.min(validSizeList, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size s1, Camera.Size s2) {
                return s1.width - s2.width;
            }
        });

        return size;
    }

    /**
     * 打开关闭闪光灯
     *
     * @param isOpen 打开或关闭
     */
    public void toggleFlash(boolean isOpen) {
        if (isOpen) {
            if (cameraParameters.getSupportedFlashModes().contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            } else {
                Log.e("KK","flash open fail, FLASH_MODE_TORCH not supported");
            }

        } else {
            cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

        }
        mCamera.setParameters(cameraParameters);
    }

    /**
     * 调用open打开摄像头
     *
     * @param isOpenBack 是否是后置摄像头
     * @return
     */
    private Camera open(boolean isOpenBack) {
        int numberOfCameras = getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            getCameraInfo(i, cameraInfo);
            if (isOpenBack) {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    caculatePreviewRotation(cameraInfo);
                    return Camera.open(i);
                }
            } else {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    caculatePreviewRotation(cameraInfo);
                    return Camera.open(i);
                }
            }

        }
        return null;
    }

    public void setDisplayRotation(int degree) {
        displayRotation = degree;
    }

    /**
     * 计算旋转
     * @param info
     */
    private void caculatePreviewRotation(Camera.CameraInfo info) {
        int degree;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            degree = (info.orientation + displayRotation) % 360;
            degree = (360 - degree) % 360;
            previewCaptureRotation = degree;
        } else {
            // back-facing
            degree = (info.orientation - displayRotation + 360) % 360;
            previewCaptureRotation = (360 + degree) % 360;
        }
        previewRotation = degree;
    }

    /**
     * 直接开始预览
     */
    public void startPreviewForce() {
        if (status != STATUS_OPENED) {
            return;
        }
        mCamera.startPreview();
        mCamera.setPreviewCallback(mPreviewCallback);
        createAutoFocusTimerTask(new Runnable() {
            @Override
            public void run() {
                synchronized (CameraProxy1.this) {
                    try {
                        mCamera.autoFocus(new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean success, Camera camera) {
                            }
                        });
                    } catch (Throwable e) {
                        // startPreview是异步实现，可能在某些机器上前几次调用会autofocus failß
                    }
                }
            }
        });
    }

    @Override
    public void startPreview() {
        // MIUI上会在activity stop时destory关闭相机
        // 触发textureView的destory方法,此时会设置mSurfaceTexture为null
        // 这里就需要重新打开摄像头后再预览
        if (mCamera == null) {
            try {
                openCamera();
                status = STATUS_OPENED;
            } catch (RuntimeException e) {
                return;
            }
            if (mSurfaceTexture != null ) {
                try {
                    mCamera.setPreviewTexture(mSurfaceTexture);
                } catch (IOException e) {
                    UiLog.error("camera setPreviewTexture throw exception", e);
                }
            }
            startPreviewForce();
        } else  {
            // 如果已经TextureAvailable，设置texture并且预览,(必须先调用了openCamera)
            try {
                mCamera.setPreviewTexture(mSurfaceTexture);
            } catch (IOException e) {
                UiLog.error("camera setPreviewTexture throw exception", e);
            }

            startPreviewForce();
            mCamera.setPreviewCallback(mPreviewCallback);
            status = STATUS_PREVIEWING;
            return;
        }
    }

    /**
     * 预览图片过滤
     */
    private Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            // 在某些机型和某项项目中，某些帧的data的数据不符合nv21的格式，需要过滤，否则后续处理会导致crash
            if (data.length != cameraParameters.getPreviewSize().width
                    * cameraParameters.getPreviewSize().height * 1.5) {
                return;
            }
            currentFrameData = data;
        }
    };

    /**
     * 打开相机，设置一些参数
     */
    public void openCamera() {
        try {
            mCamera = open(isBack);
        } catch (RuntimeException e) {
            // 某些机子会在权限检查通过后跳出权限选择界面
            UiLog.error("unexpected fail to open camera", e);
            throw e;
        }

        cameraParameters = mCamera.getParameters();

        List<Camera.Size> supportedPreviewSizes =  mCamera.getParameters().getSupportedPreviewSizes();

        // 挑选合适的预览尺寸，并存起来供视图获取
        Camera.Size size = getPreviewSize(supportedPreviewSizes);

        // 如果是90或者270度这对调长宽比
        if (previewRotation == 90 || previewRotation == 270) {
            this.previewSize[0] = size.height;
            this.previewSize[1] = size.width;
        } else {
            this.previewSize[0] = size.width;
            this.previewSize[1] = size.height;
        }


        if (isBack) {
            cameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        cameraParameters.setPreviewSize(size.width, size.height);
        mCamera.setDisplayOrientation(previewRotation);
        mCamera.setParameters(cameraParameters);
        status = STATUS_OPENED;
        if (mCameraListener != null) {
            mCameraListener.onSwitchCamera();
        }
    }

    @Override
    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        mSurfaceTexture = surfaceTexture;
    }

    /**
     * 关闭相机
     */
    public void closeCamera() {
        stopPreview();
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;
        status = STATUS_INIT;
        UiLog.info("close camera");
    }

    @Override
    public void stopPreview() {
        cancelAutoFocusTimer();
        mCamera.stopPreview();
        status = STATUS_OPENED;
        UiLog.info("stop preview");
    }

    @Override
    public void switchSide() {
        isBack = !isBack;
        stopPreview();
        closeCamera();
        openCamera();
        startPreview();
    }

    /**
     * 把原始图片数据
     *
     * @param data
     * @return
     */
    private Bitmap convertPreviewDataToBitmap(byte[] data) {
        Camera.Size size = cameraParameters.getPreviewSize();
        YuvImage img = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
        img.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, os);
        byte[] jpeg = os.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(jpeg, 0, jpeg.length);
        Bitmap bitmapRotate;
        if (isBack) {
            bitmapRotate = IDCardImageUtil.createRotateBitmap(bitmap, previewCaptureRotation);
        } else {
            bitmapRotate = IDCardImageUtil.createRotateBitmap(bitmap, previewCaptureRotation);
        }
        try {
            os.close();
        } catch (IOException e) {
            UiLog.error("convertPreviewDataToBitmap close io exception", e);
        }
        return bitmapRotate;
    }

    @Override
    public void takePicture(CameraListener.TakePictureListener listener) {
        if (currentFrameData != null) {
            Bitmap bitmap = convertPreviewDataToBitmap(currentFrameData);
            listener.onTakenPicture(bitmap);
            UiLog.info("convert bitmap success");
        }
    }

    @Override
    public void setEventListener(CameraListener.CommonListener listener) {
        mCameraListener = listener;
    }
}
