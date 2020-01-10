package com.baseeasy.commonlibrary.weight;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.baseeasy.commonlibrary.R;


/**
 * Created by Admin on 2018/9/28 .宽高相对比例
 */

public class SquareFrameLayoutRel extends FrameLayout {
    private float reletiveWidth = 1.0f;
    private float reletiveHeight = 1.0f;

    public SquareFrameLayoutRel(Context context) {
        super(context, null);
    }

    public SquareFrameLayoutRel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareFrameLayoutRel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.squareframelayout, defStyleAttr, 0);
        this.initByAttributes(attributes);
        attributes.recycle();
    }

    protected void initByAttributes(TypedArray attributes) {
        this.reletiveWidth = attributes.getFloat(R.styleable.squareframelayout_reltiveWidth, 1.0f);
        this.reletiveHeight = attributes.getFloat(R.styleable.squareframelayout_reltiveHeight, 1.0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        // 高度和宽度一样
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize * reletiveHeight / reletiveWidth), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
