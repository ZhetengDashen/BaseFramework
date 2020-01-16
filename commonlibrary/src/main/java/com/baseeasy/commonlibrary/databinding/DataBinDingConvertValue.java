package com.baseeasy.commonlibrary.databinding;

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

}
