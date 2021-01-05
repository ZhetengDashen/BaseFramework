package com.baseeasy.baseframework;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.aliyun.svideo.snap.record.AliShootVideoUtils;
import com.apkfuns.logutils.LogUtils;
import com.baseeasy.baseframework.demoactivity.DataBindingActivity;
import com.baseeasy.baseframework.demoactivity.FingerprintActivity;
import com.baseeasy.baseframework.demoactivity.MuTestBean;
import com.baseeasy.baseframework.demoactivity.httptest.HTTPTestActivity;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseFragment;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;

import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoSeparateCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectvideo.ShootVideoCallBack;

import com.baseeasy.commonlibrary.selectimageandvideo.selectvideo.ShootVideoUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.selectvideo.ShotVideoConfig;
import com.baseeasy.commonlibrary.selectimageandvideo.selectvideo.VideoPlayUtils;
import com.baseeasy.commonlibrary.weight.dialog.CustomDialog;
import com.baseeasy.commonlibrary.weight.dialog.actiondialog.SelectActionListDialog;
import com.baseeasy.commonlibrary.weight.dialog.multipledialog.SelectMultipleListDialog;
import com.luck.picture.lib.PictureSelector;
import com.magiclon.individuationtoast.ToastUtil;
import com.test.TestUser;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.AppMode.MAIN_ACTIVITY)
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Button bt_arouter;
    private Button bt_eventbus;
    private Button bt_imageloader;
    private Button bt_loadingview;
    private Button bt_recycler;
    private Button bt_log;
    private Button bt_rxpermissions;
    private Button select_image;
    private Button fingbt;
    private List<String> selectImageBeans=new ArrayList<>();
    private TextView textView;
    private Button qianming;
    private Button button_data_binding;
    private Button button_http;
    private Button button_video;
    private  List<String> videopathList=new ArrayList<>();
    CustomDialog customDialog;
    CustomDialog.DialogBuilder dialogBuilder;

    @Override
    public void init_view() {
        super.init_view();
        bt_arouter = (Button) findViewById(R.id.bt_arouter);
        bt_arouter.setOnClickListener(this);
        bt_eventbus = (Button) findViewById(R.id.bt_eventbus);
        bt_eventbus.setOnClickListener(this);
        bt_imageloader = (Button) findViewById(R.id.bt_imageloader);
        bt_imageloader.setOnClickListener(this);
        bt_loadingview = (Button) findViewById(R.id.bt_loadingview);
        bt_loadingview.setOnClickListener(this);
        bt_recycler = (Button) findViewById(R.id.bt_recycler);
        bt_recycler.setOnClickListener(this);
        bt_log = (Button) findViewById(R.id.bt_log);
        bt_log.setOnClickListener(this);
        bt_rxpermissions = (Button) findViewById(R.id.bt_rxpermissions);
        bt_rxpermissions.setOnClickListener(this);
        select_image = findViewById(R.id.bt_select_image);
        select_image.setOnClickListener(this);
        textView = findViewById(R.id.textView);
        fingbt = findViewById(R.id.button_ff);
        fingbt.setOnClickListener(this);
        EventBusUtils.register(this);

        qianming = (Button) findViewById(R.id.qianming);
        qianming.setOnClickListener(this);
        button_data_binding = findViewById(R.id.binding);
        button_data_binding.setOnClickListener(this);

        button_http = (Button) findViewById(R.id.button_http);
        button_http.setOnClickListener(this);
        button_video=findViewById(R.id.video);
        button_video.setOnClickListener(this);
        dialogBuilder=new CustomDialog.DialogBuilder(this);
        dialogBuilder.title("ttt").content("contn").left("123").right("312");
        customDialog=new CustomDialog(dialogBuilder,false);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    // 在主线程处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMessageOnMainThread(EventMessage event) {
        switch (event.getFlag()) {
            case ARouterPath.AppMode.DEMO_EVENTBUS_ACTIVITY:
                TestUser testUser = (TestUser) event.getEvent();
                Toast.makeText(this, testUser.getName(), Toast.LENGTH_SHORT).show();
                break;
            case "imageCallback":

                selectImageBeans = (List<String>) event.getEvent();
                for (int i = 0; i < selectImageBeans.size(); i++) {
                    textView.setText(textView.getText().toString() + selectImageBeans.get(i));
                }
                break;
            case "video":
                  textView.setText((String)event.getEvent());
                break;
        }


    }

    private void initView() {
//       ARouterTools.startActivity(ARouterPath.AppMode.MAIN_TEST,"wo","卡机是");//
//     ARouterTools.startActivity(ARouterPath.TestMode.TEST_ACTIVITY,"user",new TestUser("王大锤" ,"0","女"));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_arouter:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_AROUTER_ACTIVITY, "user", new TestUser("王大锤", "0", "女"));
                break;
            case R.id.bt_eventbus:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_EVENTBUS_ACTIVITY);
                break;
            case R.id.bt_imageloader:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_IMAGERLOADER_ACTIVITY);
                break;
            case R.id.bt_loadingview:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_LOADINGVIEW_ACTIVITY);
                break;
            case R.id.bt_recycler:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_RECYCLER_ACTIVITY);
                break;
            case R.id.bt_log:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_LOGUTILS_ACTIVITY);
                break;

            case R.id.bt_rxpermissions:
                ARouterTools.startActivity(ARouterPath.AppMode.DEMO_RXPERMISSIONS_ACTIVITY);
                break;
            case R.id.bt_select_image:
