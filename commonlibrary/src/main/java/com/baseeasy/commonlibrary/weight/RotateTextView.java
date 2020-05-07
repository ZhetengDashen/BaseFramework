package com.baseeasy.commonlibrary.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;


public class RotateTextView extends AppCompatTextView {

    final boolean topDown;


    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final int gravity = getGravity();
        if(Gravity.isVertical(gravity) && (gravity& Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
            setGravity((gravity&Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.TOP);
            topDown = false;
        }else {
            topDown = true;
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }


    @Override
    protected boolean setFrame(int l, int t, int r, int b){
        return super.setFrame(l, t, l+(b-t), t+(r-l));
    }


    @Override
    public void draw(Canvas canvas){
        if(topDown){
            canvas.translate(getHeight(), 0);
            canvas.rotate(90);
        }else {
            canvas.translate(0, getWidth());
            canvas.rotate(-90);
        }
        if(Build.VERSION.SDK_INT >= 26){
            canvas.clipRect(0, 0, getWidth(), getHeight());
        }else {
            canvas.clipRect(0, 0, getWidth(), getHeight(), Region.Op.UNION);
        }
        super.draw(canvas);
    }




}