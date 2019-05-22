package com.baseeasy.baseframework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.test.TestUser;

@Route(path = ARouterPath.AppMode.MAIN_ACTIVITY)
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Button bt_arouter;
    private Button bt_eventbus;
    private Button bt_imageloader;
    private Button bt_loadingview;
    private Button bt_recycler;
    private Button bt_log;
    private Button bt_rxpermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @Override
    public boolean isOpenEventBus() {
        return true;
    }


    @Override
    public void eventBusMessageOnMainThread(EventMessage event) {
        super.eventBusMessageOnMainThread(event);
        switch (event.getFlag()) {
            case ARouterPath.AppMode.DEMO_EVENTBUS_ACTIVITY:
                TestUser testUser = (TestUser) event.getEvent();
                Toast.makeText(this, testUser.getName(), Toast.LENGTH_SHORT).show();
                break;
        }


    }

    private void initView() {
//       ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"wo","卡机是");//
//     ARouterTools.startActivity(ARouterPath.TestMode.TEST_ACTIVITY,"user",new TestUser("王大锤" ,"0","女"));


        bt_arouter = (Button) findViewById(R.id.bt_arouter);
        bt_arouter.setOnClickListener(this);
        bt_eventbus = (Button) findViewById(R.id.bt_eventbus);
        bt_eventbus.setOnClickListener(this);
        bt_imageloader = (Button) findViewById(R.id.bt_imageloader);
        bt_imageloader.setOnClickListener(this);
        bt_loadingview = (Button) findViewById(R.id.bt_loadingview);
        bt_loadingview.setOnClickListener(this);
        bt_recycler = (Button) findViewById(R.id.bt_recycler);
        bt_recycler.setOnClickListener(this);
        bt_log = (Button) findViewById(R.id.bt_log);
        bt_log.setOnClickListener(this);
        bt_rxpermissions = (Button) findViewById(R.id.bt_rxpermissions);
        bt_rxpermissions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_arouter:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_AROUTER_ACTIVITY, "user", new TestUser("王大锤", "0", "女"));
                break;
            case R.id.bt_eventbus:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_EVENTBUS_ACTIVITY);
                break;
            case R.id.bt_imageloader:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_IMAGERLOADER_ACTIVITY);
                break;
            case R.id.bt_loadingview:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_LOADINGVIEW_ACTIVITY);
                break;
            case R.id.bt_recycler:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_RECYCLER_ACTIVITY);
                break;
            case R.id.bt_log:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_LOGUTILS_ACTIVITY);
                break;

            case R.id.bt_rxpermissions:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_RXPERMISSIONS_ACTIVITY);
                break;
        }
    }
}
