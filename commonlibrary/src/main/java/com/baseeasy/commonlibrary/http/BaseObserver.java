package com.baseeasy.commonlibrary.http;

import android.accounts.NetworkErrorException;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by WangZhiQiang on 2017/12/12
 */
public abstract class BaseObserver<T> implements Observer<BaseResult<T>> {

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }


    @Override
    public void onNext(BaseResult<T> tBaseEntity) {

        if (tBaseEntity.isSuccess()) {
            try {
                LogUtils.e(tBaseEntity);
                onSuccess(tBaseEntity);
            } catch (Exception e) {
              onError(e);
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(tBaseEntity.getCode(),tBaseEntity.getMessage());
            } catch (Exception e) {
                  onError(e);
                e.printStackTrace();
            }
        }
        onRequestEnd();
    }

    @Override
    public void onError(Throwable e) {
//
       Log.e("MyBaseObserver", "onError------------------------------------------: ");

        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
                if(e instanceof NetworkOnMainThreadException){
                    Log.e("Observer", "————————————线程错误--注意切换线程----------------------------------------: ");
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void onComplete() {

    }

    /**
     * 返回成功
     *
     * @param t
     * @throws
     */
    protected abstract void onSuccess(BaseResult<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param
     * @throws
     */
    protected   void onCodeError(String errorCode, String errorMsg) throws Exception {
        LogUtils.e(errorMsg);

    };

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws
     */
    protected  void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
        e.printStackTrace();

    };

    protected void onRequestStart() {

    }

    protected void onRequestEnd() {

    }



}