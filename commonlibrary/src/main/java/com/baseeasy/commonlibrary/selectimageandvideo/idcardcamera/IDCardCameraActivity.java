package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.IntentExtraName.IDCARD_RESULT_DATA;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_MAIN;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_MASK_TYPE;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.MASK_TYPE_IDCARD_BACK;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.MASK_TYPE_IDCARD_FRONT;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDcardParameter;

import java.io.File;

/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：自定义相机拍摄身份证
 */
public class IDCardCameraActivity extends CameraImageBaseActivity {



    @Override
    protected void onPictureProcess(Bitmap bitmap, @Nullable Bitmap origin, boolean isTriggerManual) {

        File file= FileUtils.saveBitmapFile(bitmap,PictureShared.FolderNameConfig.COMPRESSION+"/","compress_idcard"+System.currentTimeMillis() +".jpg");
        Intent intent =new Intent();
        intent.putExtra(IDCARD_RESULT_DATA,file.getAbsolutePath());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onDispatchCreate(@Nullable Bundle savedInstanceState) {
        IDcardParameter parameter = new IDcardParameter();
        Bundle bundle = getIntent().getBundleExtra(KEY_MAIN);
        if (bundle != null) {
            if (bundle.getInt(KEY_MASK_TYPE) == MASK_TYPE_IDCARD_FRONT) {
                parameter.setDefaultTip("请对正身份证头像页");
            }
            if (bundle.getInt(KEY_MASK_TYPE) == MASK_TYPE_IDCARD_BACK) {
                parameter.setDefaultTip("请对正身份证国徽页");
            }
        }
        setParameter(parameter);
    }
}