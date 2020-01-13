package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;



import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/12/27
 * 邮箱：sos181@163.com
 * 描述：拍摄视频回调
 */
public interface ShootVideoCallBack   {
    void onShootVideo(List<String> pathList);
    void onAddVideoList(List<String> pathList);
    void onDeleteVideoList(List<String> pathList);
}
