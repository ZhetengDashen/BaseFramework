package com.baseeasy.commonlibrary.http;

import android.util.DebugUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
/**
 * 作者：WangZhiQiang
 * 时间：2020/6/2
 * 邮箱：sos181@163.com
 * 描述：Rxjava 失敗自动重试
 */
public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {
    //最大出错重试次数
    private int maxRetries = 3;
    //重试间隔时间
    private int retryDelayMillis = 1000;
    //当前出错重试次数
    private int retryCount = 0;
 
    public RetryWithDelay() {
    }
 
    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }
 
    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        if (++retryCount <= maxRetries) {

                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            return Observable.timer(retryDelayMillis * retryCount,
                                    TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(throwable);
                    }
                });
    }
}
