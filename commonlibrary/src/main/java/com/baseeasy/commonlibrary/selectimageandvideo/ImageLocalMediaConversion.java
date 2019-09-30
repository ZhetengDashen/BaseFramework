package com.baseeasy.commonlibrary.selectimageandvideo;

import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/9/29
 * 邮箱：sos181@163.com
 * 描述： SelectImage  和 LocalMedia 转换类
 */
public class ImageLocalMediaConversion {
    public static List<SelectImageBean> localMediaToSelectImage(List<LocalMedia> localMediaList){
        List<SelectImageBean> selectImageBeans=new ArrayList<>();
        for (int i = 0; i <localMediaList.size() ; i++) {
            SelectImageBean selectImageBean=new SelectImageBean();
            selectImageBean.setPath(localMediaList.get(i).getPath());
            if(localMediaList.get(i).isCompressed()){
                selectImageBean.setCompressPath(localMediaList.get(i).getCompressPath());
            }
            selectImageBeans.add(selectImageBean);
        }
        return selectImageBeans;
    }

    public static  List<LocalMedia> selectImageToLocalMedia(List<SelectImageBean> selectImageBeans){
        List<LocalMedia> localMediaList=new ArrayList<>();
        for (int i = 0; i <selectImageBeans.size() ; i++) {
            LocalMedia localMedia=new LocalMedia();
            localMedia.setPath(selectImageBeans.get(i).getPath());
            localMedia.setCompressPath(selectImageBeans.get(i).getCompressPath());
            localMediaList.add(localMedia);
        }
        return localMediaList;

    }
}
