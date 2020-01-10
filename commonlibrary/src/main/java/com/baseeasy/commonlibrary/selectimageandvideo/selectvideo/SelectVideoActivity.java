package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.arouter.ARouterPath;
import com.baseeasy.commonlibrary.config.BaseConfig;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventConst;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;
import com.baseeasy.commonlibrary.selectimageandvideo.EventBusFlagImageOrVideo;
import com.baseeasy.commonlibrary.selectimageandvideo.GlideEngine;
import com.baseeasy.commonlibrary.selectimageandvideo.ImageLocalMediaConversion;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageActivity;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.magiclon.individuationtoast.ToastUtil;
import com.zyf.vc.ui.PlayVideoActiviy;
import com.zyf.vc.ui.RecorderActivity;
import com.zyf.vc.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SELECT_IMAGE_REQUEST;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.TAKING_PHOTO_REQUEST;

@Route(path = ARouterPath.CommonLibrary.COMMON_SELECTVIDEO_ACTIVITY)
public class SelectVideoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_title_back;
    private TextView tv_title_title;
    private TextView tv_save;
    private String pathList_json="";//展示的视频
    private List<String> currentSelectList=new ArrayList<>();
    private RecyclerView recyclerView;
    private SelectVideoAdapter selectVideoAdapter;
    private TextView tv_add;
    private int  maxVideoNum= PictureShared.MAX_VIDEO_NUM;//默认100
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video);
        initView();
        initData();
        FileUtils.createSDDir(PictureShared.FolderNameConfig.VIDEO);
        FileUtils.createNoMedia(PictureShared.FolderNameConfig.VIDEO);
    }



    private void initView() {
        recyclerView=findViewById(R.id.rv_image);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(this);
        tv_title_title = (TextView) findViewById(R.id.tv_title_title);
        tv_title_title.setText("添加视频");
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);

    }

    private void initData() {
        try {
            pathList_json= getIntent().getStringExtra(PictureShared.IntentExtraName.EXIST_IMAGES);
            if(null!=pathList_json&&!pathList_json.equals("")&&!pathList_json.equals("null")){
                currentSelectList= JSONArray.parseArray(pathList_json, String.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        maxVideoNum=getIntent().getIntExtra(PictureShared.IntentExtraName.MAXVIDEONUM,PictureShared.MAX_VIDEO_NUM);

        selectVideoAdapter=new SelectVideoAdapter(currentSelectList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(selectVideoAdapter);
        selectVideoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.iv_delete){
                    EventBusUtils.post(new EventMessage(EventConst.EVENT_CODE_OK, EventBusFlagImageOrVideo.DELETE_VIDEO,selectVideoAdapter.getData().get(position)));
                    selectVideoAdapter.remove(position);

                }else  if(view.getId()==R.id.iv_image){
                 VideoPlayUtils.play(SelectVideoActivity.this,selectVideoAdapter.getData().get(position));


                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_add) {
            int allowNum=  maxVideoNum-selectVideoAdapter.getData().size();
            if(allowNum<=0){
                ToastUtil.showwarning(this,"只能添加"+maxVideoNum+"个视频");
                return;
            }
            startShootVideo();


        }else if(v.getId()==R.id.tv_save){

            Intent intent=new Intent();
            intent.putExtra(PictureShared.IntentExtraName.SELECTVIDEO_DATA, JSONObject.toJSONString(currentSelectList));
            setResult(RESULT_OK, intent);
            finish();
        }else  if(v.getId()==R.id.iv_title_back){
            finish();
        }
    }

    /**
     * 开启拍摄视频
     * **/
    private void startShootVideo() {
        try {

            String videoPath = FileUtils.SDPATH+PictureShared.FolderNameConfig.VIDEO + "/" + UUID.randomUUID().toString().replace("-", "") + ".mp4";
            Intent intent = new Intent(SelectVideoActivity.this, RecorderActivity.class);
            intent.putExtra("videoPath", videoPath);
            startActivity(intent);
            PlayVideoActiviy.setOnPickFinishListener(new PlayVideoActiviy.PickFinishListener() {
                @Override
                public void onPickFinish(String path) {
                    EventBusUtils.post(new EventMessage<String>(EventConst.EVENT_CODE_OK, EventBusFlagImageOrVideo.ADD_VIDEO,path));
                    currentSelectList.add(path);
                    selectVideoAdapter.notifyDataSetChanged();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
          switch (requestCode){



          }


        }

    }
}
