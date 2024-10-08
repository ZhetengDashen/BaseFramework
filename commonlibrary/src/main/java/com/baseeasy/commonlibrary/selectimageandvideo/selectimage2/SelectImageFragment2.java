package com.baseeasy.commonlibrary.selectimageandvideo.selectimage2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.mytool.time.TimeUtil;
import com.baseeasy.commonlibrary.selectimageandvideo.GlideEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.ImageLocalMediaConversion;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;

import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.ImageFileCompressEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.MeOnCameraInterceptListener;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoSeparateCallBack;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_BANK;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_IDCRD_EMBLEM;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_IDCRD_HEAD;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_REQUESTCODE;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKINGPHOTO_SEPARATE_REQUESTCODE;


/**
 * @author：Mr.Zan
 * @date： 2021/11/11 17:58
 * email：644962006@qq.com
 * detail：
 */
public class SelectImageFragment2 extends Fragment {

    private SelectImageCallBack selectImageCallBack;

    private TakingPhotoSeparateCallBack takingPhotoSeparateCallBack;
    public static SelectFileResultCallBack mSelectFileResultCallBack;
    private int minCompressSize = PictureShared.MINPHOTOCOMPRESSSIZE;

    public SelectImageFragment2() {
    }


    public void startSelectImage(int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TYPE_SELECT_IMAGE);
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, PictureShared.SELECTIMAGE_REQUESTCODE);
        this.startActivity(intent);
    }

    public void startSelectImage(int maxNum, int compressSize) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        intent.putExtra(PictureShared.IntentExtraName.MINCOMPRESSSIZE, compressSize);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TYPE_SELECT_IMAGE);
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, PictureShared.SELECTIMAGE_REQUESTCODE);
        this.startActivity(intent);
    }

    public void startSelectImage(String select, int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TYPE_SELECT_IMAGE);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, select);
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, PictureShared.SELECTIMAGE_REQUESTCODE);
        this.startActivity(intent);
    }

    public void startSelectImage(String select, int maxNum, int compressSize) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        intent.putExtra(PictureShared.IntentExtraName.MINCOMPRESSSIZE, compressSize);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TYPE_SELECT_IMAGE);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, select);
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, PictureShared.SELECTIMAGE_REQUESTCODE);
        this.startActivity(intent);
    }

    public void startTakingPhoto(int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TYPE_TAKING_PHOTO);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, PictureShared.TAKINGPHOTO_REQUESTCODE);
        this.startActivity(intent);
    }

    public void startTakingPhoto(int maxNum, int compressSize) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TYPE_TAKING_PHOTO);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        intent.putExtra(PictureShared.IntentExtraName.MINCOMPRESSSIZE, compressSize);
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, PictureShared.TAKINGPHOTO_REQUESTCODE);
        this.startActivity(intent);
    }

    public void startTakingPhoto(String select, int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TYPE_TAKING_PHOTO);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, select);
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, PictureShared.TAKINGPHOTO_REQUESTCODE);
        this.startActivity(intent);
    }

    public void startTakingPhoto(String select, int maxNum, int compressSize) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TYPE_TAKING_PHOTO);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        intent.putExtra(PictureShared.IntentExtraName.MINCOMPRESSSIZE, compressSize);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, select);
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, PictureShared.TAKINGPHOTO_REQUESTCODE);
        this.startActivity(intent);
    }

    public void startTakingPhotoIdcarHead(String select, int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TAKING_IDCARDHEAD_REQUEST);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        if (!TextUtils.isEmpty(select)) {
            intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, select);
        }
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, TAKINGPHOTO_IDCRD_HEAD);
        this.startActivity(intent);
    }

    public void startTakingPhotoIdcarEmblem(String select, int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TAKING_IDCARDEMBLEM_REQUEST);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        if (!TextUtils.isEmpty(select)) {
            intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, select);
        }
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, TAKINGPHOTO_IDCRD_EMBLEM);
        this.startActivity(intent);
    }

    public void startTakingPhotoBank(String select, int maxNum) {
        Intent intent = new Intent(getActivity(), SelectImageActivity2.class);
        intent.putExtra(PictureShared.IntentExtraName.ACTION_TYPE, PictureShared.ACTION_TAKING_BANK_REQUEST);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM, maxNum);
        if (!TextUtils.isEmpty(select)) {
            intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, select);
        }
        intent.putExtra(PictureShared.IntentExtraName.REQUSETCODE, TAKINGPHOTO_BANK);
        this.startActivity(intent);
    }


    public void setSelectImageCallBack(SelectImageCallBack selectImageCallBack) {
        this.selectImageCallBack = selectImageCallBack;
    }

    public void setMinCompressSize(int minCompressSize) {
        this.minCompressSize = minCompressSize;
    }

    public void setTakingPhotoSeparateCallBack(TakingPhotoSeparateCallBack takingPhotoSeparateCallBack) {
        this.takingPhotoSeparateCallBack = takingPhotoSeparateCallBack;
    }

    public void startTakingPhotoSeparate() {
        PictureSelector.create(this)
                .openCamera(SelectMimeType.ofImage())
//                .setCameraInterceptListener(new MeOnCameraInterceptListener())
                .setCompressEngine(new ImageFileCompressEngine())//压缩文件夹，压缩文件命名，压缩方式
                .setCameraImageFormat(PictureMimeType.PNG)
                .setOutputCameraImageFileName("camera" + System.currentTimeMillis() + ".jpg")
                .setOutputCameraDir(FileUtils.SDPATH + PictureShared.FolderNameConfig.CAMERA)
                .forResultActivity(TAKINGPHOTO_SEPARATE_REQUESTCODE);
    }


    /**
     * 作者：WangZhiQiang
     * 时间：2021/4/5
     * 邮箱：sos181@163.com
     * 描述：由于之前PictureSelector框架有问题 现在这种办法临时解决。
     */
    public void openCamera() {
        File file = null;
        try {
            file = File.createTempFile(TimeUtil.getnow_time_seconds(), ".jpg", new File(FileUtils.SDPATH + PictureShared.FolderNameConfig.COMPRESSION));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTakingPhotoAndImageSeparate() {
        startTakingPhotoAndImageSeparate(1);
    }

    public void startTakingPhotoAndImageSeparate(int maxNum) {
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
//                .setCameraInterceptListener(new MeOnCameraInterceptListener())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setCompressEngine(new ImageFileCompressEngine())//压缩文件夹，压缩文件命名，压缩方式
                .setOutputCameraDir(FileUtils.SDPATH + PictureShared.FolderNameConfig.CAMERA)
                .setMaxSelectNum(maxNum)// 最大图片选择数量 int
                .forResult(TAKINGPHOTO_SEPARATE_REQUESTCODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == PictureShared.SELECTIMAGE_REQUESTCODE || requestCode == TAKINGPHOTO_REQUESTCODE) {

                List<String> selectImageBeans = new ArrayList<>();
                List<String> addData = new ArrayList<>();
                List<String> deleteData = new ArrayList<>();
                String selectImageList_json = data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_DATA);
                String addImageList_json = data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_ADD_DATA);
                String deleteImageList_json = data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_DELETE_DATA);

                if (StringUtils.isNotBlank(selectImageList_json)) {
                    selectImageBeans = JSONArray.parseArray(selectImageList_json, String.class);
                }
                if (StringUtils.isNotBlank(addImageList_json)) {
                    addData = JSONArray.parseArray(addImageList_json, String.class);
                }
                if (StringUtils.isNotBlank(deleteImageList_json)) {
                    deleteData = JSONArray.parseArray(deleteImageList_json, String.class);
                }

                if (requestCode == PictureShared.SELECTIMAGE_REQUESTCODE && null != selectImageCallBack) {
                    if (null != selectImageBeans) {
                        selectImageCallBack.onImageSelected(selectImageBeans);
                    }
                    if (null != addData) {
                        selectImageCallBack.onAddImage(addData);
                    }
                    if (null != deleteData) {
                        selectImageCallBack.onDeleteImage(deleteData);
                    }
                } else if (requestCode == TAKINGPHOTO_REQUESTCODE && null != SelectImageUtils2.takingPhotoCallBack) {

                    if (null != selectImageBeans) {
                        SelectImageUtils2.takingPhotoCallBack.onTakingPhoto(selectImageBeans);
                    }
                    if (null != addData) {
                        SelectImageUtils2.takingPhotoCallBack.onAddTakingPhoto(addData);
                    }
                    if (null != deleteData) {
                        SelectImageUtils2.takingPhotoCallBack.onDeleteTakingPhoto(deleteData);
                    }
                }
            } else if (requestCode == TAKINGPHOTO_SEPARATE_REQUESTCODE && null != takingPhotoSeparateCallBack) {
                List<LocalMedia> localMediaList = PictureSelector.obtainSelectorList(data);
                takingPhotoSeparateCallBack.onTakingPhoto(ImageLocalMediaConversion.localMediaToSelectImage(localMediaList).get(0));
                takingPhotoSeparateCallBack.onTakingPhotoResult(ImageLocalMediaConversion.localMediaToSelectImage(localMediaList));
            }else if ((requestCode == TAKINGPHOTO_IDCRD_HEAD || requestCode == TAKINGPHOTO_IDCRD_EMBLEM || requestCode == TAKINGPHOTO_BANK) && null != takingPhotoSeparateCallBack) {

                List<String> selectImageBeans = new ArrayList<>();
                List<String> addData = new ArrayList<>();
                List<String> deleteData = new ArrayList<>();
                String selectImageList_json = data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_DATA);
                String addImageList_json = data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_ADD_DATA);
                String deleteImageList_json = data.getStringExtra(PictureShared.IntentExtraName.SELECTIMAGE_DELETE_DATA);

                if (StringUtils.isNotBlank(selectImageList_json)) {
                    selectImageBeans = JSONArray.parseArray(selectImageList_json, String.class);
                }
                if (StringUtils.isNotBlank(addImageList_json)) {
                    addData = JSONArray.parseArray(addImageList_json, String.class);
                }
                if (StringUtils.isNotBlank(deleteImageList_json)) {
                    deleteData = JSONArray.parseArray(deleteImageList_json, String.class);
                }

                if (null != selectImageBeans) {
                    selectImageCallBack.onImageSelected(selectImageBeans);
                }
                if (null != addData) {
                    selectImageCallBack.onAddImage(addData);
                }
                if (null != deleteData) {
                    selectImageCallBack.onDeleteImage(deleteData);
                }
            }
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mSelectFileResultCallBack = new SelectFileResultCallBack() {
            @Override
            public void onCurrentSelectResult(String path, int requestCode) {
                if (requestCode == PictureShared.SELECTIMAGE_REQUESTCODE || requestCode == TAKINGPHOTO_REQUESTCODE) {
                    List<String> selectImageBeans = new ArrayList<>();
                    if (StringUtils.isNotBlank(path)) {
                        selectImageBeans = JSONArray.parseArray(path, String.class);
                    }
                    if (requestCode == PictureShared.SELECTIMAGE_REQUESTCODE && null != selectImageCallBack) {
                        if (null != selectImageBeans) {
                            selectImageCallBack.onImageSelected(selectImageBeans);
                        }
                    } else if (requestCode == TAKINGPHOTO_REQUESTCODE && null != SelectImageUtils2.takingPhotoCallBack) {
                        if (null != selectImageBeans) {
                            SelectImageUtils2.takingPhotoCallBack.onTakingPhoto(selectImageBeans);
                        }
                    }
                }
            }

            @Override
            public void onDeleteResult(String path, int requestCode) {
                if (requestCode == PictureShared.SELECTIMAGE_REQUESTCODE || requestCode == TAKINGPHOTO_REQUESTCODE) {
                    List<String> deleteData = new ArrayList<>();
                    if (StringUtils.isNotBlank(path)) {
                        deleteData.add(path);
                    }
                    if (requestCode == PictureShared.SELECTIMAGE_REQUESTCODE && null != selectImageCallBack) {
                        if (null != deleteData) {
                            selectImageCallBack.onDeleteImage(deleteData);
                        }
                    } else if (requestCode == TAKINGPHOTO_REQUESTCODE && null != SelectImageUtils2.takingPhotoCallBack) {
                        if (null != deleteData) {
                            SelectImageUtils2.takingPhotoCallBack.onDeleteTakingPhoto(deleteData);
                        }
                    }
                }
            }

            @Override
            public void onAddResult(String path, int requestCode) {
                if (requestCode == PictureShared.SELECTIMAGE_REQUESTCODE || requestCode == TAKINGPHOTO_REQUESTCODE) {
                    List<String> addData = new ArrayList<>();
                    if (StringUtils.isNotBlank(path)) {
                        addData = JSONArray.parseArray(path, String.class);
                    }
                    if (requestCode == PictureShared.SELECTIMAGE_REQUESTCODE && null != selectImageCallBack) {
                        if (null != addData) {
                            selectImageCallBack.onAddImage(addData);
                        }
                    } else if (requestCode == TAKINGPHOTO_REQUESTCODE && null != SelectImageUtils2.takingPhotoCallBack) {
                        if (null != addData) {
                            SelectImageUtils2.takingPhotoCallBack.onAddTakingPhoto(addData);
                        }
                    }
                }
            }
        };
        return view;
    }

}
