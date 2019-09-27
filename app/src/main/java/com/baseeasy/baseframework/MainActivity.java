package com.baseeasy.baseframework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageBean;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageUtils;
import com.test.TestUser;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.AppMode.MAIN_ACTIVITY)
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Button bt_arouter;
    private Button bt_eventbus;
    private Button bt_imageloader;
    private Button bt_loadingview;
    private Button bt_recycler;
    private Button bt_log;
    private Button bt_rxpermissions;
    private Button select_image;
    private  List<SelectImageBean> selectImageBeans;
    private TextView textView;
    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void init_view() {
        super.init_view();
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
        select_image=findViewById(R.id.bt_select_image);
        select_image.setOnClickListener(this);
        textView=findViewById(R.id.textView);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
            case "imageCallback":

                selectImageBeans= (List<SelectImageBean>) event.getEvent();
                for (int i = 0; i <selectImageBeans.size() ; i++) {
                    textView.setText(textView.getText().toString()+selectImageBeans.get(i).getPath());
                }
                break;
        }


    }

    private void initView() {
//       ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"wo","卡机是");//
//     ARouterTools.startActivity(ARouterPath.TestMode.TEST_ACTIVITY,"user",new TestUser("王大锤" ,"0","女"));

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
            case  R.id.bt_select_image:
//                ARouterTools.startActivity(ARouterPath.CommonLibrary.COMMON_SELECTIMAGE_ACTIVITY);
//                SelectImageUtils.getInstance().startSelectImage(this, new SelectImageCallBack() {
//                    @Override
//                    public void onImageSelected(List<SelectImageBean> localMediaList) {
//                        selectImageBeans=localMediaList;
//                        for (int i = 0; i <selectImageBeans.size() ; i++) {
//                            textView.setText(textView.getText().toString()+selectImageBeans.get(i).getPath());
//                        }
//                    }
//                },selectImageBeans);

                SelectImageUtils.getInstance().startSelectImage(this,"imageCallback",selectImageBeans);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }
}
