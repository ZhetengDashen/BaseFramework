package com.baseeasy.baseframework;



import com.baseeasy.commonlibrary.baseview.baseframework.BaseApplication;
import com.baseeasy.commonlibrary.imageloader.GlideImageLoader;
import com.baseeasy.commonlibrary.imageloader.ImageLoader;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/13
 * 邮箱：sos181@163.com
 * 描述：
 */
public class TestWorkAppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "b905e1a395", true);
    }
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(context);
//        Multidex.install(this);
//    }
    @Override
    public ImageLoader initImageLoader() {
//       return new PicassoImageLoader(getApplicationContext());
     return new GlideImageLoader(getApplicationContext());
    }
}
