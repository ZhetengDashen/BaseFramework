package com.baseeasy.commonlibrary.loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.baseeasy.commonlibrary.R;

import com.wang.avi.AVLoadingIndicatorView;

public class LoadingDialog extends Dialog {

   private AVLoadingIndicatorView avi;
   private TextView messagetv;
   private ConstraintLayout loadingbg;


   public LoadingDialog(Context context, int theme){
       super(context, theme);
       /**设置对话框背景透明*/
       getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       setContentView(R.layout.dialog_loading);
       loadingbg =  findViewById(R.id.loadingbg);
       avi = findViewById(R.id.avi);
       messagetv =findViewById(R.id.message);
   }

   /**
    * 为加载进度个对话框设置不同的提示消息
    *
    * @param message 给用户展示的提示信息
    * @return build模式设计，可以链式调用
    */
   public LoadingDialog setMessage(String message) {
       messagetv.setText(message);
       return this;
   }

   @Override
   public void show() {
       super.show();
       avi.smoothToShow();
   }

   /***
    * 设置loading背景色
    * @param Colorbg
    * @return
    */
   public LoadingDialog setLoadingBg(int Colorbg){
       loadingbg.setBackgroundColor(Colorbg);
       return this;
   }

   @Override
   public void dismiss() {
       super.dismiss();
       avi.smoothToHide();
   }

}