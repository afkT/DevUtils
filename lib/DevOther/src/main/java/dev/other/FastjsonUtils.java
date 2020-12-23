package dev.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.utils.JCLogUtils;

/**
 * detail: Fastjson 工具类
 * @author Ttt
 */
public final class FastjsonUtils {

    private FastjsonUtils() {
    }

    // 日志 TAG
    private static final String TAG = FastjsonUtils.class.getSimpleName();

    // ===========
    // = 转换方法 =
    // ===========

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
    public static <T> T fromJson(
            final String json,
            final Class<T> classOfT
    ) {
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
    public static <T> T fromJson(
            final String json,
            final Type typeOfT
    ) {
        if (json != null) {
            try {
                return JSON.parseObject(json, typeOfT);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "fromJson");
            }
        }
        return null;
    }

    // ===========
    // = 其他方法 =
    // ===========

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
        try {
            Object object = JSON.parse(json);
            return (object instanceof JSONObject);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断字符串是否 JSON Array 格式
     * @param json 待校验 JSON String
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isJSONArray(final String json) {
        try {
            Object object = JSON.parse(json);
            return (object instanceof JSONArray);
        } catch (Exception e) {
        }
        return false;
    }

    // =

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    public static String toJsonIndent(final String json) {
        if (json != null) {
            try {
                // 保持 JSON 字符串次序
                Object object = JSON.parse(json, Feature.OrderedField);
                if (object instanceof JSONObject) {
                    return JSONObject.toJSONString(object, true);
                } else if (object instanceof JSONArray) {
                    return JSONArray.toJSONString(object, true);
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent");
            }
        }
        return null;
    }

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJsonIndent(final Object object) {
        if (object != null) {
            try {
                return toJsonIndent(toJson(object));
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent");
            }
        }
        return null;
    }

    // ========
    // = Type =
    // ========

    /**
     * 获取 Array Type
     * @param type Bean.class
     * @return Bean[] Type
     */
    public static Type getArrayType(final Type type) {
        return new GenericArrayType() {
            @Override
            public Type getGenericComponentType() {
                return type;
            }
        };
    }

    /**
     * 获取 List Type
     * @param type Bean.class
     * @return List<Bean> Type
     */
    public static Type getListType(final Type type) {
        return new ParameterizedTypeImpl(new Type[]{type}, null, List.class);
    }

    /**
     * 获取 Set Type
     * @param type Bean.class
     * @return Set<Bean> Type
     */
    public static Type getSetType(final Type type) {
        return new ParameterizedTypeImpl(new Type[]{type}, null, Set.class);
    }

    /**
     * 获取 Map Type
     * @param keyType   Key.class
     * @param valueType Value.class
     * @return Map<Key, Value> Type
     */
    public static Type getMapType(
            final Type keyType,
            final Type valueType
    ) {
        return new ParameterizedTypeImpl(new Type[]{keyType, valueType}, null, Map.class);
    }

    /**
     * 获取 Type
     * @param rawType       raw type
     * @param typeArguments type arguments
     * @return Type
     */
    public static Type getType(
            final Type rawType,
            final Type... typeArguments
    ) {
        return new ParameterizedTypeImpl(typeArguments, null, rawType);
    }
}