package com.baseeasy.baseframework.demoactivity.entity;

/**
 * 作者：WangZhiQiang
 * 时间：2019/5/5
 * 邮箱：sos181@163.com
 * 描述：
 */
public class RVDemoEntity {
    String  image;
    String  type;
    String  title;
    String  msg;
    int index;

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
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
