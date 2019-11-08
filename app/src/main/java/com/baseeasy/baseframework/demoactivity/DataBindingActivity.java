package com.baseeasy.baseframework.demoactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baseeasy.baseframework.R;


import com.baseeasy.baseframework.databinding.TestDataBinding;
import com.baseeasy.baseframework.demoactivity.entity.RVDemoEntity;

import com.baseeasy.commonlibrary.baseview.baseframework.BaseDataBinDingActivity;


public class DataBindingActivity extends BaseDataBinDingActivity<IDataBinDingView,DataBinDingPresenter<IDataBinDingView>,TestDataBinding> implements View.OnClickListener{

   Button button;
   EditText editText;
    @Override
    protected int setContentViewId() {
        return R.layout.activitydatabinding;
    }

    @Override
    public void init_view() {
        super.init_view();
        button=findViewById(R.id.rv_item_bt);
        button.setOnClickListener(this);
        editText=findViewById(R.id.textView2);

    }

    @Override
    public void init_data() {
        super.init_data();

    }

    @Override
    protected DataBinDingPresenter<IDataBinDingView> createPresenter() {
        return new DataBinDingPresenter<>(this,this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.rv_item_bt){
//            editText.setText("123");
//          dataBinding.getRventity().setMsg("456456");
            Toast.makeText(this, dataBinding.getRventity().getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

}
