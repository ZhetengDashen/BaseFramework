package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.util;

import android.content.Context;




/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：
 */
public class CrashReporterHandler implements Thread.UncaughtExceptionHandler {



    private static CrashReporterHandler instance;

    private static Thread.UncaughtExceptionHandler defaultHandler = null;

    private Context ctx;

    public CrashReporterHandler(Context context) {
        ctx = context;
    }

    /**
     * Factory method, return a singleton CrashReporterHandler object
     *
     * @param ctx application context
     */
    public static CrashReporterHandler init(Context ctx) {
        if (instance == null) {
            instance = new CrashReporterHandler(ctx);
        }
        if (defaultHandler == null) {
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        }

        Thread.setDefaultUncaughtExceptionHandler(instance);
        return instance;
    }

    public void release() {
        ctx = null;
    }



    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            UiLog.error("uncaughtException", e);
        } catch (Throwable throwable) {
            // do nothing
        }
        defaultHandler.uncaughtException(t, e);
    }
}
