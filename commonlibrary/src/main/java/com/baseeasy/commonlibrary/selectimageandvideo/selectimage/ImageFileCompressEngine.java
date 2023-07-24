package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;

import android.content.Context;
import android.net.Uri;

import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.utils.DateUtils;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;

public class ImageFileCompressEngine implements CompressFileEngine {
    @Override
    public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
        Luban.with(context).load(source).ignoreBy(200).setTargetDir(FileUtils.SDPATH+ PictureShared.FolderNameConfig.COMPRESSION).setRenameListener(new OnRenameListener() {
            @Override
            public String rename(String filePath) {
                int indexOf = filePath.lastIndexOf(".");
                String postfix = indexOf != -1 ? filePath.substring(indexOf) : ".jpg";
                return DateUtils.getCreateFileName("CMP_") + postfix;
            }
        }).setCompressListener(new OnNewCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String source, File compressFile) {
                if (call != null) {
                    call.onCallback(source, compressFile.getAbsolutePath());
                }
            }

            @Override
            public void onError(String source, Throwable e) {
                if (call != null) {
                    call.onCallback(source, null);
                }
            }
        }).launch();
    }
}
