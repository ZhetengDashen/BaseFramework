package com.baseeasy.commonlibrary.basemvp.psenter;

/**
 * Created by WangZhiQiang on 2017/12/11
 */

import android.content.Context;

import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<T extends IBaseView> {
    private LifecycleProvider provider;
    private Context view_context;
    public BasePresenter(LifecycleProvider provider) {
        this.provider = provider;
    }
    public BasePresenter(LifecycleProvider provider, Context context) {
        this.provider = provider;
        this.view_context=context;
    }
    public LifecycleProvider getProvider() {
        return provider;
    }

    public Context getView_context(){
      return   view_context;
    }

    // 改成弱引用的方式
    protected WeakReference<T> mViewRef;
    //    绑定View
    public void attachView(T view){
        this.mViewRef=new WeakReference<T>(view);
        if ((!EventBusUtils.isRegister(this))&& isOpenEventBus()==true) {
            EventBusUtils.register(this);
        }
    }
    //    解绑
    public void detachView(){
        if(this.mViewRef.isEnqueued()){
            if (EventBusUtils.isRegister(this)&&isOpenEventBus()==true) {
                EventBusUtils.unregister(this);
            }
            this.mViewRef.clear();
        }
    }
    /**
     * 是否使用EventBus
     */
    public abstract  boolean isOpenEventBus();
    /**
     * 设置线程并且绑定Lifecycle生命周期
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T,T> setThreadAndLifecycle(){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY));
            }
        };
    }
    public <T> ObservableTransformer<T,T> setThreadAndLifecycleFragment(){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(getProvider().bindUntilEvent(FragmentEvent.DESTROY));
            }
        };
    }
    // 在主线程处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMessageOnMainThread(EventMessage event) {

    }

    // 在后台处理事件
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void eventBusMessageOnBackgroundThread(EventMessage event) {

    }

    // 在发送事件的同一线程中处理事件
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void eventBusMessageOnPostThread(EventMessage event) {


    }

    // 在主线程处理粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMessageOnMainStickyThread(EventMessage event) {


    }
}
