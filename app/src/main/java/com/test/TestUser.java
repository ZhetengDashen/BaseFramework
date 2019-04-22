package com.test;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/14
 * 邮箱：sos181@163.com
 * 描述：
 */
public class TestUser {
    String  name;
    String  id;
    String  gender;

    public TestUser() {
    }

    public TestUser(String name, String id, String gender) {
        this.name = name;
        this.id = id;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
