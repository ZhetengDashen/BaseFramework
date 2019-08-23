package com.baseeasy.commonlibrary.http;

import com.baseeasy.commonlibrary.mytool.TokenUtils;

/**
 * 作者：WangZhiQiang
 * 时间：2019/8/14
 * 邮箱：sos181@163.com
 * 描述：公共参数
 */
public class CommonParameter {
    String appversion="1";		//app版本号
    String timestamp="";		//时间戳
    String token="";	       //token
    String userid="";		//userid

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getTimestamp() {



        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
        this.timestamp= System.currentTimeMillis()+"";
        this.token= TokenUtils.getToken(userid,getTimestamp());
    }


    @Override
    public String toString() {
        return "CommonParameter{" +
                "appversion='" + appversion + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", token='" + token + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
