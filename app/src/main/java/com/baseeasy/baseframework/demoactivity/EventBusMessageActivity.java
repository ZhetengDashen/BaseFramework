package com.baseeasy.baseframework.demoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.baseframework.R;
import com.baseeasy.baseframework.demoactivity.eventbuspresenter.EventBusMainPresenter;
import com.baseeasy.baseframework.demoactivity.eventbuspresenter.EventBusMessagePresenter;
import com.baseeasy.baseframework.demoactivity.eventbuspresenter.IEventBusView;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventConst;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.test.TestUser;

import static com.baseeasy.commonlibrary.arouter.ARouterPath.AppMode.DEMO_EVENTBUS_ACTIVITY;

@Route(path = DEMO_EVENTBUS_ACTIVITY)
public class EventBusMessageActivity extends BaseActivity<IEventBusView, EventBusMessagePresenter<IEventBusView>>implements View.OnClickListener {

    private Button bt;
    private Button bt2;


    @Override
    protected int setContentViewId() {
        return R.layout.activity_event_bus_message;
    }

    @Override
    public void init_view() {
        super.init_view();
        initView();
    }

    @Override
    protected EventBusMessagePresenter<IEventBusView> createPresenter() {
        return new EventBusMessagePresenter<>(this,this);
    }


    private void initView() {
        bt = (Button) findViewById(R.id.bt);

        bt.setOnClickListener(this);
        bt2 = (Button) findViewById(R.id.bt2);

        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                EventMessage eventMessage= new EventMessage.Builder<TestUser>()
                        .setCode(EventConst.EVENT_CODE_OK)
                        .setFlag(DEMO_EVENTBUS_ACTIVITY)
                        .setEvent(new TestUser("瓦不服","123","女")).create();
                EventBusUtils.post(eventMessage);
                finish();
                break;
            case R.id.bt2:
                EventMessage eventMessage2= new EventMessage.Builder<TestUser>()
                        .setCode(EventConst.EVENT_CODE_OK)
                        .setFlag("123")
                        .setEvent(new TestUser("瓦不服2","123","女")).create();
                EventBusUtils.postSticky(eventMessage2);
                startActivity(new Intent(EventBusMessageActivity.this,EventBusMainActivity.class));
                break;
        }
    }
}
