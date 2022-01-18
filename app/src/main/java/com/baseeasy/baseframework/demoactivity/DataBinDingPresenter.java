package com.baseeasy.baseframework.demoactivity;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.baseeasy.baseframework.databinding.TestDataBinding;
import com.baseeasy.baseframework.demoactivity.entity.RVDemoEntity;
import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BaseDadaBinDingPresenter;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
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


    }



    @Override
    public boolean isOpenEventBus() {
        return false;
    }

    @Override
    public void dataBinDing(TestDataBinding dataBinDing) {
        RVDemoEntity rvDemoEntity=new RVDemoEntity();
        rvDemoEntity.setImage("https://imgsa.baidu.com/forum/w%3D580/sign=c47e413076cb0a4685228b315b62f63e/d772036d55fbb2fb8b9bfbd8424a20a44423dce1.jpg");
        rvDemoEntity.setIndex(1);
        rvDemoEntity.setMsg("May the world turn into a sea, and may you and I return to the first sight");
        rvDemoEntity.setTitle("2Deliberately");
        rvDemoEntity.setType("1");
        rvDemoEntity.setContent("22");
        dataBinDing.setRventity(rvDemoEntity);
        rvDemoEntity.setMsg("MG___________________-");

    }

    @Override
    public void eventBusMessageOnMainStickyThread(EventMessage event) {
        super.eventBusMessageOnMainStickyThread(event);
    }

    @Override

    public void eventBusMessageOnMainStickyThread(Object event) {
        super.eventBusMessageOnMainStickyThread(event);
    }
}
