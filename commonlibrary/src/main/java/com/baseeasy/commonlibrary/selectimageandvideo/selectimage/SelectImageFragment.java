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
import com.baseeasy.commonlibrary.mytool.time.TimeUtil;
import com.baseeasy.commonlibrary.selectimageandvideo.EventBusFlagImageOrVideo;
import com.baseeasy.commonlibrary.selectimageandvideo.GlideEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.ImageLocalMediaConversion;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;

import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.IDCardCameraActivity;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.BankCardParameter;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;



import org.apache.commons.lang3.StringUtils;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.IntentExtraName.IDCARD_RESULT_DATA;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_BANK;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_IDCRD_EMBLEM;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_IDCRD_HEAD;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_REQUESTCODE;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_SEPARATE_REQUESTCODE;
import static com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.parameter.IDCardBaseParameter.KEY_MAIN;


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
    private TakingPhotoIDCardCallBack takingPhotoIDCardCallBack;
    private TakingPhotoBankCallBack takingPhotoBankCallBack;
//    private String  takingPhotoSeparateEventBusFlag="";

    public SelectImageFragment() {
        // Required empty public constructor

    }
    public void setTakingPhotoIDCardCallBack(TakingPhotoIDCardCallBack takingPhotoIDCardCallBack) {
        this.takingPhotoIDCardCallBack = takingPhotoIDCardCallBack;
    }
    public void setTakingPhotoBankCallBack(TakingPhotoBankCallBack takingPhotoBankCallBack) {
        this.takingPhotoBankCallBack = takingPhotoBankCallBack;
    }

    /**
     * 作者：WangZhiQiang
     * 时间：2023/6/1
     * 邮箱：sos181@163.com
     * 描述：拍摄身份证国徽面
     */
    public  void  startTakingPhotoIdCardImageHeadEmblem(){
        Intent intent = new Intent(getActivity(), IDCardCameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(IDCardBaseParameter.KEY_TAKE_PICTURE_TYPE, IDCardBaseParameter.TYPE_MANUAL);
        bundle.putInt(IDCardBaseParameter.KEY_MASK_TYPE, IDCardBaseParameter.MASK_TYPE_IDCARD_BACK);
        intent.putExtra(KEY_MAIN, bundle);
        startActivityForResult(intent, TAKINGPHOTO_IDCRD_EMBLEM);
    }
    /**
     * 作者：WangZhiQiang
     * 时间：2023/6/1
     * 邮箱：sos181@163.com
     * 描述：拍摄身份证（头像页面）
     */
    public  void  startTakingPhotoIdCardImageHead(){
        Intent intent = new Intent(getActivity(), IDCardCameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(IDCardBaseParameter.KEY_TAKE_PICTURE_TYPE, IDCardBaseParameter.TYPE_MANUAL);
        bundle.putInt(IDCardBaseParameter.KEY_MASK_TYPE, IDCardBaseParameter.MASK_TYPE_IDCARD_FRONT);
        intent.putExtra(KEY_MAIN, bundle);
        startActivityForResult(intent, TAKINGPHOTO_IDCRD_HEAD);
    }
    /**
     * 作者：WangZhiQiang
     * 时间：2023/6/1
     * 邮箱：sos181@163.com
     * 描述：银行卡
     */
    public  void  startTakingPhotoBank(){
        Intent intent = new Intent(getActivity(), IDCardCameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(IDCardBaseParameter.KEY_TAKE_PICTURE_TYPE, IDCardBaseParameter.TYPE_MANUAL);
        bundle.putInt(IDCardBaseParameter.KEY_MASK_TYPE, BankCardParameter.MASK_TYPE_BANKCARD);
        intent.putExtra(KEY_MAIN, bundle);
        startActivityForResult(intent, TAKINGPHOTO_BANK);
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
//        openCamera();

        PictureSelector.create(this)
                .openCamera(SelectMimeType.ofImage())
//                .setCameraInterceptListener(new MeOnCameraInterceptListener())
                .setCompressEngine(new ImageFileCompressEngine())//压缩文件夹，压缩文件命名，压缩方式
                .setCameraImageFormat(PictureMimeType.PNG)
                .setOutputCameraImageFileName("camera"+System.currentTimeMillis() +".jpg")
                .setOutputCameraDir(FileUtils.SDPATH +PictureShared.FolderNameConfig.CAMERA)
                .forResultActivity(TAKINGPHOTO_SEPARATE_REQUESTCODE);
    }

    /**
     * 作者：WangZhiQiang
     * 时间：2021/4/5
     * 邮箱：sos181@163.com
     * 描述：由于之前PictureSelector框架有问题 现在这种办法临时解决。
     */
    public void openCamera(){
        File file = null;
        try {
            file=File.createTempFile(TimeUtil.getnow_time_seconds(),".jpg",new File(FileUtils.SDPATH+PictureShared.FolderNameConfig.COMPRESSION));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        CoCo.with(this).take(file).then() //处理图片
//                .dispose().start(new CoCoAdapter<DisposeResult>() {
//            @Override
//            public void onFailed(@NotNull Exception exception) {
//                super.onFailed(exception);
//                exception.printStackTrace();
//
//            }
//
//            @Override
//            public void onSuccess(DisposeResult disposeResult) {
//                super.onSuccess(disposeResult);
//               if(!disposeResult.getSavedFile().getAbsolutePath().isEmpty()){
//                   takingPhotoSeparateCallBack.onTakingPhoto(disposeResult.getSavedFile().getAbsolutePath());
//
//               }
//
//
//
//            }
//        });

    }

    public void startTakingPhotoAndImageSeparate() {
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
//                .setCameraInterceptListener(new MeOnCameraInterceptListener())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setCompressEngine(new ImageFileCompressEngine())//压缩文件夹，压缩文件命名，压缩方式
                .setOutputCameraDir(FileUtils.SDPATH +PictureShared.FolderNameConfig.CAMERA)
                .setMaxSelectNum(1)// 最大图片选择数量 int
                .forResult(TAKINGPHOTO_SEPARATE_REQUESTCODE);
    }

    public void startSelectVideo(int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_SELECT_VIDEO);
        this.startActivityForResult(intent, PictureShared.SELECTIMAGE_REQUESTCODE);
    }
    public void startSelectVideo(String select,int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_SELECT_VIDEO);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES,select);
        this.startActivityForResult(intent, PictureShared.SELECTIMAGE_REQUESTCODE);
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
                    List<LocalMedia> localMediaList=   PictureSelector.obtainSelectorList(data);
                    takingPhotoSeparateCallBack.onTakingPhoto(ImageLocalMediaConversion.localMediaToSelectImage(localMediaList).get(0));
                }else  if(requestCode== TAKINGPHOTO_IDCRD_HEAD&&null!=takingPhotoIDCardCallBack){

                try {
                    String path= data.getExtras().getString(IDCARD_RESULT_DATA);
                    takingPhotoIDCardCallBack.onTakingPhotoHead(path);
                }catch (Exception e){
                    takingPhotoIDCardCallBack.onTakingPhotoHead("");
                } }else  if(requestCode== TAKINGPHOTO_IDCRD_EMBLEM&&null!=takingPhotoIDCardCallBack){

                try {
                    String path= data.getExtras().getString(IDCARD_RESULT_DATA);
                    takingPhotoIDCardCallBack.onTakingPhotoEmblem(path);
                }catch (Exception e){
                    takingPhotoIDCardCallBack.onTakingPhotoEmblem("");
                }
            }if(requestCode== TAKINGPHOTO_BANK&&null!=takingPhotoBankCallBack){

                try {
                    String path= data.getExtras().getString(IDCARD_RESULT_DATA);
                    takingPhotoBankCallBack.onTakingPhoto(path);
                }catch (Exception e){
                    takingPhotoBankCallBack.onTakingPhoto("");
                }
            }


        }
    }






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
