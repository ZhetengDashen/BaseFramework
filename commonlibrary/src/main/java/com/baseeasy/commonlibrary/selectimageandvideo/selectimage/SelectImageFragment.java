package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONArray;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public class SelectImageFragment extends Fragment {
    private static final int REQUESTCODE = 1001;
    private SelectImageCallBack selectImageCallBack;


    public SelectImageFragment() {
        // Required empty public constructor
    }



    public void startSelectImage() {
        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        this.startActivityForResult(intent, REQUESTCODE);
    }
    public void startSelectImage(String select) {
        Intent intent = new Intent(getActivity(), SelectImageActivity.class);
        intent.putExtra("data",select);
        this.startActivityForResult(intent, REQUESTCODE);
    }

    public void setSelectLocationCallBack(SelectImageCallBack selectImageCallBack) {
        this.selectImageCallBack = selectImageCallBack;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("KK","kk1");
        if (requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK && data != null) {
            Log.e("KK","kk2");
            String localMediaList_json="";
            List<SelectImageBean> selectImageBeans=new ArrayList<>();
            try{
                  localMediaList_json= data.getStringExtra("localMediaList");
                  if(null==localMediaList_json){
                      selectImageCallBack.onImageSelected(selectImageBeans);
                      return;
                  }
            }catch (Exception e){
                e.printStackTrace();
                return;
            }

            if (selectImageCallBack != null&&(!localMediaList_json.equals(""))) {
                selectImageBeans= JSONArray.parseArray(localMediaList_json, SelectImageBean.class);
            }
            selectImageCallBack.onImageSelected(selectImageBeans);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
