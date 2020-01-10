package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.luck.picture.lib.PictureSelector;
import com.zyf.vc.ui.PlayVideoActiviy;

/**
 * 作者：WangZhiQiang
 * 时间：2019/12/27
 * 邮箱：sos181@163.com
 * 描述：播放视频
 */
public class VideoPlayUtils {
    public static void play(Activity activity, String  videoPath){
//        PictureSelector.create(activity).externalPictureVideo(videoPath);
        Intent intent =new Intent(activity, PlayVideoActiviy.class);
        intent.putExtra(PlayVideoActiviy.KEY_FILE_PATH, videoPath);
        activity.startActivity(intent);
    }
}
