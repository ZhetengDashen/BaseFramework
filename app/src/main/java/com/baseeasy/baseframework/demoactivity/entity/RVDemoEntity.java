package com.baseeasy.baseframework.demoactivity.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.baseeasy.commonlibrary.BR;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;

/**
 * 作者：WangZhiQiang
 * 时间：2019/5/5
 * 邮箱：sos181@163.com
 * 描述：
 */


public class RVDemoEntity extends BaseObservable implements MultiItemEntity {
    String  image;
    String  type;
    String  title;


   public  String  msg;
    int index;
    ObservableField<String> content = new ObservableField<>();

    public ObservableField<String> getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public RVDemoEntity() {
    }

    public RVDemoEntity(String image, String type, String title, String msg, int index) {
        this.image = image;
        this.type = type;
        this.title = title;
        this.msg = msg;
        this.index = index;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        notifyChange();

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int getItemType() {
        return Integer.parseInt(type);
    }


}
