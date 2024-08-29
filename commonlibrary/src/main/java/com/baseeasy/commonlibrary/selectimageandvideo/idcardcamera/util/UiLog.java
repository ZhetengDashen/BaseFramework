package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util;

import android.util.Log;

/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：
 */

public class UiLog {

    private static final String TAG = "bd_aipe_ocr_offline_cameraui_log";

    public static void info(String log) {
        Log.i(TAG, log);
    }

    public static void warn(String log) {
        Log.w(TAG, log);
    }

    public static void error(String log) {
        Log.e(TAG, log);
    }

    public static void error(String log, Throwable e) {
        Log.e(TAG, log, e);
    }
}
