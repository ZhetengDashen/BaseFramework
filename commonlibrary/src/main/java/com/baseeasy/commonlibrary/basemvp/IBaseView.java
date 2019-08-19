package com.baseeasy.commonlibrary.basemvp;
/**
 * Created by WangZhiQiang on 2017/12/5
 */
public interface IBaseView {
    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载动画
     */
    void hideLoading();

    void showMessage(String message);

    /**
     * 显示网络错误
     */
    void showNetError();

    void showCodeError(String errorCode, String errorMsg);

}