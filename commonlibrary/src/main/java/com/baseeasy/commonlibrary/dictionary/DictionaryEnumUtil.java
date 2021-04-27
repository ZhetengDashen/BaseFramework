package com.baseeasy.commonlibrary.dictionary;

/**
 * 作者：WangZhiQiang
 * 时间：2019/10/25
 * 邮箱：sos181@163.com
 * 描述：字典枚举工具类
 *
 *   DictionaryEnumUtil.getDictionaryName("1", MyDictionary.WHETHER.class)
 */
public class DictionaryEnumUtil {
    /**
     * 通过code编码获取枚举对象
     * @param code   枚举的编码
     * @param tClass  枚举类
     * @param <T>
     * @return
     */
    public static<T extends DictionaryEnum> String getDictionaryName(String code, Class<T> tClass){
        for(T each:tClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each.getName();
            }
        }
        return "";


    }
    /**
     * 通过code编码获取枚举对象
     * @param code   枚举的编码
     * @param tClass  枚举类
     * @param <T>
     * @return
     */
    public static<T extends DictionaryEnum> T getDictionaryFromCode(String code, Class<T> tClass){
        for(T each:tClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }

        }
        return null;


    }
    /**
     * 通过code编码获字典取枚举Name
     * @param name   名称
     * @param tClass  枚举类
     * @param <T>
     * @return
     */
    public static<T extends DictionaryEnum> T getDictionaryFromName(String name, Class<T> tClass){
        for(T each:tClass.getEnumConstants()){
            if(name.equals(each.getName())){
                return each;
            }
        }
        return null;
    }

    /**
     * 通过name编码获字典取枚举Code
     * @param name   名称
     * @param tClass  枚举类
     * @param <T>
     * @return
     */
    public static<T extends DictionaryEnum> String getDictionaryCode(String name, Class<T> tClass){
        for(T each:tClass.getEnumConstants()){
            if(name.equals(each.getName())){
                return each.getCode();
            }
        }
        return "";
    }


}