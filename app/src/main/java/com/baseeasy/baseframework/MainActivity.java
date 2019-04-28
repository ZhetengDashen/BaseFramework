package com.baseeasy.baseframework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.test.TestUser;

@Route(path = ARouterPath.AppMode.MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {

    private TextView tv;

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
        switch (event.getFlag()){
            case  ARouterPath.AppMode.MAIN_TEST:
                TestUser testUser=   (TestUser)event.getEvent();
                Toast.makeText(this,testUser.getName() , Toast.LENGTH_SHORT).show();
            break;
        }


    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//       ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"wo","卡机是");
//           ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"user",new TestUser("王大锤" ,"0","女"));
//               ARouterTools.startActivity(ARouterPath.TestMode.TEST_ACTIVITY,"user",new TestUser("王大锤" ,"0","女"));
                ARouterTools.startActivity(ARouterPath.TestMode2.MAIN_TEST);

            }
        });
    }
}
