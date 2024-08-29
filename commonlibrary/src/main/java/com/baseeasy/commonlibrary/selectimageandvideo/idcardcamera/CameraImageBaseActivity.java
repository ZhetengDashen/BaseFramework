package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera;



import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_HAS_ALBUM_BUTTON;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_HAS_FLASH_BUTTON;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_MAIN;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_MASK_TYPE;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_OUTPUT_FILE_PATH;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_TAKE_PICTURE_TYPE;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.TYPE_MANUAL;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.camera.CameraListener;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util.AutoTrigger;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util.CrashReporterHandler;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util.IDCardImageUtil;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util.UiLog;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.view.CropView;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.view.MaskView;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.view.PreviewView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ruanshimin on 2018/6/13.
 */

abstract class CameraImageBaseActivity<T extends IDCardBaseParameter> extends Activity {

    static final int REQUEST_PERMISSION_CODE_CAMERA = 1;
    static final int REQUEST_PERMISSION_CODE_STORAGE = 2;

    static final int INTENT_CODE_PICK_IMAGE = 1;

    private volatile boolean statusPreview = true;

    private PreviewView mPreviewView;
    private ImageView previewSnapshot;

    private MaskView mMaskView;
    private CropView mCropView;

    private ImageView takePictureBtn;
    private ImageView flashBtn;
    private ImageView albumBtn;

    private ImageView rotatePictureBtn;
    private ImageView cancelBtn;
    private ImageView confirmBtn;

    private boolean isFlashOpen = false;

    private boolean isAuto;

    private boolean isToAlbumActivity = false;

    protected T parameter;

    protected abstract void onPictureProcess(Bitmap bitmap, @Nullable Bitmap origin, boolean isTriggerManual);

    protected abstract void onDispatchCreate(@Nullable Bundle savedInstanceState);

    protected void setParameter(T param) {
        parameter = param;
    }

    protected T getParameter() {
        return parameter;
    }

    /**
     * 保存图片，供activity之间参数传递
     * @param bitmap
     */
    protected void saveOutputFile(Bitmap bitmap) {
        File file = new File(parameter.getResultImagePath());
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            if (!isSuccess) {
                UiLog.error("bitmap save output fail");
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            UiLog.error("bitmap save output exception" , e);
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashReporterHandler.init(this);
        Bundle bundle = getIntent().getBundleExtra(KEY_MAIN);
        onDispatchCreate(savedInstanceState);
        if (parameter == null) {
            parameter = (T) new IDCardBaseParameter();
        }
        if (bundle != null) {
            parameter.setFlashShow(bundle.getBoolean(KEY_HAS_FLASH_BUTTON, true));
            parameter.setAlbumShow(bundle.getBoolean(KEY_HAS_ALBUM_BUTTON, true));
            parameter.setType(bundle.getInt(KEY_TAKE_PICTURE_TYPE, IDCardBaseParameter.TYPE_AUTO));
            parameter.setMaskType(bundle.getInt(KEY_MASK_TYPE));
            parameter.setResultImagePath(bundle.getString(KEY_OUTPUT_FILE_PATH, ""));

            try {

                parameter.setFlashShow(  this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH));//不显示闪光灯按钮
            }catch (Exception e){
               e.printStackTrace();
                parameter.setFlashShow(false);//不显示闪光灯按钮
            }

        }
        if (parameter.getType() == IDCardBaseParameter.TYPE_AUTO) {
            isAuto = true;
        }

        requestPermissionCamera();
    }

