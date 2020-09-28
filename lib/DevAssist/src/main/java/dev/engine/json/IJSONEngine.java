package dev.engine.json;

import java.lang.reflect.Type;

/**
 * detail: JSON Engine 接口
 * @author Ttt
 */
public interface IJSONEngine {

    /**
     * detail: JSON Config
     * @author Ttt
     */
    class JSONConfig {
    }

    // ===========
    // = 转换方法 =
    // ===========

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @return JSON String
     */
    String toJson(Object object);

    /**
     * 将对象转换为 JSON String
     * @param object     {@link Object}
     * @param jsonConfig {@link JSONConfig}
     * @return JSON String
     */
    String toJson(Object object, JSONConfig jsonConfig);

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param <T>      泛型
     * @return instance of type
     */
    <T> T fromJson(String json, Class<T> classOfT);

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json       JSON String
     * @param classOfT   {@link Class} T
     * @param jsonConfig {@link JSONConfig}
     * @param <T>        泛型
     * @return instance of type
     */
    <T> T fromJson(String json, Class<T> classOfT, JSONConfig jsonConfig);

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT {@link Type} T
     * @param <T>     泛型
     * @return instance of type
     */
    <T> T fromJson(String json, Type typeOfT);

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json       JSON String
     * @param typeOfT    {@link Type} T
     * @param jsonConfig {@link JSONConfig}
     * @param <T>        泛型
     * @return instance of type
     */
    <T> T fromJson(String json, Type typeOfT, JSONConfig jsonConfig);

    // ===========
    // = 其他方法 =
    // ===========

    /**
     * 判断字符串是否 JSON 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    boolean isJSON(String json);

    /**
     * 判断字符串是否 JSON Object 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    boolean isJSONObject(final String json);

    /**
     * 判断字符串是否 JSON Array 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    boolean isJSONArray(final String json);

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    String toJsonIndent(String json);

    /**
     * JSON String 缩进处理
     * @param json       JSON String
     * @param jsonConfig {@link JSONConfig}
     * @return JSON String
     */
    String toJsonIndent(String json, JSONConfig jsonConfig);

    // =

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object {@link Object}
     * @return JSON String
     */
    String toJsonIndent(Object object);

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object     {@link Object}
     * @param jsonConfig {@link JSONConfig}
     * @return JSON String
     */
    String toJsonIndent(Object object, JSONConfig jsonConfig);
}