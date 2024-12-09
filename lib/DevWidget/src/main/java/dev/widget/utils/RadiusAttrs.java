package dev.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import dev.widget.R;
import dev.widget.ui.radius.IRadiusMethod;

/**
 * detail: RadiusLayout 圆角属性封装处理类
 * @author arvinljw
 * @author Ttt
 */
public class RadiusAttrs
        implements IRadiusMethod<RadiusAttrs> {

    // 是否清空绘制圆角值
    private       boolean mClearRadius = false;
    // 绘制区域
    private       RectF   mRectF;
    // 绘制路径
    private final Path    mPath        = new Path();
    // 圆角范围
    private       float[] mRadii       = new float[8];
    // 圆角值
    private       float   mRadius;
    private       float   mRadiusLeftTop;
    private       float   mRadiusLeftBottom;
    private       float   mRadiusRightTop;
    private       float   mRadiusRightBottom;

    /**
     * 初始化
     * @param context      {@link Context}
     * @param attrs        {@link AttributeSet}
     * @param defStyleAttr 默认样式
     * @param defStyleRes  默认样式资源
     */
    public RadiusAttrs(
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr,
            final int defStyleRes
    ) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.DevWidget, defStyleAttr, defStyleRes
            );
            mRadius            = a.getLayoutDimension(R.styleable.DevWidget_dev_radius, 0);
            mRadiusLeftTop     = a.getLayoutDimension(R.styleable.DevWidget_dev_radiusLeftTop, 0);
            mRadiusLeftBottom  = a.getLayoutDimension(R.styleable.DevWidget_dev_radiusLeftBottom, 0);
            mRadiusRightTop    = a.getLayoutDimension(R.styleable.DevWidget_dev_radiusRightTop, 0);
            mRadiusRightBottom = a.getLayoutDimension(R.styleable.DevWidget_dev_radiusRightBottom, 0);
            mClearRadius       = a.getBoolean(R.styleable.DevWidget_dev_clearRadius, false);
            a.recycle();

            if (mRadius != 0) {
                mRadiusLeftTop     = mRadius;
                mRadiusLeftBottom  = mRadius;
                mRadiusRightTop    = mRadius;
                mRadiusRightBottom = mRadius;
            }
        }
        resetRadius();
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 重置绘制圆角值
     */
    private void resetRadius() {
        mRadii[0] = mRadii[1] = mRadiusLeftTop;
        mRadii[2] = mRadii[3] = mRadiusRightTop;
        mRadii[4] = mRadii[5] = mRadiusRightBottom;
        mRadii[6] = mRadii[7] = mRadiusLeftBottom;
    }

    /**
     * 内部校验清空绘制圆角值
     */
    private void innerClearRadius() {
        if (mClearRadius) clearRadius();
    }

    // =============
    // = 对外公开方法 =
    // =============

    // ================
    // = 数据恢复 / 存储 =
    // ================

    private static final String SUPER_DATA = "super_data";
    private static final String DATA_RADII = "data_radii";

    public final Parcelable onSaveInstanceState(final Parcelable superState) {
        Bundle data = new Bundle();
        data.putParcelable(SUPER_DATA, superState);
        data.putFloatArray(DATA_RADII, mRadii);
        return data;
    }

    public final Parcelable onRestoreInstanceState(final Parcelable state) {
        Bundle bundle = (Bundle) state;
        mRadii = bundle.getFloatArray(DATA_RADII);
        if (mRadii != null) {
            mRadiusLeftTop     = mRadii[0];
            mRadiusRightTop    = mRadii[2];
            mRadiusRightBottom = mRadii[4];
            mRadiusLeftBottom  = mRadii[6];
        }
        return bundle.getParcelable(SUPER_DATA);
    }

    /**
     * View 宽高改变时调用
     * @param w 宽度
     * @param h 高度
     */
    public final void onSizeChanged(
            final int w,
            final int h
    ) {
        mRectF = new RectF(0, 0, w, h);
    }

    /**
     * 获取绘制路径
     * @return {@link Path}
     */
    public final Path getPath() {
        mPath.reset();
        if (mRectF != null) {
            mPath.addRoundRect(mRectF, mRadii, Path.Direction.CW);
        }
        return mPath;
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
        return mClearRadius;
    }

    /**
     * 设置是否清空绘制圆角值
     * @param clear {@code true} yes, {@code false} no
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setClearRadius(final boolean clear) {
        this.mClearRadius = clear;
        return this;
    }

    /**
     * 清空绘制圆角值 ( 默认不进行绘制 )
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs clearRadius() {
        return setRadius(0);
    }

    /**
     * 清空绘制圆角值
     * @param invalidate 是否进行绘制
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs clearRadius(final boolean invalidate) {
        return setRadius(0);
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 设置圆角值
     * @param radius 圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadius(final float radius) {
        this.mRadius            = radius;
        this.mRadiusLeftTop     = radius;
        this.mRadiusLeftBottom  = radius;
        this.mRadiusRightTop    = radius;
        this.mRadiusRightBottom = radius;
        resetRadius();
        return this;
    }

    /**
     * 设置左上圆角值
     * @param radiusLeftTop 左上圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadiusLeftTop(final float radiusLeftTop) {
        innerClearRadius();
        this.mRadiusLeftTop = radiusLeftTop;
        resetRadius();
        return this;
    }

    /**
     * 设置左下圆角值
     * @param radiusLeftBottom 左下圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadiusLeftBottom(final float radiusLeftBottom) {
        innerClearRadius();
        this.mRadiusLeftBottom = radiusLeftBottom;
        resetRadius();
        return this;
    }

    /**
     * 设置右上圆角值
     * @param radiusRightTop 右上圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadiusRightTop(final float radiusRightTop) {
        innerClearRadius();
        this.mRadiusRightTop = radiusRightTop;
        resetRadius();
        return this;
    }

    /**
     * 设置右下圆角值
     * @param radiusRightBottom 右下圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadiusRightBottom(final float radiusRightBottom) {
        innerClearRadius();
        this.mRadiusRightBottom = radiusRightBottom;
        resetRadius();
        return this;
    }

    // =

    /**
     * 设置左上、左下圆角值
     * @param radiusLeft 左边圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadiusLeft(final float radiusLeft) {
        innerClearRadius();
        this.mRadiusLeftTop    = radiusLeft;
        this.mRadiusLeftBottom = radiusLeft;
        resetRadius();
        return this;
    }

    /**
     * 设置右上、右下圆角值
     * @param radiusRight 右边圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadiusRight(final float radiusRight) {
        innerClearRadius();
        this.mRadiusRightTop    = radiusRight;
        this.mRadiusRightBottom = radiusRight;
        resetRadius();
        return this;
    }

    /**
     * 设置左上、右上圆角值
     * @param radiusTop 上边圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadiusTop(final float radiusTop) {
        innerClearRadius();
        this.mRadiusLeftTop  = radiusTop;
        this.mRadiusRightTop = radiusTop;
        resetRadius();
        return this;
    }

    /**
     * 设置左下、右下圆角值
     * @param radiusBottom 下边圆角值
     * @return {@link RadiusAttrs}
     */
    @Override
    public RadiusAttrs setRadiusBottom(final float radiusBottom) {
        innerClearRadius();
        this.mRadiusLeftBottom  = radiusBottom;
        this.mRadiusRightBottom = radiusBottom;
        resetRadius();
        return this;
    }

    // =

    /**
     * 获取圆角值
     * @return 圆角值
     */
    @Override
    public float getRadius() {
        return mRadius;
    }

    /**
     * 获取左上圆角值
     * @return 左上圆角值
     */
    @Override
    public float getRadiusLeftTop() {
        return mRadiusLeftTop;
    }

    /**
     * 获取左下圆角值
     * @return 左下圆角值
     */
    @Override
    public float getRadiusLeftBottom() {
        return mRadiusLeftBottom;
    }

    /**
     * 获取右上圆角值
     * @return 右上圆角值
     */
    @Override
    public float getRadiusRightTop() {
        return mRadiusRightTop;
    }

    /**
     * 获取右下圆角值
     * @return 右下圆角值
     */
    @Override
    public float getRadiusRightBottom() {
        return mRadiusRightBottom;
    }
}