package com.baseeasy.mytest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;


@Route(path = ARouterPath.TestMode.TEST_ACTIVITY)
public class TestActivity extends BaseActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        initView();
        Toast.makeText(this, ""+ARouterPath.TestMode.TEST_ACTIVITY, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }

    private void initView() {


    }
}
