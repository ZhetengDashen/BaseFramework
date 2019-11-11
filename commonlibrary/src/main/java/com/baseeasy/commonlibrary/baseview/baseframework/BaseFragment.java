package com.baseeasy.commonlibrary.baseview.baseframework;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseeasy.commonlibrary.basemvp.IBaseView;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.loading.MyLoader;
import com.magiclon.individuationtoast.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者：WangZhiQiang
 * 时间：2019/4/24
 * 邮箱：sos181@163.com
 * 描述：
 */
public abstract class BaseFragment<V extends IBaseView,T extends BasePresenter<V>> extends RxFragment implements IBaseView {
    protected T presenter;
    private String TAG = "";

    public BaseFragment() {
        // Required empty public constructor
    }
    @Override
    public void showLoading() {
        MyLoader.showLoading(getContext());
    }
    @Override
    public void hideLoading() {
        MyLoader.stopLoading();
    }
    @Override
    public void showMessage(String message) {
        ToastUtil.showinfo(getContext(),message);
    }


    @Override
    public void showNetError() {
        ToastUtil.showerror(getContext(),"网络链接异常");
    }
    @Override
    public void showCodeError(String errorCode, String errorMsg) {
        ToastUtil.showerror(getContext(),errorMsg);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG="BaseLog:"+getActivity().getLocalClassName();



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View   view = inflater.inflate(setContentViewID(), container, false);
        init_view(view);
        presenter = createPresenter();
        if(presenter!=null) {
            presenter.attachView((V) this);
            createPresenterComplete(true);
        }else {
            createPresenterComplete(false);
        }
        init_data();
        return view;
    }

    /*
    * 设置View
    * */
    protected  abstract int   setContentViewID();
    /*
    * 初始化View
    * */
    public void init_view(View view){};
    /*
    * 初始化Data
    * */
    public void init_data(){};

    /**
     * 创建Presenter
     * */
    protected abstract T createPresenter();

    /**
     * @param  isCreate 创建Presenter 是否成功
     * */
    public void createPresenterComplete(Boolean isCreate){}
    @Override
    public void onDestroy() {

        super.onDestroy();
        if(presenter!=null){
            presenter.detachView();

        }
    }





}
