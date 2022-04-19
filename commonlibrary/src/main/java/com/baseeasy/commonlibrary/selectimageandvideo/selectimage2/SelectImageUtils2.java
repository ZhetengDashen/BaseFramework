package com.baseeasy.commonlibrary.selectimageandvideo.selectimage2;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoSeparateCallBack;

import java.util.List;


/**
 * @author：Mr.Zan
 * @date： 2021/11/11 15:29
 * email：644962006@qq.com
 * detail：实时回调保存删除图片
 */
public class SelectImageUtils2 {
    public static final String FRAGMENT_TAG = "SelectImageUtils2";

    public volatile static SelectImageUtils2 selectImageUtils;
    public static TakingPhotoCallBack takingPhotoCallBack;
    public static SelectImageUtils2 getInstance() {
        if (selectImageUtils == null) {
            synchronized (SelectImageUtils2.class) {
                if (selectImageUtils == null) {
                    selectImageUtils = new SelectImageUtils2();
                }
            }
        }
        return selectImageUtils;
    }
     private SelectImageFragment2 initFragment(FragmentActivity activity){
         SelectImageFragment2 selectImageFragment2 = (SelectImageFragment2) activity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
         if (selectImageFragment2 == null) {
             FileUtils.createSDDir(PictureShared.FolderNameConfig.COMPRESSION);
             FileUtils.createNoMedia(PictureShared.FolderNameConfig.COMPRESSION);
             selectImageFragment2 = new SelectImageFragment2();
             FragmentManager manager = activity.getSupportFragmentManager();
             manager.beginTransaction()
                     .add(selectImageFragment2, FRAGMENT_TAG)
                     .commitAllowingStateLoss();
             manager.executePendingTransactions();
         }
      return   selectImageFragment2;
     }

    private SelectImageFragment2 getSelectImageFragment(FragmentActivity activity, SelectImageCallBack selectLocationCallBack) {
        SelectImageFragment2   selectImageFragment=   initFragment(activity);
        selectImageFragment.setSelectImageCallBack(selectLocationCallBack);
        return selectImageFragment;
    }
    private SelectImageFragment2 getTakingPhotoFragment(FragmentActivity activity, TakingPhotoCallBack takingPhotoCallBack) {
        SelectImageFragment2   selectImageFragment=   initFragment(activity);
        SelectImageUtils2.takingPhotoCallBack =new TakingPhotoCallBack() {
            @Override
            public void onTakingPhoto(List<String> imagePaths) {
                takingPhotoCallBack.onTakingPhoto(imagePaths);
            }

            @Override
            public void onAddTakingPhoto(List<String> imagePaths) {
                takingPhotoCallBack.onAddTakingPhoto(imagePaths);
            }

            @Override
            public void onDeleteTakingPhoto(List<String> imagePaths) {
                takingPhotoCallBack.onDeleteTakingPhoto(imagePaths);
            }
        };
//        selectImageFragment.setTakingPhotoCallBack(takingPhotoCallBack);
        return selectImageFragment;
    }
    private SelectImageFragment2 getTakingPhotoSeparateFragment(FragmentActivity activity, TakingPhotoSeparateCallBack takingPhotoSeparateCallBack) {
        SelectImageFragment2   selectImageFragment=   initFragment(activity);
        selectImageFragment.setTakingPhotoSeparateCallBack(takingPhotoSeparateCallBack);
        return selectImageFragment;
    }
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

    /**
     * 只有拍照 单张
     * @param activity
     * @param takingPhotoSeparateCallBack 回调
     *
     **/
    public void startTakingPhotoSeparate(FragmentActivity activity, TakingPhotoSeparateCallBack takingPhotoSeparateCallBack){

        getTakingPhotoSeparateFragment(activity, takingPhotoSeparateCallBack).startTakingPhotoSeparate();
    }
    /**
     * 拍照或选择图片 单张
     * @param activity
     * @param takingPhotoSeparateCallBack 回调
     *
     **/
    public void startTakingPhotoAndImageSeparate(FragmentActivity activity, TakingPhotoSeparateCallBack takingPhotoSeparateCallBack){

        getTakingPhotoSeparateFragment(activity, takingPhotoSeparateCallBack).startTakingPhotoAndImageSeparate();
    }


}
