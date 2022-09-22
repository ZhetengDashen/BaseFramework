package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;

import android.content.Context;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.baseeasy.commonlibrary.selectimageandvideo.GlideEngine;
import com.luck.lib.camerax.CustomCameraConfig;
import com.luck.lib.camerax.SimpleCameraX;
import com.luck.picture.lib.interfaces.OnCameraInterceptListener;

public class MeOnCameraInterceptListener implements OnCameraInterceptListener {
    @Override
    public void openCamera(Fragment fragment, int cameraMode, int requestCode) {
        SimpleCameraX camera = SimpleCameraX.of();
//        camera.isAutoRotation(true);
        camera.setCameraMode(cameraMode);
        camera.isDisplayRecordChangeTime(true);
//        camera.isManualFocusCameraPreview(true);
//        camera.isZoomCameraPreview(true);
        camera.setImageEngine(GlideEngine.createGlideEngine());
        camera.start(fragment.requireActivity(), fragment, requestCode);
    }
}
