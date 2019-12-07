package dev.engine.json;

import java.lang.reflect.Type;

/**
 * detail: JSON Engine
 * @author Ttt
 * <pre>
 *     Application: DevJSONEngine.initEngine()
 *     use: DevJSONEngine.xxx
 * </pre>
 */
public final class DevJSONEngine {

    private DevJSONEngine() {
    }

    // IJSONEngine
    private static IJSONEngine sJSONEngine;

    /**
     * 初始化 Engine
     * @param jsonEngine {@link IJSONEngine}
     */
    public static void initEngine(final IJSONEngine jsonEngine) {
        DevJSONEngine.sJSONEngine = jsonEngine;
    }

    // ===============
    // = IJSONEngine =
    // ===============

    // ============
    // = 转换方法 =
    // ============

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJson(final Object object) {
        if (sJSONEngine != null) {
            return sJSONEngine.toJson(object);
        }
        return null;
    }

    /**
     * 将对象转换为 JSON String
     * @param object     {@link Object}
     * @param jsonConfig {@link IJSONEngine.JSONConfig}
     * @return JSON String
     */
    public static String toJson(final Object object, final IJSONEngine.JSONConfig jsonConfig) {
        if (sJSONEngine != null) {
            return sJSONEngine.toJson(object, jsonConfig);
        }
        return null;
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param <T>      泛型
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Class<T> classOfT) {
        if (sJSONEngine != null) {
            return sJSONEngine.fromJson(json, classOfT);
        }
        return null;
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json       JSON String
     * @param classOfT   {@link Class} T
     * @param jsonConfig {@link IJSONEngine.JSONConfig}
     * @param <T>        泛型
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Class<T> classOfT, final IJSONEngine.JSONConfig jsonConfig) {
        if (sJSONEngine != null) {
            return sJSONEngine.fromJson(json, classOfT, jsonConfig);
        }
        return null;
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT {@link Type} T
     * @param <T>     泛型
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Type typeOfT) {
        if (sJSONEngine != null) {
            return sJSONEngine.fromJson(json, typeOfT);
        }
        return null;
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json       JSON String
     * @param typeOfT    {@link Type} T
     * @param jsonConfig {@link IJSONEngine.JSONConfig}
     * @param <T>        泛型
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Type typeOfT, final IJSONEngine.JSONConfig jsonConfig) {
        if (sJSONEngine != null) {
            return sJSONEngine.fromJson(json, typeOfT, jsonConfig);
        }
        return null;
    }

    // ============
    // = 其他方法 =
    // ============

    /**
     * 判断字符串是否 JSON 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSON(final String json) {
        if (sJSONEngine != null) {
            return sJSONEngine.isJSON(json);
        }
        return false;
    }

    /**
     * 判断字符串是否 JSON Object 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSONObject(final String json) {
        if (sJSONEngine != null) {
            return sJSONEngine.isJSONObject(json);
        }
        return false;
    }

    /**
     * 判断字符串是否 JSON Array 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSONArray(final String json) {
        if (sJSONEngine != null) {
            return sJSONEngine.isJSONArray(json);
        }
        return false;
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    public static String toJsonIndent(final String json) {
        if (sJSONEngine != null) {
            return sJSONEngine.toJsonIndent(json);
        }
        return null;
    }

    /**
     * JSON String 缩进处理
     * @param json       JSON String
     * @param jsonConfig {@link IJSONEngine.JSONConfig}
     * @return JSON String
     */
    public static String toJsonIndent(final String json, final IJSONEngine.JSONConfig jsonConfig) {
        if (sJSONEngine != null) {
            return sJSONEngine.toJsonIndent(json, jsonConfig);
        }
        return null;
    }

    // =

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJsonIndent(final Object object) {
        if (sJSONEngine != null) {
            return sJSONEngine.toJsonIndent(object);
        }
        return null;
    }

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object     {@link Object}
     * @param jsonConfig {@link IJSONEngine.JSONConfig}
     * @return JSON String
     */
    public static String toJsonIndent(final Object object, final IJSONEngine.JSONConfig jsonConfig) {
        if (sJSONEngine != null) {
            return sJSONEngine.toJsonIndent(object, jsonConfig);
        }
        return null;
    }
}
