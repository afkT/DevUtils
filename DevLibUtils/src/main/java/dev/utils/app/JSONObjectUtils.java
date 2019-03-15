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
 * Created by Ttt
 */
public final class JSONObjectUtils {

    private JSONObjectUtils() {
    }

    // 日志TAG
    private static final String TAG = JSONObjectUtils.class.getSimpleName();
    /** JSON 格式内容 默认缩进 */
    public static final int JSON_INDENT = 4;

    // ======================
    // == 转换 JSON 字符串 ==
    // ======================

    /**
     * 转换为 JSON 格式字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return toJson(obj, -1);
    }

    /**
     * 转换为 JSON 格式字符串
     * @param obj
     * @param jsonIndent
     * @return
     */
    public static String toJson(Object obj, int jsonIndent) {
        if (obj == null) return null;
        // 判断是否格式化
        boolean format = jsonIndent >= 1;
        try {
            if (obj instanceof String) {
                String json = (String) obj;
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    return format ? jsonObject.toString(jsonIndent) : jsonObject.toString();
                } else if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
                }
            } else if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                return format ? jsonObject.toString(jsonIndent) : jsonObject.toString();
            } else if (obj instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) obj;
                return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
            } else if (obj instanceof Map) {
                JSONObject jsonObject = new JSONObject((Map) obj);
                return format ? jsonObject.toString(jsonIndent) : jsonObject.toString();
            } else if (obj instanceof Collection) {
                JSONArray jsonArray = new JSONArray((Collection) obj);
                return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
            } else if (obj.getClass().isArray()) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    JSONArray jsonArray = new JSONArray(obj);
                    return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
                } else {
                    JSONArray jsonArray = new JSONArray();
                    final int length = Array.getLength(obj);
                    for (int i = 0; i < length; ++i) {
                        jsonArray.put(wrap(Array.get(obj, i)));
                    }
                    return format ? jsonArray.toString(jsonIndent) : jsonArray.toString();
                }
            } else if (obj instanceof JSONTokener) {
                JSONTokener jsonTokener = (JSONTokener) obj;
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
            throw new Exception("Value " + obj + " of className" + obj.getClass().getName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toJson");
        }
        return null;
    }

    // ======================
    // === 转换 JSON 对象 ===
    // ======================

    /**
     * Object 转换 JSONObject 对象
     * @param obj
     * @param type JSONObject.class || JSONArray.class || JSONTokener.class
     * @return
     * use:
     * fromJson(xx, JSONObject.class);
     * fromJson(xx, JSONArray.class);
     * fromJson(xx, JSONTokener.class);
     */
    public static <T> T fromJson(Object obj, final Class<T> type) {
        if (obj == null || type == null) return null;
        try {
            if (type.equals(JSONObject.class)) {
                if (obj instanceof JSONObject) {
                    return (T) obj;
                } else if (obj instanceof String) {
                    return (T) new JSONObject((String) obj);
                } else if (obj instanceof JSONTokener) {
                    return (T) new JSONObject((JSONTokener) obj);
                } else if (obj instanceof Map) {
                    return (T) new JSONObject((Map) obj);
                }
            } else if (type.equals(JSONArray.class)) {
                if (obj instanceof JSONArray) {
                    return (T) obj;
                } else if (obj instanceof String) {
                    return (T) new JSONArray((String) obj);
                } else if (obj instanceof JSONTokener) {
                    return (T) new JSONArray((JSONTokener) obj);
                } else if (obj instanceof Collection) {
                    return (T) new JSONArray((Collection) obj);
                } else if (obj.getClass().isArray()) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        return (T) new JSONArray(obj);
                    } else {
                        JSONArray jsonArray = new JSONArray();
                        final int length = Array.getLength(obj);
                        for (int i = 0; i < length; ++i) {
                            jsonArray.put(wrap(Array.get(obj, i)));
                        }
                        return (T) jsonArray;
                    }
                }
            } else if (type.equals(JSONTokener.class)) {
                if (obj instanceof String) {
                    return (T) new JSONTokener((String) obj);
                }
            }
            // 抛出不支持的类型
            throw new Exception("Value " + obj + " of className" + obj.getClass().getName()
                    + " converted Type " + type.getCanonicalName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "fromJson");
        }
        return null;
    }

    // ==================
    // ==== 其他处理 ====
    // ==================

    /**
     * 包装转换 Object - {@link JSONObject#wrap(Object)}
     * @param obj
     * @return
     */
    public static Object wrap(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONArray || obj instanceof JSONObject) {
            return obj;
        }
        try {
            if (obj instanceof Collection) {
                return new JSONArray((Collection) obj);
            } else if (obj.getClass().isArray()) {
                // 版本兼容
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    return new JSONArray(obj);
                } else {
                    JSONArray jsonArray = new JSONArray();
                    final int length = Array.getLength(obj);
                    for (int i = 0; i < length; ++i) {
                        jsonArray.put(wrap(Array.get(obj, i)));
                    }
                    return jsonArray;
                }
            }
            if (obj instanceof Map) {
                return new JSONObject((Map) obj);
            }
            if (obj instanceof Boolean ||
                    obj instanceof Byte ||
                    obj instanceof Character ||
                    obj instanceof Double ||
                    obj instanceof Float ||
                    obj instanceof Integer ||
                    obj instanceof Long ||
                    obj instanceof Short ||
                    obj instanceof String) {
                return obj;
            }
            if (obj.getClass().getPackage().getName().startsWith("java.")) {
                return obj.toString();
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
    public static String stringJSONEscape(String str) {
        if (str == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            sb.append('0');
                        }
                        sb.append(ss.toUpperCase());
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }
}
