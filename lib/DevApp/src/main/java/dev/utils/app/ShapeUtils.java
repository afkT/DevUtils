package dev.utils.app;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Shape 工具类
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.cnblogs.com/popfisher/p/6238119.html"/>
 *     @see <a href="https://www.cnblogs.com/popfisher/p/5606690.html"/>
 * </pre>
 */
public final class ShapeUtils {

    // 日志 TAG
    private static final String TAG = ShapeUtils.class.getSimpleName();
    // Shape Drawable
    private final GradientDrawable mDrawable;

    /**
     * 构造函数
     * @param builder {@link Builder}
     */
    private ShapeUtils(final Builder builder) {
        mDrawable = builder.gradientDrawable;
    }

    /**
     * 获取 GradientDrawable
     * @return {@link GradientDrawable}
     */
    public GradientDrawable getDrawable() {
        return mDrawable;
    }

    /**
     * 设置 Drawable 背景
     * @param view {@link View}
     */
    public void setDrawable(final View view) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(mDrawable);
            else
                view.setBackgroundDrawable(mDrawable);
        }
    }

    // =

    /**
     * detail: 构造者模式
     * @author Ttt
     */
    public static final class Builder {

        // Shape Drawable
        private GradientDrawable gradientDrawable = new GradientDrawable();

        public Builder() {
        }

        /**
         * 构造函数
         * @param drawable {@link GradientDrawable}
         */
        public Builder(final GradientDrawable drawable) {
            if (drawable != null) {
                this.gradientDrawable = drawable;
            }
        }

        /**
         * build ShapeUtils 对象
         * @return {@link ShapeUtils}
         */
        public ShapeUtils build() {
            return new ShapeUtils(this);
        }

        // ============
        // = 设置圆角 =
        // ============

        /**
         * 设置圆角
         * @param radius 圆角大小
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setRadius(final float radius) {
            if (gradientDrawable != null) {
                gradientDrawable.setCornerRadius(radius);
            }
            return this;
        }

        // ============
        // = 设置左边 =
        // ============

        /**
         * 设置圆角
         * @param left leftTop、leftBottom 圆角大小
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setRadiusLeft(final float left) {
            setCornerRadii(left, 0, 0, left);
            return this;
        }

        /**
         * 设置圆角
         * @param leftTop    左上圆角大小
         * @param leftBottom 左下圆角大小
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setRadiusLeft(final float leftTop, final float leftBottom) {
            setCornerRadii(leftTop, 0, 0, leftBottom);
            return this;
        }

        // ============
        // = 设置右边 =
        // ============

        /**
         * 设置圆角
         * @param right rightTop、rightBottom 圆角大小
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setRadiusRight(final float right) {
            setCornerRadii(0, right, right, 0);
            return this;
        }

        /**
         * 设置圆角
         * @param rightTop    右上圆角大小
         * @param rightBottom 右下圆角大小
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setRadiusRight(final float rightTop, final float rightBottom) {
            setCornerRadii(0, rightTop, rightBottom, 0);
            return this;
        }

        // ================
        // = 圆角内部处理 =
        // ================

        /**
         * 设置圆角 ( 统一处理方法 )
         * <pre>
         *     <corners
         *          android:bottomLeftRadius="8dp"
         *          android:bottomRightRadius="8dp"
         *          android:topLeftRadius="8dp"
         *          android:topRightRadius="8dp" />
         * </pre>
         * @param leftTop     左上圆角大小
         * @param rightTop    左下圆角大小
         * @param rightBottom 右上圆角大小
         * @param leftBottom  右下圆角大小
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setCornerRadii(final float leftTop, final float rightTop, final float rightBottom, final float leftBottom) {
            if (gradientDrawable != null) {
                // radii 数组分别指定四个圆角的半径, 每个角可以指定 [X_Radius, Y_Radius]
                // 四个圆角的顺序为左上、右上、右下、左下, 如果 X_Radius, Y_Radius 为 0 表示还是直角
                gradientDrawable.setCornerRadii(new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom});
            }
            return this;
        }

        // =======================
        // = 设置背景色 ( 填充 ) =
        // =======================

        /**
         * 设置背景色 ( 填充铺满 )
         * <pre>
         *     <solid android:color="#DFDFE0" />
         * </pre>
         * @param color argb/rgb color
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setColor(final String color) {
            if (gradientDrawable != null && !TextUtils.isEmpty(color)) {
                try {
                    gradientDrawable.setColor(Color.parseColor(color));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setColor");
                }
            }
            return this;
        }

        /**
         * 设置背景色 ( 填充铺满 )
         * @param color R.color.id
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setColor(@ColorRes final int color) {
            if (gradientDrawable != null) {
                try {
                    gradientDrawable.setColor(ContextCompat.getColor(DevUtils.getContext(), color));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setColor");
                }
            }
            return this;
        }

        // ================
        // = 设置边框颜色 =
        // ================

        /**
         * 设置边框颜色
         * <pre>
         *     描边
         *     <stroke
         *          android:width      整型 描边的宽度
         *          android:color      颜色值 描边的颜色
         *          android:dashWidth  整型 表示描边的样式是虚线的宽度, 值为 0 时, 表示为实线, 值大于 0 则为虚线
         *          android:dashGap    整型 表示描边为虚线时, 虚线之间的间隔 即「 - - - - 」
         *          />
         * </pre>
         * @param width 描边的宽度
         * @param color 描边的颜色
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setStroke(final int width, final String color) {
            if (gradientDrawable != null) {
                try {
                    gradientDrawable.setStroke(width, Color.parseColor(color));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setStroke");
                }
            }
            return this;
        }

        /**
         * 设置边框颜色
         * @param width 描边的宽度
         * @param color 描边的颜色
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setStroke(final int width, @ColorRes final int color) {
            if (gradientDrawable != null) {
                try {
                    gradientDrawable.setStroke(width, ContextCompat.getColor(DevUtils.getContext(), color));
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setStroke");
                }
            }
            return this;
        }

        /**
         * 设置边框颜色
         * @param width 描边的宽度
         * @param color 描边的颜色
         * @return {@link ShapeUtils.Builder}
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public Builder setStroke(final int width, final ColorStateList color) {
            if (gradientDrawable != null && color != null) {
                try {
                    gradientDrawable.setStroke(width, color);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setStroke");
                }
            }
            return this;
        }

        // ============
        // = 设置大小 =
        // ============

        /**
         * 设置大小
         * <pre>
         *     <size
         *          android:width   整型 宽度
         *          android:height  整型 高度
         *          />
         * </pre>
         * @param width  宽度
         * @param height 高度
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setSize(final int width, final int height) {
            if (gradientDrawable != null) {
                try {
                    gradientDrawable.setSize(width, height);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setSize");
                }
            }
            return this;
        }

        // ============
        // = 设置边距 =
        // ============

        /**
         * 设置边距
         * @param padding Padding
         * @return {@link ShapeUtils.Builder}
         */
        public Builder setPadding(final int padding) {
            if (gradientDrawable != null) {
                try {
                    Rect rect = new Rect();
                    rect.left = padding;
                    rect.top = padding;
                    rect.right = padding;
                    rect.bottom = padding;
                    gradientDrawable.getPadding(rect);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "setPadding");
                }
            }
            return this;
        }

        // ============
        // = 设置渐变 =
        // ============

        /**
         * 获取渐变角度
         * @param angle 角度
         * @return {@link GradientDrawable.Orientation}
         */
        public GradientDrawable.Orientation getOrientation(final int angle) {
            switch (angle) {
                case 0:
                    return GradientDrawable.Orientation.LEFT_RIGHT;
                case 45:
                    return GradientDrawable.Orientation.BL_TR;
                case 90:
                    return GradientDrawable.Orientation.BOTTOM_TOP;
                case 135:
                    return GradientDrawable.Orientation.BR_TL;
                case 180:
                    return GradientDrawable.Orientation.RIGHT_LEFT;
                case 225:
                    return GradientDrawable.Orientation.TR_BL;
                case 270:
                    return GradientDrawable.Orientation.TOP_BOTTOM;
                case 315:
                    return GradientDrawable.Orientation.TL_BR;
            }
            return null;
        }

        /**
         * 设置渐变颜色
         * <pre>
         *     渐变, 这个设置之后一般就不设置 solid 填充色了
         *     <gradient
         *          android:startColor      颜色值 起始颜色
         *          android:endColor        颜色值 结束颜色
         *          android:centerColor     整型 渐变中间颜色, 即开始颜色与结束颜色之间的颜色
         *          android:angle           整型 渐变角度 ( 当 angle = 0 时, 渐变色是从左向右, 然后逆时针方向转, 当 angle = 90 时为从下往上, angle 必须为 45 的整数倍 )
         *          android:type            渐变类型 ( 取值: linear、radial、sweep)
         *          =
         *          linear                  线性渐变, 这是默认设置
         *          radial                  放射性渐变, 以开始色为中心
         *          sweep                   扫描线式的渐变
         *          =
         *          android:useLevel        如果要使用 LevelListDrawable 对象, 就要设置为 true, 设置为 true 无渐变, false 有渐变色
         *          android:gradientRadius  整型 渐变色半径, 当 android:type="radial" 时才使用, 单独使用 android:type="radial" 会报错
         *          android:centerX         整型 渐变中心 X 点坐标的相对位置
         *          android:centerY         整型 渐变中心 Y 点坐标的相对位置
         *         />
         *     <p></p>
         *     <gradient
         *          android:startColor="@android:color/white"
         *          android:centerColor="@android:color/black"
         *          android:endColor="@android:color/black"
         *          android:useLevel="true"
         *          android:angle="45"
         *          android:type="radial"
         *          android:centerX="0"
         *          android:centerY="0"
         *          android:gradientRadius="90" />
         * </pre>
         * @param colors 渐变颜色
         * @return {@link ShapeUtils.Builder}
         */
        public Builder(@ColorInt final int[] colors) {
            this(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors));
        }

        /**
         * 设置渐变颜色
         * @param orientation 渐变角度
         * @param colors      渐变颜色
         * @return {@link ShapeUtils.Builder}
         */
        public Builder(final GradientDrawable.Orientation orientation, @ColorInt final int[] colors) {
            this(new GradientDrawable(orientation, colors));
        }
    }

    // ============
    // = 快捷方法 =
    // ============

    /**
     * 创建新的 Shape Builder 对象
     * @param radius 圆角大小
     * @param color  背景色
     * @return {@link ShapeUtils.Builder}
     */
    public static Builder newBuilder(final float radius, @ColorRes final int color) {
        return new Builder().setRadius(radius).setColor(color);
    }

    /**
     * 创建新的 Shape Builder 对象
     * @param radius 圆角大小
     * @param color  背景色
     * @return {@link ShapeUtils.Builder}
     */
    public static Builder newBuilder(final float radius, final String color) {
        return new Builder().setRadius(radius).setColor(color);
    }

    // =

    /**
     * 创建新的 Shape Builder 对象
     * <pre>
     *     leftTop、leftBottom 圆角大小 + 背景色
     * </pre>
     * @param left  leftTop、leftBottom 圆角大小
     * @param color 背景色
     * @return {@link ShapeUtils.Builder}
     */
    public static Builder newBuilderToLeft(final float left, final String color) {
        return new Builder().setRadiusLeft(left).setColor(color);
    }

    /**
     * 创建新的 Shape Builder 对象
     * <pre>
     *     leftTop、leftBottom 圆角大小 + 背景色
     * </pre>
     * @param left  leftTop、leftBottom 圆角大小
     * @param color 背景色
     * @return {@link ShapeUtils.Builder}
     */
    public static Builder newBuilderToLeft(final float left, @ColorRes final int color) {
        return new Builder().setRadiusLeft(left).setColor(color);
    }

    // =

    /**
     * 创建新的 Shape Builder 对象
     * <pre>
     *     rightTop、rightBottom 圆角大小 + 背景色
     * </pre>
     * @param right rightTop、rightBottom 圆角大小
     * @param color 背景色
     * @return {@link ShapeUtils.Builder}
     */
    public static Builder newBuilderToRight(final float right, final String color) {
        return new Builder().setRadiusRight(right).setColor(color);
    }

    /**
     * 创建新的 Shape Builder 对象
     * <pre>
     *     rightTop、rightBottom 圆角大小 + 背景色
     * </pre>
     * @param right rightTop、rightBottom 圆角大小
     * @param color 背景色
     * @return {@link ShapeUtils.Builder}
     */
    public static Builder newBuilderToRight(final float right, @ColorRes final int color) {
        return new Builder().setRadiusRight(right).setColor(color);
    }

    // =

    /**
     * 创建渐变的 Shape Builder 对象
     * @param colors 渐变颜色
     * @return {@link ShapeUtils.Builder}
     */
    public static Builder newBuilderToGradient(@ColorInt final int[] colors) {
        return new Builder(colors);
    }

    /**
     * 创建渐变的 Shape Builder 对象
     * @param orientation 渐变角度
     * @param colors      渐变颜色
     * @return {@link ShapeUtils.Builder}
     */
    public static Builder newBuilderToGradient(final GradientDrawable.Orientation orientation, @ColorInt final int[] colors) {
        return new Builder(orientation, colors);
    }
}