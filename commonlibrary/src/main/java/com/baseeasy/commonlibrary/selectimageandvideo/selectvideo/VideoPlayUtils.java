package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

import android.app.Activity;
import android.content.Context;

import com.luck.picture.lib.PictureSelector;

/**
 * 作者：WangZhiQiang
 * 时间：2019/12/27
 * 邮箱：sos181@163.com
 * 描述：播放视频
 */
public class VideoPlayUtils {
    public static void play(Activity activity, String  videoPath){
        PictureSelector.create(activity).externalPictureVideo(videoPath);
    }
}
