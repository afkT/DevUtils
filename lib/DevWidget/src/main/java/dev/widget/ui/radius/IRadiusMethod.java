package dev.widget.ui.radius;

/**
 * detail: 圆角通用方法接口
 * @author Ttt
 */
public interface IRadiusMethod<V> {

    // =============
    // = Operation =
    // =============

    /**
     * 是否清空绘制圆角值
     * @return {@code true} yes, {@code false} no
     */
    boolean isClearRadius();

    /**
     * 设置是否清空绘制圆角值
     * @param clear {@code true} yes, {@code false} no
     * @return V
     */
    V setClearRadius(final boolean clear);

    /**
     * 清空绘制圆角值 ( 默认不进行绘制 )
     * @return V
     */
    V clearRadius();

    /**
     * 清空绘制圆角值
     * @param invalidate 是否进行绘制
     * @return V
     */
    V clearRadius(final boolean invalidate);

    // ===========
    // = get/set =
    // ===========

    /**
     * 设置圆角值
     * @param radius 圆角值
     * @return V
     */
    V setRadius(final float radius);

    /**
     * 设置左上圆角值
     * @param radiusLeftTop 左上圆角值
     * @return V
     */
    V setRadiusLeftTop(final float radiusLeftTop);

    /**
     * 设置左下圆角值
     * @param radiusLeftBottom 左下圆角值
     * @return V
     */
    V setRadiusLeftBottom(final float radiusLeftBottom);

    /**
     * 设置右上圆角值
     * @param radiusRightTop 右上圆角值
     * @return V
     */
    V setRadiusRightTop(final float radiusRightTop);

    /**
     * 设置右下圆角值
     * @param radiusRightBottom 右下圆角值
     * @return V
     */
    V setRadiusRightBottom(final float radiusRightBottom);

    // =

    /**
     * 设置左上、左下圆角值
     * @param radiusLeft 左边圆角值
     * @return V
     */
    V setRadiusLeft(final float radiusLeft);

    /**
     * 设置右上、右下圆角值
     * @param radiusRight 右边圆角值
     * @return V
     */
    V setRadiusRight(final float radiusRight);

    /**
     * 设置左上、右上圆角值
     * @param radiusTop 上边圆角值
     * @return V
     */
    V setRadiusTop(final float radiusTop);

    /**
     * 设置左下、右下圆角值
     * @param radiusBottom 下边圆角值
     * @return V
     */
    V setRadiusBottom(final float radiusBottom);

    // =

    /**
     * 获取圆角值
     * @return 圆角值
     */
    float getRadius();

    /**
     * 获取左上圆角值
     * @return 左上圆角值
     */
    float getRadiusLeftTop();

    /**
     * 获取左下圆角值
     * @return 左下圆角值
     */
    float getRadiusLeftBottom();

    /**
     * 获取右上圆角值
     * @return 右上圆角值
     */
    float getRadiusRightTop();

    /**
     * 获取右下圆角值
     * @return 右下圆角值
     */
    float getRadiusRightBottom();
}