package com.baseeasy.commonlibrary.baseview.baseframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.luban.LuBanUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import top.zibin.luban.OnCompressListener;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/14
 * 邮箱：sos181@163.com
 * 描述：
 */
public abstract class BaseActivity extends AppCompatActivity {

     public   String  TAG="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        TAG="BaseLog:"+this.getLocalClassName();
        if ((!EventBusUtils.isRegister(this))&&isOpenEventBus()==true) {
            EventBusUtils.register(this);
        }


    }

    /**
     * 是否使用EventBus
     */
    public abstract  boolean isOpenEventBus();

    @Override
    protected void onDestroy() {
        if (EventBusUtils.isRegister(this)&&isOpenEventBus()==true) {
            EventBusUtils.unregister(this);
        }
        super.onDestroy();
    }

    // 在主线程处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMessageOnMainThread(EventMessage event) {
        Log.e(TAG,"MainThread: " + Thread.currentThread().getName());

    }

    // 在后台处理事件
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void eventBusMessageOnBackgroundThread(EventMessage event) {
        Log.e(TAG,"BackgroundThread: " + Thread.currentThread().getName());
    }

    // 在发送事件的同一线程中处理事件
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void eventBusMessageOnPostThread(EventMessage event) {
        Log.e(TAG,"PostThread: " + Thread.currentThread().getName());

    }

    // 在主线程处理粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMessageOnMainStickyThread(EventMessage event) {
        Log.e(TAG,"MainStickyThread: " + Thread.currentThread().getName());

    }


}
