package com.baseeasy.commonlibrary.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：WangZhiQiang
 * 时间：2018/7/19
 * 邮箱：sos181@163.com
 * 描述：请求参数不添加到‘json’中,请求头中添加User-Agent、prefix_user_token_ 等
 */
public class RetrofitFactory2 {
    public static int HTTP_TIME = 180;
    public static <T> T create(Class<T> service) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIME, TimeUnit.SECONDS)
              .addInterceptor(new RetryIntercepter(3))
                .addInterceptor(new AddHeaderInterceptor())//添加请求头拦截器 添加token等
                .addInterceptor(LoggingInterceptor.LogInterceptor())//添加日志拦截器
                .retryOnConnectionFailure(true)
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.6.134/appdb_nm/")//这里必须设置一个baseurl 否则会抛出异常 所以先随便设置一个
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        return mRetrofit.create(service);
    }
}