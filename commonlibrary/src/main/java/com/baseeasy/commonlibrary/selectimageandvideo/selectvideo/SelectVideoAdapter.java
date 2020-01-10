package com.baseeasy.commonlibrary.selectimageandvideo.selectvideo;


import androidx.annotation.Nullable;

import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.imageloader.ImageLoaderFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/7/9
 * 邮箱：sos181@163.com
 * 描述：
 */
public class SelectVideoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SelectVideoAdapter(@Nullable List<String> data) {
        super(R.layout.item_select_image,data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageLoaderFactory.getInstance().displayImage(helper.getView(R.id.iv_image),item);
        helper.addOnClickListener(R.id.iv_delete);
        helper.addOnClickListener(R.id.iv_image);
    }
}
