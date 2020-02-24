package com.baseeasy.commonlibrary.weight.dialog.multipledialog;

import android.widget.CheckBox;

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
public class SelectMultipleListAdapter<T extends SelectMultipleListDialog.MuCheck> extends BaseQuickAdapter<T , BaseViewHolder> {
    public SelectMultipleListAdapter(@Nullable List<T> data) {
        super(R.layout.item_select_check, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, T  item) {
        CheckBox checkbox_user = helper.getView(R.id.item_checkbox);
        checkbox_user.setText(item.getMuCheckName());
        helper.addOnClickListener(R.id.item_checkbox);
        checkbox_user.setChecked(item.getMuCheck());
    }
}
