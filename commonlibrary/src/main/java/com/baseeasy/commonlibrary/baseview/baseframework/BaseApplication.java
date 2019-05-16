package com.baseeasy.commonlibrary.baseview.baseframework;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.log2file.LogFileEngineFactory;
import com.apkfuns.logutils.LogUtils;

import com.baseeasy.commonlibrary.imageloader.GlideImageLoader;
import com.baseeasy.commonlibrary.imageloader.ImageLoader;
import com.baseeasy.commonlibrary.imageloader.ImageLoaderFactory;
import com.baseeasy.commonlibrary.imageloader.PicassoImageLoader;
import com.baseeasy.commonlibrary.mlog.MyLogFileEngine;
import com.baseeasy.commonlibrary.mytool.AppUtils;

import java.util.Stack;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/13
 * 邮箱：sos181@163.com
 * 描述：
 */
public  class BaseApplication extends Application {
    private static Stack<Activity> activityStack;
    private static BaseApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        if (AppUtils.isApkInDebug(getApplicationContext())) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        ImageLoaderFactory.init(initImageLoader());
        initLog();

    }
   //初始化图形加载框架
   public  ImageLoader initImageLoader(){
       //根据当前情况初始化合适的图像框架  也可以在子类中重写该方法
      ImageLoader imageLoader=new PicassoImageLoader(getApplicationContext());
//       ImageLoader imageLoader2=new GlideImageLoader(getApplicationContext());
         return imageLoader;
   };

    //初始化日志框架
    public void initLog(){
        //studio日志配置
        LogUtils.getLogConfig()
                .configAllowLog(AppUtils.isApkInDebug(getApplicationContext()))//是否允许日志输出
                .configTagPrefix(AppUtils.getAppName(this))//日志log的前缀 TAG
                .configShowBorders(true)//是否显示边界
                .configFormatTag("时间:%d{HH:mm:ss:SSS} 线程:%t  类: %c{-5}");


         //sdk日志配置
        LogUtils.getLog2FileConfig().configLog2FileEnable(true)
                // targetSdkVersion >= 23 需要确保有写sdcard权限
                .configLog2FilePath(Environment.getExternalStorageDirectory()+"/"+ AppUtils.getAppName(this)+"/log")
                .configLog2FileNameFormat("%d{yyyyMMdd}.txt")
                .configLogFileEngine(new MyLogFileEngine(getApplicationContext()));
    }

    public static BaseApplication getInstance() {
        return singleton;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity getcurrentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public  void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public  void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public  void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();

            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }
}
