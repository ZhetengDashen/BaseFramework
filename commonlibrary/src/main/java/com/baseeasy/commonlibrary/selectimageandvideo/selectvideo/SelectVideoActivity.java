package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.svideo.common.utils.FastClickUtil;
import com.aliyun.svideo.sdk.external.struct.common.CropKey;
import com.aliyun.svideo.sdk.external.struct.common.VideoQuality;
import com.aliyun.svideo.sdk.external.struct.encoder.VideoCodecs;
import com.aliyun.svideo.sdk.external.struct.recorder.CameraType;
import com.aliyun.svideo.sdk.external.struct.recorder.FlashType;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.aliyun.svideo.snap.record.AliyunVideoRecorder;
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


import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SELECTIMAGE_REQUESTCODE;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SELECT_IMAGE_REQUEST;
import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SHOOTVIDEO;
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
    private ShotVideoConfig shotVideoConfig;
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

        try {
            String shootVideoConfigJson=getIntent().getStringExtra(PictureShared.IntentExtraName.SHOOTVIDEOCONFIG);
            if(StringUtils.isNotBlank(shootVideoConfigJson)){
               shotVideoConfig=JSON.parseObject(shootVideoConfigJson,ShotVideoConfig.class);
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
        if (v.getId() == R.id.tv_save ) {
            int allowNum=  maxVideoNum-selectVideoAdapter.getData().size();
            if(allowNum<=0){
                ToastUtil.showwarning(this,"只能添加"+maxVideoNum+"个视频");
                return;
            }
            startShootVideo(shotVideoConfig);


        }else if(v.getId()==R.id.tv_add){

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
    private void startShootVideo(ShotVideoConfig shotVideoConfig) {
        try {
         if(null==shotVideoConfig){
             shotVideoConfig=new ShotVideoConfig();

         }
            VideoQuality   videoQuality;
            switch (shotVideoConfig.getVideoQuality()){
                case ShotVideoConfig.ConfigParameter.PROGRESS_LOW:
                    videoQuality = VideoQuality.LD;
                    break;
                case ShotVideoConfig.ConfigParameter.PROGRESS_MEIDAN:
                    videoQuality = VideoQuality.SD;
                    break;
                case ShotVideoConfig.ConfigParameter.PROGRESS_HIGH:
                    videoQuality = VideoQuality.HD;
                    break;
                case ShotVideoConfig.ConfigParameter.PROGRESS_SUPER:
                    videoQuality = VideoQuality.SSD;
                    break;

                default:
                    videoQuality = VideoQuality.HD;

            }
            String videoPath=FileUtils.SDPATH+BaseConfig.FOLDER_PATH.VIDEO+"/";
           AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
                .setResolutionMode(shotVideoConfig.getEesolutionMode())//分辨率等级 360  480 540 720
                .setRatioMode(shotVideoConfig.eatioMode)//视频比例  3:4 1:1  16：9
                .setRecordMode(AliyunSnapVideoParam.RECORD_MODE_AUTO)//拍摄方式 自动、按下、 直接录制完
//                .setFilterList(effectDirs)//滤镜文件
//                .setBeautyLevel(80)//美颜等级
                .setBeautyStatus(false)//美容状态
                .setCameraType(CameraType.BACK)//前置摄像头
                .setFlashType(FlashType.ON)//闪光灯
                .setNeedClip(true)//分段录制
                .setMaxDuration(shotVideoConfig.getMaxTime()*1000)
                .setMinDuration(2000)//最小时长
                .setVideoQuality(videoQuality)//视频质量
//                .setGop(gop)//帧数
                .setVideoCodec(VideoCodecs.H264_HARDWARE)
                /**
                 * 裁剪参数
                 */
//            .setFrameRate(25)
//            .setCropMode(VideoDisplayMode.FILL)
                //显示分类SORT_MODE_VIDEO视频;SORT_MODE_PHOTO图片;SORT_MODE_MERGE图片和视频
                .setSortMode(AliyunSnapVideoParam.SORT_MODE_VIDEO)
                .build();

        if (!FastClickUtil.isFastClickActivity(AliyunVideoRecorder.class.getSimpleName())) {
            AliyunVideoRecorder.startRecordForResult(this, SHOOTVIDEO, recordParam,videoPath);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //  tring videoPath = FileUtils.SDPATH+PictureShared.FolderNameConfig.VIDEO + "/" + UUID.randomUUID().toString().replace("-", "") + ".mp4";
//            Intent intent = new Intent(SelectVideoActivity.this, RecorderActivity.class);
//            intent.putExtra("videoPath", videoPath);
//            startActivity(intent);
//            PlayVideoActiviy.setOnPickFinishListener(new PlayVideoActiviy.PickFinishListener() {
//                @Override
//                public void onPickFinish(String path) {
//                    EventBusUtils.post(new EventMessage<String>(EventConst.EVENT_CODE_OK, EventBusFlagImageOrVideo.ADD_VIDEO,path));
//                    currentSelectList.add(path);
//                    selectVideoAdapter.notifyDataSetChanged();
//                }
//            });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&& data != null){
            if (requestCode == SHOOTVIDEO) {
                int type = data.getIntExtra(AliyunVideoRecorder.RESULT_TYPE, 0);
               if (type == AliyunVideoRecorder.RESULT_TYPE_RECORD) {
                   Log.e("KK",data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH));
                    videoCallback(data.getStringExtra(AliyunVideoRecorder.OUTPUT_PATH));
                }
            }
        }

    }
    private void videoCallback(String path){
        EventBusUtils.post(new EventMessage<String>(EventConst.EVENT_CODE_OK, EventBusFlagImageOrVideo.ADD_VIDEO,path));
        currentSelectList.add(path);
        selectVideoAdapter.notifyDataSetChanged();
    }
}
