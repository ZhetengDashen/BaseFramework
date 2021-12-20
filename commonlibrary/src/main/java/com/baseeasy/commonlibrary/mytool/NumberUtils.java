package com.baseeasy.commonlibrary.mytool;

import android.text.TextUtils;

import com.aliyun.common.utils.StringUtils;

import java.math.BigDecimal;

/**
 * *********************************************
 * Created by Mr.Z on 2020/1/9
 * *********************************************
 */
public class NumberUtils {
    /**
     * 格式化为指定位小数的数字,返回未使用科学计数法表示的具有指定位数的字符串。
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。
     * <pre>
     * 	"3.1415926", 1			--> 3.1
     * 	"3.1415926", 3			--> 3.142
     * 	"3.1415926", 4			--> 3.1416
     * 	"3.1415926", 6			--> 3.141593
     * 	"1234567891234567.1415926", 3	--> 1234567891234567.142
     * </pre>
     *
     * @param number    String类型的数字对象
     * @param precision 小数精确度总位数,如2表示两位小数

     * @return 返回数字格式化后的字符串表示形式(注意返回的字符串未使用科学计数法)
     */
    public static String keepPrecision(String number, int precision) {
        return keepPrecision(number,precision,true);
    }

    /**
     * @param isNeedDealEndWithZero 是否需要处理小数点后的0
     * @return
     */
    public static String keepPrecision(String number, int precision,boolean isNeedDealEndWithZero) {
        if (TextUtils.isEmpty(number)) {
            number = "0";
        }
        BigDecimal bg = new BigDecimal(number);
        String s = bg.setScale(precision, BigDecimal.ROUND_HALF_UP).toPlainString();
        if (isNeedDealEndWithZero) {
            while (s.contains(".") && (s.endsWith("0") || s.endsWith("."))) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }

    /**
     * 格式化为指定位小数的数字,返回未使用科学计数法表示的具有指定位数的字符串。<br>
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
     * 如果给定的数字没有小数，则转换之后将以0填充；例如：int 123  1 --> 123.0<br>
     * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
     *
     * @param number    类型的数字对象
     * @param precision 小数精确度总位数,如2表示两位小数
     * @return 返回数字格式化后的字符串表示形式(注意返回的字符串未使用科学计数法)
     */
    public static String keepPrecision(Number number, int precision) {
        return keepPrecision(String.valueOf(number), precision);
    }

    /**
     * 对double类型的数值保留指定位数的小数。<br>
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
     * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
     *
     * @param number    要保留小数的数字
     * @param precision 小数位数
     * @return double 如果数值较大，则使用科学计数法表示
     */
    public static double keepPrecision(double number, int precision) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double keepPrecisionDown(double number, int precision) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(precision, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 对float类型的数值保留指定位数的小数。<br>
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。<br>
     * <b>注意：</b>如果精度要求比较精确请使用 keepPrecision(String number, int precision)方法
     *
     * @param number    要保留小数的数字
     * @param precision 小数位数
     * @return float 如果数值较大，则使用科学计数法表示
     */
    public static float keepPrecision(float number, int precision) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 不确定个数的数字加和
     *
     * @param precision 小数位数
     * @param num       String类型的数字对象
     * @return
     */
    public static String addDoubleNum(int precision, String... num) {
        double result = 0;
        for (String i : num) {
            try {
                if (!TextUtils.isEmpty(i)) {
                    double parseDouble = Double.parseDouble(i);
                    result += parseDouble;
                }
            } catch (Exception e) {
                e.printStackTrace();
//                return "请填写数字";
            }
        }
        String s = keepPrecision(result, precision) + "";
        if (precision > 0) {
            return s;
        } else {
            return s.substring(0, s.length() - 2);
        }
    }

    /**
     * 不确定个数的数字加和
     *
     * @param num String类型的数字对象
     * @return
     */
    public static String addIntNum(String... num) {
        double result = 0;
        for (String i : num) {
            try {
                if (!TextUtils.isEmpty(i)) {
                    double parseInt = Double.parseDouble(i);
                    result += parseInt;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "请填写数字";
            }
        }
        double v = keepPrecision(result, 0);

        return ((int) v) + "";
    }

    /**
     * 乘积
     *
     * @param precision 小数位数
     * @param num1      String类型的数字对象
     * @return
     */
    public static String productDoubleNum(int precision, String num1, String num2) {
        double result = 0;

        try {
            if (!TextUtils.isEmpty(num1) && !TextUtils.isEmpty(num2)) {
                double parseDouble1 = Double.parseDouble(num1);
                double parseDouble2 = Double.parseDouble(num2);
                result = parseDouble1 * parseDouble2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "请填写数字";
        }

        return keepPrecision(result, precision) + "";
    }

    /**
     * 判断大小
     *
     * @param num1 String类型的数字对象
     * @param num2 String类型的数字对象
     * @return num1小于等于 num2
     * isEqual 是否包含等于
     */
    public static boolean bigDoubleNum(String num1, String num2) {
        return bigDoubleNum(num1,num2,true);
    }
    public static boolean bigDoubleNum(String num1, String num2,boolean isEqual) {


        try {
            if (!TextUtils.isEmpty(num1) || !TextUtils.isEmpty(num2)) {
                if (TextUtils.isEmpty(num1)) {
                    num1 = "0";
                }
                if (TextUtils.isEmpty(num2)) {
                    num2 = "0";
                }
                double parseDouble1 = Double.parseDouble(num1);
                double parseDouble2 = Double.parseDouble(num2);
                if (isEqual) {
                    if (parseDouble1 <= parseDouble2) {
                        return true;
                    } else {
                        return false;
                    }
                }else {
                    if (parseDouble1 < parseDouble2) {
                        return true;
                    } else {
                        return false;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * 判断等于
     *
     * @param num1 String类型的数字对象
     * @return
     */
    public static boolean EqualDoubleNum(String num1, String num2) {


        try {
            if (!TextUtils.isEmpty(num1)||!TextUtils.isEmpty(num2)) {
                if (TextUtils.isEmpty(num1)){
                    num1="0";
                }
                if (TextUtils.isEmpty(num2)){
                    num2="0";
                }
                double parseDouble1 = Double.parseDouble(num1);
                double parseDouble2 = Double.parseDouble(num2);
                if (parseDouble1 == parseDouble2) {
                    return true;
                } else {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public static Integer strToInt(String str) {
        Integer integer = null;
        try {
            if (StringUtils.isEmpty(str)) {
                return integer;
            }
            double v = keepPrecision(Double.parseDouble(str), 0);
            integer = (int) v;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return integer;
    }

    /**
     * 除
     *
     * @param precision 小数位数
     * @param num1      String类型的数字对象
     * @return 默认四舍五入
     */
    public static String exceptDoubleNum(int precision, String num1, String num2) {
        return exceptDoubleNum(precision, num1, num2, true);
    }

    /**
     * 除
     *
     * @param precision 小数位数
     * @param num1      String类型的数字对象
     * @param isHalfUp  boolean ture 四舍五入 false 向下取整
     * @return 默认四舍五入
     */
    public static String exceptDoubleNum(int precision, String num1, String num2, boolean isHalfUp) {
        double doubleResult = 0;
        String result = "";
        try {
            if (!TextUtils.isEmpty(num1) && !TextUtils.isEmpty(num2)) {
                double parseDouble1 = Double.parseDouble(num1);
                double parseDouble2 = Double.parseDouble(num2);
                if (parseDouble2 != 0) {
                    doubleResult = parseDouble1 / parseDouble2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        double v = 0;
        if (isHalfUp) {
            v = keepPrecision(doubleResult, precision);
        } else {
            v = keepPrecisionDown(doubleResult, precision);
        }
        if (precision == 0) {
            result = ((int) v) + "";
        } else {
            result = v + "";
        }
        return result;
    }

    /**
     * 不确定个数的数字相减
     *
     * @param precision 小数位数
     * isLessThanZero 是否小于0按0计算 默认按0
     * @param father    String类型的数字对象 减数
     * @param num       String类型的数字对象 被减数
     * @return
     */
    public static String reduceDoubleNum(int precision, String father, String... num) {
        return reduceDoubleNum(precision,true,father,num);
    }
    public static String reduceDoubleNum(int precision,boolean isLessThanZero, String father, String... num) {
        double doubleResult = 0;
        double fatherNum = 0;
        String result = "0";
        try {
            fatherNum = Double.parseDouble(father);
            for (String i : num) {

                if (!TextUtils.isEmpty(i)) {
                    double parseDouble = Double.parseDouble(i);
                    doubleResult += parseDouble;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return keepPrecision(result, precision);
        }
        double v = keepPrecision((fatherNum - doubleResult), precision);

        if (isLessThanZero &&v < 0) {
            v = 0;
        }
        if (precision == 0) {
            result = ((int) v) + "";
        } else {
            result = v + "";
        }

        return result;
    }

}
