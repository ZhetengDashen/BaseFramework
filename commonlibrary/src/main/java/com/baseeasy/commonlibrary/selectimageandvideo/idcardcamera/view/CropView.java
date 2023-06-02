package com.baseeasy.commonlibrary.selectimageandvideo.idcardcamera.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 作者：WangZhiQiang
 * 时间：2023/6/1
 * 邮箱：sos181@163.com
 * 描述：裁剪框类
 */

public class CropView extends View {
    private Rect regionRect;
    private Rect regionRectDefault = new Rect();

    static final int ANCHOR_LEFT_TOP = 1;
    static final int ANCHOR_RIGHT_TOP = 3;
    static final int ANCHOR_LEFT_BOTTOM = 7;
    static final int ANCHOR_RIGHT_BOTTOM = 9;

    static final int ANCHOR_CENTER = 10;
    static final int ANCHOR_RELEASE = -1;

    private int ox;
    private int oy;

    private int lmtTop;
    private int lmtBottom;
    private int lmtLeft;
    private int lmtRight;

    private int minW = 300;
    private int minH = 300;

    private int rectAnchorFlag = ANCHOR_RELEASE;

    public CropView(Context context) {
        super(context);
    }

    public CropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取参见的矩形范围
     * @return
     */
    public Rect getCropRect() {
        return regionRect;
    }

