package dev.utils.app;

import android.os.Build;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Array;
import java.util.Collection;
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
    // JSON 格式内容 默认缩进
    private static final int JSON_INDENT = 4;

    // ====================
    // = 转换 JSON 字符串 =
    // ====================

    /**
     * 转换为 JSON 格式字符串
     * <pre>
     *      @TODO 不支持 实体类 转 JSON字符串
     * </pre>
     * @param object
     * @return
     */
    public static String toJson(final Object object) {
        return toJson(object, -1);
    }

    /**
     * 转换为 JSON 格式字符串
     * <pre>
     *      @TODO 不支持 实体类 转 JSON字符串
     * </pre>
     * @param object
     * @param jsonIndent
     * @return
     */
    public static String toJson(final Object object, final int jsonIndent) {
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
                    int length = Array.getLength(object);
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

    // ==================
    // = 转换 JSON 对象 =
    // ==================

    /**
     * Object 转换 JSON 对象
     * <pre>
     *      fromJson(xx, JSONObject.class);
     *      fromJson(xx, JSONArray.class);
     *      fromJson(xx, JSONTokener.class);
     * </pre>
     * @param object
     * @param type   JSONObject.class || JSONArray.class || JSONTokener.class
     * @return
     */
    public static <T> T fromJson(final Object object, final Class<T> type) {
        if (object == null || type == null) return null;
        try {
            if (type.equals(JSONObject.class)) {
                if (object instanceof JSONObject) {
                    return (T) object;
                } else if (object instanceof String) {
                    return (T) new JSONObject((String) object);
                } else if (object instanceof JSONTokener) {
                    return (T) new JSONObject((JSONTokener) object);
                } else if (object instanceof Map) {
                    return (T) new JSONObject((Map) object);
                }
            } else if (type.equals(JSONArray.class)) {
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
                        int length = Array.getLength(object);
                        for (int i = 0; i < length; ++i) {
                            jsonArray.put(wrap(Array.get(object, i)));
                        }
                        return (T) jsonArray;
                    }
                }
            } else if (type.equals(JSONTokener.class)) {
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

    // ============
    // = 其他处理 =
    // ============

    /**
     * 包装转换 Object - {@link JSONObject#wrap(Object)}
     * @param object
     * @return
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
                    int length = Array.getLength(object);
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
     * @param str
     * @return
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
}
