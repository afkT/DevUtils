package dev.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.utils.JCLogUtils;
import dev.utils.common.StringUtils;

/**
 * detail: Fastjson 工具类
 * @author Ttt
 */
public final class FastjsonUtils {

    private FastjsonUtils() {
    }

    // 日志 TAG
    private static final String TAG = FastjsonUtils.class.getSimpleName();

    // ============
    // = 转换方法 =
    // ============

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJson(final Object object) {
        if (object != null) {
            try {
                return JSON.toJSONString(object);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toJson");
            }
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
        if (json != null) {
            try {
                return JSON.parseObject(json, classOfT);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "fromJson");
            }
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
        if (json != null) {
            try {
                return JSON.parseObject(json, typeOfT);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "fromJson");
            }
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
        try {
            Object object = JSON.parse(json);
            return (object != null);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否 JSON Object 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSONObject(final String json) {
        if (!StringUtils.isEmpty(json)) {
            if (json.startsWith("{") && json.endsWith("}")) {
                try {
                    Object object = JSON.parse(json);
                    return (object instanceof JSONObject);
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
        if (!StringUtils.isEmpty(json)) {
            if (json.startsWith("[") && json.endsWith("]")) {
                try {
                    Object object = JSON.parse(json);
                    return (object instanceof JSONArray);
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

//    /**
//     * JSON String 缩进处理
//     * @param json JSON String
//     * @return JSON String
//     */
//    public static String toJsonIndent(final String json) {
//        return toJsonIndent(json, INDENT_GSON);
//    }
//
//    /**
//     * JSON String 缩进处理
//     * @param json JSON String
//     * @param gson {@link Gson}
//     * @return JSON String
//     */
//    public static String toJsonIndent(final String json, final Gson gson) {
//        if (gson != null) {
//            try {
//                JsonReader reader = new JsonReader(new StringReader(json));
//                reader.setLenient(true);
//                JsonParser jsonParser = new JsonParser();
//                JsonElement jsonElement = jsonParser.parse(reader);
//                return gson.toJson(jsonElement);
//            } catch (Exception e) {
//                JCLogUtils.eTag(TAG, e, "toJsonIndent");
//            }
//        }
//        return null;
//    }
//
//    // =
//
//    /**
//     * Object 转 JSON String 并进行缩进处理
//     * @param object {@link Object}
//     * @return JSON String
//     */
//    public static String toJsonIndent(final Object object) {
//        return toJsonIndent(object, INDENT_GSON);
//    }
//
//    /**
//     * Object 转 JSON String 并进行缩进处理
//     * @param object {@link Object}
//     * @param gson   {@link Gson}
//     * @return JSON String
//     */
//    public static String toJsonIndent(final Object object, final Gson gson) {
//        if (gson != null) {
//            try {
//                return gson.toJson(object);
//            } catch (Exception e) {
//                JCLogUtils.eTag(TAG, e, "toJsonIndent");
//            }
//        }
//        return null;
//    }

    // ========
    // = Type =
    // ========

//    /**
//     * 获取 Array Type
//     * @param type Bean.class
//     * @return Bean[] Type
//     */
//    public static Type getArrayType(final Type type) {
//        return TypeToken.getArray(type).getType();
//    }
//
//    /**
//     * 获取 List Type
//     * @param type Bean.class
//     * @return List<Bean> Type
//     */
//    public static Type getListType(final Type type) {
//        return TypeToken.getParameterized(List.class, type).getType();
//    }
//
//    /**
//     * 获取 Set Type
//     * @param type Bean.class
//     * @return Set<Bean> Type
//     */
//    public static Type getSetType(final Type type) {
//        return TypeToken.getParameterized(Set.class, type).getType();
//    }
//
//    /**
//     * 获取 Map Type
//     * @param keyType   Key.class
//     * @param valueType Value.class
//     * @return Map<Bean> Type
//     */
//    public static Type getMapType(final Type keyType, final Type valueType) {
//        return TypeToken.getParameterized(Map.class, keyType, valueType).getType();
//    }
//
//    /**
//     * 获取 Type
//     * @param rawType       raw type
//     * @param typeArguments type arguments
//     * @return Type
//     */
//    public static Type getType(final Type rawType, final Type... typeArguments) {
//        return TypeToken.getParameterized(rawType, typeArguments).getType();
//    }
}