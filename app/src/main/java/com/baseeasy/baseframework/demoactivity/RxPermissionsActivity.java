package com.baseeasy.baseframework.demoactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;

import static com.baseeasy.commonlibrary.arouter.ARouterPath.AppMode.DEMO_RXPERMISSIONS_ACTIVITY;

@Route(path = DEMO_RXPERMISSIONS_ACTIVITY)
public class RxPermissionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_permissions);
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }
}