//                customDialog.show();
//              ARouterTools.startActivity(ARouterPath.CommonLibrary.COMMON_SELECTIMAGE_ACTIVITY);
//                SelectImageUtils.getInstance().startSelectImage(this, new SelectImageCallBack() {
//                    @Override
//                    public void onImageSelected(List<SelectImageBean> localMediaList) {
//                        selectImageBeans=localMediaList;
//                        for (int i = 0; i <selectImageBeans.size() ; i++) {
//                            textView.setText(textView.getText().toString()+selectImageBeans.get(i).getPath());
//                        }
//                    }
//                },selectImageBeans);

//      SelectImageUtils.getInstance().startSelectImage(this,"imageCallback",selectImageBeans);
//                SelectImageUtils.getInstance().startSelectImage(this, new SelectImageCallBack() {
//                    @Override
//                    public void onImageSelected(List<String> imageBeans) {
//
//                    }
//
//                    @Override
//                    public void onAddImage(List<String> imageBeans) {
//                        selectImageBeans.addAll(imageBeans);
//                    }
//
//                    @Override
//                    public void onDeleteImage(List<String> imageBeans) {
//                        selectImageBeans.removeAll(imageBeans);
//                    }
//                },selectImageBeans);
                SelectImageUtils.getInstance().startTakingPhoto(this, new TakingPhotoCallBack() {
                    @Override
                    public void onTakingPhoto(List<String> localMediaList) {

//                         selectImageBeans = localMediaList;
//                        for (int i = 0; i < selectImageBeans.size(); i++) {
//                            textView.setText(textView.getText().toString() + selectImageBeans.get(i).getPath());
//                        }
                    }

                    @Override
                    public void onAddTakingPhoto(List<String> imageBeans) {

                        selectImageBeans.addAll(imageBeans);
                    }

                    @Override
                    public void onDeleteTakingPhoto(List<String> imageBeans) {

                        selectImageBeans.removeAll(imageBeans);

                    }
                }, selectImageBeans);

//             SelectImageUtils.getInstance().startTakingPhotoAndImageSeparate(this, "imageCallback");
//                SelectImageUtils.getInstance().startTakingPhotoAndImageSeparate(this, new TakingPhotoSeparateCallBack() {
//                    @Override
//                    public void onTakingPhoto(SelectImageBean imageBean) {
//                        LogUtils.e(JSON.toJSONString(imageBean));
//                    }
//                });
//             SelectImageUtils.getInstance().startTakingPhoto(this,"imageCallback",selectImageBeans,1);
//                SelectImageUtils.getInstance().startTakingPhotoSeparate(this, new TakingPhotoSeparateCallBack() {
//                    @Override
//                    public void onTakingPhoto(SelectImageBean imageBean) {
//                        textView.setText(imageBean.getPath());
//                    }
//                });
//
//                SelectImageUtils.getInstance().startSelectImage(this,"imageCallback",selectImageBeans);
//             SelectImageUtils.getInstance().startTakingPhotoSeparate(this,"imageCallback");
//
//                ShootVideoUtils.getInstance().startShootVideo(this,new ShootVideoConfig(10,0), new ShootVideoCallBack() {
//                    @Override
//                    public void onShootVideo(String videoPath) {
//                        Log.e("KK",videoPath);
//
//                    }
//                });
//                ShootVideoUtils.getInstance().startShootVideo(this,new ShootVideoConfig(10,0),"video");
//                SelectImageUtils.getInstance().startTakingPhotoAndImageSeparate(this, new TakingPhotoSeparateCallBack() {
//                    @Override
//                    public void onTakingPhoto(String imagePath) {
//                        LogUtils.e(imagePath);
//                    }
//                });
                break;
            case R.id.button_ff:

                startActivity(new Intent(this, FingerprintActivity.class));
                break;

            case R.id.qianming:
                Toast.makeText(this, "222", Toast.LENGTH_SHORT).show();
