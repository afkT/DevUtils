package dev.widget.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import dev.widget.utils.RadiusUtils;

/**
 * detail: 自定义圆角 View
 * @author Ttt
 * <pre>
 *     app:dev_radius=""
 *     app:dev_radius_left_top=""
 *     app:dev_radius_left_bottom=""
 *     app:dev_radius_right_top=""
 *     app:dev_radius_right_bottom=""
 * </pre>
 */
public class RadiusView extends FrameLayout {

    private RadiusUtils mRadiusUtils;

    public RadiusView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public RadiusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public RadiusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RadiusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    /**
     * 初始化
     * @param context {@link Context}
     * @param attrs   {@link AttributeSet}
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        mRadiusUtils = new RadiusUtils(context, attrs);
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadiusUtils.onSizeChanged(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mRadiusUtils.getPath());
        super.draw(canvas);
        canvas.restore();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return mRadiusUtils.onSaveInstanceState(super.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(mRadiusUtils.onRestoreInstanceState(state));
        mRadiusUtils.onSizeChanged(getWidth(), getHeight());
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 设置圆角值
     * @param radius 圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadius(float radius) {
        mRadiusUtils.setRadius(radius);
        postInvalidate();
        return this;
    }

    /**
     * 设置左上圆角值
     * @param radiusLeftTop 左上圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadiusLeftTop(float radiusLeftTop) {
        mRadiusUtils.setRadiusLeftTop(radiusLeftTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置左下圆角值
     * @param radiusLeftBottom 左下圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadiusLeftBottom(float radiusLeftBottom) {
        mRadiusUtils.setRadiusLeftBottom(radiusLeftBottom);
        postInvalidate();
        return this;
    }

    /**
     * 设置右上圆角值
     * @param radiusRightTop 右上圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadiusRightTop(float radiusRightTop) {
        mRadiusUtils.setRadiusRightTop(radiusRightTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置右下圆角值
     * @param radiusRightBottom 右下圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadiusRightBottom(float radiusRightBottom) {
        mRadiusUtils.setRadiusRightBottom(radiusRightBottom);
        postInvalidate();
        return this;
    }

    // =

    /**
     * 设置左上、左下圆角值
     * @param radiusLeft 左边圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadiusLeft(int radiusLeft) {
        mRadiusUtils.setRadiusLeft(radiusLeft);
        postInvalidate();
        return this;
    }

    /**
     * 设置右上、右下圆角值
     * @param radiusRight 右边圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadiusRight(int radiusRight) {
        mRadiusUtils.setRadiusRight(radiusRight);
        postInvalidate();
        return this;
    }

    /**
     * 设置左上、右上圆角值
     * @param radiusTop 上边圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadiusTop(int radiusTop) {
        mRadiusUtils.setRadiusTop(radiusTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置左下、右下圆角值
     * @param radiusBottom 下边圆角值
     * @return {@link RadiusView}
     */
    public RadiusView setRadiusBottom(int radiusBottom) {
        mRadiusUtils.setRadiusBottom(radiusBottom);
        postInvalidate();
        return this;
    }

    // =

    /**
     * 获取圆角值
     * @return 圆角值
     */
    public float getRadius() {
        return mRadiusUtils.getRadius();
    }

    /**
     * 获取左上圆角值
     * @return 左上圆角值
     */
    public float getRadiusLeftTop() {
        return mRadiusUtils.getRadiusLeftTop();
    }

    /**
     * 获取左下圆角值
     * @return 左下圆角值
     */
    public float getRadiusLeftBottom() {
        return mRadiusUtils.getRadiusLeftBottom();
    }

    /**
     * 获取右上圆角值
     * @return 右上圆角值
     */
    public float getRadiusRightTop() {
        return mRadiusUtils.getRadiusRightTop();
    }

    /**
     * 获取右下圆角值
     * @return 右下圆角值
     */
    public float getRadiusRightBottom() {
        return mRadiusUtils.getRadiusRightBottom();
    }
}