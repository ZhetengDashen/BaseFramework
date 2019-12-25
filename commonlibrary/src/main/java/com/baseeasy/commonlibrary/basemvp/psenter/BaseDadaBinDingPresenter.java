package com.baseeasy.commonlibrary.basemvp.psenter;

import android.content.Context;

import androidx.databinding.ViewDataBinding;


import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * 作者：WangZhiQiang
 * 时间：2019/11/6
 * 邮箱：sos181@163.com
 * 描述：
 */
public abstract class BaseDadaBinDingPresenter<T extends IBaseView,K extends ViewDataBinding> extends BasePresenter<T>  {

    public BaseDadaBinDingPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);
    }

    public BaseDadaBinDingPresenter(LifecycleProvider<ActivityEvent> provider, Context context) {
        super(provider, context);

    }


    public void setDataBinDing(K dataBinDing){
        dataBinDing(dataBinDing);
    }
    /**
     * 是否使用EventBus
     */
    @Override
    public abstract  boolean isOpenEventBus();

    public abstract void dataBinDing(K dataBinDing);
}
