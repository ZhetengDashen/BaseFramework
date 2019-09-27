package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;

@Route(path = ARouterPath.CommonLibrary.COMMON_SELECTVIDEO_ACTIVITY)
public class SelectVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video);
    }
}
