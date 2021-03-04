package dev.widget.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

import dev.widget.R;

/**
 * detail: RadiusLayout 圆角属性封装处理类
 * @author arvinljw
 */
public class RadiusAttrs {

    // 绘制区域
    private       RectF   mRectF;
    // 绘制路径
    private final Path    mPath  = new Path();
    // 圆角范围
    private       float[] mRadii = new float[8];
    // 圆角值
    private       float   mRadius;
    private       float   mRadiusLeftTop;
    private       float   mRadiusLeftBottom;
    private       float   mRadiusRightTop;
    private       float   mRadiusRightBottom;

    /**
     * 初始化
     * @param context {@link Context}
     * @param attrs   {@link AttributeSet}
     */
    public RadiusAttrs(
            Context context,
            AttributeSet attrs
    ) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DevWidget);
            mRadius = a.getLayoutDimension(R.styleable.DevWidget_dev_radius, 0);
            mRadiusLeftTop = a.getLayoutDimension(R.styleable.DevWidget_dev_radiusLeftTop, 0);
            mRadiusLeftBottom = a.getLayoutDimension(R.styleable.DevWidget_dev_radiusLeftBottom, 0);
            mRadiusRightTop = a.getLayoutDimension(R.styleable.DevWidget_dev_radiusRightTop, 0);
            mRadiusRightBottom = a.getLayoutDimension(R.styleable.DevWidget_dev_radiusRightBottom, 0);
            a.recycle();

            if (mRadius != 0) {
                mRadiusLeftTop = mRadius;
                mRadiusLeftBottom = mRadius;
                mRadiusRightTop = mRadius;
                mRadiusRightBottom = mRadius;
            }
        }
        resetRadius();
    }

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
     * View 宽高改变时调用
     * @param w 宽度
     * @param h 高度
     */
    public void onSizeChanged(
            int w,
            int h
    ) {
        mRectF = new RectF(0, 0, w, h);
    }

    /**
     * 获取绘制路径
     * @return {@link Path}
     */
    public Path getPath() {
        mPath.reset();
        if (mRectF != null) {
            mPath.addRoundRect(mRectF, mRadii, Path.Direction.CW);
        }
        return mPath;
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 设置圆角值
     * @param radius 圆角值
     */
    public void setRadius(float radius) {
        this.mRadius = radius;
        this.mRadiusLeftTop = radius;
        this.mRadiusLeftBottom = radius;
        this.mRadiusRightTop = radius;
        this.mRadiusRightBottom = radius;
        resetRadius();
    }

    /**
     * 设置左上圆角值
     * @param radiusLeftTop 左上圆角值
     */
    public void setRadiusLeftTop(float radiusLeftTop) {
        this.mRadiusLeftTop = radiusLeftTop;
        resetRadius();
    }

    /**
     * 设置左下圆角值
     * @param radiusLeftBottom 左下圆角值
     */
    public void setRadiusLeftBottom(float radiusLeftBottom) {
        this.mRadiusLeftBottom = radiusLeftBottom;
        resetRadius();
    }

    /**
     * 设置右上圆角值
     * @param radiusRightTop 右上圆角值
     */
    public void setRadiusRightTop(float radiusRightTop) {
        this.mRadiusRightTop = radiusRightTop;
        resetRadius();
    }

    /**
     * 设置右下圆角值
     * @param radiusRightBottom 右下圆角值
     */
    public void setRadiusRightBottom(float radiusRightBottom) {
        this.mRadiusRightBottom = radiusRightBottom;
        resetRadius();
    }

    // =

    /**
     * 设置左上、左下圆角值
     * @param radiusLeft 左边圆角值
     */
    public void setRadiusLeft(int radiusLeft) {
        this.mRadiusLeftTop = radiusLeft;
        this.mRadiusLeftBottom = radiusLeft;
        resetRadius();
    }

    /**
     * 设置右上、右下圆角值
     * @param radiusRight 右边圆角值
     */
    public void setRadiusRight(int radiusRight) {
        this.mRadiusRightTop = radiusRight;
        this.mRadiusRightBottom = radiusRight;
        resetRadius();
    }

    /**
     * 设置左上、右上圆角值
     * @param radiusTop 上边圆角值
     */
    public void setRadiusTop(int radiusTop) {
        this.mRadiusLeftTop = radiusTop;
        this.mRadiusRightTop = radiusTop;
        resetRadius();
    }

    /**
     * 设置左下、右下圆角值
     * @param radiusBottom 下边圆角值
     */
    public void setRadiusBottom(int radiusBottom) {
        this.mRadiusLeftBottom = radiusBottom;
        this.mRadiusRightBottom = radiusBottom;
        resetRadius();
    }

    // =

    /**
     * 获取圆角值
     * @return 圆角值
     */
    public float getRadius() {
        return mRadius;
    }

    /**
     * 获取左上圆角值
     * @return 左上圆角值
     */
    public float getRadiusLeftTop() {
        return mRadiusLeftTop;
    }

    /**
     * 获取左下圆角值
     * @return 左下圆角值
     */
    public float getRadiusLeftBottom() {
        return mRadiusLeftBottom;
    }

    /**
     * 获取右上圆角值
     * @return 右上圆角值
     */
    public float getRadiusRightTop() {
        return mRadiusRightTop;
    }

    /**
     * 获取右下圆角值
     * @return 右下圆角值
     */
    public float getRadiusRightBottom() {
        return mRadiusRightBottom;
    }

    // ==================
    // = 数据恢复 / 存储 =
    // ==================

    private static final String SUPER_DATA = "super_data";
    private static final String DATA_RADII = "data_radii";

    public Parcelable onSaveInstanceState(Parcelable superState) {
        Bundle data = new Bundle();
        data.putParcelable(SUPER_DATA, superState);
        data.putFloatArray(DATA_RADII, mRadii);
        return data;
    }

    public Parcelable onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        mRadii = bundle.getFloatArray(DATA_RADII);
        if (mRadii != null) {
            mRadiusLeftTop = mRadii[0];
            mRadiusRightTop = mRadii[2];
            mRadiusRightBottom = mRadii[4];
            mRadiusLeftBottom = mRadii[6];
        }
        return bundle.getParcelable(SUPER_DATA);
    }
}