package com.baseeasy.baseframework.demoactivity.httptest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baseeasy.baseframework.R;
import com.baseeasy.baseframework.demoactivity.httptest.api.ApiUrls;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.http.BaseObserver;
import com.baseeasy.commonlibrary.http.BaseResult;
import com.baseeasy.commonlibrary.http.RetrofitFactory;
import com.baseeasy.commonlibrary.http.uploadfile.FilesToMultipartUtils;
import com.baseeasy.commonlibrary.http.uploadfile.UploadFileRequestBody;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class HTTPTestActivity extends BaseActivity implements View.OnClickListener {

    private EditText ed;
    private Button bt;

    @Override
    public void init_view() {
        super.init_view();
        initView();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_httptest;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initView() {
        ed = (EditText) findViewById(R.id.ed);
        bt = (Button) findViewById(R.id.bt);

        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                Toast.makeText(this, "2222", Toast.LENGTH_SHORT).show();
                File file=new File("/storage/emulated/0/无纸化办公/screenshots/406ab8c3-20f8-4024-815e-98421922df4b.jpg");
                MultipartBody.Part part = FilesToMultipartUtils.fileToMultipartBodyPart(file, "ad_file", new UploadFileRequestBody.UploadBodyListener() {
                    @Override
                    public void onProgress(int percentage, boolean done) {

                    }
                });
                String aa="{\"niu\":0,\"jyall\":0,\"pfDependencyMembers\":[{\"year_income\":0,\"support_if\":1,\"supportIfName\":\"双方\",\"workTypeName\":\"务农\",\"identifcard_id\":\"\",\"marriage_state\":0,\"supportMemberTypeName\":\"\",\"peopleid_num\":\"210411198011242117\",\"people_name\":\"是\",\"support_member_type\":0,\"dependency_id\":\"\",\"relationship_code\":0,\"work_type\":1,\"labor_number\":0,\"support_type\":0,\"member_type\":0,\"year_support_money\":695,\"hel_relation\":2,\"member_name\":\"\",\"helRelationName\":\"配偶\",\"supportTypeName\":\"\"},{\"year_income\":0,\"support_if\":1,\"supportIfName\":\"双方\",\"workTypeName\":\"务工\",\"identifcard_id\":\"\",\"marriage_state\":0,\"supportMemberTypeName\":\"\",\"peopleid_num\":\"440804198705050735\",\"people_name\":\"超市\",\"support_member_type\":0,\"dependency_id\":\"\",\"relationship_code\":0,\"work_type\":2,\"labor_number\":0,\"support_type\":0,\"member_type\":0,\"year_support_money\":1478,\"hel_relation\":2,\"member_name\":\"\",\"helRelationName\":\"配偶\",\"supportTypeName\":\"\"}],\"apply_hid\":\"402886f66ec46c29016ecaa89142074c\",\"wgsr\":0,\"createtime\":\"2019-12-23 10:17:16\",\"yearpre\":0,\"ldother\":0,\"paytype\":0,\"gzall\":0,\"g1\":\"\",\"ts_G1\":\"\",\"yeartotal\":0,\"gzother\":0,\"ylbxj\":0,\"ccall\":0,\"ltype\":\"2\",\"zyall\":0,\"other\":\"\",\"g2\":\"100\",\"zyother\":0,\"tdjy\":0,\"unitid\":\"150422002002\",\"token\":\"60089868e4d21494606a2b783cd5f490\",\"jyother\":0,\"kjB\":308484,\"tdzybt\":0,\"fwczcs\":0,\"hnbt\":0,\"timestamp\":\"1577067448051\",\"shfl\":0,\"userid\":\"10006157\",\"g4\":\"0\",\"appversion\":\"1\",\"other_money\":0,\"hbc\":4860,\"pfMembers\":[{\"relationship_name\":\"本人\",\"year_income\":0,\"support_if\":0,\"workTypeName\":\"务农\",\"identifcard_id\":\"320928193902132752\",\"marriage_state\":2,\"peopleid_num\":\"\",\"people_name\":\"\",\"support_member_type\":0,\"dependency_id\":\"\",\"memberTypeName\":\"正常人员\",\"relationship_code\":1,\"work_type\":1,\"labor_number\":0,\"support_type\":0,\"member_type\":19,\"year_support_money\":0,\"hel_relation\":0,\"marriage_name\":\"已婚\",\"member_name\":\"测试1\"}],\"g5\":\"0\",\"pretotal\":\"1\",\"hh_id\":\"737959dffd8d4ede8a8d1ea9df099735\",\"syf\":2173,\"ccother\":0,\"g3\":\"0\",\"ttype\":\"G1:;G2:100;G3:0;G4:0;G5:0\",\"hometype\":\"0\",\"kjC\":0,\"cxxtzlx\":0,\"jtdm\":0,\"yang\":0}";

                    JSONObject jsonObject=  (JSONObject) JSONObject.parse(aa);
                    jsonObject.put("haha",ed.getText().toString());


               RetrofitFactory.create(ReApi.class).actionCs(ApiUrls.ACTION_CS,jsonObject.toJSONString() , part).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver(this) {
                   @Override
                   protected void onSuccess(BaseResult t) throws Exception {

                   }
               });

                break;
        }
    }


}
