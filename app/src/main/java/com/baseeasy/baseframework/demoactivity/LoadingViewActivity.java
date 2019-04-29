package com.baseeasy.baseframework.demoactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.loading.LoaderStyle;
import com.baseeasy.commonlibrary.loading.MyLoader;

import static com.baseeasy.commonlibrary.arouter.ARouterPath.AppMode.DEMO_LOADINGVIEW_ACTIVITY;

@Route(path = DEMO_LOADINGVIEW_ACTIVITY)
public class LoadingViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);


        MyLoader.showLoading(LoadingViewActivity.this, LoaderStyle.BallPulseSyncIndicator);
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }


}
