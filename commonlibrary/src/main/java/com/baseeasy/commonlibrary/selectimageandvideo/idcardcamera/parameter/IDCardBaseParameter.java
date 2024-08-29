package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter;

/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：
 */
public class IDCardBaseParameter {

    public static final String KEY_HAS_FLASH_BUTTON = "has_flash_btn";
    public static final String KEY_HAS_ALBUM_BUTTON = "has_album_btn";
    public static final String KEY_OUTPUT_FILE_PATH = "temp_file_path";
    public static final String KEY_TAKE_PICTURE_TYPE = "take_picture_type";
    public static final String KEY_MASK_TYPE = "mask_type";
    public static final String KEY_DEFAULT_TIP = "default_tip";
    public static final String KEY_MAIN = "extra";

    public static final int TYPE_AUTO = 0;
    public static final int TYPE_MANUAL = 1;

    public static final int MASK_TYPE_NONE = -1;
    public static final int MASK_TYPE_BANKCARD = 0;
    public static final int MASK_TYPE_IDCARD_FRONT = 1;
    public static final int MASK_TYPE_IDCARD_BACK = 2;

    private int type;
    private boolean isFlashShow;
    private boolean isAlbumShow;

    public String getDefaultTip() {
        return defaultTip;
    }

    public void setDefaultTip(String defaultTip) {
        this.defaultTip = defaultTip;
    }

    protected String defaultTip;

    protected int maskType;

    private String resultImagePath;

    public int getMaskType() {
        return maskType;
    }

    public void setMaskType(int maskType) {
        this.maskType = maskType;
    }

    public String getResultImagePath() {
        return resultImagePath;
    }

    public void setResultImagePath(String resultImagePath) {
        this.resultImagePath = resultImagePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public IDCardBaseParameter() {
        this.type = TYPE_MANUAL;
        this.isFlashShow = true;
        this.isAlbumShow = true;
    }

    public IDCardBaseParameter(int type, boolean isFlashShow, boolean isAlbumShow) {
        this.type = type;
        this.isFlashShow = isFlashShow;
        this.isAlbumShow = isAlbumShow;
    }

    public boolean isFlashShow() {
        return isFlashShow;
    }

    public void setFlashShow(boolean flashShow) {
        isFlashShow = flashShow;
    }

    public boolean isAlbumShow() {
        return isAlbumShow;
    }

    public void setAlbumShow(boolean albumShow) {
        isAlbumShow = albumShow;
    }
}
