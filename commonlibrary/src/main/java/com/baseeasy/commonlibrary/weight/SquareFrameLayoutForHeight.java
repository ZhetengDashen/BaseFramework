package com.baseeasy.commonlibrary.weight;
import android.content.Context;

import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

/**
 * Created by Admin on 2018/5/30 .FrameLayout宽高一致正方形----以高度为准
 */

public class SquareFrameLayoutForHeight extends FrameLayout {

    public SquareFrameLayoutForHeight(Context context) {
        super(context);
    }

    public SquareFrameLayoutForHeight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareFrameLayoutForHeight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
//        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
