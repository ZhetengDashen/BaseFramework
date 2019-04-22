package com.baseeasy.commonlibrary.exception;

import com.baseeasy.commonlibrary.exception.base.BaseException;
import com.baseeasy.commonlibrary.http.HttpCode;




public class ConnectionException extends BaseException {

    public ConnectionException() {
        super(HttpCode.CODE_CONNECTION_FAILED, "网络请求失败");
    }

}
