package com.baseeasy.baseframework.demoactivity;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.loading.LoaderStyle;
import com.baseeasy.commonlibrary.loading.MyLoader;

import java.util.ArrayList;
import java.util.List;

import static com.baseeasy.commonlibrary.arouter.ARouterPath.AppMode.DEMO_LOADINGVIEW_ACTIVITY;

@Route(path = DEMO_LOADINGVIEW_ACTIVITY)
public class LoadingViewActivity extends BaseActivity {
    private static final String[] INDICATORS = new String[]{
            "BallPulseIndicator",
            "BallGridPulseIndicator",
            "BallClipRotateIndicator",
            "BallClipRotatePulseIndicator",
            "SquareSpinIndicator",
            "BallClipRotateMultipleIndicator",
            "BallPulseRiseIndicator",
            "BallRotateIndicator",
            "CubeTransitionIndicator",
            "BallZigZagIndicator",
            "BallZigZagDeflectIndicator",
            "BallTrianglePathIndicator",
            "BallScaleIndicator",
            "LineScaleIndicator",
            "LineScalePartyIndicator",
            "BallScaleMultipleIndicator",
            "BallPulseSyncIndicator",
            "BallBeatIndicator",
            "LineScalePulseOutIndicator",
            "LineScalePulseOutRapidIndicator",
            "BallScaleRippleIndicator",
            "BallScaleRippleMultipleIndicator",
            "BallSpinFadeLoaderIndicator",
            "LineSpinFadeLoaderIndicator",
            "TriangleSkewSpinIndicator",
            "PacmanIndicator",
            "BallGridBeatIndicator",
            "SemiCircleSpinIndicator"
    };
    private RecyclerView recycler;
     private LoadingAdapter loadingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        initView();

        //启动LoadingView
        MyLoader.showLoading(LoadingViewActivity.this, LoaderStyle.BallPulseSyncIndicator);
        //停止LoadingView
        // MyLoader.stopLoading();

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

        // LoaderStyle的展示
        List<String> stringArrayList=new ArrayList<>();
        for (int i = 0; i <INDICATORS.length ; i++) {
            stringArrayList.add(INDICATORS[i]) ;
        }
        loadingAdapter=new LoadingAdapter(R.layout.item_loadingview,stringArrayList);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this ,4);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(loadingAdapter);
        recycler. addItemDecoration(new MDGridRvDividerDecoration(this));

    }
}
