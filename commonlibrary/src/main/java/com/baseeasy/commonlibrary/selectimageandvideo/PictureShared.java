package com.baseeasy.commonlibrary.selectimageandvideo;

import com.baseeasy.commonlibrary.config.BaseConfig;

/**
 * 作者：WangZhiQiang
 * 时间：2019/9/29
 * 邮箱：sos181@163.com
 * 描述：
 */
public class PictureShared {
    public final static int  MAX_PHOTO_NUM=100;
    public final static int  MAX_VIDEO_NUM=100;
    public final static int SELECT_IMAGE_REQUEST = 0;
    public final static int TAKING_PHOTO_REQUEST = 1;

    public final static int ACTION_TYPE_SELECT_IMAGE=0;
    public final static int ACTION_TYPE_TAKING_PHOTO=1;


    public static final int SELECTIMAGE_REQUESTCODE = 1001;
    public static final int TAKINGPHOTO_REQUESTCODE=1002;
    public static final int TAKINGPHOTO_SEPARATE_REQUESTCODE=1003;
    public  static final int SHOOTVIDEO=1004;
    //IntentExtra Name
    public static class IntentExtraName {
        public final static String EVENT_BUS_FLAG = "picture_eventBusFlag";
        public final static String EXIST_IMAGES = "picture_data";
        public final static String ACTION_TYPE = "picture_action_type";
        public final static String SELECTIMAGE_DATA="picture_selectImageBeansData";
        public final static String SELECTIMAGE_ADD_DATA="picture_addImageBeansData";
        public final static String SELECTIMAGE_DELETE_DATA="picture_deleteImageBeansData";
        public final static String SELECTVIDEO_DATA="picture_selectVideoBeansData";
        public  final static String MAXPHOTONUM="maxphotonum";
        public  final static String MAXVIDEONUM="maxvideonum";
        public  final static String SHOOTVIDEOCONFIG="shootVideoConfig";
        public  final static String ISPREVIEW="previewVideo";
        public  final static String REQUSETCODE="requestCode";
    }
      //文件夹相关配置
    public static class FolderNameConfig {
          public final static String COMPRESSION = BaseConfig.FOLDER_PATH.COMPRESSION;//压缩文件目录
          public final static String CAMERA = BaseConfig.FOLDER_PATH.CAMERA;//拍照路径
          public final static String VIDEO=BaseConfig.FOLDER_PATH.VIDEO;//视频

    }

}
