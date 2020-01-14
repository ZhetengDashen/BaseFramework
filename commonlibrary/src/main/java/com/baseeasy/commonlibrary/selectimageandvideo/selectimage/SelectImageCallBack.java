package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public interface SelectImageCallBack {
 void onImageSelected (List<String> imagePaths);
 void onAddImage(List<String> imagePaths);
 void onDeleteImage(List<String> imagePaths);
}
