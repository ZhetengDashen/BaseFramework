package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import com.baseeasy.commonlibrary.R;


/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：展示不同样式的模板遮罩层
 */

public class MaskView extends View {

    public MaskView(Context context) {
        super(context);
    }

    public MaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static final int MASK_NONE = 0;

    public static final int MASK_TYPE_BANKCARD = 1;

    public static final int MASK_TYPE_IDCARD_FRONT = 2;

    public static final int MASK_TYPE_IDCARD_BACK = 3;

    public void setTipString(String tipString) {
        this.tipString = tipString;
    }

    public void setTipStringAndUpdate(String tipString) {
        this.tipString = tipString;
        invalidate();
    }

    private String tipString;

    private int maskType;

    public int getMaskType() {
        return maskType;
    }

    private Paint eraser;

    private Rect maskFrame;

    private int width;
    private int height;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                width = getWidth();
                height = getHeight();
                int hc = height / 2;
                int wPad = 50;
                float whRatio = 0.64f;
                int rw = width - wPad * 2;
                int rh = (int) (whRatio * rw);
                maskFrame = new Rect(wPad,
                        (int) (hc - (0.5 * rh)),  width - wPad, (int) (hc + (0.5 * rh)));
                return true;
            }
        });
    }

    public Rect getMaskFrame() {
        return maskFrame;
    }

    public void setMaskType(int maskType) {
        this.maskType = maskType;
    }

    private Path fillRectRound(float left, float top, float right, float bottom, float rx, float ry, boolean
            conformToOriginalPost) {

        Path path = new Path();
        if (rx < 0) {
            rx = 0;
        }
        if (ry < 0) {
            ry = 0;
        }
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) {
            rx = width / 2;
        }
        if (ry > height / 2) {
            ry = height / 2;
        }
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        path.rQuadTo(0, -ry, -rx, -ry);
        path.rLineTo(-widthMinusCorners, 0);
        path.rQuadTo(-rx, 0, -rx, ry);
        path.rLineTo(0, heightMinusCorners);
        path.rQuadTo(0, ry, rx, ry);
        path.rLineTo(widthMinusCorners, 0);
        path.rQuadTo(rx, 0, rx, -ry);

        path.rLineTo(0, -heightMinusCorners);
        path.close();
        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        switch (maskType) {
            case MASK_TYPE_BANKCARD:
                drawMaskBankcard(canvas);
                drawTip(canvas);
                break;
            case MASK_TYPE_IDCARD_FRONT:
                drawTip(canvas);
                drawMaskIDcard(canvas, true);
                break;
            case MASK_TYPE_IDCARD_BACK:
                drawTip(canvas);
                drawMaskIDcard(canvas, false);
                break;
            default:
                drawMaskBackground(canvas);
        }

        super.onDraw(canvas);
    }

    /**
     * 绘制背景
     *
     */
    private void drawMaskBackground(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        Paint filled = new Paint();
        filled.setStyle(Paint.Style.FILL);
        filled.setColor(Color.BLACK);
        filled.setAlpha(50);
        canvas.drawRect(new Rect(0, 0, w, h), filled);
    }

    /**
     * 绘制提示信息
     *
     * @param canvas
     */
    private void drawTip(Canvas canvas) {
        if (tipString == null) {
            return;
        }
        Paint pt = new Paint();
        float w = getWidth();
        float bt = maskFrame.bottom;
        pt.setColor(Color.WHITE);
        pt.setTextSize(35);
        pt.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(tipString,  w / 2 , bt + 100, pt);
    }

    /**
     * 绘制银行卡蒙版
     *
     * @param canvas
     */
    private void drawMaskBankcard(Canvas canvas) {

        Path path = fillRectRound(maskFrame.left, maskFrame.top, maskFrame.right,
                maskFrame.bottom, 25, 25, false);

        Paint stroke = new Paint();

        stroke.setStyle(Paint.Style.STROKE);
        stroke.setColor(Color.WHITE);
        stroke.setStrokeWidth(2);

        canvas.drawPath(path, stroke);
        canvas.drawPath(path, eraser);
    }

    /**
     * 绘制身份证模板
     * @param canvas
     * @param isFront 是否是正面
     */
    private void drawMaskIDcard(Canvas canvas, boolean isFront) {

        Path path = fillRectRound(maskFrame.left, maskFrame.top, maskFrame.right,
                maskFrame.bottom, 25, 25, false);

        int offsetAvatarLeft = (int) (maskFrame.left + maskFrame.width() * 0.58f);
        int offsetAvatarTop = (int) (maskFrame.top + maskFrame.height() / 7.0f);
        int offsetAvatarRight = (int) (maskFrame.left + maskFrame.width() * 0.94f);
        int offsetAvatarBottom = (int) (maskFrame.top + maskFrame.height() * 0.74f);

        int offsetEmblemLeft = (int) (maskFrame.left + maskFrame.width() * 0.05f);
        int offsetEmblemTop = (int) (maskFrame.top + maskFrame.height() * 0.07f);
        int offsetEmblemRight = (int) (maskFrame.left + maskFrame.width() * 0.25f);
        int offsetEmblemBottom = (int) (maskFrame.top + maskFrame.height() * 0.39f);

        Paint stroke = new Paint();

        stroke.setStyle(Paint.Style.STROKE);
        stroke.setColor(Color.WHITE);
        stroke.setStrokeWidth(2);

        Paint bitmapPaint = new Paint();

        canvas.drawPath(path, stroke);
        canvas.drawPath(path, eraser);
        if (isFront) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.basecamera_id_card_locator_front);
            canvas.drawBitmap(bitmap,
                    null,
                    new Rect(offsetAvatarLeft, offsetAvatarTop, offsetAvatarRight, offsetAvatarBottom),
                    bitmapPaint);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.basecamera_id_card_locator_back);
            canvas.drawBitmap(bitmap,
                    null,
                    new Rect(offsetEmblemLeft, offsetEmblemTop, offsetEmblemRight, offsetEmblemBottom),
                    bitmapPaint);
        }

    }
}
