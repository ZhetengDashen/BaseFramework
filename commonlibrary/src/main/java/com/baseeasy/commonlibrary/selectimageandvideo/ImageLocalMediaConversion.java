package com.baseeasy.commonlibrary.selectimageandvideo;


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
    public static List<String> localMediaToSelectImage(List<LocalMedia> localMediaList){
        List<String> selectImageBeans=new ArrayList<>();
        for (int i = 0; i <localMediaList.size() ; i++) {

            String path="";
            if(localMediaList.get(i).isCompressed()){
              path=  localMediaList.get(i).getCompressPath();
            }else {
             path=localMediaList.get(i).getPath();
            }
            selectImageBeans.add(path);
        }
        return selectImageBeans;
    }

    public static  ArrayList<LocalMedia> selectImageToLocalMedia(List<String> selectImageBeans){
        ArrayList<LocalMedia> localMediaList=new ArrayList<>();
        for (int i = 0; i <selectImageBeans.size() ; i++) {
            LocalMedia localMedia=new LocalMedia();
            localMedia.setPath(selectImageBeans.get(i));
            localMedia.setCompressPath(selectImageBeans.get(i));
            localMediaList.add(localMedia);
        }
        return localMediaList;

    }
}
