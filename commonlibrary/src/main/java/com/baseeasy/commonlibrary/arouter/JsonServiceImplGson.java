package com.baseeasy.commonlibrary.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;


import java.lang.reflect.Type;

@Route(path = "/service/json")
public class JsonServiceImplGson implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        Log.w("TAG", "text---" + text);
        return new Gson().fromJson(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        Log.w("TAG", "Object---" + instance);
        return new Gson().toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {

        return new Gson().fromJson(input,clazz);
    }
}