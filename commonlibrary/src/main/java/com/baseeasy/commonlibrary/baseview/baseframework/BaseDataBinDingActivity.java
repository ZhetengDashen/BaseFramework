package com.baseeasy.commonlibrary.baseview.baseframework;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.loading.MyLoader;
import com.magiclon.individuationtoast.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 作者：WangZhiQiang
 * 时间：2019/11/6
 * 邮箱：sos181@163.com
 * 描述：
 */
public abstract class BaseDataBinDingActivity<V extends IBaseView,T extends BasePresenter<V>, K extends ViewDataBinding> extends RxAppCompatActivity implements IBaseView {

    public String  TAG="";
    public T presenter;
    public K dataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        TAG="BaseLog:"+this.getLocalClassName();
        dataBinding=  DataBindingUtil.setContentView(this,setContentViewId());

        init_view();
        presenter=createPresenter();
        if(presenter!=null){
            presenter.attachView((V) this);

            createPresenterComplete(true);
        }else {
            createPresenterComplete(false);
        }
        init_data();

    }




    protected  abstract int   setContentViewId();
    public void init_view(){};
    public void init_data(){};
    protected   abstract  T createPresenter();

    public void createPresenterComplete(Boolean isCreate){}


    @Override
    public void showCodeError(String errorCode, String errorMsg) {
        ToastUtil.showerror(this,errorMsg);
    }

    @Override
    public void showNetError() {
        ToastUtil.showerror(this,"网络链接异常");
    }


    @Override
    public void showLoading() {
        MyLoader.showLoading(this);
    }

    @Override
    public void hideLoading() {
        MyLoader.stopLoading();
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.showinfo(this,message);
    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
        if(presenter!=null){
            presenter.detachView();

        }

    }


}
