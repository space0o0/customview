package com.example.xxx.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.xxx.customview.R;
import com.example.xxx.customview.utils.DensityUtils;

/**
 * 加载精度条
 * Created by space on 16/9/13.
 */
public class LoadingLine extends View {

    private final int COLOR = R.color.blue;

    private Paint mPaint;

    //宽度
    int widthSpace = DensityUtils.dp2px(getContext(), 80);
    //高度
    int heightSpace = DensityUtils.dp2px(getContext(), 4);
    //间隔
    int amongSpace = DensityUtils.dp2px(getContext(), 10);

    float startX = 0;
    float delta = 1f;

    public LoadingLine(Context context) {
        this(context,null);
    }

    public LoadingLine(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public LoadingLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//添加抗锯齿
        mPaint.setDither(true);//添加防抖
        mPaint.setColor(getResources().getColor(COLOR));
        mPaint.setStrokeWidth(heightSpace);

    }

    int index = 0;
    Object object=new Object();
    Thread thread=new Thread();

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        float sw = this.getMeasuredWidth();
        if (startX >= sw + (widthSpace + amongSpace) - (sw % (widthSpace + amongSpace))) {
            startX = 0;
        } else {
            startX += delta;
        }

        float start = startX;

        while (start < sw) {
            android.util.Log.i("start<sw","start="+start+"  sw="+sw);
            canvas.drawLine(start, 10, widthSpace + start, 10, mPaint);
            start += (widthSpace + amongSpace);
//            try {
//                thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        start = startX - amongSpace - widthSpace;

        while (start >= -widthSpace) {
            android.util.Log.i("start >= -widthSpace","start="+start+"  widthSpace="+widthSpace);
            canvas.drawLine(start, 10, widthSpace + start, 10, mPaint);
            start-=(widthSpace+amongSpace);
//            try {
//                thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        invalidate();
        Log.i("invalidate","--------------------------------");
//        try {
//            thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
