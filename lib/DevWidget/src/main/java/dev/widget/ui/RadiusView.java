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
    protected void onDraw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.clipPath(mRadiusUtils.getPath());
        canvas.restore();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return mRadiusUtils.onSaveInstanceState(super.onSaveInstanceState());
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(mRadiusUtils.onRestoreInstanceState(state));
        mRadiusUtils.onSizeChanged(getWidth(), getHeight());
    }
}