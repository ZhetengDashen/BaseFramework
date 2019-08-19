package com.baseeasy.commonlibrary.basemvp.psenter;

/**
 * Created by WangZhiQiang on 2017/12/11
 */

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<T> {
    private LifecycleProvider<ActivityEvent> provider;
    private Context view_context;
    public BasePresenter(LifecycleProvider<ActivityEvent> provider) {
        this.provider = provider;
    }
    public BasePresenter(LifecycleProvider<ActivityEvent> provider, Context context) {
        this.provider = provider;
        this.view_context=context;
    }
    public LifecycleProvider<ActivityEvent> getProvider() {
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
    }
    //    解绑
    public void detachView(){
        if(this.mViewRef.isEnqueued()){
            this.mViewRef.clear();
        }
    }

    /**
     * 设置线程并且绑定Lifecycle生命周期
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T,T> setThreadAndLifecycle(){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(getProvider().<T>bindUntilEvent(ActivityEvent.DESTROY));
            }
        };
    }
}
