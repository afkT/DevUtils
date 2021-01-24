package dev.utils.app;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;

/**
 * detail: Shape 工具类
 * @author Ttt
 * <pre>
 *     Android XML shape 标签使用详解 ( apk 瘦身, 减少内存好帮手 )
 *     @see <a href="https://www.cnblogs.com/popfisher/p/6238119.html"/>
 *     Android GradientDrawable 静态使用和动态使用
 *     @see <a href="https://www.cnblogs.com/popfisher/p/5606690.html"/>
 *     Android 开发之 Shape 详细解读
 *     @see <a href="https://www.cnblogs.com/lang-yu/p/6112052.html"/>
 *     layer-list 的基本使用介绍
 *     @see <a href="https://blog.csdn.net/north1989/article/details/53485729"/>
 *     使用 layer-list 实现特殊的效果
 *     @see <a href="https://www.cnblogs.com/jianglijs/p/9441074.html"/>
 * </pre>
 * <pre>
 *     <shape
 *         android:shape=[rectangle | oval | line | ring]  矩形 ( 默认 ) 、椭圆形、直线形、环形
 *         android:useLevel=[true | false]                 使用 LevelListDrawable 对象需设置为 true, 设置为 true 无渐变, false 有渐变色
 *         // 以下 4 个属性只有当类型为环形时才有效
 *         android:innerRadius="dimension"                 内环半径
 *         android:innerRadiusRatio="float"                内环半径相对于环的宽度比例, 比如环的宽度为 50, 比例为 2.5, 那么内环半径为 20
 *         android:thickness="dimension"                   环厚度
 *         android:thicknessRatio="float"                  环厚度相对于环的宽度比例
 *         />
 *     <solid
 *         android:color="color"                           背景填充颜色
 *         />
 *     <stroke
 *         android:width="dimension"                       描边宽度
 *         android:color="color"                           描边颜色
 *         android:dashWidth="dimension"                   描边虚线宽度, 值为 0 时, 表示为实线, 值大于 0 则为虚线
 *         android:dashGap="dimension"                     描边为虚线时, 虚线之间的间隔 即「 - - - - 」
 *         />
 *     <corners
 *         android:radius="dimension"                      圆角值
 *         android:topLeftRadius="dimension"               左上圆角值
 *         android:topRightRadius="dimension"              右上圆角值
 *         android:bottomLeftRadius="dimension"            左下圆角值
 *         android:bottomRightRadius="dimension"           右下圆角值
 *         />
 *     <gradient
 *         android:startColor="color"                      起始颜色
 *         android:centerColor="color"                     渐变中间颜色, 即开始颜色与结束颜色之间的颜色
 *         android:endColor="color"                        结束颜色
 *         android:angle="integer"                         渐变角度 ( 当 angle = 0 时, 渐变色是从左向右, 然后逆时针方向转, 当 angle = 90 时为从下往上, angle 必须为 45 的整数倍 )
 *         android:type=[linear | radial | sweep]          渐变类型 linear ( 线性渐变 ) 、radial ( 放射性渐变 ) 、sweep ( 扫描线式渐变 )
 *         android:centerX="float"                         渐变中心 X 点坐标的相对位置, 范围: 0 ~ 1
 *         android:centerY="float"                         渐变中心 Y 点坐标的相对位置, 范围: 0 ~ 1
 *         android:gradientRadius="float"                  渐变色半径, 当 android:type="radial" 时才使用, 单独使用 android:type="radial" 会报错
 *         android:useLevel=[true | false]                 使用 LevelListDrawable 对象需设置为 true, 设置为 true 无渐变, false 有渐变色
 *         />
 *     <padding
 *         android:left="dimension"                        左内边距
 *         android:top="dimension"                         上内边距
 *         android:right="dimension"                       右内边距
 *         android:bottom="dimension"                      下内边距
 *         />
 *     <size
 *         android:width="dimension"                       shape drawable 宽
 *         android:height="dimension"                      shape drawable 高
 *         />
 * </pre>
 */
