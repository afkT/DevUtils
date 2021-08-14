package dev.widget.ui.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

import dev.utils.LogPrintUtils;
import dev.widget.R;

/**
 * detail: 圆角图片
 * @author hdodenhof
 * <pre>
 *     该类使用 CircleImageView 代码, 减少非必要代码依赖
 *     <p></p>
 *     app:dev_borderWidth=""
 *     app:dev_borderColor=""
 *     app:dev_borderOverlay=""
 *     app:dev_circleBackgroundColor=""
 * </pre>
 */
public class RoundImageView
        extends AppCompatImageView {

    // 日志 TAG
    public static final String TAG = RoundImageView.class.getSimpleName();

    // 默认缩放类型
    private static final ScaleType SCALE_TYPE                      = ScaleType.CENTER_CROP;
    // 默认值
    private static final boolean   DEFAULT_BORDER_OVERLAY          = false;
    private static final int       DEFAULT_BORDER_WIDTH            = 0;
    private static final int       DEFAULT_BORDER_COLOR            = Color.BLACK;
    private static final int       DEFAULT_CIRCLE_BACKGROUND_COLOR = Color.TRANSPARENT;
    private static final int       DEFAULT_IMAGE_ALPHA             = 255;

    // =======
    // = 变量 =
    // =======

    // 圆角绘制坐标
    private final RectF  mDrawableRect          = new RectF();
    // 边框绘制坐标
    private final RectF  mBorderRect            = new RectF();
    // 画笔绘制相关
    private final Matrix mShaderMatrix          = new Matrix();
    private final Paint  mBitmapPaint           = new Paint();
    private final Paint  mBorderPaint           = new Paint();
    private final Paint  mCircleBackgroundPaint = new Paint();
    // 绘制相关属性
    private       int    mBorderColor           = DEFAULT_BORDER_COLOR;
    private       int    mBorderWidth           = DEFAULT_BORDER_WIDTH;
    private       int    mCircleBackgroundColor = DEFAULT_CIRCLE_BACKGROUND_COLOR;
    private       int    mImageAlpha            = DEFAULT_IMAGE_ALPHA;
    // 待绘制资源信息
    private       Bitmap mBitmap;
    private       Canvas mBitmapCanvas;
    // 绘制圆角数据
    private       float  mDrawableRadius;
    private       float  mBorderRadius;

    private ColorFilter mColorFilter;

    private boolean mInitialized;
    private boolean mRebuildShader;
    private boolean mDrawableDirty;
    private boolean mBorderOverlay;
    private boolean mDisableCircularTransformation;

    public RoundImageView(Context context) {
        super(context);
        initAttrs(context, null, 0, 0);
    }

    public RoundImageView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs, 0, 0);
    }

    public RoundImageView(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr, 0);
    }

    /**
     * 初始化
     * @param context      {@link Context}
     * @param attrs        {@link AttributeSet}
     * @param defStyleAttr 默认样式
     * @param defStyleRes  默认样式资源
     */
    private void initAttrs(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.DevWidget, defStyleAttr, defStyleRes
            );
            mBorderWidth           = a.getDimensionPixelSize(R.styleable.DevWidget_dev_borderWidth, DEFAULT_BORDER_WIDTH);
            mBorderColor           = a.getColor(R.styleable.DevWidget_dev_borderColor, DEFAULT_BORDER_COLOR);
            mBorderOverlay         = a.getBoolean(R.styleable.DevWidget_dev_borderOverlay, DEFAULT_BORDER_OVERLAY);
            mCircleBackgroundColor = a.getColor(R.styleable.DevWidget_dev_circleBackgroundColor, DEFAULT_CIRCLE_BACKGROUND_COLOR);
            a.recycle();
        }
        initialize();
    }

    private void initialize() {
        mInitialized = true;

        super.setScaleType(SCALE_TYPE);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);
        mBitmapPaint.setAlpha(mImageAlpha);
        mBitmapPaint.setColorFilter(mColorFilter);

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mCircleBackgroundPaint.setStyle(Paint.Style.FILL);
        mCircleBackgroundPaint.setAntiAlias(true);
        mCircleBackgroundPaint.setColor(mCircleBackgroundColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new OutlineProvider());
        }
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(
                    String.format("ScaleType %s not supported.", scaleType)
            );
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    // =

    @SuppressLint("CanvasSize")
    @Override
    protected void onDraw(Canvas canvas) {
        if (mDisableCircularTransformation) {
            super.onDraw(canvas);
            return;
        }

        if (mCircleBackgroundColor != Color.TRANSPARENT) {
            canvas.drawCircle(
                    mDrawableRect.centerX(), mDrawableRect.centerY(),
                    mDrawableRadius, mCircleBackgroundPaint
            );
        }

        if (mBitmap != null) {
            if (mDrawableDirty && mBitmapCanvas != null) {
                mDrawableDirty = false;
                Drawable drawable = getDrawable();
                drawable.setBounds(
                        0, 0, mBitmapCanvas.getWidth(), mBitmapCanvas.getHeight()
                );
                drawable.draw(mBitmapCanvas);
            }

            if (mRebuildShader) {
                mRebuildShader = false;

                BitmapShader bitmapShader = new BitmapShader(
                        mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
                );
                bitmapShader.setLocalMatrix(mShaderMatrix);

                mBitmapPaint.setShader(bitmapShader);
            }
            canvas.drawCircle(
                    mDrawableRect.centerX(), mDrawableRect.centerY(),
                    mDrawableRadius, mBitmapPaint
            );
        }

        if (mBorderWidth > 0) {
            canvas.drawCircle(
                    mBorderRect.centerX(), mBorderRect.centerY(),
                    mBorderRadius, mBorderPaint
            );
        }
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable dr) {
        mDrawableDirty = true;
        invalidate();
    }

    // =

    @Override
    protected void onSizeChanged(
            int w,
            int h,
            int oldw,
            int oldh
    ) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateDimensions();
        invalidate();
    }

    @Override
    public void setPadding(
            int left,
            int top,
            int right,
            int bottom
    ) {
        super.setPadding(left, top, right, bottom);
        updateDimensions();
        invalidate();
    }

    @Override
    public void setPaddingRelative(
            int start,
            int top,
            int end,
            int bottom
    ) {
        super.setPaddingRelative(start, top, end, bottom);
        updateDimensions();
        invalidate();
    }

    // =

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initializeBitmap();
        invalidate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initializeBitmap();
        invalidate();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initializeBitmap();
        invalidate();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
        invalidate();
    }

    // =

    @Override
    public int getImageAlpha() {
        return mImageAlpha;
    }

    @Override
    public void setImageAlpha(int alpha) {
        alpha &= 0xFF;

        if (alpha == mImageAlpha) {
            return;
        }
        mImageAlpha = alpha;

        // This might be called during ImageView construction before
        // member initialization has finished on API level >= 16.
        if (mInitialized) {
            mBitmapPaint.setAlpha(alpha);
            invalidate();
        }
    }

    @Override
    public ColorFilter getColorFilter() {
        return mColorFilter;
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) {
            return;
        }
        mColorFilter = cf;

        // This might be called during ImageView construction before
        // member initialization has finished on API level <= 19.
        if (mInitialized) {
            mBitmapPaint.setColorFilter(cf);
            invalidate();
        }
    }

    // =

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDisableCircularTransformation) {
            return super.onTouchEvent(event);
        }
        return inTouchableArea(event.getX(), event.getY()) && super.onTouchEvent(event);
    }

    private boolean inTouchableArea(
            float x,
            float y
    ) {
        if (mBorderRect.isEmpty()) {
            return true;
        }
        return (Math.pow(x - mBorderRect.centerX(), 2)
                + Math.pow(y - mBorderRect.centerY(), 2)
        ) <= Math.pow(mBorderRadius, 2);
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * detail: 描边轮廓
     * @author hdodenhof
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class OutlineProvider
            extends ViewOutlineProvider {
        @Override
        public void getOutline(
                View view,
                Outline outline
        ) {
            if (mDisableCircularTransformation) {
                ViewOutlineProvider.BACKGROUND.getOutline(view, outline);
            } else {
                Rect bounds = new Rect();
                mBorderRect.roundOut(bounds);
                outline.setRoundRect(bounds, bounds.width() / 2.0f);
            }
        }
    }

    /**
     * 初始化 Bitmap
     */
    private void initializeBitmap() {
        mBitmap = getBitmapFromDrawable(getDrawable());

        if (mBitmap != null && mBitmap.isMutable()) {
            mBitmapCanvas = new Canvas(mBitmap);
        } else {
            mBitmapCanvas = null;
        }

        if (!mInitialized) return;

        if (mBitmap != null) {
            updateShaderMatrix();
        } else {
            mBitmapPaint.setShader(null);
        }
    }

    /**
     * 更新尺寸信息
     */
    private void updateDimensions() {
        mBorderRect.set(calculateBounds());
        mBorderRadius = Math.min(
                (mBorderRect.height() - mBorderWidth) / 2.0f,
                (mBorderRect.width() - mBorderWidth) / 2.0f
        );
        mDrawableRect.set(mBorderRect);
        if (!mBorderOverlay && mBorderWidth > 0) {
            mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f);
        }
        mDrawableRadius = Math.min(
                mDrawableRect.height() / 2.0f,
                mDrawableRect.width() / 2.0f
        );
        updateShaderMatrix();
    }

    /**
     * 计算边界
     * @return {@link RectF}
     */
    private RectF calculateBounds() {
        int availableWidth  = getWidth() - getPaddingLeft() - getPaddingRight();
        int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
        float top  = getPaddingTop() + (availableHeight - sideLength) / 2f;

        return new RectF(left, top, left + sideLength, top + sideLength);
    }

    /**
     * 更新着色器 Matrix
     */
    private void updateShaderMatrix() {
        if (mBitmap == null) return;

        float scale, dx = 0, dy = 0;

        mShaderMatrix.set(null);

        int bitmapHeight = mBitmap.getHeight();
        int bitmapWidth  = mBitmap.getWidth();

        if (bitmapWidth * mDrawableRect.height() > mDrawableRect.width() * bitmapHeight) {
            scale = mDrawableRect.height() / (float) bitmapHeight;
            dx    = (mDrawableRect.width() - bitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) bitmapWidth;
            dy    = (mDrawableRect.height() - bitmapHeight * scale) * 0.5f;
        }
        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate(
                (int) (dx + 0.5f) + mDrawableRect.left,
                (int) (dy + 0.5f) + mDrawableRect.top
        );
        mRebuildShader = true;
    }

    // ==========================
    // = Drawable 转 Bitmap 常量 =
    // ==========================

    private static final int           COLORDRAWABLE_DIMENSION = 2;
    private static final Bitmap.Config BITMAP_CONFIG           = Bitmap.Config.ARGB_8888;

    /**
     * Drawable 转 Bitmap
     * @param drawable 待转换图片
     * @return {@link Bitmap}
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) return null;

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(
                        COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG
                );
            } else {
                bitmap = Bitmap.createBitmap(
                        drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG
                );
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapFromDrawable");
            return null;
        }
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 获取边框宽度
     * @return 边框宽度
     */
    public int getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * 设置边框宽度
     * @param borderWidth 边框宽度
     * @return {@link RoundImageView}
     */
    public RoundImageView setBorderWidth(int borderWidth) {
        if (borderWidth == mBorderWidth) {
            return this;
        }
        mBorderWidth = borderWidth;
        mBorderPaint.setStrokeWidth(borderWidth);
        updateDimensions();
        invalidate();
        return this;
    }

    /**
     * 获取边框颜色
     * @return 边框颜色
     */
    public int getBorderColor() {
        return mBorderColor;
    }

    /**
     * 设置边框颜色
     * @param borderColor 边框颜色
     * @return {@link RoundImageView}
     */
    public RoundImageView setBorderColor(@ColorInt int borderColor) {
        if (borderColor == mBorderColor) {
            return this;
        }
        mBorderColor = borderColor;
        mBorderPaint.setColor(borderColor);
        invalidate();
        return this;
    }

    /**
     * 获取圆圈背景颜色
     * @return 圆圈背景颜色
     */
    public int getCircleBackgroundColor() {
        return mCircleBackgroundColor;
    }

    /**
     * 设置圆圈背景颜色
     * @param circleBackgroundColor 圆圈背景颜色
     * @return {@link RoundImageView}
     */
    public RoundImageView setCircleBackgroundColor(@ColorInt int circleBackgroundColor) {
        if (circleBackgroundColor == mCircleBackgroundColor) {
            return this;
        }
        mCircleBackgroundColor = circleBackgroundColor;
        mCircleBackgroundPaint.setColor(circleBackgroundColor);
        invalidate();
        return this;
    }

    /**
     * 是否叠加边框
     * @return {@code true} yes, {@code false} no
     */
    public boolean isBorderOverlay() {
        return mBorderOverlay;
    }

    /**
     * 设置是否叠加边框
     * @param borderOverlay {@code true} yes, {@code false} no
     * @return {@link RoundImageView}
     */
    public RoundImageView setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == mBorderOverlay) {
            return this;
        }
        mBorderOverlay = borderOverlay;
        updateDimensions();
        invalidate();
        return this;
    }

    /**
     * 是否开启圆圈处理
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDisableCircularTransformation() {
        return mDisableCircularTransformation;
    }

    /**
     * 设置是否开启圆圈处理
     * @param disableCircularTransformation {@code true} yes, {@code false} no
     * @return {@link RoundImageView}
     */
    public RoundImageView setDisableCircularTransformation(boolean disableCircularTransformation) {
        if (disableCircularTransformation == mDisableCircularTransformation) {
            return this;
        }
        mDisableCircularTransformation = disableCircularTransformation;

        if (disableCircularTransformation) {
            mBitmap       = null;
            mBitmapCanvas = null;
            mBitmapPaint.setShader(null);
        } else {
            initializeBitmap();
        }
        invalidate();
        return this;
    }
}