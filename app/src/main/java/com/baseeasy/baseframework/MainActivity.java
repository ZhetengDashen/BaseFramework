package com.baseeasy.baseframework;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.baseeasy.baseframework.demoactivity.DataBindingActivity;
import com.baseeasy.baseframework.demoactivity.FingerprintActivity;
import com.baseeasy.baseframework.demoactivity.FingerprintActivitysz;
import com.baseeasy.baseframework.demoactivity.MainPresenter;
import com.baseeasy.baseframework.demoactivity.eventbuspresenter.EventBusMainPresenter;
import com.baseeasy.baseframework.demoactivity.eventbuspresenter.IEventBusView;
import com.baseeasy.baseframework.demoactivity.httptest.HTTPTestActivity;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.arouter.ARouterTools;
import com.baseeasy.commonlibrary.basemvp.psenter.BasePresenter;
import com.baseeasy.commonlibrary.baseview.baseframework.BaseActivity;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;

import com.baseeasy.commonlibrary.imageloader.ImageLoaderFactory;
import com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.IDCardCameraActivity;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoBankCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.TakingPhotoIDCardCallBack;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage2.SelectImageUtils2;
import com.baseeasy.commonlibrary.selectimageandvideo.selectvideo.ShootVideoCallBack;

import com.baseeasy.commonlibrary.selectimageandvideo.selectvideo.ShootVideoUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.selectvideo.ShotVideoConfig;
import com.baseeasy.commonlibrary.soloader.SoLoader;
import com.baseeasy.commonlibrary.soloader.SoUtils;
import com.baseeasy.commonlibrary.weight.dialog.CustomDialog;
import com.baseeasy.commonlibrary.weight.dialog.OnCustomLeftClickLister;
import com.baseeasy.commonlibrary.weight.dialog.OnCustomRightClickLister;
import com.getkeepsafe.relinker.ReLinker;
import com.test.TestUser;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.AppMode.MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<IEventBusView, MainPresenter<IEventBusView>> implements View.OnClickListener {


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
    private  Button button_btsz;
    private Button button_sz;
    private Button button_za;
    private Button button_idcard;
    private ImageView image;

    private Button button_idcard_fm;
    private Button button_bank;
    @Override
    public void init_view() {
        super.init_view();
        image=findViewById(R.id.image);
        button_idcard_fm=findViewById(R.id.button);
        button_idcard_fm.setOnClickListener(this);
        button_bank=findViewById(R.id.button2);
        button_bank.setOnClickListener(this);
        button_idcard=findViewById(R.id.bt_idcard);
        button_idcard.setOnClickListener(this);
        button_sz=findViewById(R.id.button_sz);
        button_sz.setOnClickListener(this);
        button_za=findViewById(R.id.button_za);
        button_za.setOnClickListener(this);
        button_btsz = (Button) findViewById(R.id.btsz);
        button_btsz.setOnClickListener(this);
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
        dialogBuilder.title("ttt").content("contn").left("123").right("312").leftLister(new OnCustomLeftClickLister() {
            @Override
            public void onLeftClicked() {

            }
        }).rightLister(new OnCustomRightClickLister() {
            @Override
            public void onRightClicked() {

            }
        });
        customDialog=new CustomDialog(dialogBuilder,false);


    }

    @Override
    protected MainPresenter<IEventBusView> createPresenter() {
        return new MainPresenter<>(this,this);
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
            case "456":
                LogUtils.e("Activity:456");
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
            case  R.id.btsz:
                startActivity(new Intent(this, FingerprintActivitysz.class));
                break;
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

//                LogUtils.d("KKKKKK------------------");
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
//                    public void onTakingPhoto(String imagePath) {
//                        selectImageBeans.add(imagePath);
//                    }
//                });
//             SelectImageUtils.getInstance().startTakingPhoto(this,"imageCallback",selectImageBeans,1);
//                SelectImageUtils.getInstance().startTakingPhotoSeparate(this, new TakingPhotoSeparateCallBack() {
//
//                    @Override
//                    public void onTakingPhoto(String imagePath) {
//
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
                                .withMaxTime(30)
                                .withMinTime(20)
                                .build();
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
            case R.id.button_sz:
                loadSoFromAssetPathCopy();



                break;
            case R.id.button_za:

                break;

            case R.id.bt_idcard:
                Log.e("asd","555");
//                SelectImageUtils.getInstance().startTakingPhotoIdCardImageHead(this, new TakingPhotoIDCardCallBack() {
//                    @Override
//                    public void onTakingPhotoHead(String imagePaths) {
//                        Log.e("asd",imagePaths);
//                        ImageLoaderFactory.getInstance().displayImage(image,imagePaths);
//                    }
//
//                    @Override
//                    public void onTakingPhotoEmblem(String imagePaths) {
//
//                    }
//                });
                SelectImageUtils2.getInstance().startTakeIdcardHead(this, new SelectImageCallBack() {
                    @Override
                    public void onImageSelected(List<String> imagePaths) {

                    }

                    @Override
                    public void onAddImage(List<String> imagePaths) {
                        selectImageBeans.addAll(imagePaths);
                    }

                    @Override
                    public void onDeleteImage(List<String> imagePaths) {
                        selectImageBeans.removeAll(imagePaths);
                    }
                },selectImageBeans);


                break;

            case R.id.button:
//                SelectImageUtils.getInstance().startTakingPhotoIdCardImageEmblem(this, new TakingPhotoIDCardCallBack() {
//                    @Override
//                    public void onTakingPhotoHead(String imagePaths) {
//
//                    }
//
//                    @Override
//                    public void onTakingPhotoEmblem(String imagePaths) {
//                        Log.e("BEIMIAN",imagePaths);
//                        ImageLoaderFactory.getInstance().displayImage(image,imagePaths);
//
//                    }
//                });
                SelectImageUtils2.getInstance().startTakeIdcardEmblem(this, new SelectImageCallBack() {
                    @Override
                    public void onImageSelected(List<String> imagePaths) {

                    }

                    @Override
                    public void onAddImage(List<String> imagePaths) {
                        selectImageBeans.addAll(imagePaths);
                    }

                    @Override
                    public void onDeleteImage(List<String> imagePaths) {
                        selectImageBeans.removeAll(imagePaths);
                    }
                });
                break;
            case R.id.button2:
//                SelectImageUtils.getInstance().startTakingPhotoBankImage(this, new TakingPhotoBankCallBack() {
//                    @Override
//                    public void onTakingPhoto(String imagePaths) {
//                        ImageLoaderFactory.getInstance().displayImage(image,imagePaths);
//                    }
//                });
                SelectImageUtils2.getInstance().startTakeBankCard(this, new SelectImageCallBack() {
                    @Override
                    public void onImageSelected(List<String> imagePaths) {

                    }

                    @Override
                    public void onAddImage(List<String> imagePaths) {
                        selectImageBeans.addAll(imagePaths);
                    }

                    @Override
                    public void onDeleteImage(List<String> imagePaths) {
                        selectImageBeans.removeAll(imagePaths);
                    }
                });
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

    private void loadSoFromAssetPathCopy() {
        List<String> out = new ArrayList<>();
        try {
            SoUtils.copyAssetsDirectory(this, "szso", out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //arm64-v8a, armeabi-v7a, armeabi
        Log.d(TAG, "supported api:" + Build.CPU_ABI + " " + Build.CPU_ABI2);

        if (Build.VERSION.SDK_INT >= 21) {
            String[] abis = Build.SUPPORTED_ABIS;
            for (String abi : abis) {
                if (loadSoFile(abi, out)) {
                    break;
                }
            }
        } else {
            if (TextUtils.isEmpty(Build.CPU_ABI)) {
                if (loadSoFile(Build.CPU_ABI, out)) {
                    return;
                }
            }
            if (TextUtils.isEmpty(Build.CPU_ABI2)) {
                if (loadSoFile(Build.CPU_ABI2, out)) {
                    return;
                }
            }
            if (loadSoFile("armeabi", out)) {
                return;
            }
        }

    }

    private boolean loadSoFile(String abi, List<String> out) {
        Log.i("KK1",abi);
        boolean success = false;
        for (final String soPath : out) {
            Log.i("KK2",soPath);
            if (soPath.contains(abi)) {
                Log.i("KK3",soPath);

                String parentDir = SoUtils.getParentDir(new File(soPath));
                //注入so路径，如果清除了的话。没有清除可以不用每次注入
                boolean b = SoLoader.loadSoFile(this, parentDir);
                //加载so库
                if (b && new File(soPath).exists()) {
                    final String soName = soPath.substring(soPath.lastIndexOf("/") + 1, soPath.lastIndexOf(".")).substring(3);
                /*    final String soName = soPath.substring(soPath.lastIndexOf("/") + 1, soPath.lastIndexOf(".")).substring(3);
                    System.loadLibrary(soName); //加载有可能直接崩掉
                    //System.load(soPath); //load使用的是文件绝对路径
                    */
                    //or 使用第三方库加载，这个加载报错回调failure，不会直接崩溃，底层也是 System.load实现，只不过加载之前做了一些验证
                    ReLinker.loadLibrary(this, soName, new ReLinker.LoadListener() {
                        @Override
                        public void success() {
                            Log.i(TAG, "加载成功:" + soPath);

                        }

                        @Override
                        public void failure(Throwable t) {
                            Log.e(TAG, "加载异常:" + soPath, t);
                        }
                    });
                    success = true;
                    break;
                }
            }
        }
        return success;
    }


    /**
     * 检查能否找到动态链接库，如果找不到，请修改工程配置
     *
     * @param libraries 需要的动态链接库
     * @return 动态库是否存在
     */
    private boolean checkSoFile(String[] libraries) {
        File dir = new File(getApplicationInfo().nativeLibraryDir);

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return false;
        }
        List<String> libraryNameList = new ArrayList<>();
        for (File file : files) {
            libraryNameList.add(file.getName());
        }
        boolean exists = true;
        for (String library : libraries) {
            exists &= libraryNameList.contains(library);
        }
        return exists;
    }

}
