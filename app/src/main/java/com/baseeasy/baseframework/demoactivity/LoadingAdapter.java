package com.baseeasy.baseframework.demoactivity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.baseeasy.baseframework.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/4/30
 * 邮箱：sos181@163.com
 * 描述：
 */
public class LoadingAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public LoadingAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
         helper.setText(R.id.tx,item);
        AVLoadingIndicatorView avLoadingIndicatorView=helper.getView(R.id.avl);
        avLoadingIndicatorView.setIndicator(item);
}
}
