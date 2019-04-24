package com.baseeasy.commonlibrary.arouter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/14
 * 邮箱：sos181@163.com
 * 描述：ARouter工具类
 */
public class ARouterTools {


    /**
     * @param aRouterPath 路由跳转路径
     */
    public static void startActivity(String aRouterPath){
        ARouter.getInstance().build(aRouterPath).navigation();
    }


    /**
     * @param aRouterPath
     * @param activity
     * @param requestCode 必须大于0
     */
    public static void startActivityResult(String  aRouterPath, Activity activity, int requestCode){
        ARouter.getInstance().build(aRouterPath).navigation(activity,requestCode);
    }



    /**
     * @param aRouterPath
     * @param key
     * @param bundle
     */
     public static void startActivity(String  aRouterPath, String key, Bundle bundle){
             ARouter.getInstance().build(aRouterPath)
            .withBundle(key,bundle)
            .navigation();
     }


    /**
     * @param aRouterPath
     * @param key
     * @param bundle
     * @param activity
     * @param requestCode
     */
    public static void startActivityResult(String  aRouterPath, String key, Bundle bundle, Activity activity, int requestCode){
        ARouter.getInstance().build(aRouterPath).withBundle(key,bundle).navigation(activity,requestCode);
    }

    /**
     * @param aRouterPath
     * @param key
     * @param value
     */
    public static void startActivity(String  aRouterPath, String key,String value){
        ARouter.getInstance().build(aRouterPath)
                .withString(key,value)
                .navigation();
    }


    /**
     * @param aRouterPath
     * @param key
     * @param value
     * @param activity
     * @param requestCode
     */
    public static void startActivityResult(String  aRouterPath,String key,String value, Activity activity, int requestCode){
        ARouter.getInstance().build(aRouterPath).withString(key,value).navigation(activity,requestCode);
    }


    /**
     * @param aRouterPath
     * @param key
     * @param object
     */
    public static void startActivity(String  aRouterPath, String key,Object object){
        ARouter.getInstance().build(aRouterPath)
                .withObject(key,object)
                .navigation();

     }


    /**
     * @param aRouterPath
     * @param key
     * @param object
     * @param activity
     * @param requestCode
     */
    public static void startActivityResult(String  aRouterPath,String key,Object object, Activity activity, int requestCode){
        ARouter.getInstance().build(aRouterPath).withObject(key,object).navigation(activity,requestCode);
    }
}
