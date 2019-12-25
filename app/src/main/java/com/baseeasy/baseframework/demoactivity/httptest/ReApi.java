package com.baseeasy.baseframework.demoactivity.httptest;


import com.baseeasy.commonlibrary.http.BaseResult;


import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

;

/**
 * Created by Administrator on 2018/3/16/016.
 * 如果请求为post实现，那么最好传递参数时使用@Field、@FieldMap和@FormUrlEncoded。
 * 因为@Query和或QueryMap都是将参数拼接在url后面的，
 * 而@Field或@FieldMap传递的参数时放在请求体的。
 * https://www.jianshu.com/p/7c907686f6c5
 */

public interface ReApi {



    /*
     *上传测算表数据
     */
    @Multipart
    @POST()
    Observable<BaseResult> actionCs(@Url String url, @Part("jsonString") String bean, @Part MultipartBody.Part part);


}
