package com.example.xxx.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.xxx.customview.R;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * Created by space on 16/9/18.
 */
public class XfermodeView extends View {

    float screenWidth, screenHeight;
    Bitmap srcBitmap, dstBitmap;
    Paint mPaint;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public XfermodeView(Context context) {
        this(context, null);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #View(Context, AttributeSet, int)
     */
    public XfermodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of View allows subclasses to use their
     * own base style when they are inflating. For example, a Button class's
     * constructor would call this version of the super class constructor and
     * supply <code>R.attr.buttonStyle</code> for <var>defStyleAttr</var>; this
     * allows the theme's button style to modify all of the base view attributes
     * (in particular its background) as well as the Button class's attributes.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @see #View(Context, AttributeSet)
     */
    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        screenWidth = getWidth();
        screenHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(getResources().getColor(R.color.black));
        canvas.drawRect(0, 0, screenWidth, screenHeight, mPaint);

        super.onDraw(canvas);

        srcBitmap = drawSrc();
        canvas.drawBitmap(srcBitmap, screenWidth / 8, screenHeight / 8, mPaint);
        dstBitmap = drawDst();
        canvas.drawBitmap(dstBitmap, screenWidth * 5 / 8, screenHeight / 8, mPaint);

//        int sc = canvas.saveLayer(0, 0, screenWidth, screenHeight, null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG);

        canvas.save();
        canvas.drawBitmap(dstBitmap, screenWidth*3 / 8, screenHeight*5 / 8, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.drawBitmap(srcBitmap, screenWidth*3 / 8, screenHeight*5 / 8, mPaint);
        mPaint.setXfermode(null);
//        canvas.restoreToCount(sc);

    }

    /**
     * Src为原图
     * Dst为目标图
     */

    private Bitmap drawSrc() {

        if (screenHeight<0|screenWidth<0){
            return null;
        }


        Bitmap bitmap = Bitmap.createBitmap((int) screenWidth / 4, (int) screenHeight / 4, ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.yellow));
        canvas.drawOval(new RectF(0, 0, screenWidth / 4, screenHeight / 4), paint);
        return bitmap;
    }

    private Bitmap drawDst() {
        if (screenHeight<0|screenWidth<0){
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap((int) screenWidth / 4, (int) screenHeight / 4, ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.green));
        canvas.drawRect(new RectF(0, 0, screenWidth / 4, screenHeight / 4), paint);
        return bitmap;
    }


}
