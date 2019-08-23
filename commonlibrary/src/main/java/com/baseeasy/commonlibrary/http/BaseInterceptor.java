package com.baseeasy.commonlibrary.http;

import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseApplication;
import com.baseeasy.commonlibrary.mytool.SharePreferenceKeys;
import com.baseeasy.commonlibrary.mytool.SharePreferenceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BaseInterceptor implements Interceptor {
 @Override
public Response intercept(Chain chain) throws IOException {
       Request original = chain.request();

       JSONObject jsonObject=new JSONObject();
       List<String> parkeys=new ArrayList<>();
       for (int i = 0; i < original.url().querySize(); i++) {
              String name= original.url().queryParameterName(i);
              Object value=original.url().queryParameterValue(i);
              if(!name.equals("action")){//忽略掉后台指定字段
                  if (!(value instanceof File)){
                      jsonObject.put(name,value);
                      parkeys.add(name);
                  }
              }
       }

        //去除掉添加到json字段里面的字段避免重复
         HttpUrl.Builder newBuilder= original.url().newBuilder();
         for (int i = 0; i <parkeys.size() ; i++) {
             newBuilder.removeAllEncodedQueryParameters(parkeys.get(i));
         }

     String userid= SharePreferenceUtils.getString(BaseApplication.getInstance().getApplicationContext(), SharePreferenceKeys.USER_ID);

     CommonParameter userParameter=new CommonParameter();
       if(!userid.equals("")){
           userParameter.setUserid(userid);
       }

       jsonObject.putAll((JSONObject)JSONObject.toJSON(userParameter));
       HttpUrl url= newBuilder
                .addQueryParameter("json", jsonObject.toJSONString())
                .build();


      Request request = original.newBuilder()     
               .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
               .method(original.method(), original.body())
               .url(url)
               .build();
      return chain.proceed(request); 
   }}