package com.example.xxx.customview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.xxx.customview.R;

/**
 * 渐变图标
 * Created by space on 16/9/14.
 */
public class ShadeView extends View {

    private String mText;
    private int mTextSize;
    private int defaultColor;
    private int changeColor;
    private Bitmap mIconBitmap;
    private Paint mTextPaint;
    private Rect mTextBound = new Rect();
    private Rect mIconRect;
    private float mAlpha = 0.0f;


    public ShadeView(Context context) {
        this(context, null);
    }

    public ShadeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        @SuppressLint("Recycle") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.shadeView);
        mText = typedArray.getString(R.styleable.shadeView_text);
        mTextSize = typedArray.getInt(R.styleable.shadeView_textsize, 32);
        defaultColor = typedArray.getColor(R.styleable.shadeView_defaultColor, getResources().getColor(R.color.black));
        changeColor = typedArray.getColor(R.styleable.shadeView_changeColor, getResources().getColor(R.color.darkgreen));

        BitmapDrawable bitmapDrawable = (BitmapDrawable) typedArray.getDrawable(R.styleable.shadeView_Icon);
        assert bitmapDrawable != null;
        mIconBitmap = bitmapDrawable.getBitmap();

        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(defaultColor);
        if (!TextUtils.isEmpty(mText)) {
            mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //图标边长取：控件的高度-文本的高度-内边距   与  控件的宽度-内边距  两者的小值
        int bitmapWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());

        int left = (getMeasuredWidth() - bitmapWidth) / 2;
        int top = (getMeasuredHeight() - mTextBound.height() - bitmapWidth) / 2;

        mIconRect = new Rect(left, top, left + bitmapWidth, top + bitmapWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int alpha = (int) Math.ceil(255 * mAlpha);
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);//绘制原图
        setupTargetBitmap(alpha);
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    public Canvas mCanvas;
    public Paint mPaint;
    public Bitmap mBitmap;

    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(changeColor);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(alpha);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        if (TextUtils.isEmpty(mText))
            return;
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2, mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    private void drawTargetText(Canvas canvas, int alpha) {
        if (TextUtils.isEmpty(mText))
            return;
        mTextPaint.setColor(changeColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2, mIconRect.bottom + mTextBound.height(), mTextPaint);
    }

    public void setIconAlpha(float alpha) {
        Log.i("alpha:=========", alpha + "");
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

}
