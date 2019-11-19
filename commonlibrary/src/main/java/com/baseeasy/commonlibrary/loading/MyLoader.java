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
    private static final int LOADER_SIZE_SCALE=8;

    private static final int LOADER_OFFSET_SCALE=10;

    private static final ArrayList<AppCompatDialog> LOADERS=new ArrayList<>();

    private static final String DEFAULT_LOADER=LoaderStyle.BallPulseSyncIndicator.name();

    public static void showLoading(Context context,Enum<LoaderStyle> styleEnum){
        showLoading(context,styleEnum.name());
    }

    public static void showLoading(Context context,String type){
         AppCompatDialog dialog=new AppCompatDialog(context, R.style.customdialog);

         AVLoadingIndicatorView avLoadingIndicatorView=LoaderCreator.create(type,context);
         dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth= ScreenUtils.getScreenWidth(BaseApplication.getInstance());
        int deviceHeight= ScreenUtils.getScreenHeight(BaseApplication.getInstance());

         Window dialogWindow=dialog.getWindow();

        if (dialogWindow!=null){
            WindowManager.LayoutParams lp=dialogWindow.getAttributes();
            lp.width=deviceWidth/LOADER_SIZE_SCALE;
            lp.height=deviceHeight/LOADER_SIZE_SCALE;
            lp.height=lp.height+deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity= Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog:LOADERS){
            if (null!=dialog){
              if(dialog.isShowing()) {
                  dialog.cancel();
              }
            }
        }
    }
}
