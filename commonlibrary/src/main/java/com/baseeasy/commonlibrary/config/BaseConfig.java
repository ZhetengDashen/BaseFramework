package com.baseeasy.commonlibrary.config;


import com.baseeasy.commonlibrary.baseview.baseframework.BaseApplication;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.MD5Utils;
import com.baseeasy.commonlibrary.mytool.SharePreferenceKeys;
import com.baseeasy.commonlibrary.mytool.SharePreferenceUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;

/**
 * 作者：WangZhiQiang
 * 时间：2019/10/23
 * 邮箱：sos181@163.com
 * 描述：配置
 */
public class BaseConfig {

    /*
    * 文件夹路径
    * */
    public static class FOLDER_PATH{
        private static  String userid= SharePreferenceUtils.getString(BaseApplication.getInstance().getApplicationContext(), SharePreferenceKeys.USER_ID);
        public static final String APP_FOLDER_PATH=AppUtils.getAppName(BaseApplication.getInstance().getApplicationContext())+"/"+
                       userid=="" ?  MD5Utils.encodeMD5(userid):"0" +"/";
        public static final String LOG=APP_FOLDER_PATH+FOLDER_NAME.LOG;
        public static final String CAMERA=APP_FOLDER_PATH+FOLDER_NAME.CAMERA;
        public static final String COMPRESSION=APP_FOLDER_PATH+FOLDER_NAME.COMPRESSION;
        public static final String SIGN=APP_FOLDER_PATH+FOLDER_NAME.SIGN;
        public static final String FINGERPRINT=APP_FOLDER_PATH+FOLDER_NAME.FINGERPRINT;
        public static final String VIDEO=APP_FOLDER_PATH+FOLDER_NAME.VIDEO;

    }
    /**
     *文件夹名称
    * */
    public static class  FOLDER_NAME{
        public static final String LOG="log";//日志路径
        public static final String CAMERA="camera_image";//拍照路径
        public static final String COMPRESSION="compression";//压缩图片路径
        public static final String SIGN="sign";//签名
        public static final String FINGERPRINT="fingerprint";//指纹图片路径
        public static final String VIDEO ="video" ;//视频
    }

}
