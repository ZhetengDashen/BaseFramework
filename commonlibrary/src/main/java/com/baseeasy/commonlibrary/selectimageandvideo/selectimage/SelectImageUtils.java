package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;


import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public class SelectImageUtils {
    public static final String FRAGMENT_TAG = "SelectImageUtils";

    public volatile static SelectImageUtils selectImageUtils;

    public static SelectImageUtils getInstance() {
        if (selectImageUtils == null) {
            synchronized (SelectImageUtils.class) {
                if (selectImageUtils == null) {
                    selectImageUtils = new SelectImageUtils();

                }
            }
        }
        return selectImageUtils;
    }
     private  SelectImageFragment initFragment(FragmentActivity activity){
         SelectImageFragment selectImageFragment = (SelectImageFragment) activity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
         if (selectImageFragment == null) {
             FileUtils.createSDDir(PictureShared.FolderNameConfig.COMPRESSION);
             FileUtils.createNoMedia(PictureShared.FolderNameConfig.COMPRESSION);
             selectImageFragment = new SelectImageFragment();
             FragmentManager manager = activity.getSupportFragmentManager();
             manager.beginTransaction()
                     .add(selectImageFragment, FRAGMENT_TAG)
                     .commitAllowingStateLoss();
             manager.executePendingTransactions();
         }
      return   selectImageFragment;
     }

    private SelectImageFragment getSelectImageFragment(FragmentActivity activity, SelectImageCallBack selectLocationCallBack) {
        SelectImageFragment   selectImageFragment=   initFragment(activity);
        selectImageFragment.setSelectImageCallBack(selectLocationCallBack);
        return selectImageFragment;
    }
    private SelectImageFragment getTakingPhotoFragment(FragmentActivity activity, TakingPhotoCallBack takingPhotoCallBack) {
        SelectImageFragment   selectImageFragment=   initFragment(activity);
        selectImageFragment.setTakingPhotoCallBack(takingPhotoCallBack);
        return selectImageFragment;
    }
    private SelectImageFragment getTakingPhotoSeparateFragment(FragmentActivity activity, TakingPhotoSeparateCallBack takingPhotoSeparateCallBack) {
        SelectImageFragment   selectImageFragment=   initFragment(activity);
        selectImageFragment.setTakingPhotoSeparateCallBack(takingPhotoSeparateCallBack);
        return selectImageFragment;
    }
//    private SelectImageFragment getTakingPhotoSeparateFragment(FragmentActivity activity, String eventBusFlag) {
//        SelectImageFragment selectImageFragment=   initFragment(activity);
//        selectImageFragment.setTakingPhotoSeparateEventBusFlag(eventBusFlag);
//        return selectImageFragment;
//    }
    /**
     * 选择照片
     * @param  activity
     * @param  selectLocationCallBack 回调接口
     *
     * */
    public void startSelectImage(FragmentActivity activity, SelectImageCallBack selectLocationCallBack){
        startSelectImage(activity,selectLocationCallBack,PictureShared.MAX_PHOTO_NUM);
    }


    /**
     * 选择照片
     * @param  activity
     * @param  selectLocationCallBack 回调接口
     *
     * */
    public void startSelectImage(FragmentActivity activity, SelectImageCallBack selectLocationCallBack,int maxNum){
        getSelectImageFragment(activity, selectLocationCallBack).startSelectImage(maxNum);
    }

    /**
     * 选择照片
     *  @param  activity
     * @param  selectLocationCallBack 回调接口
     * @selectImageBeans 已选择照片
     *
     * */
    public void startSelectImage(FragmentActivity activity, SelectImageCallBack selectLocationCallBack, List<String> selectImageBeans){
          startSelectImage(activity,selectLocationCallBack,selectImageBeans,PictureShared.MAX_PHOTO_NUM);

    }


    /**
     * 选择照片
     *  @param  activity
     * @param  selectLocationCallBack 回调接口
     * @selectImageBeans 已选择照片
     *
     * */
    public void startSelectImage(FragmentActivity activity, SelectImageCallBack selectLocationCallBack, List<String> selectImageBeans,int  maxNum){
        getSelectImageFragment(activity, selectLocationCallBack).startSelectImage( JSONObject.toJSONString(selectImageBeans),maxNum);
    }

//    /**
//     * 选择照片
//     * @param  activity
//     * @param  eventBusFlag 回调接口
//     * @param  eventBusFlag eventBus 回调标记
//     * */
//    public void startSelectImage(FragmentActivity activity,String eventBusFlag){
//        startSelectImage(activity,eventBusFlag,PictureShared.MAX_PHOTO_NUM);
//
//    }


//    /**
//     * 选择照片
//     * @param  activity
//     * @param  eventBusFlag 回调接口
//     * @param  eventBusFlag eventBus 回调标记
//     * */
//    public void startSelectImage(FragmentActivity activity,String eventBusFlag,int maxNum){
//
//        Intent intent = new Intent(activity, SelectImageActivity.class);
//        intent.putExtra(PictureShared.IntentExtraName.EVENT_BUS_FLAG,eventBusFlag);
//        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
//        activity.startActivity(intent);
//    }

//    /**
//     * 选择照片
//     * @param  activity
//     * @param  eventBusFlag eventBus 回调标记
//     * @selectImageBeans 已选择照片
//     *
//     * */
//    public void startSelectImage(FragmentActivity activity,String eventBusFlag,List<SelectImageBean> selectImageBeans){
//        startSelectImage(activity,eventBusFlag,selectImageBeans,PictureShared.MAX_PHOTO_NUM);
//    }


//    /**
//     * 选择照片
//     * @param  activity
//     * @param  eventBusFlag eventBus 回调标记
//     * @selectImageBeans 已选择照片
//     *
//     * */
//    public void startSelectImage(FragmentActivity activity,String eventBusFlag,List<SelectImageBean> selectImageBeans,int maxNum){
//        Intent intent = new Intent(activity, SelectImageActivity.class);
//        intent.putExtra(PictureShared.IntentExtraName.EVENT_BUS_FLAG,eventBusFlag);
//        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, JSONObject.toJSONString(selectImageBeans));
//        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
//        activity.startActivity(intent);
//    }

   /**
    * 只有拍照
    * @param activity
    * @param takingPhotoCallBack 回调
    *
    **/
    public void startTakingPhoto(FragmentActivity activity, TakingPhotoCallBack takingPhotoCallBack){

        startTakingPhoto(activity,takingPhotoCallBack,PictureShared.MAX_PHOTO_NUM);
    }

    /**
     * 只有拍照
     * @param activity
     * @param takingPhotoCallBack 回调
     *
     **/
    public void startTakingPhoto(FragmentActivity activity, TakingPhotoCallBack takingPhotoCallBack,int maxNum){
        getTakingPhotoFragment(activity, takingPhotoCallBack).startTakingPhoto(maxNum);
    }


    /**
     * 只有拍照
     * @param activity
     * @param takingPhotoCallBack 回调
     * @param  takingPhoto 展示的照片
     **/
    public void startTakingPhoto(FragmentActivity activity, TakingPhotoCallBack takingPhotoCallBack, List<String> takingPhoto){

        startTakingPhoto(activity,takingPhotoCallBack,takingPhoto,PictureShared.MAX_PHOTO_NUM);
    }


    /**
     * 只有拍照
     * @param activity
     * @param takingPhotoCallBack 回调
     * @param  takingPhoto 展示的照片
     **/
    public void startTakingPhoto(FragmentActivity activity, TakingPhotoCallBack takingPhotoCallBack, List<String> takingPhoto,int maxNum){
        getTakingPhotoFragment(activity, takingPhotoCallBack).startTakingPhoto( JSONObject.toJSONString(takingPhoto),maxNum);
    }

//    /**
//     * 只有拍照
//     * @param activity
//     * @param eventBusFlag 回调
//     **/
//    public void startTakingPhoto(FragmentActivity activity,String eventBusFlag){
//
//        startTakingPhoto(activity,eventBusFlag,PictureShared.MAX_PHOTO_NUM);
//    }
//    /**
//     * 只有拍照
//     * @param activity
//     * @param eventBusFlag 回调
//     **/
//    public void startTakingPhoto(FragmentActivity activity,String eventBusFlag, int maxNum){
//        Intent intent = new Intent(activity, SelectImageActivity.class);
//        intent.putExtra(PictureShared.IntentExtraName.EVENT_BUS_FLAG,eventBusFlag);
//        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_TAKING_PHOTO);
//        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
//        activity.startActivity(intent);
//
//    }


//    /**
//     * 只有拍照
//     * @param activity
//     * @param eventBusFlag 回调
//     * @param  takingPhoto 展示的照片
//     **/
//    public void startTakingPhoto(FragmentActivity activity,String eventBusFlag,List<SelectImageBean> takingPhoto){
//        startTakingPhoto(activity,eventBusFlag,takingPhoto,PictureShared.MAX_PHOTO_NUM);
//    }


//    /**
//     * 只有拍照
//     * @param activity
//     * @param eventBusFlag 回调
//     * @param  takingPhoto 展示的照片
//     **/
//    public void startTakingPhoto(FragmentActivity activity,String eventBusFlag,List<SelectImageBean> takingPhoto,int maxNum){
//        Intent intent = new Intent(activity, SelectImageActivity.class);
//        intent.putExtra(PictureShared.IntentExtraName.EVENT_BUS_FLAG,eventBusFlag);
//        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, JSONObject.toJSONString(takingPhoto));
//        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE,PictureShared.ACTION_TYPE_TAKING_PHOTO);
//        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
//        activity.startActivity(intent);
//    }







    /**
     * 只有拍照 单张
     * @param activity
     * @param takingPhotoSeparateCallBack 回调
     *
     **/
    public void startTakingPhotoSeparate(FragmentActivity activity, TakingPhotoSeparateCallBack takingPhotoSeparateCallBack){

        getTakingPhotoSeparateFragment(activity, takingPhotoSeparateCallBack).startTakingPhotoSeparate();
    }
//    /**
//     * 只有拍照 单张
//     * @param activity
//     * @param eventBusFlag 回调
//     **/
//    public void startTakingPhotoSeparate(FragmentActivity activity,String eventBusFlag){
//
//        getTakingPhotoSeparateFragment(activity, eventBusFlag).startTakingPhotoSeparate();
//
//    }

    /**
     * 拍照或选择图片 单张
     * @param activity
     * @param takingPhotoSeparateCallBack 回调
     *
     **/
    public void startTakingPhotoAndImageSeparate(FragmentActivity activity, TakingPhotoSeparateCallBack takingPhotoSeparateCallBack){

        getTakingPhotoSeparateFragment(activity, takingPhotoSeparateCallBack).startTakingPhotoAndImageSeparate();
    }
//    /**
//     * 拍照或选择图片 单张
//     * @param activity
//     * @param eventBusFlag 回调
//     **/
//    public void startTakingPhotoAndImageSeparate(FragmentActivity activity,String eventBusFlag){
//
//        getTakingPhotoSeparateFragment(activity, eventBusFlag).startTakingPhotoAndImageSeparate();
//
//    }


}
