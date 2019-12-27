package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SELECT_IMAGE_REQUEST;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_REQUESTCODE;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_SEPARATE_REQUESTCODE;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKING_PHOTO_REQUEST;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public class SelectImageFragment extends Fragment {

    private SelectImageCallBack selectImageCallBack;
    private TakingPhotoCallBack takingPhotoCallBack;
    private TakingPhotoSeparateCallBack takingPhotoSeparateCallBack;
    private String  takingPhotoSeparateEventBusFlag="";

    public SelectImageFragment() {
        // Required empty public constructor

    }



    public void startSelectImage() {
        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_SELECT_IMAGE);
        this.startActivityForResult(intent, PictureShared.SELECTIMAGE_REQUESTCODE);
    }
    public void startSelectImage(String select) {
        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_SELECT_IMAGE);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES,select);
        this.startActivityForResult(intent, PictureShared.SELECTIMAGE_REQUESTCODE);
    }

    public void startTakingPhoto() {
        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_TAKING_PHOTO);
        this.startActivityForResult(intent, TAKINGPHOTO_REQUESTCODE);
    }
    public void startTakingPhoto(String select) {
        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_TAKING_PHOTO);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES,select);
        this.startActivityForResult(intent, TAKINGPHOTO_REQUESTCODE);
    }





    public void setSelectImageCallBack(SelectImageCallBack selectImageCallBack) {
        this.selectImageCallBack = selectImageCallBack;
    }

    public void setTakingPhotoCallBack(TakingPhotoCallBack takingPhotoCallBack) {
        this.takingPhotoCallBack = takingPhotoCallBack;
    }
    public void setTakingPhotoSeparateCallBack(TakingPhotoSeparateCallBack takingPhotoSeparateCallBack) {
        this.takingPhotoSeparateCallBack = takingPhotoSeparateCallBack;
    }
    public void startTakingPhotoSeparate() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .compress(true)
                .compressSavePath(Environment.getExternalStorageDirectory()+"/"+ AppUtils.getAppName(getActivity())+"/"+PictureShared.FolderNameConfig.COMPRESSION)//压缩图片保存地址
                .setOutputCameraPath("/"+AppUtils.getAppName(getActivity())+"/"+PictureShared.FolderNameConfig.CAMERA)
                .forResult(TAKINGPHOTO_SEPARATE_REQUESTCODE);
    }

    public void startTakingPhotoAndImageSeparate() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .compress(true)
                .compressSavePath(Environment.getExternalStorageDirectory()+"/"+AppUtils.getAppName(getActivity())+"/"+PictureShared.FolderNameConfig.COMPRESSION)//压缩图片保存地址
                .setOutputCameraPath("/"+AppUtils.getAppName(getActivity())+"/"+PictureShared.FolderNameConfig.CAMERA)
                .maxSelectNum(1)// 最大图片选择数量 int
                .forResult(TAKINGPHOTO_SEPARATE_REQUESTCODE);
    }


    public  void setTakingPhotoSeparateEventBusFlag(String takingPhotoSeparateEventBusFlag){
        this.takingPhotoSeparateEventBusFlag=takingPhotoSeparateEventBusFlag;
   }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == Activity.RESULT_OK && data != null) {

            if(requestCode == PictureShared.SELECTIMAGE_REQUESTCODE ||requestCode == TAKINGPHOTO_REQUESTCODE){
                String selectImageList_json="";
                List<SelectImageBean> selectImageBeans=new ArrayList<>();
                selectImageList_json= data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_DATA);
                if(null!=selectImageList_json&&(!selectImageList_json.equals(""))&&!selectImageList_json.equals("null")){
                    selectImageBeans= JSONArray.parseArray(selectImageList_json, SelectImageBean.class);
                }else {
                    return;
                }
                if(requestCode == PictureShared.SELECTIMAGE_REQUESTCODE&&null!=selectImageCallBack ){
                    selectImageCallBack.onImageSelected(selectImageBeans);
                }else if(requestCode == TAKINGPHOTO_REQUESTCODE&&null!=takingPhotoCallBack){
                    takingPhotoCallBack.onTakingPhoto(selectImageBeans);
                }
            }else if(requestCode== TAKINGPHOTO_SEPARATE_REQUESTCODE&&!takingPhotoSeparateEventBusFlag.equals("")){
                 EventBusUtils.post(new EventMessage(EventConst.EVENT_CODE_OK,takingPhotoSeparateEventBusFlag,  ImageLocalMediaConversion.localMediaToSelectImage(PictureSelector.obtainMultipleResult(data))));
                   }else if(requestCode== TAKINGPHOTO_SEPARATE_REQUESTCODE&&null!=takingPhotoSeparateCallBack){
                    List<LocalMedia> localMediaList=   PictureSelector.obtainMultipleResult(data);
                    takingPhotoSeparateCallBack.onTakingPhoto(ImageLocalMediaConversion.localMediaToSelectImage(localMediaList).get(0));
                }


        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
