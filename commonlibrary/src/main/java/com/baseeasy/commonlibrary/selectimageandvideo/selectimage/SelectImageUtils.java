package com.baseeasy.commonlibrary.selectimageandvideo.selectimage;


import android.content.Intent;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSONObject;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/9/26
 * 邮箱：sos181@163.com
 * 描述：
 */
public class SelectImageUtils {
    public static final String FRAGMENT_TAG = "SelectImageUtils";

    public volatile static SelectImageUtils selectImageUtils;

    public static SelectImageUtils getInstance() {
        if (selectImageUtils == null) {
            synchronized (SelectImageUtils.class) {
                if (selectImageUtils == null) {
                    selectImageUtils = new SelectImageUtils();
                }
            }
        }
        return selectImageUtils;
    }

    /**
     * 选择位置工具类
     *
     * @param activity
     * @return PermissionFragment
     */
    private SelectImageFragment getSelectImageFragment(FragmentActivity activity, SelectImageCallBack selectLocationCallBack) {
        SelectImageFragment selectLocationFragment = (SelectImageFragment) activity.getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (selectLocationFragment == null) {
            selectLocationFragment = new SelectImageFragment();
            FragmentManager manager = activity.getSupportFragmentManager();
            manager.beginTransaction()
                    .add(selectLocationFragment, FRAGMENT_TAG)
                    .commitAllowingStateLoss();
            manager.executePendingTransactions();
        }
        selectLocationFragment.setSelectLocationCallBack(selectLocationCallBack);
        return selectLocationFragment;
    }
    public void startSelectImage(FragmentActivity activity, SelectImageCallBack selectLocationCallBack){
        getSelectImageFragment(activity, selectLocationCallBack).startSelectImage();
    }
    public void startSelectImage(FragmentActivity activity, SelectImageCallBack selectLocationCallBack, List<SelectImageBean> selectImageBeans){

        getSelectImageFragment(activity, selectLocationCallBack).startSelectImage( JSONObject.toJSONString(selectImageBeans));
    }
    public void startSelectImage(FragmentActivity activity,String eventBusFlag){
        Intent intent = new Intent(activity, SelectImageActivity.class);
        intent.putExtra("eventBusFlag",eventBusFlag);
        activity.startActivity(intent);
    }
    public void startSelectImage(FragmentActivity activity,String eventBusFlag,List<SelectImageBean> selectImageBeans){
        Intent intent = new Intent(activity, SelectImageActivity.class);
        intent.putExtra("eventBusFlag",eventBusFlag);
        intent.putExtra("data", JSONObject.toJSONString(selectImageBeans));
        activity.startActivity(intent);
    }
}
