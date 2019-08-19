package com.baseeasy.commonlibrary.http;

import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class InterceptorUtil {
    public static String TAG = "----";
    public final static Charset UTF8 = Charset.forName("UTF-8");

    //日志拦截器
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
             Log.w(TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }
    /**
     * 设置公共参数
     */
    public static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                LogUtils.e(originalRequest.body());
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()

                      .addQueryParameter("json", "1111")
//                        .addQueryParameter("params2", "")
                        .build();

                Request request = originalRequest.newBuilder().url(modifiedUrl).build();

                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }



}