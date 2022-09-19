package com.baseeasy.commonlibrary.luban;

import android.content.Context;
import android.util.Log;



import java.io.File;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * 作者：WangZhiQiang.
 * 时间：2018/12/13
 * 邮箱：sos181@163.com
 * 描述：图片压缩
 */
public class LuBanUtils {
    private static int n=0;

    static{
      n=0;
    }


    public static  void compressionImage(String path, Context context, OnCompressListener onCompressListener) {
        String directory = path.substring(0, path.lastIndexOf('/'));
        final String fileName = path.substring(path.lastIndexOf('/') + 1, path.length());
        Luban.with(context)
                .load(path)                                   // 传人要压缩的图片
                .ignoreBy(300)                                // 忽略不压缩图片的大小
                .setTargetDir(directory)                      //目录
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {

                        return fileName;
                    }
                })

                .setCompressListener(onCompressListener).launch();


    }

    public   interface OnCompressionImageListListener{

        void onStart();

        void onSuccess(File file,int index);


        void onError(Throwable e);


    }

    public  static void compressionImage(final List<String> path, final Context context,OnCompressionImageListListener onCompressionImageListListener) {
        if (n == path.size()) {
            return;
        }

        String directory = path.get(n).substring(0, path.lastIndexOf('/'));
        final String fileName = path.get(n).substring(path.lastIndexOf('/') + 1, path.get(n).length());
        Luban.with(context)
                .load(path)                                   // 传人要压缩的图片
                .ignoreBy(300)                                // 忽略不压缩图片的大小
                .setTargetDir(directory)                      //目录
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {

                        return fileName;
                    }
                })

                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        onCompressionImageListListener.onStart();
                    }

                    @Override
                    public void onSuccess(int index,File file) {
                        n++;
                        compressionImage(path, context,onCompressionImageListListener);
                        onCompressionImageListListener.onSuccess(file,n);
                    }

                    @Override
                    public void onError(int index,Throwable e) {
                        Log.e("失败：", e.toString());
                        onCompressionImageListListener.onError(e);
                    }
                }).launch();
    }
}
