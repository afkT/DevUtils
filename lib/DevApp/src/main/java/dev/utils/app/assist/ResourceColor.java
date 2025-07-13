package dev.utils.app.assist;

import android.content.Context;
import android.content.res.Resources;
import android.util.ArrayMap;
import android.util.SparseIntArray;

import androidx.annotation.ColorRes;

import dev.utils.DevFinal;
import dev.utils.common.ColorUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Resources Color 辅助类
 * @author Ttt
 */
public final class ResourceColor {

    // ResourceColor 实例
    private static volatile ResourceColor sInstance;

    /**
     * 获取 ResourceColor 实例
     * @return {@link ResourceColor}
     */
    private static ResourceColor getInstance() {
        if (sInstance == null) {
            synchronized (ResourceColor.class) {
                if (sInstance == null) {
                    sInstance = new ResourceColor();
                }
            }
        }
        return sInstance;
    }

    // 日志 TAG
    private static final String TAG = ResourceColor.class.getSimpleName();

    // Resources 辅助类
    private ResourceAssist mAssist;

    // ==========
    // = 构造函数 =
    // ==========

    private ResourceColor() {
        this(ResourceAssist.get());
    }

    private ResourceColor(final Context context) {
        this(ResourceAssist.get(context));
    }

    private ResourceColor(final Resources resource) {
        this(ResourceAssist.get(resource));
    }

    private ResourceColor(final ResourceAssist assist) {
        this.mAssist = assist;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 获取 ResourceColor
     * @return {@link ResourceColor}
     */
    public static ResourceColor get() {
        return getInstance();
    }

    /**
     * 获取 ResourceColor
     * @param context {@link Context}
     * @return {@link ResourceColor}
     */
    public static ResourceColor get(final Context context) {
        return new ResourceColor(context);
    }

    /**
     * 获取 ResourceColor
     * @param resource {@link Resources}
     * @return {@link ResourceColor}
     */
    public static ResourceColor get(final Resources resource) {
        return new ResourceColor(resource);
    }

    // ==========
    // = 资源获取 =
    // ==========

    // =============
    // = Int Color =
    // =============

    /**
     * 获取 Color
     * @param colorId R.color.id
     * @return Color
     */
    public int getColor(@ColorRes final int colorId) {
        return _getIntColor(colorId, DF_ERROR);
    }

    /**
     * 获取 Color
     * @param colorId      R.color.id
     * @param defaultValue 默认值
     * @return Color
     */
    public int getColor(
            @ColorRes final int colorId,
            final int defaultValue
    ) {
        return _getIntColor(colorId, defaultValue);
    }

    // ===================
    // = R.color.id Color =
    // ===================

    /**
     * 通过 R.color.id 获取 Color
     * @param resName resource name
     * @return Color
     */
    public int getColorById(final String resName) {
        return _getIntColorById(resName, DF_ERROR);
    }

    /**
     * 通过 R.color.id 获取 Color
     * @param resName      resource name
     * @param defaultValue 默认值
     * @return Color
     */
    public int getColorById(
            final String resName,
            final int defaultValue
    ) {
        return _getIntColorById(resName, defaultValue);
    }

    // ==============
    // = ARGB Color =
    // ==============

    /**
     * 通过 argb/rgb color String 获取 Color
     * @param colorStr argb/rgb color String
     * @return Color
     */
    public int getColorByARGB(final String colorStr) {
        return _getStringColor(colorStr, DF_ERROR);
    }

    /**
     * 通过 argb/rgb color String 获取 Color
     * @param colorStr     argb/rgb color String
     * @param defaultValue 默认值
     * @return Color
     */
    public int getColorByARGB(
            final String colorStr,
            final int defaultValue
    ) {
        return _getStringColor(colorStr, defaultValue);
    }


    // ==========
    // = 内部方法 =
    // ==========

    // 默认值
    public static final int DF_INT = DevFinal.DEFAULT.INT;
    public static final int DF_ERROR = DevFinal.DEFAULT.ERROR_INT;

    // Int Color 颜色存储
    private final SparseIntArray mIntColorMap = new SparseIntArray(10);

    // R.color.id Color 颜色存储
    private final ArrayMap<String, Integer> mIntColorIdMap = new ArrayMap<>(10);

    // String Color 颜色存储
    private final ArrayMap<String, Integer> mStringColorMap = new ArrayMap<>(10);

    /**
     * 获取 Color
     * @param colorId      R.color.id
     * @param defaultValue 默认值
     * @return Color
     */
    private int _getIntColor(
            @ColorRes final int colorId,
            final int defaultValue
    ) {
        if (mAssist == null) return defaultValue;
        int value = mIntColorMap.get(colorId, DF_ERROR);
        if (value != DF_ERROR) return value;
        value = mAssist.getColor(colorId);
        if (value == DF_ERROR) return defaultValue;
        mIntColorMap.put(colorId, value);
        return value;
    }

    /**
     * 通过 R.color.id 获取 Color
     * @param resName      resource name
     * @param defaultValue 默认值
     * @return Color
     */
    private int _getIntColorById(
            final String resName,
            final int defaultValue
    ) {
        if (StringUtils.isEmpty(resName)) return defaultValue;
        if (mAssist == null) return defaultValue;
        Integer value = mIntColorIdMap.get(resName);
        if (value != null) return value;
        int colorId = mAssist.getColorId(resName);
        if (colorId == DF_INT) return defaultValue;
        value = mAssist.getColor(colorId);
        if (value == DF_ERROR) return defaultValue;
        mIntColorIdMap.put(resName, value);
        return value;
    }

    /**
     * 通过 argb/rgb color String 获取 Color
     * @param colorStr     argb/rgb color String
     * @param defaultValue 默认值
     * @return Color
     */
    private int _getStringColor(
            final String colorStr,
            final int defaultValue
    ) {
        if (StringUtils.isEmpty(colorStr)) return defaultValue;
        Integer value = mStringColorMap.get(colorStr);
        if (value != null) return value;
        value = ColorUtils.parseColor(colorStr);
        if (value == DF_ERROR) return defaultValue;
        mStringColorMap.put(colorStr, value);
        return value;
    }
}