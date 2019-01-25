package dev.utils.app;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.util.Half;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 颜色工具类 包括常用的色值
 * Created by Ttt
 */
public final class ColorsUtils {

    private ColorsUtils() {
    }

    // 日志TAG
    private static final String TAG = ColorsUtils.class.getSimpleName();

    /**
     * 0-255 十进值转换成十六进制，如255 就是 ff
     * 255 * 0.x = 十进制 -> 十六进制
     * ============
     * 透明度0-100
     * 00、19、33、4C、66、7F、99、B2、CC、E5、FF
     */

    /** 透明 */
    public static final int TRANSPARENT = 0x00000000;
    /** 白色 */
    public static final int WHITE = 0xffffffff;
    /** 白色 - 半透明 */
    public static final int WHITE_TRANSLUCENT = 0x80ffffff;
    /** 黑色 */
    public static final int BLACK = 0xff000000;
    /** 黑色 - 半透明 */
    public static final int BLACK_TRANSLUCENT = 0x80000000;
    /** 红色 */
    public static final int RED = 0xffff0000;
    /** 红色 - 半透明 */
    public static final int RED_TRANSLUCENT = 0x80ff0000;
    /** 绿色 */
    public static final int GREEN = 0xff00ff00;
    /** 绿色 - 半透明 */
    public static final int GREEN_TRANSLUCENT = 0x8000ff00;
    /** 蓝色 */
    public static final int BLUE = 0xff0000ff;
    /** 蓝色 - 半透明 */
    public static final int BLUE_TRANSLUCENT = 0x800000ff;
    /** 灰色 */
    public static final int GRAY = 0xff969696;
    /** 灰色 - 深的 */
    public static final int GRAY_DARK = 0xffa9a9a9;
    /** 橙色 */
    public static final int ORANGE = 0xffffa500;
    /** 金色 */
    public static final int GOLD = 0xffffd700;
    /** 粉色 */
    public static final int PINK = 0xffffc0cb;
    /** 紫色 */
    public static final int PURPLE = 0xff800080;
    /** 黄色 */
    public static final int YELLOW = 0xffffff00;
    /** 青色 */
    public static final int CYAN = 0xff00ffff;
    /** 番茄色 */
    public static final int TOMATO = 0xffff6347;
    /** 高光 */
    public static final int HIGHLIGHT = 0x33ffffff;
    /** 低光 */
    public static final int LOWLIGHT = 0x33000000;

//    /**
//     * 根据颜色资源Id，获取颜色
//     * @param colorId
//     * @return
//     */
//    public static int getResourcesColor(int colorId) {
//        try {
//            return DevUtils.getContext().getResources().getColor(colorId);
//        } catch (Exception e) {
//            LogPrintUtils.eTag(TAG, e, "getResourcesColor");
//        }
//        return -1;
//    }

    // =

    /**
     * 计算百分比值
     * @param value
     * @param max
     * @return
     */
    public static float percent(float value, float max){
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return value / max;
    }

    /**
     * 计算百分比值
     * @param value
     * @param max
     * @return
     */
    public static float percent(int value, int max){
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return (float) value / (float) max;
    }

    // =

    /**
     * 返回一个颜色中的透明度值(返回10进制)
     * @param color
     * @return
     */
    public static int alpha(int color) {
        return color >>> 24;
    }

    /**
     * 返回一个颜色中的透明度百分比值
     * @param color
     * @return
     */
    public static float alphaPercent(int color){
        return percent(alpha(color), 255);
    }

    // =

    /**
     * 返回一个颜色中红色的色值(返回10进制)
     * @param color
     * @return
     */
    public static int red(int color) {
        return (color >> 16) & 0xFF;
    }

    /**
     * 返回一个颜色中红色的百分比值
     * @param color
     * @return
     */
    public static float redPercent(int color){
        return percent(red(color), 255);
    }

    // =

    /**
     * 返回一个颜色中绿色的色值(返回10进制)
     * @param color
     * @return
     */
    public static int green(int color) {
        return (color >> 8) & 0xFF;
    }

    /**
     * 返回一个颜色中绿色的百分比值
     * @param color
     * @return
     */
    public static float greenPercent(int color){
        return percent(green(color), 255);
    }

    // =

    /**
     * 返回一个颜色中蓝色的色值(返回10进制)
     * @param color
     * @return
     */
    public static int blue(int color) {
        return color & 0xFF;
    }

    /**
     * 返回一个颜色中蓝色的百分比值
     * @param color
     * @return
     */
    public static float bluePercent(int color){
        return percent(blue(color), 255);
    }

    // =

    /**
     * 根据对应的 red、green、blue 生成一个颜色值
     * @param red [0-255]
     * @param green [0-255]
     * @param blue [0-255]
     */
    public static int rgb(int red, int green, int blue) {
        return 0xff000000 | (red << 16) | (green << 8) | blue;
    }

    /**
     * 根据对应的 red、green、blue 生成一个颜色值
     * @param red [0-255]
     * @param green [0-255]
     * @param blue [0-255]
     */
    public static int rgb(float red, float green, float blue) {
        return 0xff000000 |
                ((int) (red   * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) <<  8) |
                (int) (blue  * 255.0f + 0.5f);
    }

    // =

    /**
     * 根据对应的 alpha, red、green、blue 生成一个颜色值 (含透明度)
     * @param alpha [0-255]
     * @param red [0-255]
     * @param green [0-255]
     * @param blue [0-255]
     */
    public static int argb(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * 根据对应的 alpha, red、green、blue 生成一个颜色值 (含透明度)
     * @param alpha [0-255]
     * @param red [0-255]
     * @param green [0-255]
     * @param blue [0-255]
     */
    public static int argb(float alpha, float red, float green, float blue) {
        return ((int) (alpha * 255.0f + 0.5f) << 24) |
                ((int) (red   * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) <<  8) |
                (int) (blue  * 255.0f + 0.5f);
    }


}
