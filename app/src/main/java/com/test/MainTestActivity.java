package com.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventConst;
import com.baseeasy.commonlibrary.eventbus.EventMessage;


@Route(path = ARouterPath.AppMode.MAIN_TEST)
public class MainTestActivity extends BaseActivity {
    @Autowired
    public String wo;
    @Autowired(name = "user")
    public TestUser testUser;
    private TextView huidiao;



    @Override
    protected int setContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    public void init_view() {
        super.init_view();
        initView();
    }

    @Override
    public void init_data() {
        super.init_data();
        Toast.makeText(this, "" + testUser.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }




    private void initView() {
        huidiao = (TextView) findViewById(R.id.huidiao);
        huidiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventMessage<TestUser> event = new EventMessage.Builder()
                        .setCode(EventConst.EVENT_CODE_OK)
                        .setFlag(ARouterPath.AppMode.MAIN_TEST)
                        .setEvent(new TestUser("王大锤子","23","女"))
                        .create();
                //发送普通事件
                EventBusUtils.post(event);
                finish();
            }
        });


    }
}
