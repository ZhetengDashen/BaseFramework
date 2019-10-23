package com.baseeasy.commonlibrary.weight.signboard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;


import com.baseeasy.commonlibrary.R;
import com.baseeasy.commonlibrary.config.BaseConfig;
import com.baseeasy.commonlibrary.mytool.file.FileUtils;

import java.io.File;
import java.io.FileOutputStream;


public class WriteSignPadDialog extends Dialog {
    static final int BACKGROUND_COLOR = Color.WHITE;
    Context context;
    LayoutParams p;
    WriteDialogListener dialogListener;
    PaintView mView;
    /**
     * The index of the current color to use.
     */

    public WriteSignPadDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setDialogListener(WriteDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.write_pad);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        p = getWindow().getAttributes();
        p.width = (wm.getDefaultDisplay().getWidth());
        p.height = (wm.getDefaultDisplay().getHeight()*19/20);
        getWindow().setAttributes(p);

        mView = new PaintView(context);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.tablet_view);
        frameLayout.addView(mView);
        mView.requestFocus();
        Button btnClear = (Button) findViewById(R.id.tablet_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mView.clear();

            }
        });

        Button btnOk = (Button) findViewById(R.id.tablet_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != dialogListener) {
                    try {
                        File sdRoot = new File(FileUtils.SDPATH + BaseConfig.FOLDER_NAME.SIGN+"/");
                        if (!sdRoot.exists()) {
                            sdRoot.mkdirs();
                        }
                        File file = new File(sdRoot, System.currentTimeMillis() + ".ing");
                        FileOutputStream fos = new FileOutputStream(file);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(-90f);
                        matrix.postScale(0.25f, 0.25f);
                        Bitmap resizedBitmap = Bitmap.createBitmap(mView.cachebBitmap, 0, 0,
                                mView.cachebBitmap.getWidth(), mView.cachebBitmap.getHeight(), matrix, true);
                        resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        dialogListener.callPath(file);
                        try {
                            if (mView.cachebBitmap != null && !mView.cachebBitmap.isRecycled()) {
                                mView.cachebBitmap.recycle();
                            }
                            if (!resizedBitmap.isRecycled()) {
                                resizedBitmap.recycle();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        WriteSignPadDialog.this.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Button btnCancel = (Button) findViewById(R.id.tablet_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
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

        private void init() {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(6);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            path = new Path();
            cachebBitmap = Bitmap.createBitmap(p.width, p.height, Config.ARGB_8888);
            cacheCanvas = new Canvas(cachebBitmap);
            cacheCanvas.drawColor(Color.WHITE);
        }

        public Bitmap getCachebBitmap() {
            return cachebBitmap;
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
        public boolean onTouchEvent(MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    cur_x = x;
                    cur_y = y;
                    path.moveTo(cur_x, cur_y);
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    path.quadTo(cur_x, cur_y, x, y);
                    cur_x = x;
                    cur_y = y;
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    cacheCanvas.drawPath(path, paint);
                    path.reset();
                    break;
                }
            }
            invalidate();
            return true;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {

            int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
            int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
            if (curW >= w && curH >= h) {
                return;
            }

            if (curW < w)
                curW = w;
            if (curH < h)
                curH = h;

            Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Config.ARGB_8888);
            Canvas newCanvas = new Canvas();
            newCanvas.setBitmap(newBitmap);
            if (cachebBitmap != null) {
                newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
            }
            cachebBitmap = newBitmap;
            cacheCanvas = newCanvas;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // canvas.drawColor(BRUSH_COLOR);
            if (cachebBitmap == null || cachebBitmap.isRecycled()) {
                init();
            }
            canvas.drawBitmap(cachebBitmap, 0, 0, null);
            canvas.drawPath(path, paint);
        }
    }


}
