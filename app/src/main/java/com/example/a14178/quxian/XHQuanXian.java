package com.example.a14178.quxian;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 水波纹的实现
 */

public class XHQuanXian extends View {

    private Context context;
    //周期(顶)
    private int t = 60;
    //周期(底)
    private int b = 120;
    private int mWidth, mHeight;//画布的宽带和高度
    private Path path;
    private Paint paint;
    //表示几等分
    private int wF = 3;
    private int fen;
    private float cycleWidth, cycleHeight = 60f;//周期的宽度和高度
    private float moveSet = 0;//移动的值

    //起点坐标
    private int startX = -30;
    private int startY = 500;
    //控制点
    private int waX = 0;
    private int waY = 60;
    //终点
    private int endX = 600;
    private int endY = 500;

    Handler handler;


    //总高度
    private int sizeH = 300;

    public XHQuanXian(Context context) {
        super(context);
        initContext(context);
    }

    public XHQuanXian(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initContext(context);
    }

    public XHQuanXian(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContext(context);
    }

    //初始化视图

    private void initContext(Context context) {
        this.context = context;
        path = new Path();
        paint = new Paint();
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#AFDEE4"));
        handler = new Handler();
        invalidate();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        cycleWidth = mWidth / 4;
        startNewThread();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        path.moveTo(startX, startY);
        path.quadTo(waX, sizeH, mWidth + 30, startY);
        path.lineTo(mWidth + 30, mHeight);
        path.lineTo(startX, mHeight);

        path.close();


        canvas.drawPath(path, paint);
        path.reset();


    }

    //设置高度
    //
    //

    public void setHIndex(int index) {
        sizeH = index;
        startY = (index + 200);
        invalidate();
    }

    private int d = 1;
    //启用新县城

    private void startNewThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    waX += d;
                    if (waX > mWidth + 900) {
                        d = -1;
                    }

                    if (waX < startX - 900) {
                        d = 1;
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });

                }

            }
        }).start();
    }


}
