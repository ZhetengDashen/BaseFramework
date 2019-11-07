package com.baseeasy.baseframework.demoactivity;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.baseeasy.baseframework.databinding.TestDataBinding;
import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BaseDadaBinDingPresenter;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 作者：WangZhiQiang
 * 时间：2019/11/6
 * 邮箱：sos181@163.com
 * 描述：
 */
public class DataBinDingPresenter<T extends IDataBinDingView> extends BaseDadaBinDingPresenter<T, TestDataBinding> {

    public DataBinDingPresenter(LifecycleProvider provider) {
        super(provider);
    }

    public DataBinDingPresenter(LifecycleProvider provider, Context context) {
        super(provider, context);
       getDataBinDing().getRventity();

    }



    @Override
    public boolean isOpenEventBus() {
        return false;
    }
}
