package com.baseeasy.commonlibrary.databinding;

import android.text.TextUtils;

import androidx.databinding.InverseMethod;

/**
 * 作者：WangZhiQiang
 * 时间：2019/12/16
 * 邮箱：sos181@163.com
 * 描述：DataBinDing 数字与 字符串相互转换
 */
public class DataBinDingConvertValue {


    @InverseMethod("convertIntToString")
    public static int convertStringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public static String convertIntToString(int value) {
        return String.valueOf(value);
    }


    @InverseMethod("convertDoubleToString")
    public static double convertStringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public static String convertDoubleToString(double value) {
        return String.valueOf(value);
    }


    @InverseMethod("convertFloatToString")
    public static Float convertStringToFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return -1f;
        }
    }
    public static String convertFloatToString(float value) {
        return String.valueOf(value);
    }


    public  static Boolean convertStringToBoolean(String valeue){
        if (TextUtils.isEmpty(valeue)){
            return false;
        }else if(valeue.equals("是")){
            return  true;
        }
        return false;
    }
    @InverseMethod("convertStringToBoolean")
    public static String convertBooleanToString(Boolean aBoolean){
           if(aBoolean){
               return "是";
           }else {
               return "否";
           }
     }

    public  static Boolean convertIntToBoolean(int valeue){
        if(valeue==1){
            return true;
        }else if(valeue==2){
            return false ;
        }
        return false;
    }
    @InverseMethod("convertIntToBoolean")
    public static int convertBooleanToInt(Boolean aBoolean){
        if(aBoolean){
            return 1;
        }else {
            return 2;
        }
    }

}
