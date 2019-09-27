package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventConst;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.luban.LuBanUtils;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;
/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
@Route(path = ARouterPath.CommonLibrary.COMMON_SELECTIMAGE_ACTIVITY)
public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_title_back;
    private TextView tv_title_title;


    private TextView tv_save;
    private String eventBugFlag="";
    private  List<LocalMedia> currentSelectList=new ArrayList<>();

    private RecyclerView recyclerView;
    private SelectImageAdapter selectImageAdapter;
    private String localMediaList_json="";
    private TextView tv_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        initView();
        initData();

        FileUtils.createSDDir(AppUtils.getAppName(this)+"/compression");
        FileUtils.createNoMedia(AppUtils.getAppName(this));
    }


    private void initView() {
        recyclerView=findViewById(R.id.rv_image);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_title = (TextView) findViewById(R.id.tv_title_title);
        tv_title_title.setText("添加图片");
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);


    }
    private void initData() {
        try {
            eventBugFlag=getIntent().getStringExtra("eventBusFlag");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            localMediaList_json= getIntent().getStringExtra("data");
            if(null!=localMediaList_json&&!localMediaList_json.equals("")&&!localMediaList_json.equals("null")){
                currentSelectList= JSONArray.parseArray(localMediaList_json, LocalMedia.class);
            }
        }catch (Exception e){
           e.printStackTrace();
        }

        selectImageAdapter=new SelectImageAdapter(currentSelectList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setAdapter(selectImageAdapter);
        selectImageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.iv_delete){
                    selectImageAdapter.remove(position);

                }else  if(view.getId()==R.id.iv_image){
                    PictureSelector.create(SelectImageActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, currentSelectList);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_add) {
            PictureSelector.create(SelectImageActivity.this)
                     .openGallery(PictureMimeType.ofImage())
                     .compress(true)
                     .selectionMedia(getCurrentSDCardSelectList(currentSelectList))
                     .compressSavePath(Environment.getExternalStorageDirectory()+"/"+AppUtils.getAppName(this)+"/compression")//压缩图片保存地址
                     .setOutputCameraPath("/"+AppUtils.getAppName(this)+"/camera_image")
                     .maxSelectNum(100)// 最大图片选择数量 int
                     .forResult(PictureConfig.CHOOSE_REQUEST);

        }else if(v.getId()==R.id.tv_save){
            if(null!=eventBugFlag&&!eventBugFlag.equals("")){
                EventBusUtils.post(new EventMessage(EventConst.EVENT_CODE_OK,eventBugFlag,localMediaToSelectImage(currentSelectList)));
            }
            Intent intent=new Intent();
            intent.putExtra("localMediaList",JSONObject.toJSONString(localMediaToSelectImage(currentSelectList)));
            setResult(RESULT_OK, intent);
            finish();
        }else  if(v.getId()==R.id.iv_title_back){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                // 图片、视频、音频选择结果回调
                  currentSelectList.clear();
                  currentSelectList.addAll(PictureSelector.obtainMultipleResult(data));
//                    currentSelectList.addAll(PictureSelector.obtainMultipleResult(data));
//                    for (int i = 0; i <currentSelectList.size() ; i++) {
//                        for (int j = 0; j <callbackSelectList.size() ; j++) {
//                              if(currentSelectList.get(i).getPath().equals(callbackSelectList.get(j).getPath())){
//
//                              }
//                        }
//                    }
                   selectImageAdapter.notifyDataSetChanged();
//                   Log.e("KK", JSONObject.toJSONString(selectList));
                   // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的


                    break;
            }
        }
    }
     //筛选本地SD卡
    public List<LocalMedia> getCurrentSDCardSelectList(List<LocalMedia> localMediaList) {
        List<LocalMedia> localSDCardMediaList=new ArrayList<>();
        for (int i = 0; i < localMediaList.size(); i++) {
            if(!localMediaList.get(i).getPath().startsWith("http")){
                localSDCardMediaList.add(localMediaList.get(i));
            }
        }
      return localSDCardMediaList;
    }

    public  List<SelectImageBean> localMediaToSelectImage(List<LocalMedia> localMediaList){
        List<SelectImageBean> selectImageBeans=new ArrayList<>();
        for (int i = 0; i <localMediaList.size() ; i++) {
            SelectImageBean selectImageBean=new SelectImageBean();
            selectImageBean.setPath(localMediaList.get(i).getPath());
            if(localMediaList.get(i).isCompressed()){
                selectImageBean.setCompressPath(localMediaList.get(i).getCompressPath());
            }
            selectImageBeans.add(selectImageBean);
        }
        return selectImageBeans;
    }

    public  List<LocalMedia> selectImageToLocalMedia(List<SelectImageBean> selectImageBeans){
        List<LocalMedia> localMediaList=new ArrayList<>();
        for (int i = 0; i <selectImageBeans.size() ; i++) {
            LocalMedia localMedia=new LocalMedia();
            localMedia.setPath(selectImageBeans.get(i).getPath());
            localMedia.setCompressPath(selectImageBeans.get(i).getCompressPath());
            localMediaList.add(localMedia);
        }
        return localMediaList;

    }
}
