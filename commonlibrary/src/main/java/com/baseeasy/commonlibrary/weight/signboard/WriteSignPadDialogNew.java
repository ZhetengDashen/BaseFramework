package com.baseeasy.commonlibrary.weight.signboard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.config.BaseConfig;
import com.baseeasy.commonlibrary.mytool.AppUtils;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author：MagicLon
 * @date：2018/9/6 006
 * email：1348149485@qq.com
 * detail：签字功能
 */
public class WriteSignPadDialogNew extends Dialog {
    Context context;
    WindowManager.LayoutParams p;
    WriteDialogListener dialogListener;
    int BACKGROUND_COLOR = Color.WHITE;
    PaintView mView;

    public WriteSignPadDialogNew(Context context, WriteDialogListener dialogListener) {
        super(context);
        this.context = context;
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.write_pad_new);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        p = getWindow().getAttributes();
        p.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        p.height = (int) ((wm.getDefaultDisplay().getWidth()) * 0.9);
        p.width = (wm.getDefaultDisplay().getWidth());
        getWindow().setAttributes(p);
        mView = new PaintView(context);
        FrameLayout frameLayout = findViewById(R.id.tablet_view);
        frameLayout.addView(mView);
        mView.requestFocus();
        TextView btnClear = findViewById(R.id.tablet_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.clear();
            }
        });
        TextView btnOk = findViewById(R.id.tablet_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File sdRoot = new File(FileUtils.SDPATH + AppUtils.getAppName(context)+"/"+ BaseConfig.FOLDER_NAME.SIGN+"/");

                    if (!sdRoot.exists()) {
                        sdRoot.mkdirs();
                    }
                    File file = new File(sdRoot, System.currentTimeMillis() + ".ing");
                    FileOutputStream fos = new FileOutputStream(file);
                    Bitmap bitmap = null;
                    if (mView.cachebBitmap.getWidth() > 350) {
                        Matrix matrix = new Matrix();
                        matrix.setScale(350.0f / mView.cachebBitmap.getWidth(), 350.0f / mView.cachebBitmap.getHeight());
                        bitmap = Bitmap.createBitmap(mView.cachebBitmap, 0, 0, mView.cachebBitmap.getWidth(), mView.cachebBitmap.getHeight(), matrix, true);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } else {
                        mView.cachebBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    }
                    dialogListener.callPath(file);
                    try {
                        if (mView.cachebBitmap != null && !mView.cachebBitmap.isRecycled()) {
                            mView.cachebBitmap.recycle();
                        }
                        if (bitmap != null && !bitmap.isRecycled()) {
                            bitmap.recycle();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    WriteSignPadDialogNew.this.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        TextView btnCancel = findViewById(R.id.tablet_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    /**
     * This view implements the drawing canvas.
     * <p>
     * It handles all of the input events and drawing functions.
     */
    class PaintView extends View {
        private Paint paint;
        private Canvas cacheCanvas;
        private Bitmap cachebBitmap;
        private Path path;
        private float cur_x, cur_y;

        public PaintView(Context context) {
            super(context);
            init();
        }

        public Bitmap getCachebBitmap() {
            return cachebBitmap;
        }

        private void init() {
            paint = new Paint(Paint.DITHER_FLAG);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(6);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setColor(Color.BLACK);
            paint.setDither(true);
            path = new Path();
            cachebBitmap = Bitmap.createBitmap(p.width, p.height, Bitmap.Config.RGB_565);
            cacheCanvas = new Canvas(cachebBitmap);
            cacheCanvas.drawColor(Color.WHITE);
        }

        public void clear() {
            if (cacheCanvas != null) {
                paint.setColor(BACKGROUND_COLOR);
                cacheCanvas.drawPaint(paint);
                paint.setColor(Color.BLACK);
                cacheCanvas.drawColor(Color.WHITE);
                invalidate();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (cachebBitmap == null || cachebBitmap.isRecycled()) {
                cachebBitmap = Bitmap.createBitmap(p.width, p.height, Bitmap.Config.RGB_565);
                cacheCanvas = new Canvas(cachebBitmap);
                cacheCanvas.drawColor(Color.WHITE);
            }
            canvas.drawBitmap(cachebBitmap, 0, 0, paint);
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    cur_x = x;
                    cur_y = y;
                    cacheCanvas.drawPoint(cur_x, cur_y, paint);
                    path.moveTo(cur_x, cur_y);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    path.quadTo(cur_x, cur_y, (x + cur_x) / 2, (y + cur_y) / 2);
                    cur_x = x;
                    cur_y = y;
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    cacheCanvas.drawPath(path, paint);
                    path.reset();
                    break;
                }
                default:
                    break;
            }
            invalidate();
            return true;
        }
    }
}