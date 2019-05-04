package dev.utils.common;

import java.util.HashMap;
import java.util.Locale;

import dev.utils.JCLogUtils;

/**
 * detail: 颜色工具类(包括常用的色值)
 * @author Ttt
 */
public final class ColorUtils {

    private ColorUtils() {
    }

    // 日志 TAG
    private static final String TAG = ColorUtils.class.getSimpleName();
    // 透明
    public static final int TRANSPARENT = 0x00000000;
    // 白色
    public static final int WHITE = 0xffffffff;
    // 白色 - 半透明
    public static final int WHITE_TRANSLUCENT = 0x80ffffff;
    // 黑色
    public static final int BLACK = 0xff000000;
    // 黑色 - 半透明
    public static final int BLACK_TRANSLUCENT = 0x80000000;
    // 红色
    public static final int RED = 0xffff0000;
    // 红色 - 半透明
    public static final int RED_TRANSLUCENT = 0x80ff0000;
    // 绿色
    public static final int GREEN = 0xff00ff00;
    // 绿色 - 半透明
    public static final int GREEN_TRANSLUCENT = 0x8000ff00;
    // 蓝色
    public static final int BLUE = 0xff0000ff;
    // 蓝色 - 半透明
    public static final int BLUE_TRANSLUCENT = 0x800000ff;
    // 灰色
    public static final int GRAY = 0xff969696;
    // 灰色 - 半透明
    public static final int GRAY_TRANSLUCENT = 0x80969696;
    // 天蓝
    public static final int SKYBLUE = 0xff87ceeb;
    // 橙色
    public static final int ORANGE = 0xffffa500;
    // 金色
    public static final int GOLD = 0xffffd700;
    // 粉色
    public static final int PINK = 0xffffc0cb;
    // 紫红色
    public static final int FUCHSIA = 0xffff00ff;
    // 灰白色
    public static final int GRAYWHITE = 0xfff2f2f2;
    // 紫色
    public static final int PURPLE = 0xff800080;
    // 青色
    public static final int CYAN = 0xff00ffff;
    // 黄色
    public static final int YELLOW = 0xffffff00;
    // 巧克力色
    public static final int CHOCOLATE = 0xffd2691e;
    // 番茄色
    public static final int TOMATO = 0xffff6347;
    // 橙红色
    public static final int ORANGERED = 0xffff4500;
    // 银白色
    public static final int SILVER = 0xffc0c0c0;
    // 深灰色
    public static final int DKGRAY = 0xFF444444;
    // 亮灰色
    public static final int LTGRAY = 0xFFCCCCCC;
    // 洋红色
    public static final int MAGENTA = 0xFFFF00FF;
    // 高光
    public static final int HIGHLIGHT = 0x33ffffff;
    // 低光
    public static final int LOWLIGHT = 0x33000000;

    /**
     * 0-255 十进值转换成十六进制，如 255 就是 ff
     * 255 * 0.x = 十进制 -> 十六进制
     * <p></p>
     * 透明度0-100
     * 00、19、33、4C、66、7F、99、B2、CC、E5、FF
     */

