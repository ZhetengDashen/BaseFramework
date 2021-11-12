package com.baseeasy.commonlibrary.selectimageandvideo.selectimage2;

/**
 * @author：Mr.Zan
 * @date： 2021/11/11 17:57
 * email：644962006@qq.com
 * detail：照片选择回调接口
 */
public interface SelectFileResultCallBack {
 void onCurrentSelectResult(String path,int requestCode);
 void onDeleteResult(String path,int requestCode);
 void onAddResult(String path,int requestCode);
}
