package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;


import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageActivity;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageBean;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageFragment;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoSeparateCallBack;

import java.util.List;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：选择拍摄视频
 */
public class ShootVideoUtils {
    public static final String FRAGMENT_TAG = "SelectVideoUtils";

    public volatile static ShootVideoUtils shootVideoUtils;

    public static ShootVideoUtils getInstance() {
        if (shootVideoUtils == null) {
            synchronized (ShootVideoUtils.class) {
                if (shootVideoUtils == null) {
                    shootVideoUtils = new ShootVideoUtils();

                }
            }
        }
        return shootVideoUtils;
    }
     private ShootVideoFragment initFragment(FragmentActivity activity){
         ShootVideoFragment selectVideoFragment = (ShootVideoFragment) activity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
         if (selectVideoFragment == null) {
             FileUtils.createSDDir(PictureShared.FolderNameConfig.VIDEO);
             FileUtils.createNoMedia(PictureShared.FolderNameConfig.VIDEO);
             selectVideoFragment = new ShootVideoFragment();
             FragmentManager manager = activity.getSupportFragmentManager();
             manager.beginTransaction()
                     .add(selectVideoFragment, FRAGMENT_TAG)
                     .commitAllowingStateLoss();
             manager.executePendingTransactions();
         }
      return   selectVideoFragment;
     }

    private ShootVideoFragment getShootVideoFragment(FragmentActivity activity, ShootVideoCallBack shootVideoCallBack) {
        ShootVideoFragment   shootVideoFragment=   initFragment(activity);
        shootVideoFragment.setShootVideoCallBack(shootVideoCallBack);
        return shootVideoFragment;
    }
    private ShootVideoFragment getShootVideoFragment(FragmentActivity activity, String eventBusFlag) {
        ShootVideoFragment   shootVideoFragment=   initFragment(activity);
        shootVideoFragment.setShootVideoEventBusFlag(eventBusFlag);
        return shootVideoFragment;
    }
    /**
     * 拍摄视频
     * @param  activity
     * @param  shootVideoCallBack 回调接口
     *
     * */
    public void startShootVideo(FragmentActivity activity, ShootVideoConfig shootVideoConfig,ShootVideoCallBack shootVideoCallBack){
        getShootVideoFragment(activity, shootVideoCallBack).startShootVideo(shootVideoConfig);
    }

    /**
     * 拍摄视频
     * @param  activity
     * @param  shootVideoCallBack 回调接口
     *
     * */
    public void startShootVideo(FragmentActivity activity,ShootVideoCallBack shootVideoCallBack){
        getShootVideoFragment(activity, shootVideoCallBack).startShootVideo(null);
    }
    /**
     * 拍摄视频
     * @param  activity
     * @param  eventBusFlg
     *
     * */
    public void startShootVideo(FragmentActivity activity, ShootVideoConfig shootVideoConfig,String eventBusFlg){
        getShootVideoFragment(activity, eventBusFlg).startShootVideo(shootVideoConfig);
    }

    /**
     * 拍摄视频
     * @param  activity
     * @param  eventBusFlg 回调接口
     *
     * */
    public void startShootVideo(FragmentActivity activity,String eventBusFlg){
        getShootVideoFragment(activity, eventBusFlg).startShootVideo(null);
    }
}
