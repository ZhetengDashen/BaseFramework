package com.baseeasy.commonlibrary.http;

import com.baseeasy.commonlibrary.mytool.TokenUtils;

/**
 * 作者：WangZhiQiang
 * 时间：2019/8/14
 * 邮箱：sos181@163.com
 * 描述：公共参数
 */
public class CommonParameter {
    String appVersion="1";		//app版本号
    String timestamp="";		//时间戳
    String token="";	       //token
    String userId="";		//userid

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getTimestamp() {
        timestamp= System.currentTimeMillis()+"";
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        token= TokenUtils.getToken(getUserId(),timestamp);
    }
}
