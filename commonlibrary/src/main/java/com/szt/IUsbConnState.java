package com.szt;

/**
 * 作者：WangZhiQiang
 * 时间：2021/4/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public interface IUsbConnState {
    void onUsbConnected();

    void onUsbPermissionDenied();

    void onDeviceNotFound();
}
