package com.standard.myapplication.customlayoutmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.standard.myapplication.R;

import java.lang.ref.WeakReference;

/**
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @function <描述功能>
 * @date: 2018/5/2 10:35
 */
public class CustomImageView extends AppCompatImageView {

    protected Context mContext;

    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private Paint mPaint;
    private WeakReference<Bitmap> mWeakBitmap;

    /**
     * 宽度比率
     */
    private int ratioWidth;

    /**
     * 高度比率
     */
    private int ratioHeight;

    public CustomImageView(Context context) {
        super(context);
        sharedConstructor(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context);
        init(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedConstructor(context);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        ratioWidth = ta.getInt(R.styleable.CustomImageView_ratio_width, 1);
        ratioHeight = ta.getInt(R.styleable.CustomImageView_ratio_height, 1);
        ta.recycle();
    }

    private void sharedConstructor(Context context) {
        mContext = context;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int width = getWidth() > 0 ? getWidth() : MeasureSpec.getSize(widthMeasureSpec);// PhoneUtil.getScreenDisplay().x;
            if (width == 0) {
                width = getScreenDisplay().getWidth();
            }
            if (width > 0) {
                float es = (float) width / (float) drawable.getIntrinsicWidth();
                int height;
                if (ratioWidth > 0 && ratioHeight > 0) {
                    // 比率不为0且不相等的时候，高度不取图片高度，而是按比例根据宽度计算高度
                    height = (int) ((float) width / ratioWidth * ratioHeight);
                } else {
                    height = (int) (drawable.getIntrinsicHeight() * es);
                }
                setMeasuredDimension(width, height);
            }
        }
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
        }
        super.invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInEditMode()) {
            int i = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            try {
                Bitmap bitmap = mWeakBitmap != null ? mWeakBitmap.get() : null;
                // Bitmap not loaded.
                if (bitmap == null || bitmap.isRecycled()) {
                    Drawable drawable = getDrawable();
                    if (drawable != null) {
                        // Allocation onDraw but it's ok because it will not
                        // always be called.
                        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas bitmapCanvas = new Canvas(bitmap);
                        // drawable.setBounds(0, 0, getWidth(), getHeight());
                        // drawable.draw(bitmapCanvas);
                        super.onDraw(bitmapCanvas);

                        // If mask is already set, skip and use cached mask.
                        if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                            mMaskBitmap = getBitmap();
                        }

                        // Draw Bitmap.
                        mPaint.reset();
                        mPaint.setFilterBitmap(false);
                        mPaint.setXfermode(sXfermode);
                        // mBitmapShader = new BitmapShader(mMaskBitmap,
                        // Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                        // mPaint.setShader(mBitmapShader);
                        bitmapCanvas.drawBitmap(mMaskBitmap, 0.0f, 0.0f, mPaint);

                        mWeakBitmap = new WeakReference<Bitmap>(bitmap);
                    }
                }
                // Bitmap already loaded.
                if (bitmap != null) {
                    mPaint.setXfermode(null);
                    // mPaint.setShader(null);
                    // canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
                    canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight()
                            - getPaddingBottom()), mPaint);
                    return;
                }
            } catch (Exception e) {
                System.gc();
            } finally {
                canvas.restoreToCount(i);
            }
        } else {
            super.onDraw(canvas);
        }
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawOval(new RectF(0.0f, 0.0f, getWidth(), getHeight()), paint);
        return bitmap;
    }


    /**
     * 返回屏幕数据
     *
     * @return
     */
    public Display getScreenDisplay() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

}