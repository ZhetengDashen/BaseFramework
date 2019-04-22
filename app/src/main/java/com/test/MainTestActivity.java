package com.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.http.InterceptorUtil;
import com.baseeasy.commonlibrary.http.RetrofitManagement;

import okhttp3.Interceptor;


@Route(path = ARouterPath.AppMode.MAIN_TEST)
public class MainTestActivity extends BaseActivity {
    @Autowired
    public String wo;

    @Autowired(name="user")
    public TestUser testUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toast.makeText(this, ""+testUser.toString(), Toast.LENGTH_SHORT).show();
        Log.e("KK",testUser.toString());

       RetrofitManagement.getInstance().getService();
    }
}
