package com.baseeasy.commonlibrary.mytool;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @Description：身份证号的util
 * @Author:
 * @Date: Created in 11:26 2019-03-27
 * @Modified By:
 */
public class IDCardUtil {
    /**
     * 15位身份证号
     */
    private static final Integer FIFTEEN_ID_CARD=15;
    /**
     * 18位身份证号
     */
    private static final Integer EIGHTEEN_ID_CARD=18;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 根据身份证号获取性别
     * @param IDCard
     * @return
     */
    public static String getSex(String IDCard){
        String sex ="";
        if (isNotBlank(IDCard)){
            //15位身份证号
            if (IDCard.length() == FIFTEEN_ID_CARD){
                if (Integer.parseInt(IDCard.substring(14, 15)) % 2 == 0) {
                    sex = "女";
                } else {
                    sex = "男";
                }
                //18位身份证号
            }else if(IDCard.length() == EIGHTEEN_ID_CARD){
                // 判断性别
                if (Integer.parseInt(IDCard.substring(16).substring(0, 1)) % 2 == 0) {
                    sex = "女";
                } else {
                    sex = "男";
                }
            }
        }
        return sex;
    }

    /**
     * 根据身份证号获取年龄
     * @param IDCard
     * @return
     */
    public static Integer getAge(String IDCard){
        Integer age = 0;
        Date date = new Date();
        if (isNotBlank(IDCard)&& isValid(IDCard)){
            //15位身份证号
            if (IDCard.length() == FIFTEEN_ID_CARD){
                // 身份证上的年份(15位身份证为1980年前的)
                String  year = "19" + IDCard.substring(6, 8);
                //身份证上的月份
                String  month = IDCard.substring(8, 10);
                //身份证上的日期
                String   day= IDCard.substring(10, 12);



                // 当前年份
                String fyear = format.format(date).substring(0, 4);
                // 当前月份
                String fyue = format.format(date).substring(5, 7);
                // 当前月份
                String ri = format.format(date).substring(8, 10);
                if (Integer.parseInt(month) <= Integer.parseInt(fyue)||(Integer.parseInt(month) == Integer.parseInt(fyue)&&Integer.parseInt(day)<=Integer.parseInt(ri))) {
                    age = Integer.parseInt(fyear) - Integer.parseInt(year);

                } else {
                    age = Integer.parseInt(fyear) - Integer.parseInt(year)-1;
                }
                //18位身份证号
            }else if(IDCard.length() == EIGHTEEN_ID_CARD){
                // 身份证上的年份
                String    year = IDCard.substring(6).substring(0, 4);
                // 身份证上的月份
                String   month = IDCard.substring(10).substring(0, 2);
                //身份证上的日期
                String    day=IDCard.substring(12).substring(0,2);


                // 当前年份
                String fyear = format.format(date).substring(0, 4);
                // 当前月份
                String fyue = format.format(date).substring(5, 7);
                // 当前月份
                String ri = format.format(date).substring(8, 10);

                if (Integer.parseInt(month) < Integer.parseInt(fyue)||(Integer.parseInt(month) == Integer.parseInt(fyue)&&Integer.parseInt(day)<=Integer.parseInt(ri))) {
                    age = Integer.parseInt(fyear) - Integer.parseInt(year);

                } else {
                    age = Integer.parseInt(fyear) - Integer.parseInt(year)-1;
                }
            }
        }
        return age;
    }


    /**
     * 获取出生日期  yyyy年MM月dd日
     * @param IDCard
     * @return
     */
    public static String getBirthday(String IDCard){
        String birthday="";
        String year="";
        String month="";
        String day="";
        if (isNotBlank(IDCard)){
            //15位身份证号
            if (IDCard.length() == FIFTEEN_ID_CARD){
                // 身份证上的年份(15位身份证为1980年前的)
                year = "19" + IDCard.substring(6, 8);
                //身份证上的月份
                month = IDCard.substring(8, 10);
                //身份证上的日期
                day= IDCard.substring(10, 12);
                //18位身份证号
            }else if(IDCard.length() == EIGHTEEN_ID_CARD){
                // 身份证上的年份
                year = IDCard.substring(6).substring(0, 4);
                // 身份证上的月份
                month = IDCard.substring(10).substring(0, 2);
                //身份证上的日期
                day=IDCard.substring(12).substring(0,2);
            }
            birthday=year+"-"+month+"-"+day;
        }
        return birthday;
    }
    /*********************************** 身份证验证开始 ****************************************/
    /**
     * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
     * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
     * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
     * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     * 身份证验证
     * @param id 号码内容
     * @return 是否有效
     */
    public static boolean isValid(String id){
        try{
        id=chageIdCardEndWithX(id);

        Boolean validResult = true;
//        //校验长度只能为15或18
//        int len = id.length();
//        if (len != FIFTEEN_ID_CARD && len != EIGHTEEN_ID_CARD){
//            validResult = false;
//        }
//        //校验生日
//        if (!validDate(id)){
//            validResult = false;
//        }
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (id.length() != 15 && id.length() != 18) {
            validResult = false;
            return validResult;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (id.length() == 18) {
            Ai = id.substring(0, 17);
        } else if (id.length() == 15) {
            Ai = id.substring(0, 6) + "19" + id.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            validResult=false;
              return validResult;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            Log.e("++ Mr.Z ++", "isValid: " +"身份证生日无效。");
            validResult=false;
              return validResult;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                Log.e("++ Mr.Z ++", "isValid: " +"身份证生日不在有效范围。");
                validResult=false;
                  return validResult;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            Log.e("++ Mr.Z ++", "isValid: " +"身份证月份无效");
            validResult=false;
              return validResult;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            Log.e("++ Mr.Z ++", "isValid: " +"身份证日期无效");
            validResult=false;
              return validResult;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            Log.e("++ Mr.Z ++", "isValid: " +"身份证地区编码错误。");
            validResult=false;
              return validResult;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (id.length() == 18) {
            if (Ai.equals(id) == false) {
                Log.e("++ Mr.Z ++", "isValid: " +"身份证无效，不是合法的身份证号码");
                validResult=false;
                  return validResult;
            }
        } else {
            return validResult;
        }
        // =====================(end)=====================
           return validResult;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 校验生日
     * @param id
     * @return
     */
    private static boolean validDate(String id)
    {
        try
        {
            String birth = id.length() == 15 ? "19" + id.substring(6, 12) : id.substring(6, 14);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date birthDate = sdf.parse(birth);
            if (!birth.equals(sdf.format(birthDate))){
                return false;
            }
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }
    //身份证号末尾为x时自动转大写
    public static String chageIdCardEndWithX(String idcard){
        if (idcard.length()>0){
            if (idcard.charAt(idcard.length()-1)=='x'&& idcard.length()== 18) {
                idcard=(new StringBuilder(idcard)).replace(idcard.length()-1,idcard.length(),"X").toString();
            }
        }
        return idcard;

    }
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    private static boolean isNotBlank(String idCard) {
        return  !isBlank(idCard);
    }

}
