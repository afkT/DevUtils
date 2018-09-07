package dev.utils.common;

/**
 * detail: 颜色工具类 包括常用的色值
 * Created by Ttt
 */
public final class ColorsUtils {

    private ColorsUtils() {
    }

    /**
    黑色 透明度10-90
    19
    33
    4C
    66
    7F
    99
    B2
    CC
    E5
     */

    // 0-255 十进值转换成十六进制，如255 就是 ff
    // 百分之10 透明度 = 19
    // 百分之20 透明度 = 33
    // 255 * 0.x = 十进制 -> 十六进制
    // import android.graphics.Color;
    // Color.argb()
    // Color.rgb()

    /** 白色 */
    public static final int WHITE = 0xffffffff;

    /** 白色 - 半透明 */
    public static final int WHITE_TRANSLUCENT = 0x80ffffff;

    /** 黑色 */
    public static final int BLACK = 0xff000000;

    /** 黑色 - 半透明 */
    public static final int BLACK_TRANSLUCENT = 0x80000000;

    /** 透明 */
    public static final int TRANSPARENT = 0x00000000;

    /** 红色 */
    public static final int RED = 0xffff0000;

    /** 红色 - 半透明 */
    public static final int RED_TRANSLUCENT = 0x80ff0000;

    /** 红色 - 深的 */
    public static final int RED_DARK = 0xff8b0000;

    /** 红色 - 深的 - 半透明 */
    public static final int RED_DARK_TRANSLUCENT = 0x808b0000;

    /** 绿色 */
    public static final int GREEN = 0xff00ff00;

    /** 绿色 - 半透明 */
    public static final int GREEN_TRANSLUCENT = 0x8000ff00;

    /** 绿色 - 深的 */
    public static final int GREEN_DARK = 0xff003300;

    /** 绿色 - 深的 - 半透明 */
    public static final int GREEN_DARK_TRANSLUCENT = 0x80003300;

    /** 绿色 - 浅的 */
    public static final int GREEN_LIGHT = 0xffccffcc;

    /** 绿色 - 浅的 - 半透明 */
    public static final int GREEN_LIGHT_TRANSLUCENT = 0x80ccffcc;

    /** 蓝色 */
    public static final int BLUE = 0xff0000ff;

    /** 蓝色 - 半透明 */
    public static final int BLUE_TRANSLUCENT = 0x800000ff;

    /** 蓝色 - 深的 */
    public static final int BLUE_DARK = 0xff00008b;

    /** 蓝色 - 深的 - 半透明 */
    public static final int BLUE_DARK_TRANSLUCENT = 0x8000008b;

    /** 蓝色 - 浅的 */
    public static final int BLUE_LIGHT = 0xff36a5E3;

    /** 蓝色 - 浅的 - 半透明 */
    public static final int BLUE_LIGHT_TRANSLUCENT = 0x8036a5E3;

    /** 天蓝 */
    public static final int SKYBLUE = 0xff87ceeb;

    /** 天蓝 - 半透明 */
    public static final int SKYBLUE_TRANSLUCENT = 0x8087ceeb;

    /** 天蓝 - 深的 */
    public static final int SKYBLUE_DARK = 0xff00bfff;

    /** 天蓝 - 深的 - 半透明 */
    public static final int SKYBLUE_DARK_TRANSLUCENT = 0x8000bfff;

    /** 天蓝 - 浅的 */
    public static final int SKYBLUE_LIGHT = 0xff87cefa;

    /** 天蓝 - 浅的 - 半透明 */
    public static final int SKYBLUE_LIGHT_TRANSLUCENT = 0x8087cefa;

    /** 灰色 */
    public static final int GRAY = 0xff969696;

    /** 灰色 - 半透明 */
    public static final int GRAY_TRANSLUCENT = 0x80969696;

    /** 灰色 - 深的 */
    public static final int GRAY_DARK = 0xffa9a9a9;

    /** 灰色 - 深的 - 半透明 */
    public static final int GRAY_DARK_TRANSLUCENT = 0x80a9a9a9;

