package com.baseeasy.baseframework.demoactivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.log2file.LogFileEngineFactory;
import com.apkfuns.logutils.LogUtils;
import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.mlog.MyLogFileEngine;
import com.baseeasy.commonlibrary.mytool.AppUtils;

@Route(path = ARouterPath.AppMode.DEMO_LOGUTILS_ACTIVITY)
public class LogUtilsActivity extends BaseActivity implements View.OnClickListener {

    private Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_utils);
        initView();
    }

    @Override
    public boolean isOpenEventBus() {
        return false;
    }

    private void initView() {
        bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
             // 输出字符串
//                https://github.com/pengwei1024/LogUtils
                LogUtils.d("222");
                LogUtils.d("2地方个地方");
                LogUtils.d("3的点点滴滴");
                LogUtils.d("4反反复复发");
                LogUtils.d("5反反复复2");
                LogUtils.e("6顶顶顶顶");

                LogUtils.d(Environment.getExternalStorageDirectory()+"/"+ AppUtils.getAppName(this)+"/log");
//                LogUtils.json("{\"familyBasicInfo\":{\"house_address\":\"\",\"house_name\":\"\",\"now_address\":\"\",\"phone_number\":\"\",\"unitid\":\"\",\"unitname\":\"\"},\"familyMembers\":[{\"age\":0,\"disability\":\"\",\"disability_code\":0,\"health\":\"\",\"health_code\":0,\"idcard_number\":\"142332422312342231\",\"marriage\":\"\",\"marriage_code\":0,\"member_name\":\"名字1\",\"professional\":\"工作1\",\"professional_code\":0,\"relationship\":\"关系1\",\"relationship_code\":0},{\"age\":0,\"disability\":\"\",\"disability_code\":0,\"health\":\"\",\"health_code\":0,\"idcard_number\":\"142332422312342232\",\"marriage\":\"\",\"marriage_code\":0,\"member_name\":\"名字2\",\"professional\":\"工作2\",\"professional_code\":0,\"relationship\":\"关系2\",\"relationship_code\":0},{\"age\":0,\"disability\":\"\",\"disability_code\":0,\"health\":\"\",\"health_code\":0,\"idcard_number\":\"142332422312342233\",\"marriage\":\"\",\"marriage_code\":0,\"member_name\":\"名字3\",\"professional\":\"工作3\",\"professional_code\":0,\"relationship\":\"关系3\",\"relationship_code\":0},{\"age\":0,\"disability\":\"\",\"disability_code\":0,\"health\":\"\",\"health_code\":0,\"idcard_number\":\"142332422312342234\",\"marriage\":\"\",\"marriage_code\":0,\"member_name\":\"名字4\",\"professional\":\"工作4\",\"professional_code\":0,\"relationship\":\"关系4\",\"relationship_code\":0},{\"age\":0,\"disability\":\"\",\"disability_code\":0,\"health\":\"\",\"health_code\":0,\"idcard_number\":\"142332422312342235\",\"marriage\":\"\",\"marriage_code\":0,\"member_name\":\"名字5\",\"professional\":\"工作5\",\"professional_code\":0,\"relationship\":\"关系5\",\"relationship_code\":0}],\"familyPropertyInfo\":{\"bank_money\":\"77\",\"house_number\":\"66\",\"is_car\":\"1\",\"is_gdysp\":\"2\",\"is_house\":\"1\",\"is_scp\":\"2\",\"sybx\":\"88\",\"yjzq\":\"99\",\"zq\":\"100\"},\"hh_id\":\"\",\"incomeSpendingInfo\":{\"income_ccxsr\":{\"allmoney\":\"271\",\"cxtz\":\"88\",\"fwcz\":\"36\",\"qtcc\":\"89\",\"zztd\":\"58\"},\"income_gzxsr\":{\"allmoney\":\"208\",\"qtgzsr\":\"5\",\"tzshf\":\"98\",\"wgsr\":\"66\",\"ylbxsr\":\"39\"},\"income_jyxsr\":{\"allmoney\":\"6788\",\"farming_jiaqin\":\"8\",\"farming_money\":\"6666\",\"farming_niu\":\"4\",\"farming_yang\":\"8\",\"farming_zhu\":\"8\",\"land_hd\":\"66\",\"land_money\":\"36\",\"land_sd\":\"66\",\"other_money\":\"86\"},\"income_zyxsr\":{\"allmoney\":\"273\",\"lsbt\":\"99\",\"nsyf\":\"66\",\"qtzyxsr\":\"9\",\"tghl\":\"99\"},\"spending_jtkbzc\":{\"gezf\":\"99\",\"kbgzc\":\"66\",\"kbzbx\":\"99\"},\"spending_zddx\":{\"allmoney\":\"99\",\"dxbk\":\"66\",\"dxzk\":\"33\"}},\"supportBeans\":[{\"idcard\":\"142332422312342231\",\"name\":\"名字1\",\"nosycode\":0,\"workcode\":0,\"workname\":\"工作1\"},{\"idcard\":\"142332422312342232\",\"name\":\"名字2\",\"nosycode\":0,\"workcode\":0,\"workname\":\"工作2\"},{\"idcard\":\"142332422312342233\",\"name\":\"名字3\",\"nosycode\":0,\"workcode\":0,\"workname\":\"工作3\"},{\"idcard\":\"142332422312342234\",\"name\":\"名字4\",\"nosycode\":0,\"workcode\":0,\"workname\":\"工作4\"},{\"idcard\":\"142332422312342235\",\"name\":\"名字5\",\"nosycode\":0,\"workcode\":0,\"workname\":\"工作5\"}]}");
//                LogUtils.getLog2FileConfig().configLogFileEngine(new MyLogFileEngine(this));
//                LogUtils.d(Environment.getExternalStorageDirectory()+"/"+ AppUtils.getAppName(this));


                break;
        }
    }
}
