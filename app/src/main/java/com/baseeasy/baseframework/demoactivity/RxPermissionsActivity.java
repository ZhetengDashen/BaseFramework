package com.baseeasy.baseframework.demoactivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

import static com.baseeasy.commonlibrary.arouter.ARouterPath.AppMode.DEMO_RXPERMISSIONS_ACTIVITY;

@Route(path = DEMO_RXPERMISSIONS_ACTIVITY)
public class RxPermissionsActivity extends BaseActivity implements View.OnClickListener {

    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_rx_permissions;
    }

    @Override
    public void init_view() {
        super.init_view();
        initView();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    private void initView() {
        bt = (Button) findViewById(R.id.bt);

        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                RxPermissions rxPermission = new RxPermissions(this);
                rxPermission
                        .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_CALENDAR,
                                Manifest.permission.READ_CALL_LOG,
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.CAMERA,
                                Manifest.permission.CALL_PHONE)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {
                                    // 用户已经同意该权限
                                    Toast.makeText(RxPermissionsActivity.this, ""+permission.name, Toast.LENGTH_SHORT).show();
                                    LogUtils.d("同意授权："+permission.name);

                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                                    LogUtils.d("拒绝授权："+permission.name);
                                } else {
                                    // 用户拒绝了该权限，并且选中『不再询问』
                                    LogUtils.d("彻底拒绝授权："+permission.name);
                                }
                            }
                        });

                break;
        }
    }
}
