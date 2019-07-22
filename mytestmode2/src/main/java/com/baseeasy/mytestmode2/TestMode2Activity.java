package com.baseeasy.mytestmode2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;

@Route(path = ARouterPath.TestMode2.MAIN_TEST)
public class TestMode2Activity extends AppCompatActivity {

    private TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mode2);
        initView();
    }

    private void initView() {
        tx = (TextView) findViewById(R.id.tx);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouterTools.startActivity(ARouterPath.TestMode.TEST_ACTIVITY);
            }
        });
    }
}
