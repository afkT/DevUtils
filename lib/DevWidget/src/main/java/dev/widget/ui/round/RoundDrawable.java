package dev.widget.ui.round;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import dev.widget.R;

/**
 * detail: 圆角 Drawable
 * @author Ttt
 * <pre>
 *     使用 {@link #setBgData(ColorStateList)} 设置背景色
 *     使用 {@link #setStrokeData(int, ColorStateList)} 设置描边大小、描边颜色
 *     使用 {@link #setRadiusAdjustBounds(boolean)} 设置圆角大小是否自动适应为 {@link android.view.View} 的高度的一半, 默认为 true
 *     <p></p>
 *     注意事项:
 *     因为该控件的圆角采用 View 的 background 实现, 所以与原生的 android:background 有冲突
 *     如果在 xml 中用 android:background 指定 background, 该 background 不会生效
 *     <p></p>
 *     该类使用 QMUI QMUIRoundButtonDrawable 代码, 减少非必要代码依赖
 *     <p></p>
 *     app:dev_backgroundColor=""
 *     app:dev_borderColor=""
 *     app:dev_borderWidth=""
 *     app:dev_isRadiusAdjustBounds=""
 *     app:dev_radius=""
 *     app:dev_radiusLeftBottom=""
 *     app:dev_radiusLeftTop=""
 *     app:dev_radiusRightBottom=""
 *     app:dev_radiusRightTop=""
 * </pre>
 */
