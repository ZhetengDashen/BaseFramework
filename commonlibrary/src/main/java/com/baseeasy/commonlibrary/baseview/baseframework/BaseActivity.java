package com.baseeasy.commonlibrary.baseview.baseframework;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.loading.MyLoader;
import com.magiclon.individuationtoast.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/14
 * 邮箱：sos181@163.com
 * 描述：
 */
public   abstract class BaseActivity<V extends IBaseView,T extends BasePresenter<V>> extends RxAppCompatActivity implements IBaseView {

    public String  TAG="";
    public T presenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        TAG="BaseLog:"+this.getLocalClassName();
        if ((!EventBusUtils.isRegister(this))&&isOpenEventBus()==true) {
            EventBusUtils.register(this);
        }
        setContentView(setContentViewId());
        init_view();
        init_data();
        presenter=createPresenter();
        if(presenter!=null){
            presenter.attachView((V) this);
        }
        createPresenterComplete();

    }
    protected  abstract int   setContentViewId();
    public void init_view(){};
    public void init_data(){};
    protected   abstract  T createPresenter();
    public void createPresenterComplete(){}


    @Override
    public void showCodeError(String errorCode, String errorMsg) {
        ToastUtil.showerror(this,errorMsg);
    }

    @Override
    public void showNetError() {
        ToastUtil.showerror(this,"网络链接异常");
    }


    @Override
    public void showLoading() {
        MyLoader.showLoading(this);
    }

    @Override
    public void hideLoading() {
        MyLoader.stopLoading();
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.showinfo(this,message);
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
        if(presenter!=null){
            presenter.detachView();
        }

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