public final class ShapeUtils {

    // Shape Drawable
    private final GradientDrawable mDrawable;

    /**
     * 构造函数
     */
    private ShapeUtils() {
        this(new GradientDrawable());
    }

    /**
     * 构造函数
     * @param drawable {@link GradientDrawable}
     */
    private ShapeUtils(GradientDrawable drawable) {
        if (drawable == null) drawable = new GradientDrawable();
        this.mDrawable = drawable;
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
     * @param <T>  泛型
     * @return {@link View}
     */
    public <T extends View> T setDrawable(final T view) {
        ViewUtils.setBackground(view, mDrawable);
        return view;
    }

    /**
     * 获取渐变角度
     * @param angle 角度
     * @return {@link GradientDrawable.Orientation}
     */
    public static GradientDrawable.Orientation getOrientation(final int angle) {
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

    // ===========
    // = 静态构建 =
    // ===========

    /**
     * 创建 Shape
     * @return {@link ShapeUtils}
     */
    public static ShapeUtils newShape() {
        return new ShapeUtils();
    }

    /**
     * 创建 Shape
     * @param drawable {@link GradientDrawable}
     * @return {@link ShapeUtils}
     */
    public static ShapeUtils newShape(final GradientDrawable drawable) {
        return new ShapeUtils(drawable);
    }

    /**
     * 创建圆角 Shape
     * @param radius 圆角值
     * @return {@link ShapeUtils}
     */
    public static ShapeUtils newShape(final float radius) {
        return new ShapeUtils().setCornerRadius(radius);
    }

    /**
     * 创建圆角 Shape
     * @param radius 圆角值
     * @param color  背景色
     * @return {@link ShapeUtils}
     */
    public static ShapeUtils newShape(
            final float radius,
            @ColorInt final int color
    ) {
        return new ShapeUtils().setCornerRadius(radius).setColor(color);
    }

    /**
     * 创建渐变 Shape
     * @param angle  渐变角度
     * @param colors 渐变颜色
     * @return {@link ShapeUtils}
     */
    public static ShapeUtils newShape(
            final int angle,
            @ColorInt final int[] colors
    ) {
        return new ShapeUtils(new GradientDrawable(getOrientation(angle), colors));
    }

    /**
     * 创建渐变 Shape
     * @param orientation 渐变角度
     * @param colors      渐变颜色
     * @return {@link ShapeUtils}
     */
    public static ShapeUtils newShape(
            final GradientDrawable.Orientation orientation,
            @ColorInt final int[] colors
    ) {
        return new ShapeUtils(new GradientDrawable(orientation, colors));
    }

    // ===========
    // = 设置方法 =
    // ===========

    /**
     * 设置透明度
     * @param alpha 透明度
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setAlpha(@IntRange(from = 0, to = 255) final int alpha) {
        if (mDrawable != null) mDrawable.setAlpha(alpha);
        return this;
    }

    // =========
    // = shape =
    // =========

    /**
     * 设置形状类型
     * @param shape rectangle | oval | line | ring
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setShape(final int shape) {
        if (mDrawable != null) mDrawable.setShape(shape);
        return this;
    }

    /**
     * 设置内环半径
     * @param innerRadius 内环半径
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public ShapeUtils setInnerRadius(final int innerRadius) {
        if (mDrawable != null) mDrawable.setInnerRadius(innerRadius);
        return this;
    }

    /**
     * 设置内环半径相对于环的宽度比例
     * @param innerRadiusRatio 内环半径宽度比例
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public ShapeUtils setInnerRadiusRatio(final float innerRadiusRatio) {
        if (mDrawable != null) mDrawable.setInnerRadiusRatio(innerRadiusRatio);
        return this;
    }

    /**
     * 设置环厚度
     * @param thickness 环厚度
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public ShapeUtils setThickness(final int thickness) {
        if (mDrawable != null) mDrawable.setThickness(thickness);
        return this;
    }

    /**
     * 设置环厚度相对于环的宽度比例
     * @param thicknessRatio 环厚度宽度比例
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public ShapeUtils setThicknessRatio(final float thicknessRatio) {
        if (mDrawable != null) mDrawable.setThicknessRatio(thicknessRatio);
        return this;
    }

    // =========
    // = solid =
    // =========

    /**
     * 设置背景填充颜色
     * @param argb 背景色
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setColor(@ColorInt final int argb) {
        if (mDrawable != null) mDrawable.setColor(argb);
        return this;
    }

    /**
     * 设置背景填充颜色
     * @param colors 背景色
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShapeUtils setColor(final ColorStateList colors) {
        if (mDrawable != null) mDrawable.setColor(colors);
        return this;
    }

    // ==========
    // = stroke =
    // ==========

    /**
     * 设置描边
     * @param width 描边宽度
     * @param color 描边颜色
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setStroke(
            final int width,
            @ColorInt final int color
    ) {
        if (mDrawable != null) mDrawable.setStroke(width, color);
        return this;
    }

    /**
     * 设置描边
     * @param width  描边宽度
     * @param colors 描边颜色
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShapeUtils setStroke(
            final int width,
            final ColorStateList colors
    ) {
        if (mDrawable != null) mDrawable.setStroke(width, colors);
        return this;
    }

    /**
     * 设置描边
     * @param width     描边宽度
     * @param color     描边颜色
     * @param dashWidth 描边虚线宽度, 值为 0 时, 表示为实线, 值大于 0 则为虚线
     * @param dashGap   描边为虚线时, 虚线之间的间隔 即「 - - - - 」
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setStroke(
            final int width,
            @ColorInt final int color,
            final float dashWidth,
            final float dashGap
    ) {
        if (mDrawable != null) mDrawable.setStroke(width, color, dashWidth, dashGap);
        return this;
    }

    /**
     * 设置描边
     * @param width     描边宽度
     * @param colors    描边颜色
     * @param dashWidth 描边虚线宽度, 值为 0 时, 表示为实线, 值大于 0 则为虚线
     * @param dashGap   描边为虚线时, 虚线之间的间隔 即「 - - - - 」
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShapeUtils setStroke(
            final int width,
            final ColorStateList colors,
            final float dashWidth,
            final float dashGap
    ) {
        if (mDrawable != null) mDrawable.setStroke(width, colors, dashWidth, dashGap);
        return this;
    }

    // ===========
    // = corners =
    // ===========

    /**
     * 设置圆角
     * @param radius 圆角值
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setCornerRadius(final float radius) {
        if (mDrawable != null) mDrawable.setCornerRadius(radius);
        return this;
    }

    /**
     * 设置圆角
     * @param leftTop     左上圆角值
     * @param rightTop    右上圆角值
     * @param rightBottom 右下圆角值
     * @param leftBottom  左下圆角值
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setCornerRadius(
            final float leftTop,
            final float rightTop,
            final float rightBottom,
            final float leftBottom
    ) {
        if (mDrawable != null) {
            // radii 数组分别指定四个圆角的半径, 每个角可以指定 [X_Radius, Y_Radius]
            // 四个圆角的顺序为左上、右上、右下、左下, 如果 X_Radius, Y_Radius 为 0 表示还是直角
            mDrawable.setCornerRadii(new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom});
        }
        return this;
    }

    /**
     * 设置 leftTop、leftBottom 圆角
     * @param left leftTop、leftBottom 圆角值
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setCornerRadiusLeft(final float left) {
        setCornerRadius(left, 0, 0, left);
        return this;
    }

    /**
     * 设置 rightTop、rightBottom 圆角
     * @param right rightTop、rightBottom 圆角值
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setCornerRadiusRight(final float right) {
        setCornerRadius(0, right, right, 0);
        return this;
    }

    /**
     * 设置 leftTop、rightTop 圆角
     * @param top leftTop、rightTop 圆角值
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setCornerRadiusTop(final float top) {
        setCornerRadius(top, top, 0, 0);
        return this;
    }

    /**
     * 设置 leftBottom、rightBottom 圆角
     * @param bottom leftBottom、rightBottom 圆角值
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setCornerRadiusBottom(final float bottom) {
        setCornerRadius(0, 0, bottom, bottom);
        return this;
    }

    // ============
    // = gradient =
    // ============

    /**
     * 设置渐变颜色
     * @param colors [ 起始颜色、结束颜色 ] | [ 起始颜色、中间颜色、结束颜色 ]
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ShapeUtils setColors(@ColorInt final int[] colors) {
        if (mDrawable != null) mDrawable.setColors(colors);
        return this;
    }

    /**
     * 设置渐变类型
     * @param gradient 渐变类型 linear ( 线性渐变 ) 、radial ( 放射性渐变 ) 、sweep ( 扫描线式渐变 )
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setGradientType(final int gradient) {
        if (mDrawable != null) mDrawable.setGradientType(gradient);
        return this;
    }

    /**
     * 设置渐变角度
     * @param orientation 渐变角度 ( 当 angle = 0 时, 渐变色是从左向右, 然后逆时针方向转, 当 angle = 90 时为从下往上, angle 必须为 45 的整数倍 )
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ShapeUtils setOrientation(final GradientDrawable.Orientation orientation) {
        if (mDrawable != null) mDrawable.setOrientation(orientation);
        return this;
    }

    /**
     * 设置渐变角度
     * @param angle 渐变角度 ( 当 angle = 0 时, 渐变色是从左向右, 然后逆时针方向转, 当 angle = 90 时为从下往上, angle 必须为 45 的整数倍 )
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ShapeUtils setOrientation(final int angle) {
        return setOrientation(getOrientation(angle));
    }

    /**
     * 设置渐变中心坐标值
     * @param x 渐变中心 X 点坐标的相对位置, 范围: 0 ~ 1
     * @param y 渐变中心 Y 点坐标的相对位置, 范围: 0 ~ 1
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setGradientCenter(
            final float x,
            final float y
    ) {
        if (mDrawable != null) mDrawable.setGradientCenter(x, y);
        return this;
    }

    /**
     * 设置渐变色半径
     * @param gradientRadius 渐变色半径, 当 android:type="radial" 时才使用, 单独使用 android:type="radial" 会报错
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setGradientRadius(final float gradientRadius) {
        if (mDrawable != null) mDrawable.setGradientRadius(gradientRadius);
        return this;
    }

    /**
     * 是否使用 LevelListDrawable
     * @param useLevel 使用 LevelListDrawable 对象需设置为 true, 设置为 true 无渐变, false 有渐变色
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setUseLevel(final boolean useLevel) {
        if (mDrawable != null) mDrawable.setUseLevel(useLevel);
        return this;
    }

    // ===========
    // = padding =
    // ===========

    /**
     * 设置内边距
     * @param padding 边距值
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public ShapeUtils setPadding(final int padding) {
        return setPadding(padding, padding, padding, padding);
    }

    /**
     * 设置内边距
     * @param left   左内边距
     * @param top    上内边距
     * @param right  右内边距
     * @param bottom 下内边距
     * @return {@link ShapeUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public ShapeUtils setPadding(
            final int left,
            final int top,
            final int right,
            final int bottom
    ) {
        if (mDrawable != null) mDrawable.setPadding(left, top, right, bottom);
        return this;

    }

    // ========
    // = size =
    // ========

    /**
     * 设置 shape drawable 宽高
     * @param width  shape drawable 宽
     * @param height shape drawable 高
     * @return {@link ShapeUtils}
     */
    public ShapeUtils setSize(
            final int width,
            final int height
    ) {
        if (mDrawable != null) mDrawable.setSize(width, height);
        return this;
    }
}