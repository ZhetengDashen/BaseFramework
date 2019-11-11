package com.baseeasy.commonlibrary.baseview.baseframework;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BaseDadaBinDingPresenter;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.loading.MyLoader;
import com.magiclon.individuationtoast.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * 作者：WangZhiQiang
 * 时间：2019/4/24
 * 邮箱：sos181@163.com
 * 描述：
 */
public abstract class BaseDataBinDingFragment<V extends IBaseView,T extends BaseDadaBinDingPresenter<V,K>,K extends ViewDataBinding> extends BaseFragment<V,T> implements IBaseView {

    public K dataBinding;
    public BaseDataBinDingFragment() {
        // Required empty public constructor
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding= DataBindingUtil.inflate(inflater,setContentViewID(), container, false);
        View  view=  dataBinding.getRoot();
        init_view(view);
        presenter = createPresenter();
        if(presenter!=null) {
            presenter.attachView((V) this);
            presenter.setDataBinDing(dataBinding);
            createPresenterComplete(true);
        }else {
            createPresenterComplete(false);
        }
        init_data();
        return view;
    }








}
