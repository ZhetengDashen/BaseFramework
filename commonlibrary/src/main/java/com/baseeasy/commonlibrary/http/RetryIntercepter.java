package com.baseeasy.commonlibrary.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * 作者：WangZhiQiang
 * 时间：2020/6/2
 * 邮箱：sos181@163.com
 * 描述：OKHttp请求失败拦截器
 */
public class RetryIntercepter implements Interceptor {

    public int maxRetry;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public RetryIntercepter(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        System.out.println("retryNum=" + retryNum);
        Response response = chain.proceed(request);
        System.out.println("response.isSuccessful()" +response.isSuccessful());
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            System.out.println("retryNum重试=" + retryNum);
            response = chain.proceed(request);
        }
        return response;
    }
}