package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventConst;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.GlideEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.ImageLocalMediaConversion;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageActivity;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageBean;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoSeparateCallBack;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SHOOTVIDEO;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_REQUESTCODE;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_SEPARATE_REQUESTCODE;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public class ShootVideoFragment extends Fragment {

    private ShootVideoCallBack shootVideoCallBack;

    private String  shootVideoEventBusFlag="";

    public ShootVideoFragment() {
        // Required empty public constructor

    }



    public void startShootVideo(ShootVideoConfig shootVideoConfig) {
        if(null==shootVideoConfig){
            shootVideoConfig=new ShootVideoConfig();
        }
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofVideo())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .videoQuality(shootVideoConfig.getVideoQuality())
                .recordVideoSecond(shootVideoConfig.getVideoSecond())
                .setOutputCameraPath("/"+AppUtils.getAppName(getActivity())+"/"+PictureShared.FolderNameConfig.VIDEO)
                .forResult(SHOOTVIDEO);

    }







    public void setShootVideoCallBack(ShootVideoCallBack shootVideoCallBack) {
        this.shootVideoCallBack = shootVideoCallBack;
    }
    public  void setShootVideoEventBusFlag(String shootVideoEventBusFlag){
        this.shootVideoEventBusFlag=shootVideoEventBusFlag;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == Activity.RESULT_OK && data != null) {
            List<LocalMedia> localMediaList=   PictureSelector.obtainMultipleResult(data);
                if(requestCode == SHOOTVIDEO&&null!=shootVideoCallBack ){

                    shootVideoCallBack.onShootVideo(localMediaList.get(0).getPath());
                }else if(requestCode == SHOOTVIDEO&&!"".equals(shootVideoEventBusFlag)){
                   EventBusUtils.post(new EventMessage(EventConst.EVENT_CODE_OK,shootVideoEventBusFlag, localMediaList.get(0).getPath()));
//
                }


        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
