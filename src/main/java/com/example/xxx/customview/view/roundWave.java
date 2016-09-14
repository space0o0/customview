package com.example.xxx.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆形的波浪
 *
 * Created by space on 16/9/13.
 */
public class roundWave extends View {
    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public roundWave(Context context) {
        super(context);
    }


    public roundWave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public roundWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);
    }
}
