package com.baseeasy.commonlibrary.config;


import com.baseeasy.commonlibrary.baseview.baseframework.BaseApplication;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.MD5Utils;
import com.baseeasy.commonlibrary.mytool.SharePreferenceKeys;
import com.baseeasy.commonlibrary.mytool.SharePreferenceUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;

import org.apache.commons.lang3.StringUtils;

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
         public static final String APP_FOLDER_PATH=getuser();
        public static final String LOG=APP_FOLDER_PATH+FOLDER_NAME.LOG;
        public static final String CAMERA=APP_FOLDER_PATH+FOLDER_NAME.CAMERA;
        public static final String COMPRESSION=APP_FOLDER_PATH+FOLDER_NAME.COMPRESSION;
        public static final String SIGN=APP_FOLDER_PATH+FOLDER_NAME.SIGN;
        public static final String FINGERPRINT=APP_FOLDER_PATH+FOLDER_NAME.FINGERPRINT;
        public static final String VIDEO=APP_FOLDER_PATH+FOLDER_NAME.VIDEO;
        public static final String SCREENSHOTS=APP_FOLDER_PATH+FOLDER_NAME.SCREENSHOTS;
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
        public static final String SCREENSHOTS="screenshots";//截图
    }
    public   static String getuser(){
       String userid= SharePreferenceUtils.getString(BaseApplication.getInstance().getApplicationContext(), SharePreferenceKeys.USER_ID);
       if(StringUtils.isNotBlank(userid)){
         return   MD5Utils.encodeMD5(userid)+"/";
       }else  {
           return "no_user/";
       }
    }
}
