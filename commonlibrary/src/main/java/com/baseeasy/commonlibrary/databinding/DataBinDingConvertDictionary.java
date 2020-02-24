package com.baseeasy.commonlibrary.databinding;

import androidx.databinding.InverseMethod;

import com.baseeasy.commonlibrary.dictionary.DictionaryEnum;
import com.baseeasy.commonlibrary.dictionary.DictionaryEnumUtil;

import org.apache.commons.lang3.StringUtils;

/**
 * 作者：WangZhiQiang
 * 时间：2019/12/16
 * 邮箱：sos181@163.com
 * 描述：DataBinDing 字典Code与字典Name
 */
public class DataBinDingConvertDictionary {

    /**
     *
     * @param defaultDictionaryEnum 如果传入的Code无字典值 则返回传入默认的字典值Name
     * @param code
     * @return
     */
    @InverseMethod("convertNameToCode")
    public static String convertCodeToName(DictionaryEnum defaultDictionaryEnum, String code) {
        if(null==code||null==defaultDictionaryEnum){

            return "";
        }
        String name= DictionaryEnumUtil.getDictionaryName(code,defaultDictionaryEnum.getClass());
        if(StringUtils.isNotBlank(name)){
            return name;
        }else {
            return  defaultDictionaryEnum.getName();
        }
    }
    public static String convertNameToCode(DictionaryEnum defaultDictionaryEnum,String name) {
        if(null==name||null==defaultDictionaryEnum){

            return "";
        }
        String code=DictionaryEnumUtil.getDictionaryCode(name,defaultDictionaryEnum.getClass());
        if(StringUtils.isNotBlank(code)){
            return code;
        }
        return "-1";
    }



}
