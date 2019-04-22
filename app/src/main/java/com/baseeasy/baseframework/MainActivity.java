package com.baseeasy.baseframework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK){
            if(data!=null){
                String name=data.getStringExtra("namekey");
            }

        }
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"wo","卡机是");
              ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"user",new TestUser("王大锤" ,"0","女"));
              ARouterTools.startActivityResult(ARouterPath.AppMode.MAIN_TEST,MainActivity.this,1);


            }
        });
    }
}
