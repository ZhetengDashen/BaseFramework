package com.baseeasy.commonlibrary.selectimageandvideo.selectimage2;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.mytool.time.TimeUtil;
import com.baseeasy.commonlibrary.selectimageandvideo.GlideEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.ImageLocalMediaConversion;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.ImageFileCompressEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.MeOnCameraInterceptListener;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.magiclon.individuationtoast.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SELECT_IMAGE_REQUEST;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKING_PHOTO_REQUEST;

/**
 * @author：Mr.Zan
 * @date： 2021/11/11 17:58
 * email：644962006@qq.com
 * detail：
 */
@Route(path = ARouterPath.CommonLibrary.COMMON_SELECTIMAGE_ACTIVITY2)
public class SelectImageActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_title_back;
    private TextView tv_title_title;
    private TextView tv_save;
    private String localMediaList_json="";//展示的图片
    private int action_type=0;//0选择图片   1拍照

    private RecyclerView recyclerView;
    private SelectImageAdapter selectImageAdapter;
    private TextView tv_add;
    private int  maxPhoto=PictureShared.MAX_PHOTO_NUM;//默认100
    private int requestCode;

    private  ArrayList<LocalMedia> currentSelectList=new ArrayList<>();
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
            requestCode= getIntent().getIntExtra(PictureShared.IntentExtraName.REQUSETCODE,0);

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
                    String path="";
                    if(currentSelectList.get(position).isCompressed()){
                        path=  currentSelectList.get(position).getCompressPath();
                    }else {
                        path=currentSelectList.get(position).getPath();
                    }
                    SelectImageFragment2.mSelectFileResultCallBack.onDeleteResult(path,requestCode);
                    currentSelectList.remove(position);
                    SelectImageFragment2.mSelectFileResultCallBack.onCurrentSelectResult(JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(currentSelectList)),requestCode);
                    selectImageAdapter.notifyDataSetChanged();
                }else  if(view.getId()==R.id.iv_image){
                    PictureSelector.create(SelectImageActivity2.this).openPreview().setImageEngine(GlideEngine.createGlideEngine()).startActivityPreview(position, false,currentSelectList);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_save) {//按钮修改成添加
            int allowNum=  maxPhoto-selectImageAdapter.getData().size();
            if(allowNum<=0){
                ToastUtil.showwarning(this,"只能选择"+maxPhoto+"张");
                return;
            }
            switch (action_type){
                case 0:
                    PictureSelector.create(SelectImageActivity2.this)
                            .openGallery(SelectMimeType.ofImage())
                            .setCameraInterceptListener(new MeOnCameraInterceptListener())
                            .setImageEngine(GlideEngine.createGlideEngine())
                            .setRequestedOrientation(ORIENTATION_PORTRAIT)
                            .setCompressEngine(new ImageFileCompressEngine())//压缩文件夹，压缩文件命名，压缩方式
                            .setMaxSelectNum(allowNum)// 最大图片选择数量 int
                            .forResult(SELECT_IMAGE_REQUEST);
                    break;
                case 1:
                    PictureSelector.create(SelectImageActivity2.this)
                            .openCamera(SelectMimeType.ofImage())
                            .setCameraInterceptListener(new MeOnCameraInterceptListener())
                            .setCompressEngine(new ImageFileCompressEngine())//压缩文件夹，压缩文件命名，压缩方式
                            .setOutputCameraImageFileName("camera"+System.currentTimeMillis() +".jpg")
                            .forResultActivity(TAKING_PHOTO_REQUEST);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + action_type);
            }


        }else if(v.getId()==R.id.tv_add){//按钮修改成保存
            finish();
        }else  if(v.getId()==R.id.iv_title_back){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private  void save(){
        Intent intent=new Intent();
        String current=JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(currentSelectList));
        intent.putExtra(PictureShared.IntentExtraName.SELECTIMAGE_DATA,current);
        String add=JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(getAddData()));
        intent.putExtra(PictureShared.IntentExtraName.SELECTIMAGE_ADD_DATA,add);
        String del=JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(getDeleteData()));
        intent.putExtra(PictureShared.IntentExtraName.SELECTIMAGE_DELETE_DATA,del);

        setResult(RESULT_OK, intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null!=data) {
            switch (requestCode) {
                case SELECT_IMAGE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    try {
                        currentSelectList.addAll(PictureSelector.obtainSelectorList(data));
                        selectImageAdapter.notifyDataSetChanged();
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                        SelectImageFragment2.mSelectFileResultCallBack.onCurrentSelectResult(JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(currentSelectList)),this.requestCode);
                        SelectImageFragment2.mSelectFileResultCallBack.onAddResult(JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(PictureSelector.obtainSelectorList(data))),this.requestCode);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                         break;
                case TAKING_PHOTO_REQUEST:
                    try {
                        currentSelectList.addAll(PictureSelector.obtainSelectorList(data));
                        selectImageAdapter.notifyDataSetChanged();
                        SelectImageFragment2.mSelectFileResultCallBack.onAddResult(JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(PictureSelector.obtainSelectorList(data))),this.requestCode);
                        SelectImageFragment2.mSelectFileResultCallBack.onCurrentSelectResult(JSONObject.toJSONString(ImageLocalMediaConversion.localMediaToSelectImage(currentSelectList)),this.requestCode);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                         break;
                default:
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


    /**
     * 作者：WangZhiQiang
     * 时间：2021/4/5
     * 邮箱：sos181@163.com
     * 描述：由于之前PictureSelector框架有问题 现在这种办法临时解决。
     */
    private void openCamera(){
        File file = null;
        try {
        file=File.createTempFile(TimeUtil.getnow_time_seconds(),".jpg",new File(FileUtils.SDPATH+PictureShared.FolderNameConfig.COMPRESSION));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
