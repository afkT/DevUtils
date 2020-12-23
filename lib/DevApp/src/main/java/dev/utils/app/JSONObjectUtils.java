package dev.utils.app;

import android.os.Build;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.utils.LogPrintUtils;

/**
 * detail: Android 原生 JSONObject 工具类
 * @author Ttt
 */
public final class JSONObjectUtils {

    private JSONObjectUtils() {
    }

    // 日志 TAG
    private static final String TAG = JSONObjectUtils.class.getSimpleName();

    // ===================
    // = 转换 JSON 字符串 =
    // ===================

    /**
     * 转换为 JSON 格式字符串
     * <pre>
     *     TODO 不支持 实体类 转 JSON 字符串
     * </pre>
     * @param object Object
     * @return JSON String
     */
    public static String toJson(final Object object) {
        return toJson(object, -1);
    }

    /**
     * 转换为 JSON 格式字符串
     * <pre>
     *     TODO 不支持 实体类 转 JSON 字符串
     * </pre>
     * @param object     Object
     * @param jsonIndent JSON 缩进间隔
     * @return JSON String
     */
    public static String toJson(
            final Object object,
            final int jsonIndent
    ) {
        if (object == null) return null;
        // 判断是否格式化
        boolean format = jsonIndent >= 1;
        try {
            if (object instanceof String) {
                String json = (String) object;
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    return format ? jsonObject.toString(jsonIndent) : jsonObject.toString();
                } else if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
                }
            } else if (object instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) object;
                return format ? jsonObject.toString(jsonIndent) : jsonObject.toString();
            } else if (object instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) object;
                return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
            } else if (object instanceof Map) {
                JSONObject jsonObject = new JSONObject((Map) object);
                return format ? jsonObject.toString(jsonIndent) : jsonObject.toString();
            } else if (object instanceof Collection) {
                JSONArray jsonArray = new JSONArray((Collection) object);
                return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
            } else if (object.getClass().isArray()) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    JSONArray jsonArray = new JSONArray(object);
                    return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
                } else {
                    JSONArray jsonArray = new JSONArray();
                    int       length    = Array.getLength(object);
                    for (int i = 0; i < length; ++i) {
                        jsonArray.put(wrap(Array.get(object, i)));
                    }
                    return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
                }
            } else if (object instanceof JSONTokener) {
                JSONTokener jsonTokener = (JSONTokener) object;
                // 获取 value 对象
                Object tokenerObj = jsonTokener.nextValue();
                // 判断是什么格式
                if (tokenerObj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) tokenerObj;
                    return format ? jsonObject.toString(jsonIndent) : jsonObject.toString();
                } else if (tokenerObj instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) tokenerObj;
                    return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
                }
            }
            // 抛出不支持的类型
            throw new Exception("Value " + object + " of className" + object.getClass().getName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toJson");
        }
        return null;
    }

    // =================
    // = 转换 JSON 对象 =
    // =================

    /**
     * Object 转换 JSON 对象
     * <pre>
     *     fromJson(xx, JSONObject.class);
     *     fromJson(xx, JSONArray.class);
     *     fromJson(xx, JSONTokener.class);
     * </pre>
     * @param object Object
     * @param type   JSONObject.class || JSONArray.class || JSONTokener.class
     * @param <T>    泛型
     * @return 指定 type JSON 对象
     */
    public static <T> T fromJson(
            final Object object,
            final Class<T> type
    ) {
        if (object == null || type == null) return null;
        try {
            if (type == JSONObject.class) {
                if (object instanceof JSONObject) {
                    return (T) object;
                } else if (object instanceof String) {
                    return (T) new JSONObject((String) object);
                } else if (object instanceof JSONTokener) {
                    return (T) new JSONObject((JSONTokener) object);
                } else if (object instanceof Map) {
                    return (T) new JSONObject((Map) object);
                }
            } else if (type == JSONArray.class) {
                if (object instanceof JSONArray) {
                    return (T) object;
                } else if (object instanceof String) {
                    return (T) new JSONArray((String) object);
                } else if (object instanceof JSONTokener) {
                    return (T) new JSONArray((JSONTokener) object);
                } else if (object instanceof Collection) {
                    return (T) new JSONArray((Collection) object);
                } else if (object.getClass().isArray()) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        return (T) new JSONArray(object);
                    } else {
                        JSONArray jsonArray = new JSONArray();
                        int       length    = Array.getLength(object);
                        for (int i = 0; i < length; ++i) {
                            jsonArray.put(wrap(Array.get(object, i)));
                        }
                        return (T) jsonArray;
                    }
                }
            } else if (type == JSONTokener.class) {
                if (object instanceof String) {
                    return (T) new JSONTokener((String) object);
                }
            }
            // 抛出不支持的类型
            throw new Exception("Value " + object + " of className" + object.getClass().getName()
                    + " converted Type " + type.getCanonicalName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "fromJson");
        }
        return null;
    }

    // ===========
    // = 其他处理 =
    // ===========

    /**
     * 包装转换 Object
     * <pre>
     *     {@link JSONObject#wrap(Object)}
     * </pre>
     * @param object Object
     * @return 转换后的 Object
     */
    public static Object wrap(final Object object) {
        if (object == null) return null;
        if (object instanceof JSONArray || object instanceof JSONObject) {
            return object;
        }
        try {
            if (object instanceof Collection) {
                return new JSONArray((Collection) object);
            } else if (object.getClass().isArray()) {
                // 版本兼容
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    return new JSONArray(object);
                } else {
                    JSONArray jsonArray = new JSONArray();
                    int       length    = Array.getLength(object);
                    for (int i = 0; i < length; ++i) {
                        jsonArray.put(wrap(Array.get(object, i)));
                    }
                    return jsonArray;
                }
            }
            if (object instanceof Map) {
                return new JSONObject((Map) object);
            }
            if (object instanceof Boolean ||
                    object instanceof Byte ||
                    object instanceof Character ||
                    object instanceof Double ||
                    object instanceof Float ||
                    object instanceof Integer ||
                    object instanceof Long ||
                    object instanceof Short ||
                    object instanceof String) {
                return object;
            }
            if (object.getClass().getPackage().getName().startsWith("java.")) {
                return object.toString();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "wrap");
        }
        return null;
    }

    /**
     * 字符串 JSON 转义处理
     * @param str 字符串
     * @return 转义后的 JSON 字符串
     */
    public static String stringJSONEscape(final String str) {
        if (str == null) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case '"':
                    builder.append("\\\"");
                    break;
                case '\\':
                    builder.append("\\\\");
                    break;
                case '\b':
                    builder.append("\\b");
                    break;
                case '\f':
                    builder.append("\\f");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                case '/':
                    builder.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        builder.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            builder.append('0');
                        }
                        builder.append(ss.toUpperCase());
                    } else {
                        builder.append(ch);
                    }
            }
        }
        return builder.toString();
    }

    /**
     * 判断字符串是否 JSON 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSON(final String json) {
        if (!TextUtils.isEmpty(json)) {
            if (json.startsWith("[") && json.endsWith("]")) {
                try {
                    new JSONArray(json);
                    return true;
                } catch (Exception e) {
                }
            } else if (json.startsWith("{") && json.endsWith("}")) {
                try {
                    new JSONObject(json);
                    return true;
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    /**
     * 判断字符串是否 JSON Object 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSONObject(final String json) {
        if (!TextUtils.isEmpty(json)) {
            if (json.startsWith("{") && json.endsWith("}")) {
                try {
                    new JSONObject(json);
                    return true;
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    /**
     * 判断字符串是否 JSON Array 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSONArray(final String json) {
        if (!TextUtils.isEmpty(json)) {
            if (json.startsWith("[") && json.endsWith("]")) {
                try {
                    new JSONArray(json);
                    return true;
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    // =

    /**
     * 将 JSON 格式字符串转化为 Map
     * @param json JSON String
     * @return {@link Map}
     */
    public static Map<String, Object> jsonToMap(final String json) {
        return isJSONObject(json) ? jsonToMap(fromJson(json, JSONObject.class)) : null;
    }

    /**
     * 将 JSON 对象转化为 Map
     * @param jsonObject {@link JSONObject}
     * @return {@link Map}
     */
    public static Map<String, Object> jsonToMap(final JSONObject jsonObject) {
        if (JSONObject.NULL.equals(jsonObject)) return null;
        try {
            Map<String, Object> map      = new LinkedHashMap<>();
            Iterator<String>    iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key   = iterator.next();
                Object value = jsonObject.get(key);
                if (value instanceof JSONArray) {
                    value = jsonToList((JSONArray) value);
                } else if (value instanceof JSONObject) {
                    value = jsonToMap((JSONObject) value);
                }
                map.put(key, value);
            }
            return map;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "jsonToMap");
        }
        return null;
    }

    // =

    /**
     * 将 JSON 格式字符串转化为 List
     * @param json JSON String
     * @return {@link List}
     */
    public static List<Object> jsonToList(final String json) {
        return isJSONArray(json) ? jsonToList(fromJson(json, JSONArray.class)) : null;
    }

    /**
     * 将 JSON 对象转化为 List
     * @param jsonArray {@link JSONArray}
     * @return {@link List}
     */
    public static List<Object> jsonToList(final JSONArray jsonArray) {
        if (JSONObject.NULL.equals(jsonArray)) return null;
        try {
            List<Object> list = new ArrayList<>();
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                Object value = jsonArray.get(i);
                if (value instanceof JSONArray) {
                    value = jsonToList((JSONArray) value);
                } else if (value instanceof JSONObject) {
                    value = jsonToMap((JSONObject) value);
                }
                list.add(value);
            }
            return list;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "jsonToList");
        }
        return null;
    }

    // =

    /**
     * 获取 JSONObject
     * @param json JSON String
     * @return {@link JSONObject}
     */
    public static JSONObject getJSONObject(final String json) {
        try {
            return new JSONObject(json);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getJSONObject");
        }
        return null;
    }

    /**
     * 获取 JSONArray
     * @param json JSON String
     * @return {@link JSONArray}
     */
    public static JSONArray getJSONArray(final String json) {
        try {
            return new JSONArray(json);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getJSONArray");
        }
        return null;
    }

    // =

    /**
     * 获取 JSONObject
     * @param jsonObject {@link JSONObject}
     * @param key        Key
     * @return {@link JSONObject}
     */
    public static JSONObject getJSONObject(
            final JSONObject jsonObject,
            final String key
    ) {
        try {
            return jsonObject.getJSONObject(key);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getJSONObject");
        }
        return null;
    }

    /**
     * 获取 JSONArray
     * @param jsonObject {@link JSONObject}
     * @param key        Key
     * @return {@link JSONArray}
     */
    public static JSONArray getJSONArray(
            final JSONObject jsonObject,
            final String key
    ) {
        try {
            return jsonObject.getJSONArray(key);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getJSONArray");
        }
        return null;
    }

    // =

    /**
     * 获取 JSONObject
     * @param jsonArray {@link JSONArray}
     * @param index     索引
     * @return {@link JSONObject}
     */
    public static JSONObject getJSONObject(
            final JSONArray jsonArray,
            final int index
    ) {
        try {
            return jsonArray.getJSONObject(index);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getJSONObject");
        }
        return null;
    }

    /**
     * 获取 JSONArray
     * @param jsonArray {@link JSONArray}
     * @param index     索引
     * @return {@link JSONArray}
     */
    public static JSONArray getJSONArray(
            final JSONArray jsonArray,
            final int index
    ) {
        try {
            return jsonArray.getJSONArray(index);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getJSONArray");
        }
        return null;
    }

    // =

    /**
     * 获取指定 key 数据
     * @param jsonObject {@link JSONObject}
     * @param key        Key
     * @param <T>        泛型
     * @return 指定 key 数据
     */
    public static <T> T get(
            final JSONObject jsonObject,
            final String key
    ) {
        if (jsonObject != null && key != null) {
            try {
                return (T) jsonObject.get(key);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "get - JSONObject");
            }
        }
        return null;
    }

    /**
     * 获取指定 key 数据
     * @param jsonObject {@link JSONObject}
     * @param key        Key
     * @param <T>        泛型
     * @return 指定 key 数据
     */
    public static <T> T opt(
            final JSONObject jsonObject,
            final String key
    ) {
        if (jsonObject != null && key != null) {
            try {
                return (T) jsonObject.opt(key);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "opt - JSONObject");
            }
        }
        return null;
    }

    // =

    /**
     * 获取指定索引数据
     * @param jsonArray {@link JSONArray}
     * @param index     索引
     * @param <T>       泛型
     * @return 指定 key 数据
     */
    public static <T> T get(
            final JSONArray jsonArray,
            final int index
    ) {
        if (jsonArray != null && index >= 0) {
            try {
                return (T) jsonArray.get(index);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "get - JSONArray");
            }
        }
        return null;
    }

    /**
     * 获取指定索引数据
     * @param jsonArray {@link JSONArray}
     * @param index     索引
     * @param <T>       泛型
     * @return 指定 key 数据
     */
    public static <T> T opt(
            final JSONArray jsonArray,
            final int index
    ) {
        if (jsonArray != null && index >= 0) {
            try {
                return (T) jsonArray.opt(index);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "opt - JSONArray");
            }
        }
        return null;
    }
}