package com.baseeasy.commonlibrary.baseview.baseframework;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者：WangZhiQiang
 * 时间：2019/4/24
 * 邮箱：sos181@163.com
 * 描述：
 */
public abstract class BaseFragment extends Fragment {

    private String TAG = "";

    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * 是否使用EventBus
     */
    public abstract boolean isOpenEventBus();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((!EventBusUtils.isRegister(this)) && isOpenEventBus() == true) {
            EventBusUtils.register(this);
        }
    }

    @Override
    public void onDestroy() {
        if (EventBusUtils.isRegister(this) && isOpenEventBus() == true) {
            EventBusUtils.unregister(this);
        }
        super.onDestroy();
    }


    // 在主线程处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMessageOnMainThread(EventMessage event) {
        Log.e(TAG, "MainThread: " + Thread.currentThread().getName());

    }

    // 在后台处理事件
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void eventBusMessageOnBackgroundThread(EventMessage event) {
        Log.e(TAG, "BackgroundThread: " + Thread.currentThread().getName());
    }

    // 在发送事件的同一线程中处理事件
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void eventBusMessageOnPostThread(EventMessage event) {
        Log.e(TAG, "PostThread: " + Thread.currentThread().getName());

    }

    // 在主线程处理粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMessageOnMainStickyThread(EventMessage event) {
        Log.e(TAG, "MainStickyThread: " + Thread.currentThread().getName());

    }

}