    /**
     * 初始设置裁剪框（保存为默认的裁剪框）
     * @return
     */
    public void setCropRect(Rect region) {
        regionRect = region;
        regionRectDefault.set(region);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    /**
     * 设置裁剪框约束范围
     * 弱约束与初始裁剪框冲突，则限制裁剪框
     *
     * @param left
     * @param right
     * @param top
     * @param bottom
     */
    public void setBound(int left, int top, int right, int bottom) {
        lmtTop = top;
        lmtBottom = bottom;
        lmtLeft = left;
        lmtRight = right;
        if (regionRect != null && (regionRect.top < lmtTop || regionRect.bottom > lmtBottom
                || regionRect.left < lmtLeft || regionRect.right > lmtRight)) {
            regionRect.set(regionRectDefault);

            // 判断是否款选区域大于图片区域
            regionRect.set(regionRectDefault.left < left ? left : regionRect.left,
                    regionRectDefault.top < top ? top : regionRect.top,
                    regionRectDefault.right > right ? right : regionRect.right,
                    regionRectDefault.bottom > bottom ? bottom : regionRect.bottom);
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int offsetX;
        int offsetY;
        int x;
        int y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ox = (int) event.getX();
                oy = (int) event.getY();
                rectAnchorFlag = getAnchor(ox, oy);
                if (rectAnchorFlag != ANCHOR_RELEASE) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                switch (rectAnchorFlag)  {
                    case ANCHOR_CENTER:
                        x = (int) event.getX();
                        y = (int) event.getY();
                        offsetX = x - ox;
                        offsetY = y - oy;
                        ox = x;
                        oy = y;
                        regionRect.set(tileInBound(offsetX, offsetY));
                        invalidate();
                        break;
                    case ANCHOR_LEFT_TOP:
                        x = (int) event.getX();
                        y = (int) event.getY();
                        regionRect.set(tileInBound(x, y, ANCHOR_LEFT_TOP));
                        invalidate();
                        break;
                    case ANCHOR_RIGHT_TOP:
                        x = (int) event.getX();
                        y = (int) event.getY();
                        oy = y;
                        regionRect.set(tileInBound(x, y, ANCHOR_RIGHT_TOP));
                        invalidate();
                        break;
                    case ANCHOR_LEFT_BOTTOM:
                        x = (int) event.getX();
                        y = (int) event.getY();
                        regionRect.set(tileInBound(x, y, ANCHOR_LEFT_BOTTOM));
                        invalidate();
                        break;
                    case ANCHOR_RIGHT_BOTTOM:
                        x = (int) event.getX();
                        y = (int) event.getY();
                        regionRect.set(tileInBound(x, y, ANCHOR_RIGHT_BOTTOM));
                        invalidate();
                        break;
                    default:
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                rectAnchorFlag = ANCHOR_RELEASE;
                break;
            default:
                break;
        }

        return false;
    }

    /**
     * 拖动四个角，限制最终区域
     *
     * @param x x坐标
     * @param y y坐标
     * @param type 类型，左上，右上，左下，右下
     * @return
     */
    private Rect tileInBound(int x, int y, int type) {
        int leftT = regionRect.left;
        int topT = regionRect.top;
        int rightT = regionRect.right;
        int bottomT = regionRect.bottom;
        int w = getWidth();
        int h = getHeight();
        switch (type) {
            case ANCHOR_LEFT_TOP:
                leftT = x < lmtLeft ? lmtLeft : x;
                topT = y < lmtTop ? lmtTop : y;
                if (rightT - leftT < minW) {
                    leftT = rightT - minW;
                }
                if (bottomT - topT < minH) {
                    topT = bottomT - minH;
                }
                break;
            case ANCHOR_RIGHT_TOP:
                rightT = x > lmtRight ? lmtRight : x;
                topT = y < lmtTop ? lmtTop : y;
                if (rightT - leftT < minW) {
                    rightT = leftT + minW;
                }
                if (bottomT - topT < minH) {
                    topT = bottomT - minH;
                }
                break;
            case ANCHOR_LEFT_BOTTOM:
                leftT = x < lmtLeft ? lmtLeft : x;
                bottomT = y > lmtBottom ? lmtBottom : y;
                if (rightT - leftT < minW) {
                    leftT = rightT - minW;
                }
                if (bottomT - topT < minH) {
                    bottomT =  topT + minH;
                }
                break;
            case ANCHOR_RIGHT_BOTTOM:
                rightT = x > lmtRight ? lmtRight : x;
                bottomT = y > lmtBottom ? lmtBottom : y;
                if (rightT - leftT < minW) {
                    rightT = leftT + minW;
                }
                if (bottomT - topT < minH) {
                    bottomT =  topT + minH;
                }
                break;
            default:
                break;

        }
        return new Rect(leftT, topT, rightT, bottomT);
    }

    /**
     * 拖动移动时判断是否超出边界
     *
     * @return
     */
    private Rect tileInBound(int offsetX, int offsetY) {
        int left;
        int top;
        int right;
        int bottom;
        int w = regionRect.right - regionRect.left;
        int h = regionRect.bottom - regionRect.top;
        top = regionRect.top + offsetY;
        bottom = regionRect.bottom + offsetY;
        left = regionRect.left + offsetX;
        right = regionRect.right + offsetX;
        if (regionRect.left + offsetX < lmtLeft) {
            left = lmtLeft;
            right = lmtLeft + w;
        }
        if (regionRect.right + offsetX > lmtRight) {
            right = lmtRight;
            left = lmtRight - w;
        }
        if (regionRect.top + offsetY < lmtTop) {
            top = lmtTop;
            bottom = lmtTop + h;
        }
        if (regionRect.bottom + offsetY > lmtBottom) {
            bottom = lmtBottom;
            top = bottom - h;
        }
        return new Rect(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRectFrame(canvas);
        super.onDraw(canvas);
    }

    /**
     * 绘制裁剪框框
     * @param canvas
     */
    private void drawRectFrame(Canvas canvas) {
        Paint filled = new Paint();
        filled.setStyle(Paint.Style.FILL);
        filled.setColor(Color.BLACK);
        filled.setAlpha(70);
        Paint paint = new Paint();
        int sw = 2;
        int offset = 5;
        int tw = 50;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(sw);

        // 绘制可显示区域
        canvas.drawRect(new Rect(0, 0,
                getWidth(), getHeight()), filled);

        // 去掉除可选区域
        canvas.clipRect(new Rect(regionRect.left, regionRect.top,
                regionRect.right, regionRect.bottom));

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // 绘制框框
        canvas.drawRect(new Rect(regionRect.left + offset, regionRect.top + offset,
                regionRect.right - offset, regionRect.bottom - offset), paint);

        Point p1 = new Point(regionRect.left + offset, regionRect.top + offset);
        Point p2 = new Point(regionRect.right - offset, regionRect.top + offset);
        Point p3 = new Point(regionRect.left + offset, regionRect.bottom - offset);
        Point p4 = new Point(regionRect.right - offset, regionRect.bottom - offset);

        // 绘制四个角
        Paint paintCorner = new Paint();
        paintCorner.setStyle(Paint.Style.STROKE);
        paintCorner.setColor(Color.WHITE);
        paintCorner.setStrokeWidth(10);

        Path path = new Path();

        path.moveTo(p1.x, p1.y  + tw);
        path.lineTo(p1.x, p1.y);
        path.lineTo(p1.x + tw, p1.y);

        path.moveTo(p2.x - tw, p2.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p2.x, p2.y + tw);

        path.moveTo(p3.x, p3.y  - tw);
        path.lineTo(p3.x, p3.y);
        path.lineTo(p3.x  + tw, p3.y);

        path.moveTo(p4.x, p4.y - tw);
        path.lineTo(p4.x, p4.y);
        path.lineTo(p4.x - tw, p4.y );
        canvas.drawPath(path, paintCorner);
    }

    /**
     * 1   3
     *
     * 7   9
     * 判断拖动的锚点
     * @param x
     * @param y
     * @return
     */
    private int getAnchor(int x, int y) {
        Rect rectTemp = new Rect();
        int of = 100;

        // 左上锚点
        rectTemp.set(regionRect.left - of, regionRect.top - of,
                regionRect.left + of, regionRect.top + of);
        if (rectTemp.contains(x, y)) {
            return ANCHOR_LEFT_TOP;
        }

        // 右上锚点
        rectTemp.set(regionRect.right - of, regionRect.top - of,
                regionRect.right + of, regionRect.top + of);
        if (rectTemp.contains(x, y)) {
            return ANCHOR_RIGHT_TOP;
        }

        // 左下锚点
        rectTemp.set(regionRect.left - of, regionRect.bottom - of,
                regionRect.left + of, regionRect.bottom + of);
        if (rectTemp.contains(x, y)) {
            return ANCHOR_LEFT_BOTTOM;
        }

        // 右下锚点
        rectTemp.set(regionRect.right - of, regionRect.bottom - of,
                regionRect.right + of, regionRect.bottom + of);
        if (rectTemp.contains(x, y)) {
            return ANCHOR_RIGHT_BOTTOM;
        }

        if (regionRect.contains(x, y)) {
            return ANCHOR_CENTER;
        }

        return ANCHOR_RELEASE;
    }
}
