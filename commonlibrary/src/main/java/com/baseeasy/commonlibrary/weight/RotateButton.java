package com.baseeasy.commonlibrary.weight;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class RotateButton extends Button {


    public RotateButton(Context context) {
        super(context);
    }

    public RotateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }

}