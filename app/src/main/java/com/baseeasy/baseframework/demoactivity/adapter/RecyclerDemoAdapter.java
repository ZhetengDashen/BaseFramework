package com.baseeasy.baseframework.demoactivity.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.baseeasy.baseframework.R;
import com.baseeasy.baseframework.demoactivity.entity.RVDemoEntity;
import com.baseeasy.commonlibrary.imageloader.DisplayOption;
import com.baseeasy.commonlibrary.imageloader.ImageLoaderFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/5/5
 * 邮箱：sos181@163.com
 * 描述：
 */
public class RecyclerDemoAdapter  extends BaseQuickAdapter<RVDemoEntity, BaseViewHolder> {
    public RecyclerDemoAdapter(int layoutResId, @Nullable List<RVDemoEntity> data) {
        super(layoutResId, data);
        this.openLoadAnimation();
    }

    @Override
    protected void convert(BaseViewHolder helper, RVDemoEntity item) {
//
        helper.setText(R.id.rv_item_tx1,item.getTitle());
        helper.setText(R.id.rv_item_tx2,item.getMsg());
        DisplayOption displayOption=new DisplayOption.Builder()
                .setLoadErrorResId(R.drawable.ic_launcher_background)//设置加载错误图片
                .setPlaceHolderResId(R.drawable.ic_launcher_foreground)//设置占位图
                .create();
        ImageLoaderFactory.getInstance().displayImage((ImageView)helper.getView(R.id.rv_item_image),item.getImage(),displayOption);
        helper.addOnClickListener(R.id.rv_item_bt);//添加子View的点击事件

    }
}
