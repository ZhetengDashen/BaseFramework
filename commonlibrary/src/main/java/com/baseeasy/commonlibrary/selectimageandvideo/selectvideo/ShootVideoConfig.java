package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

/**
 * 作者：WangZhiQiang
 * 时间：2019/12/27
 * 邮箱：sos181@163.com
 * 描述：拍摄视频配置
 */
public class ShootVideoConfig {
    int videoSecond=120;//视频时长秒
    int videoQuality=0;//0低质量  1高质量

    public ShootVideoConfig() {
    }

    public ShootVideoConfig(int videoSecond, int videoQuality) {
        this.videoSecond = videoSecond;
        this.videoQuality = videoQuality;
    }

    public int getVideoSecond() {
        return videoSecond;
    }

    public void setVideoSecond(int videoSecond) {
        this.videoSecond = videoSecond;
    }

    public int getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(int videoQuality) {
        this.videoQuality = videoQuality;
    }
}
