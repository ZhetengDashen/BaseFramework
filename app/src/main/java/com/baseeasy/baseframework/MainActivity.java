package com.baseeasy.baseframework;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.imageloader.DisplayOption;
import com.baseeasy.commonlibrary.imageloader.ImageLoader;
import com.baseeasy.commonlibrary.imageloader.ImageLoaderFactory;
import com.test.TestUser;

@Route(path = ARouterPath.AppMode.MAIN_ACTIVITY)
public class MainActivity extends BaseActivity {

    private TextView tv;
    private ImageView image;

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
        switch (event.getFlag()) {
            case ARouterPath.AppMode.MAIN_TEST:
                TestUser testUser = (TestUser) event.getEvent();
                Toast.makeText(this, testUser.getName(), Toast.LENGTH_SHORT).show();
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
        image = (ImageView) findViewById(R.id.image);

        DisplayOption displayOption=new DisplayOption.Builder()
                .setLoadErrorResId(R.drawable.ic_launcher_background)//设置加载错误图片
                .setPlaceHolderResId(R.drawable.ic_launcher_foreground)//设置占位图
                .setHeight(500)//设置宽高
                .setWidth(500)
                .create();

        ImageLoaderFactory.getInstance().displayImage(image,"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4084320817,2521788326&fm=111&gp=0.jpg",displayOption);
    }
}
