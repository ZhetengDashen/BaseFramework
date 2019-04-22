package com.baseeasy.commonlibrary.exception;

import com.baseeasy.commonlibrary.exception.base.BaseException;
import com.baseeasy.commonlibrary.http.HttpCode;




public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(HttpCode.CODE_PARAMETER_INVALID, "404错误");
    }

}