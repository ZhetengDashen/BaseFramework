package com.baseeasy.commonlibrary.baseview.baseframework;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;

import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.loading.MyLoader;
import com.magiclon.individuationtoast.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者：WangZhiQiang
 * 时间：2019/4/24
 * 邮箱：sos181@163.com
 * 描述：
 */
public abstract class BaseFragment<V extends IBaseView,T extends BasePresenter<V>> extends RxFragment implements IBaseView {
    protected T mPresenter;
    private String TAG = "";

    public BaseFragment() {
        // Required empty public constructor
    }
    @Override
    public void showLoading() {
        MyLoader.showLoading(getContext());
    }
    @Override
    public void hideLoading() {
        MyLoader.stopLoading();
    }
    @Override
    public void showMessage(String message) {
        ToastUtil.showinfo(getContext(),message);
    }


    @Override
    public void showNetError() {
        ToastUtil.showerror(getContext(),"网络链接异常");
    }
    @Override
    public void showCodeError(String errorCode, String errorMsg) {
        ToastUtil.showerror(getContext(),errorMsg);
    }

    /**
     * 是否使用EventBus
     */
    public abstract boolean isOpenEventBus();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG="BaseLog:"+getActivity().getLocalClassName();
        if ((!EventBusUtils.isRegister(this)) && isOpenEventBus() == true) {
            EventBusUtils.register(this);
        }
        init_view();
        init_data();
        mPresenter = createPresenter();
        if(mPresenter!=null) {
            mPresenter.attachView((V) this);
        }
    }

    public void init_view(){};
    public void init_data(){};
    protected abstract T createPresenter();
    @Override
    public void onDestroy() {
        if (EventBusUtils.isRegister(this) && isOpenEventBus() == true) {
            EventBusUtils.unregister(this);
        }
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
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
