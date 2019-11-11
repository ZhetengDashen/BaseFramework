package com.baseeasy.commonlibrary.baseview.baseframework;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;

import com.apkfuns.logutils.LogUtils;
import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BaseDadaBinDingPresenter;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.loading.MyLoader;
import com.magiclon.individuationtoast.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 作者：WangZhiQiang
 * 时间：2019/11/6
 * 邮箱：sos181@163.com
 * 描述：<V,K>
 */
public abstract class BaseDataBinDingActivity<V extends IBaseView,T extends BaseDadaBinDingPresenter<V,K>, K extends ViewDataBinding> extends BaseActivity<V,T> implements IBaseView {


    public K dataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        dataBinding=  DataBindingUtil.setContentView(this,setContentViewId());
        super.onCreate(savedInstanceState);
        init_view();
        presenter=createPresenter();
        if(presenter!=null){
            presenter.attachView((V) this);
            presenter.setDataBinDing(dataBinding);
            createPresenterComplete(true);
        }else {
            createPresenterComplete(false);
        }
        init_data();
    }
    protected  abstract int   setContentViewId();













}

