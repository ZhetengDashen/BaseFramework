package com.baseeasy.commonlibrary.baseview.baseframework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.loading.MyLoader;
import com.magiclon.individuationtoast.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/14
 * 邮箱：sos181@163.com
 * 描述：
 */
public   abstract class BaseActivity<V extends IBaseView,T extends BasePresenter<V>> extends RxAppCompatActivity implements IBaseView {

    public String  TAG="";
    public T presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG="BaseLog:"+this.getLocalClassName();
        if(this instanceof  BaseDataBinDingActivity){

        }else {
            ARouter.getInstance().inject(this);
            setContentView(setContentViewId());
            init_view();
            presenter=createPresenter();
            if(presenter!=null){
                presenter.attachView((V) this);
                createPresenterComplete(true);
            }else {
                createPresenterComplete(false);
            }
            init_data();
        }


    }




    protected  abstract int   setContentViewId();
    public void init_view(){};
    public void init_data(){};
    protected   abstract  T createPresenter();
    public void createPresenterComplete(Boolean isCreate){}


    @Override
    public void showCodeError(String errorCode, String errorMsg) {
        if(!errorMsg.isEmpty()) {
            String  newmessage=errorMsg.replace("<br>","\n").replace("<br/>","\n").replace("</br>","\n").replace("&nbsp","");

            ToastUtil.showerror(this, newmessage);
        }
    }

    @Override
    public void showNetError() {
        ToastUtil.showerror(this,"网络链接异常");
    }



    @Override
    public void showLoading() {

        if (this == null || this.isDestroyed() || this.isFinishing()) {

        }else {
            MyLoader.showLoading(this);
        }
    }

    @Override
    public void hideLoading() {
        if (this == null || this.isDestroyed() || this.isFinishing()) {

        }else {
            MyLoader.stopLoading();
        }

    }

    @Override
    public void showMessage(String message) {

        if(!message.isEmpty()){
            String  newmessage=message.replace("<br>","\n").replace("<br/>","\n").replace("</br>","\n").replace("&nbsp","");

            ToastUtil.showinfo(this,newmessage);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        if(null!=presenter){
            presenter.eventBusRegister();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(null!=presenter){
            presenter.eventBusUnregister();
        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if(presenter!=null){
            presenter.detachView();

        }

    }
    @SuppressLint( "RestrictedApi")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable @org.jetbrains.annotations.Nullable Bundle options) {
       if (checkDoubleClick(intent)) {
           super.startActivityForResult(intent, requestCode, options);
       }
    }

    private String mActivityJumpTag;        //activity跳转tag
    private long mClickTime;                //activity跳转时间

    /**
     * 检查是否重复跳转，不需要则重写方法并返回true
     */
    protected boolean checkDoubleClick(Intent intent) {

        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent().getClassName();
        }else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
        }else {
            return true;
        }

        if (tag.equals(mActivityJumpTag) && mClickTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false;
        }

        // 记录启动标记和时间
        mActivityJumpTag = tag;
        mClickTime = SystemClock.uptimeMillis();
        return result;
    }
}
