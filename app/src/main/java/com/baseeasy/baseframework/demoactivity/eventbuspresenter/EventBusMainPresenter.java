package com.baseeasy.baseframework.demoactivity.eventbuspresenter;

import static com.baseeasy.commonlibrary.arouter.ARouterPath.AppMode.DEMO_EVENTBUS_ACTIVITY;

import android.content.Context;

import androidx.annotation.CallSuper;

import com.apkfuns.logutils.LogUtils;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventConst;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.test.TestUser;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;

/**
 * 作者：WangZhiQiang
 * 时间：2022/1/18
 * 邮箱：sos181@163.com
 * 描述：
 */
public class EventBusMainPresenter<T extends IEventBusView> extends BasePresenter<T> {


    public EventBusMainPresenter(LifecycleProvider provider) {
        super(provider);
    }

    public EventBusMainPresenter(LifecycleProvider provider, Context context) {
        super(provider, context);
    }

    @Override
    public boolean isOpenEventBus() {
        return true;
    }
    @Override
    public void eventBusMessageOnMainStickyThread(EventMessage event) {
        super.eventBusMessageOnMainStickyThread(event);
        if (event.getFlag().equals("123")) {
            LogUtils.e("-----------");
            EventMessage eventMessage = new EventMessage.Builder<TestUser>()
                    .setCode(EventConst.EVENT_CODE_OK)
                    .setFlag("456")
                    .setEvent(new TestUser("瓦不服", "123", "女")).create();
            EventBusUtils.post(eventMessage);

        }
//


    }
}
