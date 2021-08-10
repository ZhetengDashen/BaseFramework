package com.baseeasy.baseframework.demoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseeasy.baseframework.R;
import com.baseeasy.commonlibrary.fingerprint.MyFingerprintUtils;
import com.baseeasy.commonlibrary.fingerprint.MyFingerprintUtils_Sz;
import com.baseeasy.commonlibrary.mytool.SharePreferenceUtils;
import com.bumptech.glide.Glide;

public class FingerprintActivitysz extends AppCompatActivity implements View.OnClickListener {


    private Button mbt;
    private Button mbt2;
    private Button mbt3;
    private TextView content;
    private ImageView image;
    private Button btweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        initView();
//        startActivity(new Intent(this, MainActivity1.class));
//        Toast.makeText(this, "222", Toast.LENGTH_SHORT).show();


    }

    private void initView() {
        mbt = (Button) findViewById(R.id.mbt);

        mbt.setOnClickListener(this);
        mbt2 = (Button) findViewById(R.id.mbt2);
        mbt2.setOnClickListener(this);
        mbt3 = (Button) findViewById(R.id.mbt3);
        mbt3.setOnClickListener(this);
        content = (TextView) findViewById(R.id.content);
        content.setOnClickListener(this);
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(this);
        btweb = (Button) findViewById(R.id.btweb);
        btweb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mbt:

                MyFingerprintUtils_Sz.getInstance(this).fingerprintOpen(new MyFingerprintUtils_Sz.OnFingerprintCallBack() {
                    @Override
                    public void callBack(int code, String msg, String data, String signatureTag) {

                        if (code == 1) {
                            //打开成功
                            content.setText(msg);
                        }

                    }
                });
                break;

            case R.id.mbt3:
                MyFingerprintUtils_Sz.getInstance(FingerprintActivitysz.this).fingerprintImage(new MyFingerprintUtils_Sz.OnFingerprintCallBack() {
                    @Override
                    public void callBack(int code, String msg, String data, String signatureTag) {
                        switch (code) {
                            case -1:
                                //录入失败
                                content.setText(msg);
                                break;
                            case 0:
                                //等待录入
                                content.setText(msg + ":" + data);
                                break;
                            case 1:
                                //录入成功
                                content.setText(data);
                                Glide.with(FingerprintActivitysz.this).load(data).into(image);

                                MyFingerprintUtils_Sz.getInstance(FingerprintActivitysz.this).fingerprintClose(new MyFingerprintUtils_Sz.OnFingerprintCallBack() {
                                    @Override
                                    public void callBack(int code, String msg, String data, String signatureTag) {
                                        
                                    }
                                });
                                break;

                        }
                    }
                });

                break;

            case R.id.btweb:
//                startActivity(new Intent(FingerprintActivity.this,WebActivity.class));
                break;
        }
    }
}
