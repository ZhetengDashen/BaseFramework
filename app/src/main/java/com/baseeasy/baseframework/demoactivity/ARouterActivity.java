package com.baseeasy.baseframework.demoactivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.test.TestUser;

@Route(path = ARouterPath.AppMode.DEMO_AROUTER_ACTIVITY)
public class ARouterActivity extends BaseActivity implements View.OnClickListener {

    private Button tv;
    @Autowired
    public TestUser user; //变量名称要与传值的key一致


    @Override
    protected int setContentViewId() {
        return R.layout.activity_arouter;
    }

    @Override
    public void init_data() {
        super.init_data();
        Toast.makeText(this, ""+user.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init_view() {
        super.init_view();
        tv = (Button) findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
//                ARouterTools.startActivity(ARouterPath.TestMode2.MAIN_TEST);
                break;
        }
    }
}