    private void init() {
        setContentView(R.layout.activity_idcard_camera);

        mPreviewView = (PreviewView) findViewById(R.id.preview_view);

        // 预览按钮
        takePictureBtn = findViewById(R.id.take_picture_btn);
        initOptionBtns();

        // 操作按钮
        rotatePictureBtn = findViewById(R.id.rotate_picture_btn);
        cancelBtn = findViewById(R.id.cancel_btn);
        confirmBtn = findViewById(R.id.confirm_btn);
        toggleActionBtns(false);

        previewSnapshot = (ImageView) findViewById(R.id.preview_snapshot);
        mMaskView = (MaskView) findViewById(R.id.maskview);
        mCropView = (CropView) findViewById(R.id.cropview);
        mCropView.setVisibility(View.GONE);

        if (!isAuto) {
            takePictureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPreviewView.takePicture(new CameraListener.TakePictureListener() {
                        @Override
                        public void onTakenPicture(Bitmap bitmap) {
                            pictureTaken(bitmap);
                        }
                    });
                }
            });
            takePictureBtn.setVisibility(View.VISIBLE);
        } else {
            takePictureBtn.setVisibility(View.GONE);
        }


        rotatePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotatePicture();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPreview();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap origin =   ((BitmapDrawable) previewSnapshot.getDrawable()).getBitmap();
                Bitmap bitmap = doCropBitmap(origin);
                onPictureProcess(bitmap, origin, true);
            }
        });

        switch (parameter.getMaskType()) {
            case IDCardBaseParameter.MASK_TYPE_BANKCARD:
                mMaskView.setMaskType(MaskView.MASK_TYPE_BANKCARD);
                mMaskView.setTipString(parameter.getDefaultTip());
                break;
            case IDCardBaseParameter.MASK_TYPE_IDCARD_FRONT:
                mMaskView.setMaskType(MaskView.MASK_TYPE_IDCARD_FRONT);
                mMaskView.setTipString(parameter.getDefaultTip());
                break;
            case IDCardBaseParameter.MASK_TYPE_IDCARD_BACK:
                mMaskView.setMaskType(MaskView.MASK_TYPE_IDCARD_BACK);
                mMaskView.setTipString(parameter.getDefaultTip());
                break;
            default:
                mMaskView.setMaskType(MaskView.MASK_NONE);
        }

        if (isAuto) {
            createAutoTakeTimer();
        }
    }

    public void setTipString(final String tip) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMaskView.setTipStringAndUpdate(tip);
            }
        });

    }


    /**
     * 开启自动拍照
     */
    private void createAutoTakeTimer() {
        if (getParameter().getType() == IDCardBaseParameter.TYPE_AUTO) {
            AutoTrigger.createAutoTakeTimerTask(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        if (statusPreview) {
                            mPreviewView.takePicture(new CameraListener.TakePictureListener() {
                                @Override
                                public void onTakenPicture(Bitmap bitmap) {
                                    pictureTakenAndCrop(bitmap);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    /**
     * 关闭自动拍照
     */
    private void cancelAutoTakeTimer() {
        if (getParameter().getType() == IDCardBaseParameter.TYPE_AUTO) {
            AutoTrigger.cancelAutoTakeTimer();
        }
    }

    /**
     * 根据参数选择性展示按钮
     */
    private void initOptionBtns() {

        albumBtn = findViewById(R.id.album_btn);
        flashBtn = findViewById(R.id.flash_btn);

        if (parameter.isFlashShow()) {
            flashBtn.setVisibility(View.VISIBLE);
            flashBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFlashOpen = !isFlashOpen;
                    refreshFlash();
                }
            });
        } else {
            flashBtn.setVisibility(View.GONE);
        }

        if (parameter.isAlbumShow()) {
            albumBtn.setVisibility(View.VISIBLE);
            albumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestPermissionAlbum();
                }
            });
        } else {
            albumBtn.setVisibility(View.GONE);
        }
    }


    /**
     * 裁剪图片
     * @param bitmap
     * @return
     */
    private Bitmap doCropBitmap(Bitmap bitmap) {
        Rect crop = mCropView.getCropRect();
        Matrix matrix = previewSnapshot.getImageMatrix();
        Matrix inverted = new Matrix();
        matrix.invert(inverted);
        float[] pts = {crop.left, crop.top, crop.right, crop.bottom};
        inverted.mapPoints(pts);
        float x = pts[0] < 0 ? 0 : pts[0];
        float y = pts[1] < 0 ? 0 : pts[1];
        float width = pts[2] - pts[0] > bitmap.getWidth() ? bitmap.getWidth() : pts[2] - pts[0];
        float height = pts[3] - pts[1] > bitmap.getHeight() ? bitmap.getHeight() : pts[3] - pts[1];
        Bitmap bt = IDCardImageUtil.createCropBitmap(bitmap, x,
                y , width, height, new Matrix());
        return bt;
    }

    /**
     * 裁剪图片
     * 不通过ImageView
     *
     * @param bitmap
     * @return
     */
    private Bitmap doCropBitmapAutoPicture(Bitmap bitmap) {
        Rect crop = mCropView.getCropRect();
        float ratio = (float) previewSnapshot.getWidth() / bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);
        matrix.invert(matrix);
        float[] pts = {crop.left, crop.top, crop.right, crop.bottom};
        matrix.mapPoints(pts);
        Bitmap bt = IDCardImageUtil.createCropBitmap(bitmap, pts[0],
                pts[1] , pts[2] - pts[0], pts[3] - pts[1], matrix);
        return bt;
    }

    /**
     * 动态申请相册权限
     * 成功后打开相册
     */
    private void requestPermissionAlbum() {
        // 判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_STORAGE);
        } else {
            openAlum();
        }
    }

    /**
     * 打开相册选图
     */
    private void openAlum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        isToAlbumActivity = true;
        intent.setType("image/*");
        startActivityForResult(intent, INTENT_CODE_PICK_IMAGE);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(contentURI, null, null, null, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * 从相册读取图片
     * @param path
     * @return
     */
    private Bitmap readFile(String path) {
        BitmapFactory.Options option = new BitmapFactory.Options();

        BitmapFactory.decodeFile(path, option);
        option.inJustDecodeBounds = true;
        if (option.outWidth > 1080 || option.outHeight > 1920) {
            option.inSampleSize = 2;
        }
        option.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, option);
        UiLog.info("pick image success");
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_CODE_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                String path = getRealPathFromURI(uri);
                UiLog.info("pick image url: " + path);
                Bitmap bitmap = readFile(path);
                ExifInterface exif = null;
                int exifRotation = 0;
                try {
                    exif = new ExifInterface(path);
                    exifRotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);
                } catch (IOException e) {
                    UiLog.error("album selection picture get exif info error", e);
                }

                int rotation = IDCardImageUtil.exifToDegrees(exifRotation);
                Bitmap rotateBitmap = IDCardImageUtil.createRotateBitmap(bitmap, rotation);
                pictureFromAlbum(rotateBitmap);
            } else {
                // 取消的时候退出到的是预览界面
                isToAlbumActivity = false;
            }
        }
    }

    private void refreshFlash() {
        if (mPreviewView.isCameraOpened()) {
            mPreviewView.getCameraControl().toggleFlash(isFlashOpen);
            if (isFlashOpen) {

                flashBtn.setImageResource(R.drawable.basecamera_flash_on_btn);
            } else {

                flashBtn.setImageResource(R.drawable.basecamera_flash_off_btn);
            }
        }
    }

    private int previewRotation = 0;

    /**
     * 旋转预览
     */
    private void rotatePicture() {
        previewRotation -= 90;
        Bitmap rotateBitmap = IDCardImageUtil.createRotateBitmap(snapShotBitmap, previewRotation);
        previewSnapshot.setImageBitmap(rotateBitmap);
        setCropBound();
    }

    /**
     * 设置边界
     */
    private void setCropBound() {
        Matrix matrix = previewSnapshot.getImageMatrix();
        float[] pts;
        if (previewRotation % 180 != 0) {
            pts = new float[]{0, 0, snapShotBitmap.getHeight(), snapShotBitmap.getWidth()};
            matrix.mapPoints(pts);

        } else {
            pts = new float[]{0, 0, snapShotBitmap.getWidth(), snapShotBitmap.getHeight()};
            matrix.mapPoints(pts);
        }
        mCropView.setBound((int) pts[0], (int) pts[1], (int) pts[2], (int) pts[3]);
    }

    private Bitmap snapShotBitmap;

    /**
     * 切换预览界面的按钮展示与否
     * @param isShow
     */
    private void togglePreviewBtns(boolean isShow) {
        int flag = isShow ? View.VISIBLE : View.GONE;
        takePictureBtn.setVisibility(flag);
        if (parameter.isFlashShow()) {
            flashBtn.setVisibility(flag);
        } else {
            flashBtn.setVisibility(View.GONE);
        }
        if (parameter.isAlbumShow()) {
            albumBtn.setVisibility(flag);
        } else {
            albumBtn.setVisibility(View.GONE);
        }
    }

    /**
     * 切换操作界面的按钮展示与否
     * @param isShow
     */
    private void toggleActionBtns(boolean isShow) {
        int flag = isShow ? View.VISIBLE : View.GONE;
        rotatePictureBtn.setVisibility(flag);
        confirmBtn.setVisibility(flag);
        cancelBtn.setVisibility(flag);
    }

    /**
     * 点击拍照按钮,或者选择相册图片
     * @param bitmap
     */
    private void pictureTaken(final Bitmap bitmap) {
        snapShotBitmap = bitmap;
        UiLog.info("takePictureSuccess");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                previewSnapshot.setImageBitmap(bitmap);
                changeToAction(false);
                setCropBound();
            }
        });
    }

    private void pictureFromAlbum(final Bitmap bitmap) {
        snapShotBitmap = bitmap;
        UiLog.info("albumSelectPictureSuccess");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                previewSnapshot.setImageBitmap(bitmap);
                changeToAction(true);
                setCropBound();
            }
        });
    }

    /**
     * 自动拍照后裁剪
     * @param bitmap
     */
    private void pictureTakenAndCrop(final Bitmap bitmap) {
        UiLog.info("autoTakePictureSuccess");
        if (mCropView.getCropRect() == null) {
            mCropView.setCropRect(mMaskView.getMaskFrame());
        }
        Bitmap cropBitmap = doCropBitmapAutoPicture(bitmap);
        onPictureProcess(cropBitmap, bitmap, false);
    }

    /**
     * 旋转图片
     */
    private void changeToAction(boolean isFromAlbum) {
        togglePreviewBtns(false);
        toggleActionBtns(true);
        mCropView.setVisibility(View.VISIBLE);
        mMaskView.setVisibility(View.GONE);
        mCropView.setCropRect(mMaskView.getMaskFrame());
        previewSnapshot.setVisibility(View.VISIBLE);

        mPreviewView.setVisibility(View.GONE);

        isFlashOpen = false;
        if (!isFromAlbum) {
            mPreviewView.stopPreview();
            refreshFlash();
        }

        statusPreview = false;

    }

    /**
     * 从裁剪图片页面返回预览页面
     */
    private void returnToPreview() {
        previewRotation = 0;
        if (getParameter().getType() == TYPE_MANUAL) {
            togglePreviewBtns(true);
        }
        toggleActionBtns(false);
        mCropView.setVisibility(View.GONE);
        mMaskView.setVisibility(View.VISIBLE);
        previewSnapshot.setVisibility(View.GONE);
        mPreviewView.getCameraControl().startPreviewForce();
        mPreviewView.setVisibility(View.VISIBLE);
        statusPreview = true;
        if (isAuto) {
            createAutoTakeTimer();
        }
    }

    /**
     * 请求相机权限
     */
    private void requestPermissionCamera() {
        // 判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE_CAMERA);
        } else {
            init();
        }
    }

    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        if (statusPreview) {
            super.onBackPressed();
        } else {
            returnToPreview();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        statusPreview = false;
    }

    @Override
    protected void onPause() {

        // 如果首次弹出权限框会导致暂时退出再返回
        // 还有一种情况是手机助手类实现动态权限错误，导致相机未打开
        // 此时不是真正的退出,无需做出反应
        if (mPreviewView != null && mPreviewView.isCameraOpened()) {
            // 关闭闪光灯
            isFlashOpen = false;
            refreshFlash();

            cancelAutoTakeTimer();

            // 停止mPreviewView预览
            mPreviewView.getCameraControl().closeCamera();

        }

        UiLog.info("activity pause");
        super.onPause();
    }

    @Override
    protected void onResume() {

        // 如果不是第一次启动就需要重新打开预览
        if (mPreviewView != null && mPreviewView.getCameraControl() != null) {
            UiLog.info("activity resume");
            if (isAuto && !isToAlbumActivity) {
                createAutoTakeTimer();
            }
            isToAlbumActivity = false;

            mPreviewView.getCameraControl().startPreview();
            statusPreview = true;

        }
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_CAMERA:
                if (grantResults[0] == -1) {
                    Toast toast = Toast.makeText(this, "请选择权限", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                } else {
                    init();
                }
                break;
            case REQUEST_PERMISSION_CODE_STORAGE:
                if (grantResults[0] == -1) {
                    Toast toast = Toast.makeText(this, "请选择权限", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    openAlum();
                }
                break;
            default:
                break;
        }

    }
}
