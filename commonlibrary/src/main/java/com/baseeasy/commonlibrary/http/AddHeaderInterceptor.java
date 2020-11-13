package com.baseeasy.commonlibrary.http;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseApplication;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.SharePreferenceKeys;
import com.baseeasy.commonlibrary.mytool.SharePreferenceUtils;

import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 作者：WangZhiQiang
 * 时间：2018/11/20
 * 邮箱：sos181@163.com
 * 描述：添加请求头拦截器 添加token  添加 */
public class AddHeaderInterceptor implements Interceptor {
 @Override
public Response intercept(Chain chain) throws IOException {
     String token =SharePreferenceUtils.getString(BaseApplication.getInstance().getApplicationContext(), SharePreferenceKeys.TOKEN);
     Request original = chain.request();
     Request.Builder requestBuilder = original.newBuilder()
             .header("User-Agent", "app_enter_home")
             .header("appversion", SharePreferenceUtils.getString(BaseApplication.getInstance().getApplicationContext(), SharePreferenceKeys.APP_VERSION))
             .header("timestamp", System.currentTimeMillis()+"");
       if(!TextUtils.isEmpty(token)){
           requestBuilder.header("prefix_user_token_",token);
       }
     Request request = requestBuilder.build();
     return chain.proceed(request);
   }
}
