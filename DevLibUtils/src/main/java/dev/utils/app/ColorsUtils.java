package dev.utils.app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

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

    /**
     * 根据颜色资源Id，获取颜色
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        try {
            return DevUtils.getContext().getResources().getColor(colorId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getResourcesColor");
        }
        return -1;
    }

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

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value
     * @param max
     * @param min
     * @return
     */
    public static int clamp(int value, int max, int min) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value
     * @param max
     * @param min
     * @return
     */
    public static float clamp(float value, float max, float min) {
        return value > max ? max : value < min ? min : value;
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

    // =

    /**
     * 判断颜色 RGB 是否有效
     * @param color
     * @return
     */
    public static boolean isRGB(int color){
        int red = red(color);
        int green = green(color);
        int blue = blue(color);
        return (red <= 255 && red >= 0) &&
                (green <= 255 && green >= 0) &&
                (blue <= 255 && blue >= 0);
    }

    /**
     * 判断颜色 ARGB 是否有效
     * @param color
     * @return
     */
    public static boolean isARGB(int color){
        int alpha = alpha(color);
        int red = red(color);
        int green = green(color);
        int blue = blue(color);
        return (alpha <= 255 && alpha >= 0) &&
                (red <= 255 && red >= 0) &&
                (green <= 255 && green >= 0) &&
                (blue <= 255 && blue >= 0);
    }

    // =

    /**
     * 设置透明度
     * @param color
     * @param alpha [0-255]
     * @return
     */
    public static int setAlpha(@ColorInt final int color, @IntRange(from = 0x0, to = 0xFF) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * 设置透明度
     * @param color
     * @param alpha [0-255]
     * @return
     */
    public static int setAlpha(@ColorInt int color, @FloatRange(from = 0, to = 1) float alpha) {
        return (color & 0x00ffffff) | ((int) (alpha * 255.0f + 0.5f) << 24);
    }

    /**
     * 改变颜色值中的红色色值
     * @param color
     * @param red [0-255]
     * @return
     */
    public static int setRed(@ColorInt int color, @IntRange(from = 0x0, to = 0xFF) int red) {
        return (color & 0xff00ffff) | (red << 16);
    }

    /**
     * 改变颜色值中的红色色值
     * @param color
     * @param red [0-255]
     * @return
     */
    public static int setRed(@ColorInt int color, @FloatRange(from = 0, to = 1) float red) {
        return (color & 0xff00ffff) | ((int) (red * 255.0f + 0.5f) << 16);
    }

    /**
     * 改变颜色值中的绿色色值
     * @param color
     * @param green [0-255]
     * @return
     */
    public static int setGreen(@ColorInt int color, @IntRange(from = 0x0, to = 0xFF) int green) {
        return (color & 0xffff00ff) | (green << 8);
    }

    /**
     * 改变颜色值中的绿色色值
     * @param color
     * @param green [0-255]
     * @return
     */
    public static int setGreen(@ColorInt int color, @FloatRange(from = 0, to = 1) float green) {
        return (color & 0xffff00ff) | ((int) (green * 255.0f + 0.5f) << 8);
    }

    /**
     * 改变颜色值中的蓝色色值
     * @param color
     * @param blue [0-255]
     * @return
     */
    public static int setBlue(@ColorInt int color, @IntRange(from = 0x0, to = 0xFF) int blue) {
        return (color & 0xffffff00) | blue;
    }

    /**
     * 改变颜色值中的蓝色色值
     * @param color
     * @param blue [0-255]
     * @return
     */
    public static int setBlue(int color, float blue) {
        return (color & 0xffffff00) | (int) (blue * 255.0f + 0.5f);
    }

    /**
     * 解析颜色字符串, 返回对应的颜色值
     * 支持的格式:
     * #RRGGBB
     * #AARRGGBB
     * 'red', 'blue', 'green', 'black', 'white', 'gray', 'cyan', 'magenta',
     * 'yellow', 'lightgray', 'darkgray'
     */
    public static int parseColor(String colorString) {
        try {
            return Color.parseColor(colorString);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "parseColor");
        }
        return -1;
    }

    /**
     * 颜色值 转换 RGB颜色 字符串
     * @param colorInt
     * @return
     */
    public static String int2RgbString(@ColorInt int colorInt) {
        colorInt = colorInt & 0x00ffffff;
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        return "#" + color;
    }

    /**
     * 颜色值 转换 ARGB颜色 字符串
     * @param colorInt
     * @return
     */
    public static String int2ArgbString(@ColorInt final int colorInt) {
        String color = Integer.toHexString(colorInt);
        while (color.length() < 6) {
            color = "0" + color;
        }
        while (color.length() < 8) {
            color = "f" + color;
        }
        return "#" + color;
    }

    // =

    /**
     * 获取随机颜色值
     * @return
     */
    public static int getRandomColor() {
        return getRandomColor(true);
    }

    /**
     * 获取随机颜色值
     * @param supportAlpha
     * @return
     */
    public static int getRandomColor(final boolean supportAlpha) {
        int high = supportAlpha ? (int) (Math.random() * 0x100) << 24 : 0xFF000000;
        return high | (int) (Math.random() * 0x1000000);
    }

    /**
     * 判断是否为ARGB格式的十六进制颜色，例如：FF990587
     * @param str
     * @return
     */
    public static boolean judgeColorString(String str) {
        if (str.length() == 8) {
            for (int i = 0; i < str.length(); i++) {
                char cc = str.charAt(i);
                return !(cc != '0' && cc != '1' && cc != '2' && cc != '3' && cc != '4' && cc != '5' && cc != '6' && cc != '7' && cc != '8' && cc != '9' && cc != 'A' && cc != 'B' && cc != 'C' &&
                        cc != 'D' && cc != 'E' && cc != 'F' && cc != 'a' && cc != 'b' && cc != 'c' && cc != 'd' && cc != 'e' && cc != 'f');
            }
        }
        return false;
    }

    /**
     * 将10进制颜色（Int）转换为Drawable对象
     * @param color
     * @return
     */
    public static Drawable intToDrawable(int color) {
        try {
            return new ColorDrawable(color);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "intToDrawable");
        }
        return null;
    }

    /**
     * 将16进制颜色（String）转化为Drawable对象
     * @param color
     * @return
     */
    public static Drawable stringToDrawable(String color) {
        try {
            return new ColorDrawable(parseColor(color));
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "stringToDrawable");
        }
        return null;
    }

    // =

    /**
     * 颜色加深(单独修改 RGB值, 不变动透明度)
     * @param colorStr
     * @param darkValue
     * @return
     */
    public static int setDark(String colorStr, int darkValue){
        int color = parseColor(colorStr);
        if (color == -1) return -1;
        return setDark(color, darkValue);
    }

    /**
     * 颜色加深(单独修改 RGB值, 不变动透明度)
     * @param color
     * @param darkValue
     * @return
     */
    public static int setDark(int color, int darkValue){
        int red = red(color);
        int green = green(color);
        int blue = blue(color);
        // 进行加深(累减)
        red -= darkValue;
        green -= darkValue;
        blue -= darkValue;
        // 进行设置
        color = setRed(color, clamp(red, 255, 0));
        color = setGreen(color, clamp(green, 255, 0));
        color = setBlue(color, clamp(blue, 255, 0));
        return color;
    }

    /**
     * 颜色变浅, 变亮(单独修改 RGB值, 不变动透明度)
     * @param colorStr
     * @param lightValue
     * @return
     */
    public static int setLight(String colorStr, int lightValue){
        int color = parseColor(colorStr);
        if (color == -1) return -1;
        return setLight(color, lightValue);
    }

    /**
     * 颜色变浅, 变亮(单独修改 RGB值, 不变动透明度)
     * @param color
     * @param lightValue
     * @return
     */
    public static int setLight(int color, int lightValue){
        int red = red(color);
        int green = green(color);
        int blue = blue(color);
        // 进行变浅, 变亮(累加)
        red += lightValue;
        green += lightValue;
        blue += lightValue;
        // 进行设置
        color = setRed(color, clamp(red, 255, 0));
        color = setGreen(color, clamp(green, 255, 0));
        color = setBlue(color, clamp(blue, 255, 0));
        return color;
    }

    /**
     * 设置透明度加深
     * @param colorStr
     * @param darkValue
     * @return
     */
    public static int setAlphaDark(String colorStr, int darkValue){
        int color = parseColor(colorStr);
        if (color == -1) return -1;
        return setAlphaDark(color, darkValue);
    }

    /**
     * 设置透明度加深
     * @param color
     * @param darkValue
     * @return
     */
    public static int setAlphaDark(int color, int darkValue){
        int alpha = alpha(color);
        // 透明度加深
        alpha += darkValue;
        // 进行设置
        color = setAlpha(color, clamp(alpha, 255, 0));
        return color;
    }

    /**
     * 设置透明度变浅
     * @param colorStr
     * @param lightValue
     * @return
     */
    public static int setAlphaLight(String colorStr, int lightValue){
        int color = parseColor(colorStr);
        if (color == -1) return -1;
        return setAlphaLight(color, lightValue);
    }

    /**
     * 设置透明度变浅
     * @param color
     * @param lightValue
     * @return
     */
    public static int setAlphaLight(int color, int lightValue){
        int alpha = alpha(color);
        // 透明度变浅
        alpha -= lightValue;
        // 进行设置
        color = setAlpha(color, clamp(alpha, 255, 0));
        return color;
    }
}
