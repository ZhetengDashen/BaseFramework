package com.baseeasy.commonlibrary.exception.base;




public class BaseException extends RuntimeException {

    private int errorCode ;

    public BaseException() {
    }

    public BaseException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}