    /** 灰色 - 暗的 */
    public static final int GRAY_DIM = 0xff696969;

    /** 灰色 - 暗的 - 半透明 */
    public static final int GRAY_DIM_TRANSLUCENT = 0x80696969;

    /** 灰色 - 浅的 */
    public static final int GRAY_LIGHT = 0xffd3d3d3;

    /** 灰色 - 浅的 - 半透明 */
    public static final int GRAY_LIGHT_TRANSLUCENT = 0x80d3d3d3;

    /** 橙色 */
    public static final int ORANGE = 0xffffa500;

    /** 橙色 - 半透明 */
    public static final int ORANGE_TRANSLUCENT = 0x80ffa500;

    /** 橙色 - 深的 */
    public static final int ORANGE_DARK = 0xffff8800;

    /** 橙色 - 深的 - 半透明 */
    public static final int ORANGE_DARK_TRANSLUCENT = 0x80ff8800;

    /** 橙色 - 浅的 */
    public static final int ORANGE_LIGHT = 0xffffbb33;

    /** 橙色 - 浅的 - 半透明 */
    public static final int ORANGE_LIGHT_TRANSLUCENT = 0x80ffbb33;

    /** 金色 */
    public static final int GOLD = 0xffffd700;

    /** 金色 - 半透明 */
    public static final int GOLD_TRANSLUCENT = 0x80ffd700;

    /** 粉色 */
    public static final int PINK = 0xffffc0cb;

    /** 粉色 - 半透明 */
    public static final int PINK_TRANSLUCENT = 0x80ffc0cb;

    /** 紫红色 */
    public static final int FUCHSIA = 0xffff00ff;

    /** 紫红色 - 半透明 */
    public static final int FUCHSIA_TRANSLUCENT = 0x80ff00ff;

    /** 灰白色 */
    public static final int GRAYWHITE = 0xfff2f2f2;

    /** 灰白色 - 半透明 */
    public static final int GRAYWHITE_TRANSLUCENT = 0x80f2f2f2;

    /** 紫色 */
    public static final int PURPLE = 0xff800080;

    /** 紫色 - 半透明 */
    public static final int PURPLE_TRANSLUCENT = 0x80800080;

    /** 青色 */
    public static final int CYAN = 0xff00ffff;

    /** 青色 - 半透明 */
    public static final int CYAN_TRANSLUCENT = 0x8000ffff;

    /** 青色 - 深的 */
    public static final int CYAN_DARK = 0xff008b8b;

    /** 青色 - 深的 - 半透明 */
    public static final int CYAN_DARK_TRANSLUCENT = 0x80008b8b;

    /** 黄色 */
    public static final int YELLOW = 0xffffff00;

    /** 黄色 - 半透明 */
    public static final int YELLOW_TRANSLUCENT = 0x80ffff00;

    /** 黄色 - 浅的 */
    public static final int YELLOW_LIGHT = 0xffffffe0;

    /** 黄色 - 浅的 - 半透明 */
    public static final int YELLOW_LIGHT_TRANSLUCENT = 0x80ffffe0;

    /** 巧克力色 */
    public static final int CHOCOLATE = 0xffd2691e;

    /** 巧克力色 - 半透明 */
    public static final int CHOCOLATE_TRANSLUCENT = 0x80d2691e;

    /** 番茄色 */
    public static final int TOMATO = 0xffff6347;

    /** 番茄色 - 半透明 */
    public static final int TOMATO_TRANSLUCENT = 0x80ff6347;

    /** 橙红色 */
    public static final int ORANGERED = 0xffff4500;

    /** 橙红色 - 半透明 */
    public static final int ORANGERED_TRANSLUCENT = 0x80ff4500;

    /** 银白色 */
    public static final int SILVER = 0xffc0c0c0;

    /** 银白色 - 半透明 */
    public static final int SILVER_TRANSLUCENT = 0x80c0c0c0;

    /** 高光 */
    public static final int HIGHLIGHT = 0x33ffffff;

    /** 低光 */
    public static final int LOWLIGHT = 0x33000000;

}
