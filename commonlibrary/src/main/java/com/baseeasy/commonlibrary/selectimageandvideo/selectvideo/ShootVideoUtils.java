package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseApplication;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageActivity;

import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageFragment;

import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoSeparateCallBack;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.BlockingBaseObserver;


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
         FileUtils.createSDDir(PictureShared.FolderNameConfig.VIDEO);
         FileUtils.createNoMedia(PictureShared.FolderNameConfig.VIDEO);
         if (selectVideoFragment == null) {
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



    /**
     * 拍摄视频
     * @param  activity
     * @param  shootVideoCallBack
     *
     * */
    public void startShootVideo(FragmentActivity activity,ShootVideoCallBack shootVideoCallBack){
        startShootVideo(activity,shootVideoCallBack,PictureShared.MAX_PHOTO_NUM);
    }
    /**
     * 拍摄视频
     * @param  activity
     * @param  shootVideoCallBack
     * @param maxNum
     *
     * */
    public void startShootVideo(FragmentActivity activity,ShootVideoCallBack shootVideoCallBack,int maxNum){
        startShootVideo(activity,shootVideoCallBack,new ArrayList<String>(),maxNum);
    }

    /**
     * 拍摄视频
     * @param  activity
     * @param  shootVideoCallBack
     * @param videoPaths
     * @param maxNum
     *
     * */
    public void startShootVideo(FragmentActivity activity,ShootVideoCallBack shootVideoCallBack,List<String> videoPaths,int maxNum){
        startShootVideo(activity,shootVideoCallBack,videoPaths,maxNum,new ShotVideoConfig(),false);
        }

    /**
     * 拍摄视频
     * @param  activity
     * @param  shootVideoCallBack
     * @param videoPaths
     * @param maxNum
     * @param shotVideoConfig 拍摄视频配置
     * @param isPreView 是否自动预览
     *
     * */
    public void startShootVideo(FragmentActivity activity,ShootVideoCallBack shootVideoCallBack,List<String> videoPaths,int maxNum,ShotVideoConfig shotVideoConfig,Boolean isPreView){
        ShootVideoFragment shootVideoFragment= getShootVideoFragment(activity, shootVideoCallBack);
        isChickPermission(shootVideoFragment.getActivity());
        shootVideoFragment.startShootVideo(videoPaths,maxNum,shotVideoConfig,isPreView);
    }

    @SuppressLint("CheckResult")
    public void isChickPermission(FragmentActivity activity){

        RxPermissions rxPermissions=new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    // 用户已经同意该权限

                } else if (permission.shouldShowRequestPermissionRationale) {
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                    activity.finish();
                } else {
                    // 用户拒绝了该权限，并且选中『不再询问』
                    activity.finish();
                }
            }
        });
    }
}
