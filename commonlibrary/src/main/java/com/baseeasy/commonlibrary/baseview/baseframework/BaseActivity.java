package com.baseeasy.commonlibrary.baseview.baseframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 作者：WangZhiQiang
 * 时间：2019/3/14
 * 邮箱：sos181@163.com
 * 描述：
 */
public class BaseActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);

    }
}
