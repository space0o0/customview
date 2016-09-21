package com.example.xxx.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.example.xxx.customview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by space on 16/9/20.
 */
public class WaveView extends View {
    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mViewWidth, mViewHeight;

    private float mLevelLine;//水平线

    private float waveHeight = 80;//波浪高度

    private float waveWidth = 200;//波长

    private float mLeftSide;//被隐藏的左边的波形

    private float mMoveLen;

    private final static float SPEED = 2.0F;

    private Timer timer;
    private MyTimerTask myTimerTask;

    private List<Point> mPointList;
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint levelPaint;
    private Path mWavePath;
    private boolean isMeasure = false;

    private void init() {
        mPointList = new ArrayList<>();
        timer = new Timer();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.blue));

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.white));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);

        levelPaint=new Paint();
        levelPaint.setColor(getResources().getColor(R.color.black));


        mWavePath = new Path();
    }

    private Handler updateHandler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mMoveLen += SPEED;

            mLevelLine -= 0.1f;
            if (mLevelLine < 0) {
                mLevelLine = 0;
            }
            mLeftSide += SPEED;

            for (int i = 0; i < mPointList.size(); i++) {
                mPointList.get(i).setX(mPointList.get(i).getX() + SPEED);
                switch (i % 4) {
                    case 0:
                        mPointList.get(i).setY(mLevelLine);
                        break;
                    case 1:
                        mPointList.get(i).setY(mLevelLine + waveHeight);
                        break;
                    case 2:
                        mPointList.get(i).setY(mLevelLine);
                        break;
                    case 3:
                        mPointList.get(i).setY(mLevelLine - waveHeight);
                        break;
                }
            }
            if (mMoveLen >= waveWidth) {
                mMoveLen = 0;
                resetPoints();
            }
            invalidate();
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!isMeasure) {
            isMeasure = true;
            mViewWidth = getMeasuredWidth();
            mViewHeight = getMeasuredHeight();

            mLevelLine = mViewHeight-waveWidth;//水位线从最底下开始上升
            waveHeight = mViewWidth / 2.5f;//根据view宽度计算水波峰值
            waveWidth = mViewWidth *2;//波长等于4倍的view宽度,波长越大,起伏越明显
            mLeftSide = waveWidth;//左边预留一个波宽

            int n = (int) Math.round(mViewWidth / waveWidth + 0.5);//???计算可见view能容纳几个波形,n取整数
            //n个波形需要4n+1个点,再加上左边的一个隐藏区域,即4n+5;
            for (int i = 0; i < (4 * n + 5); i++) {
                float x = i * waveWidth / 4 - waveWidth;
                float y = 0;
                switch (i % 4) {
                    case 0:
                        y = mLevelLine;
                        break;
                    case 1:
                        //y值位于波的最高处,
                        y = mLevelLine + waveHeight;
                        break;
                    case 2:
                        y = mLevelLine;
                        break;
                    case 3:
                        y = mLevelLine - waveHeight;
                        break;

                }
                mPointList.add(new Point(x, y));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWavePath.reset();
        int i = 0;
        mWavePath.moveTo(mPointList.get(0).getX(), mPointList.get(0).getY());
        for (; i < mPointList.size() - 2; i = i + 2) {
            mWavePath.quadTo(mPointList.get(i + 1).getX(),
                    mPointList.get(i + 1).getY(),
                    mPointList.get(i + 2).getX(),
                    mPointList.get(i + 2).getY());
        }
        mWavePath.lineTo(mPointList.get(i).getX(), mViewHeight);
        mWavePath.lineTo(mLeftSide, mViewHeight);
        mWavePath.close();

        canvas.drawPath(mWavePath, mPaint);

        canvas.drawLine(0,mLevelLine,mViewWidth,mLevelLine,levelPaint);
    }

    public void start() {
        if (myTimerTask != null) {
            myTimerTask.cancel();
            myTimerTask = null;
        }
        myTimerTask = new MyTimerTask(updateHandler);
        timer.schedule(myTimerTask, 0, 10);
    }

    /**
     * 初始化所有点的初始位置
     */
    private void resetPoints() {
        mLeftSide = -waveWidth;
        for (int i = 0; i < mPointList.size(); i++) {
            mPointList.get(i).setX(i * waveWidth / 4 - waveWidth);
        }
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        /**
         * The task to run should be specified in the implementation of the {@code run()}
         * method.
         */
        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }
    }

    class Point {
        private float x;
        private float y;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }


}
