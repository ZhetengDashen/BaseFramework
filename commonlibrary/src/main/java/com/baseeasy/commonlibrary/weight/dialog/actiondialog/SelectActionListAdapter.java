package com.baseeasy.commonlibrary.weight.dialog.actiondialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baseeasy.commonlibrary.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/11/21
 * 邮箱：sos181@163.com
 * 描述：
 */
public class SelectActionListAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public SelectActionListAdapter( @Nullable List<T> data) {
        super(R.layout.item_action_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, T item) {
          helper.setText(R.id.tv_content,item.toString()).addOnClickListener(R.id.tv_content);
           if(helper.getAbsoluteAdapterPosition()==0){
               helper.setBackgroundRes(R.id.tv_content,R.drawable.select_action_topitembg);
           }else {
               helper.setBackgroundRes(R.id.tv_content,R.drawable.select_action_itembg);
           }
    }
}
