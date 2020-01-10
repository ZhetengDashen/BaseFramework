package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baseeasy.commonlibrary.eventbus.EventBusUtils;
import com.baseeasy.commonlibrary.eventbus.EventMessage;
import com.baseeasy.commonlibrary.selectimageandvideo.EventBusFlagImageOrVideo;
import com.baseeasy.commonlibrary.selectimageandvideo.PictureShared;
import com.baseeasy.commonlibrary.selectimageandvideo.selectimage.SelectImageActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.baseeasy.commonlibrary.selectimageandvideo.PictureShared.SHOOTVIDEO;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public class ShootVideoFragment extends Fragment {

    private ShootVideoCallBack shootVideoCallBack;
    private List<String> addData=new ArrayList<>();
    private List<String> deleteData=new ArrayList<>();

    public ShootVideoFragment() {
        // Required empty public constructor

    }


    public void startShootVideo(int maxNum) {
        startShootVideo(new ArrayList<String>(),maxNum);
    }
    public void startShootVideo(List<String>  pathList,int maxNum) {
        Intent intent = new Intent(getActivity(), SelectVideoActivity.class);
        intent.putExtra(PictureShared.IntentExtraName.MAXPHOTONUM,maxNum);
        intent.putExtra(PictureShared.IntentExtraName.EXIST_IMAGES, JSONObject.toJSONString(pathList));
        this.startActivityForResult(intent, PictureShared.SHOOTVIDEO);
    }


    // 在主线程处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMessageOnMainThread(EventMessage event) {
        switch (event.getFlag()){
            case EventBusFlagImageOrVideo.ADD_VIDEO:
                if( StringUtils.isNotBlank((String)event.getEvent())){
//                    shootVideoCallBack.onAddVideoListener((String)event.getEvent());
                    addData.add((String) event.getEvent());
                }

                break;
            case EventBusFlagImageOrVideo.DELETE_VIDEO:
                if(StringUtils.isNotBlank((String)event.getEvent())) {
                    deleteData.add((String) event.getEvent());
//                    shootVideoCallBack.onDeleteVideoListener();
                }
                break;
             default:
                 break;
        }

    }



    public void setShootVideoCallBack(ShootVideoCallBack shootVideoCallBack) {
        this.shootVideoCallBack = shootVideoCallBack;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == Activity.RESULT_OK && data != null) {
                if(requestCode == SHOOTVIDEO&&null!=shootVideoCallBack ) {
                    String videodata=data.getStringExtra(PictureShared.IntentExtraName.SELECTVIDEO_DATA);
                    List<String> paths= new ArrayList<>();
                    paths.addAll(JSONObject.parseArray(videodata,String.class));
                    shootVideoCallBack.onShootVideo(paths);
                }
        }
        List<String> retain=new ArrayList<>();
        retain.addAll(addData);
        retain.retainAll(deleteData);
        addData.removeAll(retain);
        deleteData.removeAll(retain);
        shootVideoCallBack.onAddVideoList(addData);
        shootVideoCallBack.onDeleteVideoList(deleteData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBusUtils.isRegister(this)) {
            EventBusUtils.register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBusUtils.isRegister(this)) {
            EventBusUtils.unregister(this);
        }
    }
}
