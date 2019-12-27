package com.baseeasy.commonlibrary.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;
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
 * 描述：
 */
public class BaseInterceptor implements Interceptor {
 @Override
public Response intercept(Chain chain) throws IOException {
     Request original = chain.request();
     if(original.body() instanceof FormBody){
         original=addPostBaseParams_Field(original);
     }else  if(original.body() instanceof MultipartBody){
         original=addPostBaseParams_Multipart(original);
     }
     return chain.proceed(original);
   }


    private Request addPostBaseParams_Query(Request original){

        JSONObject jsonObject=new JSONObject();
        JSONObject beanJsonObject=new JSONObject();
        beanJsonObject.toJSONString();
        List<String> parkeys=new ArrayList<>();
        for (int i = 0; i < original.url().querySize(); i++) {
            String name= original.url().queryParameterName(i);
            Object value=original.url().queryParameterValue(i);
            if(!name.equals("action")){//忽略掉后台指定字段
                if (!(value instanceof File)){
                    if(name.equals("jsonString")){
                        jsonObject.putAll(JSONObject.parseObject(name));
                    }else {
                        jsonObject.put(name,value);
                    }
                    parkeys.add(name);
                }
            }
        }

        //去除掉添加到json字段里面的字段避免重复
        HttpUrl.Builder newBuilder= original.url().newBuilder();
        for (int i = 0; i <parkeys.size() ; i++) {
            newBuilder.removeAllEncodedQueryParameters(parkeys.get(i));
        }

        jsonObject.putAll((JSONObject)JSONObject.toJSON(userParameter()));

        HttpUrl url= newBuilder
                .addQueryParameter("json", jsonObject.toJSONString())
                .build();
        Request request = original.newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .method(original.method(), original.body())
                .url(url)
                .build();
        return request;
    }
    private Request addPostBaseParams_Field(Request request){
        /**
         * request.body() instanceof FormBody 为true的条件为：
         * 在ApiService 中将POST请求中设置
         * 1，请求参数注解为@FieldMap
         * 2，方法注解为@FormUrlEncoded
         */
        if (request.body() instanceof FormBody|| request.body() instanceof MultipartBody) {
            FormBody formBody = (FormBody) request.body();
            FormBody.Builder builder = new FormBody.Builder();
            JSONObject jsonObject=new JSONObject();
            for (int i = 0; i < formBody.size(); i++) {

                //@FieldMap 注解 Map元素中 key 与 value 皆不能为null,否则会出现NullPointerException
                if (formBody.value(i) != null){
                    String name= formBody.name(i);
                    Object value=formBody.value(i);
                    if (!(value instanceof File)){
                        if(name.equals("jsonString")){
                            jsonObject.putAll(JSON.parseObject((String) value));
                        }else {
                            jsonObject.put(name,value);
                        }
                    }
                }
            }


            jsonObject.putAll((JSONObject)JSONObject.toJSON(userParameter()));
            Log.e("BaseInterceptor:",jsonObject.toJSONString());
            formBody = builder
                    .add("json", jsonObject.toJSONString())
                    .build();

            request = request.newBuilder().post(formBody).build();
        }else {
            Log.e("BaseInterceptor:","请求类型标记出错");
        }

        return request;
    }
    private Request addPostBaseParams_Multipart(Request request){
        /**
         * request.body() instanceof MultipartBody 为true的条件为：
         * 在ApiService 中将POST请求中设置
         * 1，请求参数注解为@Part
         * 2，方法注解为 @Multipart
         */
        if (request.body() instanceof MultipartBody) {
            MultipartBody formBody = (MultipartBody) request.body();
            MultipartBody.Builder builder = new MultipartBody.Builder();
            JSONObject jsonObject=new JSONObject();

            List<MultipartBody.Part> parts=formBody.parts();

            for (MultipartBody.Part part:parts) {

                if(part.body().contentType().type().equals("application")){
                    Headers headers=part.headers();
                    for (int i = 0; i <headers.names().size() ; i++) {

                        String value=headers.value(i);//valueform-data; name="article_type"
                        String replaceValue="form-data; name=";//这段在MultipartBody.Part源码中看到
                        if(value.contains(replaceValue)){
                            String key=value.replace(replaceValue,"").replaceAll("\"","");;
                            String content=bodyToString(part.body());
                            content=  trimFirstAndLastChar(content,"\"");
//                            content= content.replaceAll("\\\\","").trim();
                            content=   StringEscapeUtils.unescapeJson(content);
                            if("jsonString".equals(key)){
                                jsonObject.putAll(JSON.parseObject(content));
                            }else {
                                jsonObject.put(key,content);
                            }

                            break;
                        }
                    }


                }else if("multipart".equals(part.body().contentType().type())){

                    builder.addPart(part);

                }
            }
            jsonObject.putAll((JSONObject)JSONObject.toJSON(userParameter()));
            Log.e("BaseInterceptor:",jsonObject.toJSONString());
            formBody = builder
                    .addFormDataPart("json", jsonObject.toJSONString())
                    .build();

           request = request.newBuilder().post(formBody).build();
        }else {
            Log.e("BaseInterceptor:","请求类型标记出错");
        }

        return request;
    }
    /**
     * @return  公共参数
     * **/
    private static CommonParameter userParameter(){

        String userid= SharePreferenceUtils.getString(BaseApplication.getInstance().getApplicationContext(), SharePreferenceKeys.USER_ID);
        CommonParameter userParameter=new CommonParameter();
        String appVersion=SharePreferenceUtils.getString(BaseApplication.getInstance().getApplicationContext(), SharePreferenceKeys.APP_VERSION);
        if("".equals(appVersion)){
            appVersion=   AppUtils.getVersionCode(BaseApplication.getInstance().getApplicationContext())+"";
        }
        userParameter.setAppversion(appVersion);
        if(!userid.equals("")){
            userParameter.setUserid(userid);
        }
        return  userParameter;
    }

        private static String bodyToString(final RequestBody request) {
            try {
                final RequestBody copy = request;
                final Buffer buffer = new Buffer();
                if (copy != null) {
                    copy.writeTo(buffer);
                } else {
                    return "";
                }
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }

    public static String trimFirstAndLastChar(String str, String element){
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do{
              int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
            int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
            str = str.substring(beginIndex, endIndex);
            beginIndexFlag = (str.indexOf(element) == 0);
              endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
        } while (beginIndexFlag || endIndexFlag);
         return str;
 }
}

//
//    RequestBody requestBody=  original.body();
//       if(requestBody instanceof FormBody){//传统表单
//               LogUtils.e("--------------1","11111111");
//
//               }else  if(requestBody instanceof MultipartBody){
//               LogUtils.e("--------------2","2222222222");
//               }
//               LogUtils.e("--------------3",requestBody.contentType());