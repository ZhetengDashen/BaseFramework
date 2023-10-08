package com.baseeasy.baseframework.demoactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.baseeasy.baseframework.R;


import com.baseeasy.baseframework.databinding.TestDataBinding;
import com.baseeasy.baseframework.demoactivity.entity.RVDemoEntity;

import com.baseeasy.commonlibrary.baseview.baseframework.BaseDataBinDingActivity;
import com.baseeasy.commonlibrary.weight.ZTSwitchButton;


public class DataBindingActivity extends BaseDataBinDingActivity<IDataBinDingView,DataBinDingPresenter<IDataBinDingView>,TestDataBinding> implements View.OnClickListener{

   Button button;
   EditText editText;
   ZTSwitchButton swbt;
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
        swbt=findViewById(R.id.swbt);
//        swbt.getSwitchButton().setText("是","无");

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
    public void createPresenterComplete(Boolean isCreate) {
        super.createPresenterComplete(isCreate);
        dataBinding.setDatabindevent(new DetailsDataBinDingEvent());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.rv_item_bt){
//            editText.setText("123");
          dataBinding.getRventity().setTitle("456456");
//            dataBinding.getRventity().setIschick(!dataBinding.getRventity().getIschick());
            Toast.makeText(this, dataBinding.getRventity().getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
    public CompoundButton.OnCheckedChangeListener  isCH(View view){

        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("KK","22");
            }
        };
    }

    public class DetailsDataBinDingEvent{

       public void  isCH(View view){
       ZTSwitchButton ztSwitchButton= (ZTSwitchButton) view;
        Boolean aBoolean=   ztSwitchButton.getChecked();
        dataBinding.getRventity().setIschick(!aBoolean);

       }
       public void  select_sfdsrzbzc(View view){
       ZTSwitchButton ztSwitchButton= (ZTSwitchButton) view;
        Boolean aBoolean=   ztSwitchButton.getChecked();
        dataBinding.getRventity().setIschick(!aBoolean);

       }


   }
}
