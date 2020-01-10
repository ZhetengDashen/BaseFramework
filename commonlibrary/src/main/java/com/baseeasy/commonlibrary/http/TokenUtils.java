package com.baseeasy.commonlibrary.http;

import android.util.Log;

import com.baseeasy.commonlibrary.http.CommonParameter;
import com.baseeasy.commonlibrary.mytool.MD5Utils;

/**
 * 作者：WangZhiQiang
 * 时间：2018/7/19
 * 邮箱：sos181@163.com
 * 描述：
 */
public class TokenUtils {
    public static String secret = "baseeasy";

    /**
     * @param userId
     * @param timestamp
     * @param token
     * @return 如果token相同返回true
     */
    public static boolean isSuccessToken(String userId, String timestamp, String token) {
        if (token.equals(getToken(userId, timestamp))) {
            return true;

        } else {

            return false;
        }

    }

    /**
     * @param userId
     * @param timestamp
     * @return token
     */
    public static String getToken(String userId, String timestamp) {
        String content = userId + timestamp;
        return MD5Utils.encodeMD5(MD5Utils.encodeMD5(content) + secret);
    }

//    public static void main(String[] args) {
////        1566553111067  10006017
//        CommonParameter commonParameter=new CommonParameter();
//        commonParameter.setUserid("10006017");
//
//        System.out.println(commonParameter);
//        System.out.println(getToken("10006017",commonParameter.getTimestamp()));
//
//    }

}
