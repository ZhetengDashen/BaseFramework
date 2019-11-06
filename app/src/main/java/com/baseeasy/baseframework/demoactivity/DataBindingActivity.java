package com.baseeasy.baseframework.demoactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

import com.baseeasy.baseframework.R;


import com.baseeasy.baseframework.databinding.TestDataBinding;
import com.baseeasy.baseframework.demoactivity.entity.RVDemoEntity;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseDataBinDingActivity;


public class DataBindingActivity extends BaseDataBinDingActivity<IDataBinDingView,DataBinDingPresenter<IDataBinDingView>,TestDataBinding> {


    @Override
    protected int setContentViewId() {
        return R.layout.activitydatabinding;
    }

    @Override
    public void init_data() {
        super.init_data();

        RVDemoEntity rvDemoEntity=new RVDemoEntity();
        rvDemoEntity.setImage("https://imgsa.baidu.com/forum/w%3D580/sign=c47e413076cb0a4685228b315b62f63e/d772036d55fbb2fb8b9bfbd8424a20a44423dce1.jpg");
        rvDemoEntity.setIndex(1);
        rvDemoEntity.setMsg("May the world turn into a sea, and may you and I return to the first sight");
        rvDemoEntity.setTitle("Deliberately");
        rvDemoEntity.setType("1");
        rvDemoEntity.setContent("22");
        dataBinding.setRventity(rvDemoEntity);
    }

    @Override
    protected DataBinDingPresenter<IDataBinDingView> createPresenter() {
        return new DataBinDingPresenter<>(this,this);
    }

}
