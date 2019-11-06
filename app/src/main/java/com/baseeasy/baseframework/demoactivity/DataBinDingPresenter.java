package com.baseeasy.baseframework.demoactivity;

import android.content.Context;

import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 作者：WangZhiQiang
 * 时间：2019/11/6
 * 邮箱：sos181@163.com
 * 描述：
 */
public class DataBinDingPresenter<T extends IDataBinDingView> extends BasePresenter<T> {
    public DataBinDingPresenter(LifecycleProvider provider, Context context) {
        super(provider, context);
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }
}
