package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public interface TakingPhotoCallBack {
 void onTakingPhoto(List<String> imageBeans);
 void onAddTakingPhoto(List<String> imageBeans);
 void onDeleteTakingPhoto(List<String> imageBeans);
}
