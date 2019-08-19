package com.baseeasy.commonlibrary.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static int HTTP_TIME = 3000;
    public static <T> T create(Class<T> service) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HTTP_TIME, TimeUnit.SECONDS)
//                .addInterceptor(InterceptorUtil.tokenInterceptor())
                .addInterceptor(new BaseInterceptor())//添加公共参数并且把数据统一添加到json字段中
//            .addInterceptor(InterceptorUtil.addQueryParameterInterceptor())//添加公共参数
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
//                .addNetworkInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return null;
//                    }
//                })
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