//                WriteSignPadDialog writeSignPadDialog=new WriteSignPadDialog(this);
//                writeSignPadDialog.setDialogListener(new WriteDialogListener() {
//                    @Override
//                    public void callPath(Object object) {
//
//                    }
//                });
//                writeSignPadDialog.show();

//                ArrayList<MuTestBean> muTestBeans=new ArrayList<>();
//                muTestBeans.add(new MuTestBean("aaaaaa",true));
//                muTestBeans.add(new MuTestBean("bbbbbb",false));
//                muTestBeans.add(new MuTestBean("cccccc",false));
//                muTestBeans.add(new MuTestBean("dddddd",true));
//                muTestBeans.add(new MuTestBean("eeeeee",false));
//                muTestBeans.add(new MuTestBean("ffffff",true));
//                SelectMultipleListDialog.createDialog(MainActivity.this, "请选择", muTestBeans, new SelectMultipleListDialog.SelectCallback<MuTestBean>() {
//                    @Override
//                    public void callback(List<MuTestBean> list) {
//                        for (int i = 0; i <list.size() ; i++) {
//                         Log.e("KK",list.get(i).getMuCheckName());
//                         LogUtils.e(list.get(i));
//                        }
//                    }
//                }).show();
//                ArrayList<String> aa = new ArrayList<>();
//                aa.add("aaa");
//                aa.add("bbb");
//                aa.add("ccc");
//                SelectActionListDialog.createDialog(MainActivity.this, aa, new SelectActionListDialog.ActionCallback<String>() {
//                    @Override
//                    public void callback(String action) {
//
//                    }
//                }).show();




//                WriteSignPadDialogNew writeSignPadDialogNew=new WriteSignPadDialogNew(MainActivity.this, new WriteDialogListener() {
//                    @Override
//                    public void callPath(Object object) {
//                        Toast.makeText(MainActivity.this, ((File) object).getPath()+"", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                writeSignPadDialogNew.show();


                break;

            case R.id.binding:
                startActivity(new Intent(this, DataBindingActivity.class));
                break;

            default:
                break;
            case R.id.button_http:
                 startActivity(new Intent(this, HTTPTestActivity.class));
                break;
            case  R.id.video:
//                VideoPlayUtils.play(this,"http://cdn.baseeasy.com/%E4%B8%81%E4%B8%96%E8%B4%B51501031965041325551588846442498.mp4");

                ShotVideoConfig shotVideoConfig=
                        new ShotVideoConfig.Builder()
                                .withVideoQuality(ShotVideoConfig.ConfigParameter.PROGRESS_HIGH)
                                .withEatioMode(ShotVideoConfig.ConfigParameter.RATIO_MODE_3_4)
                                .withEesolutionMode(ShotVideoConfig.ConfigParameter.RESOLUTION_720P)
                                .withMaxTime(30).build();
                ShootVideoUtils.getInstance().startShootVideo(MainActivity.this, new ShootVideoCallBack() {
                    @Override
                    public void onShootVideo(List<String> pathList) {
//                        videopathList.clear();
//                       pathList.addAll(pathList);
//                        videopathList.addAll(pathList);
                    }

                    @Override
                    public void onAddVideoList(List<String> pathList) {
                        textView.setText(textView.getText().toString()+"添加："+pathList);
                        videopathList.addAll(pathList);
                    }

                    @Override
                    public void onDeleteVideoList(List<String> pathList) {
                        videopathList.removeAll(pathList);
                        textView.setText(textView.getText().toString()+"减去："+pathList);
                    }
                }, videopathList,3,shotVideoConfig,true);
                Log.e("kk",videopathList.size()+"");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main ;
    }
}
