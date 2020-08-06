package dev.utils.app;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

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
     * @param <T>  泛型
     * @return {@link View}
     */
    public <T extends View> T setDrawable(final T view) {
        ViewUtils.setBackground(view, mDrawable);
        return view;
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
            if (drawable != null) this.gradientDrawable = drawable;
        }

        /**
         * build ShapeUtils 对象
         * @return {@link ShapeUtils}
         */
        public ShapeUtils build() {
            return new ShapeUtils(this);
        }


        /**
         * <pre>
         *     <shape
         *          android:shape=[rectangle | oval | line | ring]  矩形 ( 默认 ) 、椭圆形、直线形、环形
         *          android:useLevel=[true | false]                 使用 LevelListDrawable 对象需设置为 true, 设置为 true 无渐变, false 有渐变色
         *          =======================================
         *          = 以下 4 个属性只有当类型为环形时才有效 =
         *          =======================================
         *          android:innerRadius="dimension"                 内环半径
         *          android:innerRadiusRatio="float"                内环半径相对于环的宽度的比例, 比如环的宽度为 50, 比例为 2.5, 那么内环半径为 20
         *          android:thickness="dimension"                   环的厚度
         *          android:thicknessRatio="float"                  环的厚度相对于环的宽度的比例
         *          />
         * </pre>
         */
        private int     mShapeType             = -1;
        private boolean mShapeUseLevel         = true;
        private int     mShapeInnerRadius      = -1;
        private float   mShapeInnerRadiusRatio = -1;
        private int     mShapeThickness        = -1;
        private float   mShapeThicknessRatio   = -1;

        /**
         * <solid android:color="color"/>
         */
        private ColorStateList mSolidColor = null; // 背景填充颜色

        /**
         * <pre>
         *     <padding
         *         android:left="dimension"   左内边距
         *         android:right="dimension"  右内边距
         *         android:top="dimension"    上内边距
         *         android:bottom="dimension" 下内边距
         *         />
         * </pre>
         */
        private int mPadding       = -1;
        private int mPaddingLeft   = -1;
        private int mPaddingRight  = -1;
        private int mPaddingTop    = -1;
        private int mPaddingBottom = -1;

        /**
         * <size android:width="dimension" android:height="dimension"/>
         */
        private int mSizeWidth  = -1; // shape drawable 宽
        private int mSizeHeight = -1; // shape drawable 高

        /**
         * <pre>
         *     <stroke
         *          android:width="dimension"       描边宽度
         *          android:color="color"           描边颜色
         *          android:dashWidth="dimension"   描边虚线宽度, 值为 0 时, 表示为实线, 值大于 0 则为虚线
         *          android:dashGap="dimension"     描边为虚线时, 虚线之间的间隔 即「 - - - - 」
         *          />
         * </pre>
         */
        private int            mStrokeWidth     = -1;
        private ColorStateList mStrokeColor     = null;
        private float          mStrokeDashWidth = -1f;
        private float          mStrokeDashGap   = -1f;

        /**
         * <pre>
         *     <corners
         *          android:radius="dimension"            圆角值
         *          android:topLeftRadius="dimension"     左上圆角值
         *          android:topRightRadius="dimension"    右上圆角值
         *          android:bottomLeftRadius="dimension"  左下圆角值
         *          android:bottomRightRadius="dimension" 右下圆角值
         *          />
         * </pre>
         */
        private float mCornersRadius            = -1f;
        private float mCornersTopLeftRadius     = -1f;
        private float mCornersTopRightRadius    = -1f;
        private float mCornersBottomLeftRadius  = -1f;
        private float mCornersBottomRightRadius = -1f;

        /**
         * <pre>
         *     <gradient
         *          android:startColor="color"              起始颜色
         *          android:centerColor="color"             渐变中间颜色, 即开始颜色与结束颜色之间的颜色
         *          android:endColor="color"                结束颜色
         *          android:angle="integer"                 渐变角度 ( 当 angle = 0 时, 渐变色是从左向右, 然后逆时针方向转, 当 angle = 90 时为从下往上, angle 必须为 45 的整数倍 )
         *          android:type=[linear | radial | sweep]  渐变类型
         *          android:centerX="float"                 渐变中心 X 点坐标的相对位置, 范围: 0 ~ 1
         *          android:centerY="float"                 渐变中心 Y 点坐标的相对位置, 范围: 0 ~ 1
         *          android:gradientRadius="float"          渐变色半径, 当 android:type="radial" 时才使用, 单独使用 android:type="radial" 会报错
         *          android:useLevel=[true | false]         使用 LevelListDrawable 对象需设置为 true, 设置为 true 无渐变, false 有渐变色
         *         />
         *     <p></p>
         *     linear   线性渐变, 这是默认设置
         *     radial   放射性渐变, 以开始色为中心
         *     sweep    扫描线式的渐变
         * </pre>
         */
        private int     mGradientStartColor     = -1;
        private int     mGradientCenterColor    = -1;
        private int     mGradientEndColor       = -1;
        private int     mGradientAngle          = -1;
        private int     mGradientType           = -1;
        private float   mGradientCenterX        = -1f;
        private float   mGradientCenterY        = -1f;
        private int     mGradientGradientRadius = -1;
        private boolean mGradientUseLevel       = false;
    }
}