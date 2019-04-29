package com.baseeasy.baseframework.demoactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;

import java.nio.file.Path;

@Route(path = ARouterPath.AppMode.DEMO_AROUTER_ACTIVITY)
public class ARouterActivity extends BaseActivity implements View.OnClickListener {

    private Button tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
        initView();
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }

    private void initView() {
        tv = (Button) findViewById(R.id.tv);

        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                ARouterTools.startActivity(ARouterPath.TestMode2.MAIN_TEST);
                break;
        }
    }
}
