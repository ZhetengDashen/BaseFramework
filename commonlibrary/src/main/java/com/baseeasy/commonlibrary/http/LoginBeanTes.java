package com.baseeasy.commonlibrary.http;

/**
 * 作者：WangZhiQiang
 * 时间：2019/8/9
 * 邮箱：sos181@163.com
 * 描述：
 */
public class LoginBeanTes {


    /**
     * cityEn : 1
     * countryEn : 1
     * userid : 62009
     * unit_id : 152522
     */
//    unit_id string 区划id
//    userid string 用户的id
//    cityEn string 城市的低保权限
//    countryEn string 农村的低保权限


    private String cityEn="";
    private String countryEn="";
    private String userid="";
    private String unit_id="";

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }
}