    /**
     * 获取十六进制透明度字符串
     * @param alpha 0-255
     * @return 透明度（十六进制）值
     */
    public static String toHexAlpha(final int alpha) {
        try {
            if (alpha >= 0 && alpha <= 255) {
                return Integer.toHexString(alpha);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexAlpha");
        }
        return null;
    }

    // =

    /**
     * 返回一个颜色中的透明度值(返回10进制)
     * @param color argb color
     * @return alpha 值
     */
    public static int alpha(final int color) {
        return color >>> 24;
    }

    /**
     * 返回一个颜色中的透明度百分比值
     * @param color argb color
     * @return alpha 百分比值
     */
    public static float alphaPercent(final int color) {
        return percent(alpha(color), 255);
    }

    // =

    /**
     * 返回一个颜色中红色的色值(返回10进制)
     * @param color argb/rgb color
     * @return red 值
     */
    public static int red(final int color) {
        return (color >> 16) & 0xFF;
    }

    /**
     * 返回一个颜色中红色的百分比值
     * @param color argb/rgb color
     * @return red 百分比值
     */
    public static float redPercent(final int color) {
        return percent(red(color), 255);
    }

    // =

    /**
     * 返回一个颜色中绿色的色值(返回10进制)
     * @param color argb/rgb color
     * @return green 百分比值
     */
    public static int green(final int color) {
        return (color >> 8) & 0xFF;
    }

    /**
     * 返回一个颜色中绿色的百分比值
     * @param color argb/rgb color
     * @return green 百分比值
     */
    public static float greenPercent(final int color) {
        return percent(green(color), 255);
    }

    // =

    /**
     * 返回一个颜色中蓝色的色值(返回10进制)
     * @param color argb/rgb color
     * @return blue 百分比值
     */
    public static int blue(final int color) {
        return color & 0xFF;
    }

    /**
     * 返回一个颜色中蓝色的百分比值
     * @param color argb/rgb color
     * @return blue 百分比值
     */
    public static float bluePercent(final int color) {
        return percent(blue(color), 255);
    }

    // =

    /**
     * 根据对应的 red、green、blue 生成一个颜色值
     * @param red   红色值 [0-255]
     * @param green 绿色值 [0-255]
     * @param blue  蓝色值 [0-255]
     * @return rgb 颜色值
     */
    public static int rgb(final int red, final int green, final int blue) {
        return 0xff000000 | (red << 16) | (green << 8) | blue;
    }

    /**
     * 根据对应的 red、green、blue 生成一个颜色值
     * @param red   红色值 [0-255]
     * @param green 绿色值 [0-255]
     * @param blue  蓝色值 [0-255]
     * @return rgb 颜色值
     */
    public static int rgb(final float red, final float green, final float blue) {
        return 0xff000000 |
                ((int) (red * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) << 8) |
                (int) (blue * 255.0f + 0.5f);
    }

    // =

    /**
     * 根据对应的 alpha, red、green、blue 生成一个颜色值 (含透明度)
     * @param alpha 透明度 [0-255]
     * @param red   红色值 [0-255]
     * @param green 绿色值 [0-255]
     * @param blue  蓝色值 [0-255]
     * @return argb 颜色值
     */
    public static int argb(final int alpha, final int red, final int green, final int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * 根据对应的 alpha, red、green、blue 生成一个颜色值 (含透明度)
     * @param alpha 透明度 [0-255]
     * @param red   红色值 [0-255]
     * @param green 绿色值 [0-255]
     * @param blue  蓝色值 [0-255]
     * @return argb 颜色值
     */
    public static int argb(final float alpha, final float red, final float green, final float blue) {
        return ((int) (alpha * 255.0f + 0.5f) << 24) |
                ((int) (red * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) << 8) |
                (int) (blue * 255.0f + 0.5f);
    }

    // =

    /**
     * 判断颜色 RGB 是否有效
     * @param color rgb color
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRGB(final int color) {
        int red = red(color);
        int green = green(color);
        int blue = blue(color);
        return (red <= 255 && red >= 0) &&
                (green <= 255 && green >= 0) &&
                (blue <= 255 && blue >= 0);
    }

    /**
     * 判断颜色 ARGB 是否有效
     * @param color argb color
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isARGB(final int color) {
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
     * @param color argb/rgb color
     * @param alpha 透明度 [0-255]
     * @return argb 颜色值
     */
    public static int setAlpha(final int color, final int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * 设置透明度
     * @param color argb/rgb color
     * @param alpha 透明度 [0-255]
     * @return argb 颜色值
     */
    public static int setAlpha(final int color, final float alpha) {
        return (color & 0x00ffffff) | ((int) (alpha * 255.0f + 0.5f) << 24);
    }

    /**
     * 改变颜色值中的红色色值
     * @param color argb/rgb color
     * @param red   红色值 [0-255]
     * @return argb/rgb 颜色值
     */
    public static int setRed(final int color, final int red) {
        return (color & 0xff00ffff) | (red << 16);
    }

    /**
     * 改变颜色值中的红色色值
     * @param color argb/rgb color
     * @param red   红色值 [0-255]
     * @return argb/rgb 颜色值
     */
    public static int setRed(final int color, final float red) {
        return (color & 0xff00ffff) | ((int) (red * 255.0f + 0.5f) << 16);
    }

    /**
     * 改变颜色值中的绿色色值
     * @param color argb/rgb color
     * @param green 绿色值 [0-255]
     * @return argb/rgb 颜色值
     */
    public static int setGreen(final int color, final int green) {
        return (color & 0xffff00ff) | (green << 8);
    }

    /**
     * 改变颜色值中的绿色色值
     * @param color argb/rgb color
     * @param green 绿色值 [0-255]
     * @return argb/rgb 颜色值
     */
    public static int setGreen(final int color, final float green) {
        return (color & 0xffff00ff) | ((int) (green * 255.0f + 0.5f) << 8);
    }

    /**
     * 改变颜色值中的蓝色色值
     * @param color argb/rgb color
     * @param blue  蓝色值 [0-255]
     * @return argb/rgb 颜色值
     */
    public static int setBlue(final int color, final int blue) {
        return (color & 0xffffff00) | blue;
    }

    /**
     * 改变颜色值中的蓝色色值
     * @param color argb/rgb color
     * @param blue  蓝色值 [0-255]
     * @return argb/rgb 颜色值
     */
    public static int setBlue(final int color, final float blue) {
        return (color & 0xffffff00) | (int) (blue * 255.0f + 0.5f);
    }

    // =

    /**
     * 解析颜色字符串, 返回对应的颜色值
     * @param colorStr argb/rgb color String
     * @return argb/rgb 颜色值
     */
    private static int priParseColor(final String colorStr) {
        if (colorStr.charAt(0) == '#') {
            // Use a long to avoid rollovers on #ffXXXXXX
            long color = Long.parseLong(colorStr.substring(1), 16);
            if (colorStr.length() == 7) {
                // Set the alpha value
                color |= 0x00000000ff000000;
            } else if (colorStr.length() != 9) {
                throw new IllegalArgumentException("Unknown color");
            }
            return (int) color;
        } else {
            Integer color = sColorNameMap.get(colorStr.toLowerCase(Locale.ROOT));
            if (color != null) {
                return color;
            }
        }
        throw new IllegalArgumentException("Unknown color");
    }

    /**
     * 解析颜色字符串, 返回对应的颜色值
     * <pre>
     *      支持的格式:
     *      #RRGGBB
     *      #AARRGGBB
     *      'red', 'blue', 'green', 'black', 'white', 'gray', 'cyan', 'magenta', 'yellow', 'lightgray', 'darkgray'
     * </pre>
     * @param colorStr argb/rgb color String
     * @return argb/rgb 颜色值
     */
    public static int parseColor(final String colorStr) {
        try {
            return priParseColor(colorStr);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseColor");
        }
        return -1;
    }

    /**
     * 颜色值 转换 RGB颜色 字符串
     * @param colorInt rgb int color
     * @return rgb color String
     */
    public static String intToRgbString(final int colorInt) {
        int color = colorInt;
        color = color & 0x00ffffff;
        String colorStr = Integer.toHexString(color);
        while (colorStr.length() < 6) {
            colorStr = "0" + colorStr;
        }
        return "#" + colorStr;
    }

    /**
     * 颜色值 转换 ARGB颜色 字符串
     * @param colorInt argb int color
     * @return argb color String
     */
    public static String intToArgbString(final int colorInt) {
        String colorString = Integer.toHexString(colorInt);
        while (colorString.length() < 6) {
            colorString = "0" + colorString;
        }
        while (colorString.length() < 8) {
            colorString = "f" + colorString;
        }
        return "#" + colorString;
    }

    // =

    /**
     * 获取随机颜色值
     * @return 随机颜色值
     */
    public static int getRandomColor() {
        return getRandomColor(true);
    }

    /**
     * 获取随机颜色值
     * @param supportAlpha 是否支持透明度
     * @return argb/rgb 颜色值
     */
    public static int getRandomColor(final boolean supportAlpha) {
        int high = supportAlpha ? (int) (Math.random() * 0x100) << 24 : 0xFF000000;
        return high | (int) (Math.random() * 0x1000000);
    }

    /**
     * 判断是否为ARGB格式的十六进制颜色，例如: FF990587
     * @param colorStr color String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean judgeColorString(final String colorStr) {
        if (colorStr != null && colorStr.length() == 8) {
            for (int i = 0; i < colorStr.length(); i++) {
                char cc = colorStr.charAt(i);
                return !(cc != '0' && cc != '1' && cc != '2' && cc != '3' && cc != '4' && cc != '5' && cc != '6' && cc != '7' && cc != '8' && cc != '9'
                        && cc != 'A' && cc != 'B' && cc != 'C' && cc != 'D' && cc != 'E' && cc != 'F'
                        && cc != 'a' && cc != 'b' && cc != 'c' && cc != 'd' && cc != 'e' && cc != 'f');
            }
        }
        return false;
    }

    // =

    /**
     * 颜色加深(单独修改 RGB值, 不变动透明度)
     * @param colorStr  color String
     * @param darkValue 加深值
     * @return 加深后的颜色值
     */
    public static int setDark(final String colorStr, final int darkValue) {
        int color = parseColor(colorStr);
        if (color == -1) return -1;
        return setDark(color, darkValue);
    }

    /**
     * 颜色加深(单独修改 RGB值, 不变动透明度)
     * @param color     int color
     * @param darkValue 加深值
     * @return 加深后的颜色值
     */
    public static int setDark(final int color, final int darkValue) {
        int red = red(color);
        int green = green(color);
        int blue = blue(color);
        // 进行加深(累减)
        red -= darkValue;
        green -= darkValue;
        blue -= darkValue;
        // 颜色值
        int colorTemp = color;
        // 进行设置
        colorTemp = setRed(colorTemp, clamp(red, 255, 0));
        colorTemp = setGreen(colorTemp, clamp(green, 255, 0));
        colorTemp = setBlue(colorTemp, clamp(blue, 255, 0));
        return colorTemp;
    }

    /**
     * 颜色变浅, 变亮(单独修改 RGB值, 不变动透明度)
     * @param colorStr   color String
     * @param lightValue 变亮(变浅)值
     * @return 变亮(变浅)后的颜色值
     */
    public static int setLight(final String colorStr, final int lightValue) {
        int color = parseColor(colorStr);
        if (color == -1) return -1;
        return setLight(color, lightValue);
    }

    /**
     * 颜色变浅, 变亮(单独修改 RGB值, 不变动透明度)
     * @param color      int color
     * @param lightValue 变亮(变浅)值
     * @return 变亮(变浅)后的颜色值
     */
    public static int setLight(final int color, final int lightValue) {
        int red = red(color);
        int green = green(color);
        int blue = blue(color);
        // 进行变浅, 变亮(累加)
        red += lightValue;
        green += lightValue;
        blue += lightValue;
        // 颜色值
        int colorTemp = color;
        // 进行设置
        colorTemp = setRed(colorTemp, clamp(red, 255, 0));
        colorTemp = setGreen(colorTemp, clamp(green, 255, 0));
        colorTemp = setBlue(colorTemp, clamp(blue, 255, 0));
        return colorTemp;
    }

    /**
     * 设置透明度加深
     * @param colorStr  color String
     * @param darkValue 加深值
     * @return 透明度加深后的颜色值
     */
    public static int setAlphaDark(final String colorStr, final int darkValue) {
        int color = parseColor(colorStr);
        if (color == -1) return -1;
        return setAlphaDark(color, darkValue);
    }

    /**
     * 设置透明度加深
     * @param color     int color
     * @param darkValue 加深值
     * @return 透明度加深后的颜色值
     */
    public static int setAlphaDark(final int color, final int darkValue) {
        int alpha = alpha(color);
        // 透明度加深
        alpha += darkValue;
        // 进行设置
        return setAlpha(color, clamp(alpha, 255, 0));
    }

    /**
     * 设置透明度变浅
     * @param colorStr   color String
     * @param lightValue 变浅值
     * @return 透明度变浅后的颜色值
     */
    public static int setAlphaLight(final String colorStr, final int lightValue) {
        int color = parseColor(colorStr);
        if (color == -1) return -1;
        return setAlphaLight(color, lightValue);
    }

    /**
     * 设置透明度变浅
     * @param color      int color
     * @param lightValue 变浅值
     * @return 透明度变浅后的颜色值
     */
    public static int setAlphaLight(final int color, final int lightValue) {
        int alpha = alpha(color);
        // 透明度变浅
        alpha -= lightValue;
        // 进行设置
        return setAlpha(color, clamp(alpha, 255, 0));
    }

    // =

    // 颜色字典集合
    private static final HashMap<String, Integer> sColorNameMap;

    static {
        sColorNameMap = new HashMap<>();
        sColorNameMap.put("transparent", TRANSPARENT);
        sColorNameMap.put("white", WHITE);
        sColorNameMap.put("black", BLACK);
        sColorNameMap.put("red", RED);
        sColorNameMap.put("green", GREEN);
        sColorNameMap.put("blue", BLUE);
        sColorNameMap.put("gray", GRAY);
        sColorNameMap.put("grey", GRAY);
        sColorNameMap.put("skyblue", SKYBLUE);
        sColorNameMap.put("orange", ORANGE);
        sColorNameMap.put("gold", GOLD);
        sColorNameMap.put("pink", PINK);
        sColorNameMap.put("fuchsia", FUCHSIA);
        sColorNameMap.put("graywhite", GRAYWHITE);
        sColorNameMap.put("purple", PURPLE);
        sColorNameMap.put("cyan", CYAN);
        sColorNameMap.put("yellow", YELLOW);
        sColorNameMap.put("chocolate", CHOCOLATE);
        sColorNameMap.put("tomato", TOMATO);
        sColorNameMap.put("orangered", ORANGERED);
        sColorNameMap.put("silver", SILVER);
        sColorNameMap.put("darkgray", DKGRAY);
        sColorNameMap.put("lightgray", LTGRAY);
        sColorNameMap.put("lightgrey", LTGRAY);
        sColorNameMap.put("magenta", MAGENTA);
        sColorNameMap.put("highlight", HIGHLIGHT);
        sColorNameMap.put("lowlight", LOWLIGHT);
        sColorNameMap.put("aqua", 0xFF00FFFF);
        sColorNameMap.put("lime", 0xFF00FF00);
        sColorNameMap.put("maroon", 0xFF800000);
        sColorNameMap.put("navy", 0xFF000080);
        sColorNameMap.put("olive", 0xFF808000);
        sColorNameMap.put("teal", 0xFF008080);
    }

    // =

    /**
     * 计算百分比值 (最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    private static float percent(final int value, final int max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return (float) value / (float) max;
    }

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min之间的 value
     */
    private static int clamp(final int value, final int max, final int min) {
        return value > max ? max : value < min ? min : value;
    }
}
