package com.baseeasy.commonlibrary.eventbus;

import org.greenrobot.eventbus.EventBus;
/**
 * 作者：WangZhiQiang
 * 时间：2019/4/22
 * 邮箱：sos181@163.com
 * 描述： EventBus工具类
 */
public class EventBusUtils {
    public EventBusUtils() {

    }


    /**
     * @param subscribe
     * 判断是否注册过
     */
    public static boolean isRegister(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    /**
     * @param subscribe
     * 注册
     */
    public static void register(Object subscribe) {
        EventBus.getDefault().register(subscribe);

    }

    /**
     * @param subscribe
     * 注销
     */
    public static void unregister(Object subscribe) {
        EventBus.getDefault().unregister(subscribe);
    }

    /***
     * 移除黏性事件
     */
    public static void removeSticky(Object  event) {
        EventBus.getDefault().removeStickyEvent(event);
    }


    /***
     * 移除所有黏性事件
     */
    public static void removeAllSticky() {
        EventBus.getDefault().removeAllStickyEvents();
    }



    /**
     * @param event
     * 立即发送
     */
    public static void post(EventMessage event) {
        EventBus.getDefault().post(event);
    }

    /**
     * @param stickyEvent
     * 滞留发布
     */
    public static void postSticky(EventMessage stickyEvent) {
        EventBus.getDefault().postSticky(stickyEvent);
    }
}