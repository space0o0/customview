package com.example.xxx.customview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.example.xxx.customview.R;

/**
 * 圆形的波浪
 * <p/>
 * Created by space on 16/9/13.
 */
public class roundWave extends View {
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     * access the current theme, resources, etc.
     */

    Paint mPaint;

    public roundWave(Context context) {
        this(context, null);
    }


    public roundWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public roundWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖
    }


    RectF bodyRectF;
    float bodyWidth;
    float bodyHeight;
    float radius;//半径

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //因为绘制圆形，就先绘制一个正方形的rect区域
        bodyWidth = Math.min(getWidth() - getPaddingRight() - getPaddingLeft() - 10, getHeight() - getPaddingTop() - getPaddingBottom() - 10);
        bodyHeight = bodyWidth;

        bodyRectF = new RectF();
        bodyRectF.left = (getWidth() - bodyWidth) / 2;
        bodyRectF.top = (getHeight() - bodyHeight) / 2;
        bodyRectF.right = bodyRectF.left + bodyWidth;
        bodyRectF.bottom = bodyRectF.top + bodyHeight;

        radius = bodyWidth / 2;
    }

    Bitmap srcBitmap, dstBitmap;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(getResources().getColor(R.color.black));
        canvas.drawRect(0, 0, bodyWidth, bodyHeight, mPaint);

        super.onDraw(canvas);
/**
 * 绘制一个圆，从onmeasure中获取半径
 * 1、绘制底色
 * 2、绘制边框
 *
 */
//        dstBitmap = drawRound();
        dstBitmap = drawDst();
        srcBitmap = drawWave();

        int layoutID = canvas.saveLayer(0, 0, bodyWidth, bodyHeight, mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.drawBitmap(srcBitmap, bodyWidth / 4, bodyHeight / 4, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layoutID);
    }

    private Bitmap drawRound() {
        Bitmap bitmap = Bitmap.createBitmap((int) bodyWidth / 2, (int) bodyHeight / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.indianred));
        canvas.drawRoundRect(new RectF(0, 0, (int) bodyWidth / 2, (int) bodyHeight / 2), radius, radius, paint);
        paint.setColor(getResources().getColor(R.color.red));
        paint.setStrokeWidth(4f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(new RectF(0, 0, (int) bodyWidth / 2, (int) bodyHeight / 2), radius, radius, paint);
        return bitmap;
    }

    private Bitmap drawDst() {
        Bitmap bitmap = Bitmap.createBitmap((int) bodyWidth / 2, (int) bodyHeight / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.blue));
        canvas.drawOval(new RectF(0, 0, bodyWidth / 2, bodyHeight / 2), paint);
        return bitmap;
    }

    private Bitmap drawWave() {
        Bitmap bitmap = Bitmap.createBitmap((int) bodyWidth / 2, (int) bodyHeight / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint wavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wavePaint.setColor(getResources().getColor(R.color.cadetblue));
        wavePaint.setAntiAlias(true);
        wavePaint.setDither(true);
        canvas.drawRect(new RectF(0, 0, (int) bodyWidth / 2, (int) bodyHeight / 2), wavePaint);
        return bitmap;
    }

    public void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

}