public class RoundDrawable
        extends GradientDrawable {

    // 圆角大小是否自适应为 View 的高度的一半
    private boolean        mRadiusAdjustBounds = true;
    // 填充颜色 ( 背景色 )
    private ColorStateList mFillColors;
    // 描边粗细、颜色
    private int            mStrokeWidth        = 0;
    private ColorStateList mStrokeColors;

    @Override
    protected boolean onStateChange(int[] stateSet) {
        boolean superRet = super.onStateChange(stateSet);
        if (mFillColors != null) {
            int color = mFillColors.getColorForState(stateSet, 0);
            setColor(color);
            superRet = true;
        }
        if (mStrokeColors != null) {
            int color = mStrokeColors.getColorForState(stateSet, 0);
            setStroke(mStrokeWidth, color);
            superRet = true;
        }
        return superRet;
    }

    @Override
    public boolean isStateful() {
        return (mFillColors != null && mFillColors.isStateful())
                || (mStrokeColors != null && mStrokeColors.isStateful())
                || super.isStateful();
    }

    @Override
    protected void onBoundsChange(Rect r) {
        super.onBoundsChange(r);
        if (mRadiusAdjustBounds) {
            // 修改圆角为短边的一半
            setCornerRadius(Math.min(r.width(), r.height()) / 2);
        }
    }

    // ===========
    // = 内部方法 =
    // ===========

    private boolean hasNativeStateListAPI() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取描边粗细
     * @return 描边粗细
     */
    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    /**
     * 设置描边粗细和颜色
     * @param colors 描边颜色
     * @return {@link RoundDrawable}
     */
    public RoundDrawable setStrokeColors(@Nullable ColorStateList colors) {
        return setStrokeData(mStrokeWidth, colors);
    }

    /**
     * 设置描边粗细和颜色
     * @param width  描边粗细
     * @param colors 描边颜色
     * @return {@link RoundDrawable}
     */
    public RoundDrawable setStrokeData(
            final int width,
            final ColorStateList colors
    ) {
        mStrokeWidth = width;
        mStrokeColors = colors;
        if (hasNativeStateListAPI()) {
            super.setStroke(width, colors);
        } else {
            final int currentColor;
            if (colors == null) {
                currentColor = Color.TRANSPARENT;
            } else {
                currentColor = colors.getColorForState(getState(), 0);
            }
            setStroke(width, currentColor);
        }
        return this;
    }

    /**
     * 设置按钮的背景色 ( 只支持纯色, 不支持 Bitmap 或 Drawable )
     * @param colors {@link ColorStateList}
     * @return {@link RoundDrawable}
     */
    public RoundDrawable setBgData(final ColorStateList colors) {
        if (hasNativeStateListAPI()) {
            super.setColor(colors);
        } else {
            mFillColors = colors;
            final int currentColor;
            if (colors == null) {
                currentColor = Color.TRANSPARENT;
            } else {
                currentColor = colors.getColorForState(getState(), 0);
            }
            setColor(currentColor);
        }
        return this;
    }

    /**
     * 设置圆角大小是否自适应为 View 的高度的一半
     * @param isRadiusAdjustBounds {@code true} yes, {@code false} no
     * @return {@link RoundDrawable}
     */
    public RoundDrawable setRadiusAdjustBounds(final boolean isRadiusAdjustBounds) {
        mRadiusAdjustBounds = isRadiusAdjustBounds;
        return this;
    }

    /**
     * 通过 AttributeSet 构建 RoundDrawable
     * @param context      {@link Context}
     * @param attrs        {@link AttributeSet}
     * @param defStyleAttr 默认样式 {@link AttributeSet}
     * @return {@link RoundDrawable}
     */
    public static RoundDrawable fromAttributeSet(
            final Context context,
            final AttributeSet attrs,
            final int defStyleAttr
    ) {
        TypedArray     a                    = context.obtainStyledAttributes(attrs, R.styleable.DevWidget, defStyleAttr, 0);
        ColorStateList colorBg              = a.getColorStateList(R.styleable.DevWidget_dev_backgroundColor);
        ColorStateList colorBorder          = a.getColorStateList(R.styleable.DevWidget_dev_borderColor);
        int            borderWidth          = a.getDimensionPixelSize(R.styleable.DevWidget_dev_borderWidth, 0);
        boolean        isRadiusAdjustBounds = a.getBoolean(R.styleable.DevWidget_dev_isRadiusAdjustBounds, false);
        int            radius               = a.getDimensionPixelSize(R.styleable.DevWidget_dev_radius, 0);
        int            radiusLeftTop        = a.getDimensionPixelSize(R.styleable.DevWidget_dev_radiusLeftTop, 0);
        int            radiusLeftBottom     = a.getDimensionPixelSize(R.styleable.DevWidget_dev_radiusLeftBottom, 0);
        int            radiusRightTop       = a.getDimensionPixelSize(R.styleable.DevWidget_dev_radiusRightTop, 0);
        int            radiusRightBottom    = a.getDimensionPixelSize(R.styleable.DevWidget_dev_radiusRightBottom, 0);
        a.recycle();

        RoundDrawable bg = new RoundDrawable();
        bg.setBgData(colorBg);
        bg.setStrokeData(borderWidth, colorBorder);
        if (radiusLeftTop > 0 || radiusRightTop > 0 || radiusLeftBottom > 0 || radiusRightBottom > 0) {
            float[] radii = new float[]{
                    radiusLeftTop, radiusLeftTop,
                    radiusRightTop, radiusRightTop,
                    radiusRightBottom, radiusRightBottom,
                    radiusLeftBottom, radiusLeftBottom
            };
            bg.setCornerRadii(radii);
            isRadiusAdjustBounds = false;
        } else {
            bg.setCornerRadius(radius);
            if (radius > 0) {
                isRadiusAdjustBounds = false;
            }
        }
        bg.setRadiusAdjustBounds(isRadiusAdjustBounds);
        return bg;
    }

    /**
     * 设置背景
     * @param view     {@link View}
     * @param drawable {@link Drawable}
     */
    public static void setBackgroundKeepingPadding(
            final View view,
            final Drawable drawable
    ) {
        int[] padding = new int[]{view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom()};
        view.setBackgroundDrawable(drawable);
        view.setPadding(padding[0], padding[1], padding[2], padding[3]);
    }
}