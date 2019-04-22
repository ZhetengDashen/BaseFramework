package com.baseeasy.commonlibrary.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;
/**
 * 作者：WangZhiQiang
 * 时间：2019/3/14
 * 邮箱：sos181@163.com
 * 描述：
 */
//@Route(path = "/service/json")
public class JsonServiceImplFastJson implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return JSON.toJSONString(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return JSON.parseObject(input, clazz);
    }
}
