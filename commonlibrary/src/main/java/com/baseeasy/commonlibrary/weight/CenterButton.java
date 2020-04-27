package com.baseeasy.commonlibrary.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 作者：WangZhiQiang
 * 时间：2020/4/27
 * 邮箱：sos181@163.com
 * 描述：文字图标居中
 */

public class CenterButton extends AppCompatButton {
  public CenterButton(Context context) {
    super(context);
  }
  public CenterButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  public CenterButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
  @Override
  protected void onDraw(Canvas canvas) {
    Drawable[] drawables = getCompoundDrawables();
    if (null != drawables) {
      Drawable drawableLeft = drawables[0];
      Drawable drawableRight = drawables[2];
      float textWidth = getPaint().measureText(getText().toString());
      if (null != drawableLeft) {
        setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        float contentWidth = textWidth + getCompoundDrawablePadding() + drawableLeft.getIntrinsicWidth();
        if (getWidth() - contentWidth > 0) {
          canvas.translate((getWidth() - contentWidth - getPaddingRight() - getPaddingLeft()) / 2, 0);
        }
      }
      if (null != drawableRight) {
        setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        float contentWidth = textWidth + getCompoundDrawablePadding() + drawableRight.getIntrinsicWidth();
        if (getWidth() - contentWidth > 0) {
          canvas.translate(-(getWidth() - contentWidth - getPaddingRight() - getPaddingLeft()) / 2, 0);
        }
      }
      if (null == drawableRight && null == drawableLeft) {
        setGravity(Gravity.CENTER);
      }
    }
    super.onDraw(canvas);
  }
}