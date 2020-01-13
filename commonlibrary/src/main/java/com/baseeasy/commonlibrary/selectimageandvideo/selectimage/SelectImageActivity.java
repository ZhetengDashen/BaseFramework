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
import com.apkfuns.logutils.LogUtils;
import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventConst;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.luban.LuBanUtils;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.EventBusFlagImageOrVideo;
import com.baseeasy.commonlibrary.selectimageandvideo.GlideEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.ImageLocalMediaConversion;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.magiclon.individuationtoast.ToastUtil;

import java.util.ArrayList;
import java.util.List;


import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.IntentExtraName.EVENT_BUS_FLAG;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SELECT_IMAGE_REQUEST;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKING_PHOTO_REQUEST;

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
//    private String eventBugFlag="";
    private String localMediaList_json="";//展示的图片
    private int action_type=0;//0选择图片   1拍照

    private RecyclerView recyclerView;
    private SelectImageAdapter selectImageAdapter;
    private TextView tv_add;
    private int  maxPhoto=PictureShared.MAX_PHOTO_NUM;//默认100


    private  List<LocalMedia> currentSelectList=new ArrayList<>();
    private  List<LocalMedia> initialSelectList=new ArrayList<>();//初始数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        initView();
        initData();
        FileUtils.createSDDir(PictureShared.FolderNameConfig.COMPRESSION);
        FileUtils.createNoMedia(PictureShared.FolderNameConfig.COMPRESSION);

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
            localMediaList_json= getIntent().getStringExtra(PictureShared.IntentExtraName.EXIST_IMAGES);

            if(null!=localMediaList_json&&!localMediaList_json.equals("")&&!localMediaList_json.equals("null")){
                currentSelectList= ImageLocalMediaConversion.selectImageToLocalMedia(JSONArray.parseArray(localMediaList_json, String.class));
                for (int i = 0; i <currentSelectList.size() ; i++) {
                    currentSelectList.get(i).setCompressed(true);
                }
                initialSelectList.addAll(currentSelectList);

            }
        }catch (Exception e){
           e.printStackTrace();
        }
         action_type=getIntent().getIntExtra(PictureShared.IntentExtraName.ACTION_TYPE, SELECT_IMAGE_REQUEST);
         maxPhoto=getIntent().getIntExtra(PictureShared.IntentExtraName.MAXPHOTONUM,PictureShared.MAX_PHOTO_NUM);

        selectImageAdapter=new SelectImageAdapter(currentSelectList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setAdapter(selectImageAdapter);
        selectImageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.iv_delete){
//                    EventBusUtils.post(new EventMessage(EventConst.EVENT_CODE_OK, EventBusFlagImageOrVideo.DELETE_IMAGE,adapter.getData().get(position)));

                    selectImageAdapter.remove(position);

                }else  if(view.getId()==R.id.iv_image){
                    PictureSelector.create(SelectImageActivity.this).themeStyle(R.style.picture_default_style)  .loadImageEngine(GlideEngine.createGlideEngine()).openExternalPreview(position, currentSelectList);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_add) {
            int allowNum=  maxPhoto-selectImageAdapter.getData().size();
            if(allowNum<=0){
                ToastUtil.showwarning(this,"只能选择"+maxPhoto+"张");
                return;
            }
            switch (action_type){
                case 0:
                    PictureSelector.create(SelectImageActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .compress(true)
//                            .selectionMedia(getCurrentSDCardSelectList(currentSelectList))
                            .compressSavePath(FileUtils.SDPATH+PictureShared.FolderNameConfig.COMPRESSION)//压缩图片保存地址
                            .setOutputCameraPath("/"+PictureShared.FolderNameConfig.CAMERA)
                            .maxSelectNum(allowNum)// 最大图片选择数量 int
                            .forResult(SELECT_IMAGE_REQUEST);
                    break;
                case 1:
                    PictureSelector.create(SelectImageActivity.this)
                            .openCamera(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .compress(true)
                            .compressSavePath(FileUtils.SDPATH+PictureShared.FolderNameConfig.COMPRESSION)//压缩图片保存地址
                            .setOutputCameraPath("/"+PictureShared.FolderNameConfig.CAMERA)
                            .forResult(TAKING_PHOTO_REQUEST);
                    break;
            }


        }else if(v.getId()==R.id.tv_save){
//            if(null!=eventBugFlag&&!eventBugFlag.equals("")){
//                EventBusUtils.post(new EventMessage(EventConst.EVENT_CODE_OK,eventBugFlag, ImageLocalMediaConversion.localMediaToSelectImage(currentSelectList)));
//            }
            Intent intent=new Intent();
            String current=JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(currentSelectList));
            intent.putExtra(PictureShared.IntentExtraName.SELECTIMAGE_DATA,current);
            String add=JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(getAddData()));
            intent.putExtra(PictureShared.IntentExtraName.SELECTIMAGE_ADD_DATA,add);
            String del=JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(getDeleteData()));
            intent.putExtra(PictureShared.IntentExtraName.SELECTIMAGE_DELETE_DATA,del);

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
                case SELECT_IMAGE_REQUEST:
//                EventBusUtils.post(new EventMessage<String>(EventConst.EVENT_CODE_OK, EventBusFlagImageOrVideo.ADD_VIDEO,path));

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
                case TAKING_PHOTO_REQUEST:
                    currentSelectList.addAll(PictureSelector.obtainMultipleResult(data));
                    selectImageAdapter.notifyDataSetChanged();
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


    /**
     *获取新增数据
     */

    public List<LocalMedia> getAddData(){
        List<LocalMedia> a=new ArrayList<>();
        List<LocalMedia> b=new ArrayList<>();

        a.addAll(initialSelectList);
        b.addAll(currentSelectList);
        b.removeAll(a);
//        for (int i = 0; i <b.size() ; i++) {
//            for (int j = 0; j <a.size() ; j++) {
//                if(b.get(i).getPath().equals(a.get(j))){
//                    b.remove(i);
//                }
//            }
//        }

        return b;
    }

    /**
     *
     *获取删除数据
     */

    public  List<LocalMedia> getDeleteData(){
        List<LocalMedia> a=new ArrayList<>();
        List<LocalMedia> b=new ArrayList<>();
        a.addAll(initialSelectList);
        b.addAll(currentSelectList);
        a.removeAll(b);
        return  a;
    }

}
