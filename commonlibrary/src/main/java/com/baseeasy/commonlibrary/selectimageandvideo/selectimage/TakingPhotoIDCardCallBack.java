package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public interface TakingPhotoIDCardCallBack {
 /***
  * 身份证正面（头像）
  * @param imagePaths
  */
 void onTakingPhotoHead(String imagePaths);
 /***
  * 身份证反面（国徽）
  * @param imagePaths
  */
 void onTakingPhotoEmblem(String imagePaths);
}
