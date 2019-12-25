package com.baseeasy.commonlibrary.loading;

import android.content.Context;
import androidx.appcompat.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseApplication;
import com.baseeasy.commonlibrary.mytool.unit.ScreenUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/18
 * 邮箱：sos181@163.com
 * 描述：
 */
public class MyLoader{

    private static final ArrayList<LoadingDialog> LOADERS=new ArrayList<>();

    private static final String DEFAULT_LOADER=LoaderStyle.BallPulseSyncIndicator.name();

    public static void showLoading(Context context,Enum<LoaderStyle> styleEnum){
        showLoading(context,styleEnum.name());
    }

    public static void showLoading(Context context,String type){
        LoadingDialog dialog=new LoadingDialog(context,R.style.MyLoaderDialogStyle);
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (LoadingDialog dialog:LOADERS){
            if (null!=dialog){
              if(dialog.isShowing()) {
                  dialog.cancel();
              }
            }
        }
    }
}
