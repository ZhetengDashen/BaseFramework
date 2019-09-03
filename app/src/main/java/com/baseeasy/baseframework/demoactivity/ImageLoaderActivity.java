package com.baseeasy.baseframework.demoactivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.imageloader.DisplayOption;
import com.baseeasy.commonlibrary.imageloader.ImageLoaderFactory;
@Route(path = ARouterPath.AppMode.DEMO_IMAGERLOADER_ACTIVITY)
public class ImageLoaderActivity extends BaseActivity {

    private ImageView image;



    @Override
    protected int setContentViewId() {
        return R.layout.activity_image_loader;
    }

    @Override
    public void init_view() {
        super.init_view();
        initView();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }

    private void initView() {
        image = (ImageView) findViewById(R.id.image);

        DisplayOption displayOption=new DisplayOption.Builder()
                .setLoadErrorResId(R.mipmap.ic_launcher_round)//设置加载错误图片
                .setPlaceHolderResId(R.mipmap.ic_launcher)//设置占位图
                .setHeight(500)//设置宽高
                .setWidth(500)
                .create();

        ImageLoaderFactory.getInstance().displayImage(image,"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4084320817,2521788326&fm=111&gp=0.jpg",displayOption);

    }
}
