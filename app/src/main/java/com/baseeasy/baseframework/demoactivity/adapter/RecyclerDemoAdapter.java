package com.baseeasy.baseframework.demoactivity.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.baseeasy.baseframework.R;
import com.baseeasy.baseframework.demoactivity.entity.RVDemoEntity;
import com.baseeasy.commonlibrary.imageloader.DisplayOption;
import com.baseeasy.commonlibrary.imageloader.ImageLoaderFactory;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 作者：WangZhiQiang
 * 时间：2019/5/5
 * 邮箱：sos181@163.com
 * 描述：
 */
public class RecyclerDemoAdapter  extends BaseMultiItemQuickAdapter<RVDemoEntity, BaseViewHolder> {
    //如果不做多布局或者分组 直接继承BaseQuickAdapter即可
    public RecyclerDemoAdapter( @Nullable List<RVDemoEntity> data) {
        super(data);

        //添加布局类型
        this.addItemType(0,  R.layout.item_rv_type1);
        this.addItemType(1,  R.layout.item_rv_type2);

    }

    @Override
    protected void convert(BaseViewHolder helper, RVDemoEntity item) {
        //若需要实现多布局根据不同的布局类型赋值 。  注：对应的实体类必须实现MultiItemEntity接口
        switch (helper.getItemViewType()){
            case 0:
                helper.setText(R.id.rv_item_tx1,item.getTitle());
                helper.setText(R.id.rv_item_tx2,item.getMsg());
                DisplayOption displayOption=new DisplayOption.Builder()
                        .setLoadErrorResId(R.drawable.ic_launcher_background)//设置加载错误图片
                        .setPlaceHolderResId(R.drawable.ic_launcher_foreground)//设置占位图
                        .create();
                ImageLoaderFactory.getInstance().displayImage((ImageView)helper.getView(R.id.rv_item_image),item.getImage(),displayOption);
                helper.addOnClickListener(R.id.rv_item_bt);//添加子View的点击事件
                break;
            case 1:
                helper.setText(R.id.rv_item_tx1,item.getTitle());
                helper.setText(R.id.rv_item_tx2,item.getMsg());
                ImageLoaderFactory.getInstance().displayImage((ImageView)helper.getView(R.id.rv_item_image),item.getImage());
                helper.addOnClickListener(R.id.rv_item_bt);//添加子View的点击事件
                break;

        }


    }
}
