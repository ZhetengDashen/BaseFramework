package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

/**
 * 作者：WangZhiQiang
 * 时间：2020/1/17
 * 邮箱：sos181@163.com
 * 描述：拍摄视频配置
 */
public class ShotVideoConfig {
    int maxTime=120;//最大时长秒
    int eesolutionMode= ConfigParameter.RESOLUTION_480P;//分辨率
    int eatioMode= ConfigParameter.RATIO_MODE_3_4;//比例
    int videoQuality= ConfigParameter.PROGRESS_HIGH;//质量

    public ShotVideoConfig() {
    }

    public ShotVideoConfig(int maxTime, int eesolutionMode, int eatioMode, int videoQuality) {
        this.maxTime = maxTime;
        this.eesolutionMode = eesolutionMode;
        this.eatioMode = eatioMode;
        this.videoQuality = videoQuality;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getEesolutionMode() {
        return eesolutionMode;
    }

    public void setEesolutionMode(int eesolutionMode) {
        this.eesolutionMode = eesolutionMode;
    }

    public int getEatioMode() {
        return eatioMode;
    }

    public void setEatioMode(int eatioMode) {
        this.eatioMode = eatioMode;
    }

    public int getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(int videoQuality) {
        this.videoQuality = videoQuality;
    }

    public static class ConfigParameter{
        public static final int RATIO_MODE_3_4 = 0;
        public static final int RATIO_MODE_1_1 = 1;
        public static final int RATIO_MODE_9_16 = 2;

        public static final int RESOLUTION_360P = 0;
        public static final int RESOLUTION_480P = 1;
        public static final int RESOLUTION_540P = 2;
        public static final int RESOLUTION_720P = 3;

        public static final int PROGRESS_LOW = 25;
        public static final int PROGRESS_MEIDAN = 50;
        public static final int PROGRESS_HIGH = 75;
        public static final int PROGRESS_SUPER = 100;



    }

    public static final class Builder {
        int maxTime=120;//最大时长秒
        int eesolutionMode= ConfigParameter.RESOLUTION_480P;//分辨率
        int eatioMode= ConfigParameter.RATIO_MODE_3_4;//比例
        int videoQuality= ConfigParameter.PROGRESS_HIGH;//质量

        public Builder() {
        }

        public static Builder aShotVideoConfig() {
            return new Builder();
        }

        public Builder withMaxTime(int maxTime) {
            this.maxTime = maxTime;
            return this;
        }

        public Builder withEesolutionMode(int eesolutionMode) {
            this.eesolutionMode = eesolutionMode;
            return this;
        }

        public Builder withEatioMode(int eatioMode) {
            this.eatioMode = eatioMode;
            return this;
        }

        public Builder withVideoQuality(int videoQuality) {
            this.videoQuality = videoQuality;
            return this;
        }

        public ShotVideoConfig build() {
            return new ShotVideoConfig(maxTime, eesolutionMode, eatioMode, videoQuality);
        }
    }
}
