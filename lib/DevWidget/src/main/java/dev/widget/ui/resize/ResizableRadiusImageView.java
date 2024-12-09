package dev.widget.ui.resize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import dev.utils.app.ViewUtils;
import dev.widget.ui.radius.IRadiusMethod;
import dev.widget.utils.RadiusAttrs;

/**
 * detail: 自定义圆角自动同比放大 ImageView
 * @author Ttt
 * <pre>
 *     铺满宽度, 高度根据比例自动缩放, 并可以自定义圆角
 *     <p></p>
 *     app:dev_radius=""
 *     app:dev_radiusLeftTop=""
 *     app:dev_radiusLeftBottom=""
 *     app:dev_radiusRightTop=""
 *     app:dev_radiusRightBottom=""
 *     app:dev_clearRadius=""
 * </pre>
 */
public class ResizableRadiusImageView
        extends AppCompatImageView
        implements IRadiusMethod<ResizableRadiusImageView> {

    // 圆角属性封装处理类
    protected RadiusAttrs            mRadiusAttrs;
    // 缩放后的高度
    private   int                    mZoomHeight;
    // 宽高监听
    private   ViewUtils.OnWHListener mWHListener;

    public ResizableRadiusImageView(Context context) {
        super(context);
        initAttrs(context, null, 0, 0);
    }

    public ResizableRadiusImageView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs, 0, 0);
    }

    public ResizableRadiusImageView(
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
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr,
            final int defStyleRes
    ) {
        mRadiusAttrs = new RadiusAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    // ===============
    // = 高度自适应相关 =
    // ===============

    @Override
    protected void onMeasure(
            int widthMeasureSpec,
            int heightMeasureSpec
    ) {
        Drawable bg = getDrawable();
        if (bg != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            // 高度根据使得图片的宽度充满屏幕计算而得
            int height = (int) Math.ceil(
                    (float) width * (float) bg.getIntrinsicHeight() / (float) bg.getIntrinsicWidth()
            );
            // 保存缩放后的高度
            this.mZoomHeight = height;
            setMeasuredDimension(width, height);
            // 触发回调
            if (mWHListener != null) {
                mWHListener.onWidthHeight(this, width, height);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取缩放后的高度
     * @return 缩放后的高度
     */
    public int getZoomHeight() {
        return mZoomHeight;
    }

    /**
     * 获取宽高监听事件
     * @return {@link ViewUtils.OnWHListener}
     */
    public ViewUtils.OnWHListener getWHListener() {
        return mWHListener;
    }

    /**
     * 设置宽高监听事件
     * @param whListener {@link ViewUtils.OnWHListener}
     * @return {@link ResizableRadiusImageView}
     */
    public ResizableRadiusImageView setWHListener(final ViewUtils.OnWHListener whListener) {
        this.mWHListener = whListener;
        return this;
    }

    // ==========
    // = 圆角相关 =
    // ==========

    // ============
    // = override =
    // ============

    @Override
    protected void onSizeChanged(
            int w,
            int h,
            int oldw,
            int oldh
    ) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadiusAttrs.onSizeChanged(w, h);
    }

    @Override
    protected void onLayout(
            boolean changed,
            int l,
            int t,
            int r,
            int b
    ) {
        super.onLayout(changed, l, t, r, b);
        int w = this.getWidth();
        int h = this.getHeight();
        mRadiusAttrs.onSizeChanged(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mRadiusAttrs.getPath());
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return mRadiusAttrs.onSaveInstanceState(super.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(mRadiusAttrs.onRestoreInstanceState(state));
        mRadiusAttrs.onSizeChanged(getWidth(), getHeight());
    }

    // =================
    // = IRadiusMethod =
    // =================

    // =============
    // = Operation =
    // =============

    /**
     * 是否清空绘制圆角值
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isClearRadius() {
        return mRadiusAttrs.isClearRadius();
    }

    /**
     * 设置是否清空绘制圆角值
     * @param clear {@code true} yes, {@code false} no
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setClearRadius(final boolean clear) {
        mRadiusAttrs.setClearRadius(clear);
        return this;
    }

    /**
     * 清空绘制圆角值 ( 默认不进行绘制 )
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView clearRadius() {
        return clearRadius(false);
    }

    /**
     * 清空绘制圆角值
     * @param invalidate 是否进行绘制
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView clearRadius(final boolean invalidate) {
        mRadiusAttrs.clearRadius();
        if (invalidate) postInvalidate();
        return this;
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 设置圆角值
     * @param radius 圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadius(final float radius) {
        mRadiusAttrs.setRadius(radius);
        postInvalidate();
        return this;
    }

    /**
     * 设置左上圆角值
     * @param radiusLeftTop 左上圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadiusLeftTop(final float radiusLeftTop) {
        mRadiusAttrs.setRadiusLeftTop(radiusLeftTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置左下圆角值
     * @param radiusLeftBottom 左下圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadiusLeftBottom(final float radiusLeftBottom) {
        mRadiusAttrs.setRadiusLeftBottom(radiusLeftBottom);
        postInvalidate();
        return this;
    }

    /**
     * 设置右上圆角值
     * @param radiusRightTop 右上圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadiusRightTop(final float radiusRightTop) {
        mRadiusAttrs.setRadiusRightTop(radiusRightTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置右下圆角值
     * @param radiusRightBottom 右下圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadiusRightBottom(final float radiusRightBottom) {
        mRadiusAttrs.setRadiusRightBottom(radiusRightBottom);
        postInvalidate();
        return this;
    }

    // =

    /**
     * 设置左上、左下圆角值
     * @param radiusLeft 左边圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadiusLeft(final float radiusLeft) {
        mRadiusAttrs.setRadiusLeft(radiusLeft);
        postInvalidate();
        return this;
    }

    /**
     * 设置右上、右下圆角值
     * @param radiusRight 右边圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadiusRight(final float radiusRight) {
        mRadiusAttrs.setRadiusRight(radiusRight);
        postInvalidate();
        return this;
    }

    /**
     * 设置左上、右上圆角值
     * @param radiusTop 上边圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadiusTop(final float radiusTop) {
        mRadiusAttrs.setRadiusTop(radiusTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置左下、右下圆角值
     * @param radiusBottom 下边圆角值
     * @return {@link ResizableRadiusImageView}
     */
    @Override
    public ResizableRadiusImageView setRadiusBottom(final float radiusBottom) {
        mRadiusAttrs.setRadiusBottom(radiusBottom);
        postInvalidate();
        return this;
    }

    // =

    /**
     * 获取圆角值
     * @return 圆角值
     */
    @Override
    public float getRadius() {
        return mRadiusAttrs.getRadius();
    }

    /**
     * 获取左上圆角值
     * @return 左上圆角值
     */
    @Override
    public float getRadiusLeftTop() {
        return mRadiusAttrs.getRadiusLeftTop();
    }

    /**
     * 获取左下圆角值
     * @return 左下圆角值
     */
    @Override
    public float getRadiusLeftBottom() {
        return mRadiusAttrs.getRadiusLeftBottom();
    }

    /**
     * 获取右上圆角值
     * @return 右上圆角值
     */
    @Override
    public float getRadiusRightTop() {
        return mRadiusAttrs.getRadiusRightTop();
    }

    /**
     * 获取右下圆角值
     * @return 右下圆角值
     */
    @Override
    public float getRadiusRightBottom() {
        return mRadiusAttrs.getRadiusRightBottom();
    }
}