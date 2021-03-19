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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;

import com.apkfuns.logutils.LogUtils;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventConst;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.EventBusFlagImageOrVideo;
import com.baseeasy.commonlibrary.selectimageandvideo.GlideEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.ImageLocalMediaConversion;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

//    private String  takingPhotoSeparateEventBusFlag="";

    public SelectImageFragment() {
        // Required empty public constructor

    }



    public void startSelectImage(int maxNum) {

        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_SELECT_IMAGE);
        this.startActivityForResult(intent, PictureShared.SELECTIMAGE_REQUESTCODE);
    }
    public void startSelectImage(String select,int maxNum) {

        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_SELECT_IMAGE);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES,select);
        this.startActivityForResult(intent, PictureShared.SELECTIMAGE_REQUESTCODE);
    }

    public void startTakingPhoto(int maxNum) {

        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_TAKING_PHOTO);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
        this.startActivityForResult(intent, TAKINGPHOTO_REQUESTCODE);
    }
    public void startTakingPhoto(String select,int maxNum) {

        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_TAKING_PHOTO);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
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
                .imageEngine(GlideEngine.createGlideEngine())
                .isCompress(true)
                .imageFormat(PictureMimeType.PNG)
                .compressSavePath(FileUtils.SDPATH +PictureShared.FolderNameConfig.COMPRESSION)//压缩图片保存地址
                .setOutputCameraPath(FileUtils.SDPATH +PictureShared.FolderNameConfig.CAMERA)
                .forResult(TAKINGPHOTO_SEPARATE_REQUESTCODE);
    }

    public void startTakingPhotoAndImageSeparate() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isCompress(true)
                .compressSavePath(FileUtils.SDPATH +PictureShared.FolderNameConfig.COMPRESSION)//压缩图片保存地址
                .setOutputCameraPath(FileUtils.SDPATH +PictureShared.FolderNameConfig.CAMERA)
                .maxSelectNum(1)// 最大图片选择数量 int
                .forResult(TAKINGPHOTO_SEPARATE_REQUESTCODE);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == Activity.RESULT_OK && data != null) {
            if(requestCode == PictureShared.SELECTIMAGE_REQUESTCODE ||requestCode == TAKINGPHOTO_REQUESTCODE){


                List<String> selectImageBeans=new ArrayList<>();
                List<String> addData=new ArrayList<>();
                List<String> deleteData=new ArrayList<>();
                String  selectImageList_json= data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_DATA);
                String  addImageList_json= data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_ADD_DATA);
                String  deleteImageList_json= data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_DELETE_DATA);

                if(StringUtils.isNotBlank(selectImageList_json)){
                    selectImageBeans= JSONArray.parseArray(selectImageList_json, String.class);
                }
                if(StringUtils.isNotBlank(addImageList_json)){
                    addData= JSONArray.parseArray(addImageList_json, String.class);
                }
                if(StringUtils.isNotBlank(deleteImageList_json)){
                    deleteData= JSONArray.parseArray(deleteImageList_json, String.class);

                }

                if(requestCode == PictureShared.SELECTIMAGE_REQUESTCODE&&null!=selectImageCallBack ){
                    if(null!=selectImageBeans){
                       selectImageCallBack.onImageSelected(selectImageBeans);
                    }
                   if(null!=addData){
                       selectImageCallBack.onAddImage(addData);
                   }
                  if(null!=deleteData){
                      selectImageCallBack.onDeleteImage(deleteData);
                  }

                }else if(requestCode == TAKINGPHOTO_REQUESTCODE&&null!=takingPhotoCallBack){

                    if(null!=selectImageBeans){
                        takingPhotoCallBack.onTakingPhoto(selectImageBeans);
                    }
                    if(null!=addData){
                        takingPhotoCallBack.onAddTakingPhoto(addData);
                    }
                    if(null!=deleteData){
                        takingPhotoCallBack.onDeleteTakingPhoto(deleteData);
                    }
                }


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
