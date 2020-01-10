package com.baseeasy.commonlibrary.weight;

import android.content.Context;

import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

/**
 * Created by Admin on 2018/5/30 .高是宽的一半
 */

public class SquareHalfFrameLayout extends FrameLayout {

    public SquareHalfFrameLayout(Context context) {
        super(context);
    }

    public SquareHalfFrameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareHalfFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        // 高度和宽度一样
        heightMeasureSpec  = MeasureSpec.makeMeasureSpec(childWidthSize/2, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
