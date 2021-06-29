package dev.capture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.utils.LogPrintUtils;

class Utils {

    private Utils() {
    }

    // 日志 TAG
    private static final String TAG = Utils.class.getSimpleName();

    // ========
    // = Gson =
    // ========

    private static final Gson GSON = createGson(false).create();

    /**
     * 创建 GsonBuilder
     * @param serializeNulls 是否序列化 null 值
     * @return {@link GsonBuilder}
     */
    public static GsonBuilder createGson(final boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) builder.serializeNulls();
        return builder;
    }

    // ==========
    // = 转换方法 =
    // ==========

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJson(final Object object) {
        try {
            return GSON.toJson(object);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toJson");
        }
        return null;
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param <T>      泛型
     * @return instance of type
     */
    public static <T> T fromJson(
            final String json,
            final Class<T> classOfT
    ) {
        try {
            return GSON.fromJson(json, classOfT);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "fromJson");
        }
        return null;
    }
}
