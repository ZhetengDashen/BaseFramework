package com.baseeasy.commonlibrary.http;




/**
 * Created by WangZhiQiang on 2017/12/16
 */

public class BaseResult<T> {

    /**
     * code :
     * msg :
     * result :
     */
    private String success="";
    private String message="";
    private String code="";


    private T data;

    public Boolean isSuccess(){
        Boolean iss=false;
        try {
          iss=   Boolean.parseBoolean(success);
        }catch (Exception e){
            e.printStackTrace();
        }

     return iss;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
