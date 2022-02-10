package com.baseeasy.baseframework.demoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.baseeasy.baseframework.R;
import com.baseeasy.baseframework.demoactivity.eventbuspresenter.EventBusMainPresenter;
import com.baseeasy.baseframework.demoactivity.eventbuspresenter.IEventBusView;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;

public class EventBusMainActivity extends BaseActivity<IEventBusView, EventBusMainPresenter<IEventBusView>> {



    @Override
    protected int setContentViewId() {
        return R.layout.activity_event_bus_main;
    }

    @Override
    protected EventBusMainPresenter<IEventBusView> createPresenter() {
        return new EventBusMainPresenter<>(this,this);
    }
}