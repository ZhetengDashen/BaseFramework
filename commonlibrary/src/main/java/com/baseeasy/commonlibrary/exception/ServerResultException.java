package com.baseeasy.commonlibrary.exception;

import com.baseeasy.commonlibrary.exception.base.BaseException;




public class ServerResultException extends BaseException {

    public ServerResultException(int code, String message) {
        super(code, message);
    }

}
