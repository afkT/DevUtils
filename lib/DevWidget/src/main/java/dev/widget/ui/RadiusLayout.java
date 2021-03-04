package dev.widget.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import dev.widget.utils.RadiusAttrs;

/**
 * detail: 自定义圆角 View
 * @author Ttt
 * <pre>
 *     注意事项:
 *     该 View 会有锯齿情况
 *     如果需要裁剪 View 且四个角落都统一圆角可以使用 CardView 不增加阴影
 *     <p></p>
 *     app:dev_radius=""
 *     app:dev_radiusLeftTop=""
 *     app:dev_radiusLeftBottom=""
 *     app:dev_radiusRightTop=""
 *     app:dev_radiusRightBottom=""
 * </pre>
 */
public class RadiusLayout
        extends FrameLayout {

    private RadiusAttrs mRadiusAttrs;

    public RadiusLayout(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public RadiusLayout(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public RadiusLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr
    ) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RadiusLayout(
            Context context,
            AttributeSet attrs,
            int defStyleAttr,
            int defStyleRes
    ) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    /**
     * 初始化
     * @param context {@link Context}
     * @param attrs   {@link AttributeSet}
     */
    private void initAttrs(
            Context context,
            AttributeSet attrs
    ) {
        mRadiusAttrs = new RadiusAttrs(context, attrs);
        setWillNotDraw(false);
    }

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
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mRadiusAttrs.getPath());
        super.draw(canvas);
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

    // ===========
    // = get/set =
    // ===========

    /**
     * 设置圆角值
     * @param radius 圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadius(float radius) {
        mRadiusAttrs.setRadius(radius);
        postInvalidate();
        return this;
    }

    /**
     * 设置左上圆角值
     * @param radiusLeftTop 左上圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadiusLeftTop(float radiusLeftTop) {
        mRadiusAttrs.setRadiusLeftTop(radiusLeftTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置左下圆角值
     * @param radiusLeftBottom 左下圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadiusLeftBottom(float radiusLeftBottom) {
        mRadiusAttrs.setRadiusLeftBottom(radiusLeftBottom);
        postInvalidate();
        return this;
    }

    /**
     * 设置右上圆角值
     * @param radiusRightTop 右上圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadiusRightTop(float radiusRightTop) {
        mRadiusAttrs.setRadiusRightTop(radiusRightTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置右下圆角值
     * @param radiusRightBottom 右下圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadiusRightBottom(float radiusRightBottom) {
        mRadiusAttrs.setRadiusRightBottom(radiusRightBottom);
        postInvalidate();
        return this;
    }

    // =

    /**
     * 设置左上、左下圆角值
     * @param radiusLeft 左边圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadiusLeft(int radiusLeft) {
        mRadiusAttrs.setRadiusLeft(radiusLeft);
        postInvalidate();
        return this;
    }

    /**
     * 设置右上、右下圆角值
     * @param radiusRight 右边圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadiusRight(int radiusRight) {
        mRadiusAttrs.setRadiusRight(radiusRight);
        postInvalidate();
        return this;
    }

    /**
     * 设置左上、右上圆角值
     * @param radiusTop 上边圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadiusTop(int radiusTop) {
        mRadiusAttrs.setRadiusTop(radiusTop);
        postInvalidate();
        return this;
    }

    /**
     * 设置左下、右下圆角值
     * @param radiusBottom 下边圆角值
     * @return {@link RadiusLayout}
     */
    public RadiusLayout setRadiusBottom(int radiusBottom) {
        mRadiusAttrs.setRadiusBottom(radiusBottom);
        postInvalidate();
        return this;
    }

    // =

    /**
     * 获取圆角值
     * @return 圆角值
     */
    public float getRadius() {
        return mRadiusAttrs.getRadius();
    }

    /**
     * 获取左上圆角值
     * @return 左上圆角值
     */
    public float getRadiusLeftTop() {
        return mRadiusAttrs.getRadiusLeftTop();
    }

    /**
     * 获取左下圆角值
     * @return 左下圆角值
     */
    public float getRadiusLeftBottom() {
        return mRadiusAttrs.getRadiusLeftBottom();
    }

    /**
     * 获取右上圆角值
     * @return 右上圆角值
     */
    public float getRadiusRightTop() {
        return mRadiusAttrs.getRadiusRightTop();
    }

    /**
     * 获取右下圆角值
     * @return 右下圆角值
     */
    public float getRadiusRightBottom() {
        return mRadiusAttrs.getRadiusRightBottom();
    }
}