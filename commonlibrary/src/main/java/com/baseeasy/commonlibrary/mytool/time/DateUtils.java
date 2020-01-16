package com.baseeasy.commonlibrary.mytool.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Duo Nuo on 2017/9/7.
 */
public class DateUtils {
    // 字符串类型日期转化成date类型
    public static Date strToDate(String style, String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String dateToStr(String style, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(date);
    }
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String style,String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(style);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
     * 获取当前日期
     */
    public static String stampToDate(String style){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(style);
        long lt = new Long(System.currentTimeMillis());
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
