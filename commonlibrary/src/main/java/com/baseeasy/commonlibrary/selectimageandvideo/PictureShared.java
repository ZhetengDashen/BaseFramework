package com.baseeasy.commonlibrary.selectimageandvideo;

/**
 * 作者：WangZhiQiang
 * 时间：2019/9/29
 * 邮箱：sos181@163.com
 * 描述：
 */
public class PictureShared {
    public final static int SELECT_IMAGE_REQUEST = 0;
    public final static int TAKING_PHOTO_REQUEST = 1;

    public final static int ACTION_TYPE_SELECT_IMAGE=0;
    public final static int ACTION_TYPE_TAKING_PHOTO=1;

    public static final int SELECTIMAGE_REQUESTCODE = 1001;
    public static final int TAKINGPHOTO_REQUESTCODE=1002;
    public static final int TAKINGPHOTO_SEPARATE_REQUESTCODE=1003;
    //IntentExtra Name
    public static class IntentExtraName {
        public final static String EVENT_BUS_FLAG = "eventBusFlag";
        public final static String EXIST_IMAGES = "data";
        public final static String ACTION_TYPE = "action_type";
        public final static String SELECTIMAGE_DATA="selectImageBeansData";
    }
      //文件夹相关配置
    public static class FolderNameConfig {
        public final static String COMPRESSION = "compression";//压缩文件目录
          public final static String CAMERA = "camera_image";//压缩文件目录

    }

}
