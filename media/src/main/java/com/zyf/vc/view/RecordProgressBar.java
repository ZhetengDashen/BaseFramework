package com.zyf.vc.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.zyf.vc.R;


/**
 * 录制进度条
 *
 * @author zhangyf
 */
public class RecordProgressBar extends View {

    private static final long INVALIDATE_DELAY = 10;

    // 准备/停止状态
    public static final int STATE_PREPARE = 0;
    // 运行状态
    public static final int STATE_RUNNING = 1;
    // 取消状态
    public static final int STATE_CANCEL = 2;

    // 默认运行时间
    private static final int DEFAULT_RUNNING_TIME = 10;
    // 默认背景颜色
    private static final int DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;
    // 默认运行中颜色
    private static final int DEFAULT_RUNNING_COLOR = Color.BLUE;
    // 默认取消状态颜色
    private static final int DEFAULT_CANCEL_COLOR = Color.RED;

    // 最大时间
    private int mRunningTime;
    // 当前状态
    private int mState = STATE_PREPARE;
    // 背景颜色
    private int mBgColor;
    // 运行中颜色
    private int mRunningColor;
    // 取消状态颜色
    private int mCancelColor;
    // 启动时间
    private long mStartTime;
    // 运行速度
    private float mRunSpeed;
    /**
     画笔
      */
    private Paint mPaint;

    public long getmPastTime() {
        return mPastTime;
    }

    private long mPastTime=0;

    private OnFinishListener mOnFinishListener;

    private int mWidth;

    private Activity activity;

    public RecordProgressBar(Context context) {
        this(context, null);
        activity = (Activity) context;
    }

    public RecordProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        activity = (Activity) context;
    }

    public RecordProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordProgressBar);
        mBgColor = typedArray.getColor(R.styleable.RecordProgressBar_rpb_backgroundColor, DEFAULT_BACKGROUND_COLOR);
        mCancelColor = typedArray.getColor(R.styleable.RecordProgressBar_rpb_cancelColor, DEFAULT_CANCEL_COLOR);
        mRunningColor = typedArray.getColor(R.styleable.RecordProgressBar_rpb_runningColor, DEFAULT_RUNNING_COLOR);
        mRunningTime = typedArray.getInteger(R.styleable.RecordProgressBar_rpb_timeLength, DEFAULT_RUNNING_TIME);
        typedArray.recycle();
        activity = (Activity) context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public interface OnFinishListener {
        /**
         * Called when mRunningTime has been used up.
         *
         */
        void onFinish();
    }

    public void setOnFinishListener(OnFinishListener l) {
        this.mOnFinishListener = l;
    }

    /**
     * 获取状态
     * @return
     */
    public int getState() {
        return mState;
    }

    /**
     * 获取运行时间
     * @return
     */
    public int getRunningTime() {
        return mRunningTime;
    }

    /**
     * 设置运行时间
     * @param runningTime
     */
    public void setRunningTime(int runningTime) {
        this.mRunningTime = runningTime;
    }

    /**
     * 开始走进度
     */
    public void start() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mStartTime = System.currentTimeMillis();
        mRunSpeed = dm.widthPixels / 2f / mRunningTime;
//        Log.e("zyf","width"+dm.widthPixels);
        mState = STATE_RUNNING;
        Handler handler = getHandler();
        if (handler != null) {
            handler.post(new LoopInvalidateRunnable(handler, this));
        }
    }

    /**
     * 恢复运行状态
     */
    public void resumeRunning() {
        mState = STATE_RUNNING;
    }

    /**
     * 取消状态
     */
    public void cancel() {
        mState = STATE_CANCEL;
    }

    /**
     * 停止进度
     */
    public void stop() {
        mState = STATE_PREPARE;
        invalidate();
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth =  MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBgColor);
        int color;
        switch (mState) {
            case STATE_CANCEL:
                color = mCancelColor;
                break;
            case STATE_RUNNING:
                color = mRunningColor;
                break;
            default:
                color = -1;
                break;
        }

        long pastTime = System.currentTimeMillis() - mStartTime;
        this.mPastTime=pastTime;
        if (color != -1) {
            // 已经运行过设置的时间了就停止
            if (pastTime > mRunningTime * 1000) {
                if (mOnFinishListener != null) {
                    mOnFinishListener.onFinish();
                }
                stop();
                return;
            }
            mPaint.setColor(color);
            float left = mRunSpeed * pastTime / 1000;
            float right = canvas.getWidth() - left;
            canvas.drawRect(left, 0, right, canvas.getHeight(), mPaint);
        }
    }

    /**
     * 循环刷新视图任务
     *
     * @author Martin
     */
    private class LoopInvalidateRunnable implements Runnable {

        private Handler handler;
        private View view;

        public LoopInvalidateRunnable(Handler handler, View view) {
            this.handler = handler;
            this.view = view;
        }

        @Override
        public void run() {
            view.invalidate();
            handler.postDelayed(this, INVALIDATE_DELAY);
        }
    }

}
