package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.camera.CameraListener;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.camera.CameraProxy1;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.camera.ICameraProxy;

/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：处理预览与相机的交互
 */

public class PreviewView extends TextureView implements TextureView.SurfaceTextureListener {
    private CameraProxy1 mCameraProxy;

    private int layoutWidth;
    private int layoutHeight;
    private int actualHeight;
    private int cropHeight;

    private boolean isFirstLayout = true;
    private boolean hasSurfaceDestory = false;

    public PreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSurfaceTextureListener(this);
    }

    public PreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSurfaceTextureListener(this);
    }

    /**
     *
     * @param listener
     */
    public void takePicture(final CameraListener.TakePictureListener listener) {
        if (mCameraProxy == null) {
            return;
        }
        mCameraProxy.takePicture(new CameraListener.TakePictureListener() {
            @Override
            public void onTakenPicture(Bitmap bitmap) {
                Bitmap cropBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), cropHeight);
                listener.onTakenPicture(cropBitmap);
            }
        });
    }

    /**
     * 停止摄像机预览
     */
    public void stopPreview() {
        mCameraProxy.stopPreview();
    }

    /**
     * 返回相机操作代理
     * @return
     */
    public ICameraProxy getCameraControl() {
        return mCameraProxy;
    }

    /**
     *  根据首次绘制获得的预览框实际大小，对预览框重新调整比例(高度拉伸)
     *  从camera.getPreviewSize方法获取的size满足长宽比小于实际预览窗口长宽比
     *  仅对5.0以下camera有意义
     */
    private void setStretchHeight() {
        int[] size = mCameraProxy.getPreviewSize();
        actualHeight =  (int) (((float)  size[1] / size[0]) * layoutWidth);
        cropHeight = (int) (((float)  layoutHeight / layoutWidth) * size[0]);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 第一次正常measure，第二次之后视为需要根据camera预览实际长宽重新拉伸
        if (!isFirstLayout) {
            setMeasuredDimension(getMeasuredWidth(), actualHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public boolean isCameraOpened() {
        return mCameraProxy.getStatus() > 0;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.e("","onSurfaceTextureAvailable" + width + ":" + height);
        if (isFirstLayout) {
            layoutWidth = width;
            layoutHeight = height;
            mCameraProxy = new CameraProxy1(width, height);
            // 打开相机后可获取相机参数
            try {
                mCameraProxy.openCamera();
            } catch (RuntimeException e) {
                // 主要是某些手机权限返回granted但实际还未获取到权限
                return;
            }
            setStretchHeight();
            requestLayout();
            isFirstLayout = false;
        }

        // 某些机器stop后会销毁SurfaceTexture，需要重新打开摄像机
        if (hasSurfaceDestory) {
            try {
                mCameraProxy.openCamera();
            } catch (RuntimeException e) {
                // 主要是某些手机权限返回granted但实际还未获取到权限
                return;
            }
            hasSurfaceDestory = false;
        }

        mCameraProxy.setSurfaceTexture(surface);
        mCameraProxy.startPreview();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // MIUI activity stop后会销毁SurfaceTexture，需要通知camera
        Log.e("KK","onSurfaceTextureDestroyed");
        mCameraProxy.setSurfaceTexture(null);
        hasSurfaceDestory = true;
        return false;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
