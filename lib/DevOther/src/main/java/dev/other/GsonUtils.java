package dev.other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import dev.utils.JCLogUtils;

/**
 * detail: Gson 工具类
 * @author Ttt
 * <pre>
 *     Gson 详细使用
 *     @see <a href="https://www.jianshu.com/p/d62c2be60617"/>
 *     TypeBuilder
 *     @see <a href="https://github.com/ikidou/TypeBuilder"/>
 * </pre>
 */
public final class GsonUtils {

    private GsonUtils() {
    }

    // 日志 TAG
    private static final String TAG = GsonUtils.class.getSimpleName();
    // Object 转 JSON 字符串
    private static final Gson TO_GSON = createGson(true).create();
    // JSON 字符串转 T Object
    private static final Gson FROM_GSON = createGson(true).create();
    // JSON 缩进
    private static final Gson INDENT_GSON = createGson(true).setPrettyPrinting().create();

    // ============
    // = 转换方法 =
    // ============

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJson(Object object) {
        return toJson(object, TO_GSON);
    }

    /**
     * 将对象转换为 JSON String
     * @param object {@link Object}
     * @param gson   {@link Gson}
     * @return JSON String
     */
    public static String toJson(Object object, Gson gson) {
        if (gson != null) {
            try {
                return gson.toJson(object);
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
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return fromJson(json, classOfT, FROM_GSON);
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT {@link Class} T
     * @param gson     {@link Gson}
     * @param <T>      泛型
     * @return instance of type
     */
    public static <T> T fromJson(String json, Class<T> classOfT, Gson gson) {
        if (gson != null) {
            try {
                return gson.fromJson(json, classOfT);
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
    public static <T> T fromJson(String json, Type typeOfT) {
        return fromJson(json, typeOfT, FROM_GSON);
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT {@link Type} T
     * @param gson    {@link Gson}
     * @param <T>     泛型
     * @return instance of type
     */
    public static <T> T fromJson(String json, Type typeOfT, Gson gson) {
        if (gson != null) {
            try {
                return gson.fromJson(json, typeOfT);
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
    public static boolean isJSON(String json) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(json);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    public static String toJsonIndent(String json) {
        return toJsonIndent(json, INDENT_GSON);
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @param gson {@link Gson}
     * @return JSON String
     */
    public static String toJsonIndent(String json, Gson gson) {
        if (gson != null) {
            try {
                JsonReader reader = new JsonReader(new StringReader(json));
                reader.setLenient(true);
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(reader);
                return gson.toJson(jsonElement);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent");
            }
        }
        return null;
    }

    // =

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object {@link Object}
     * @return JSON String
     */
    public static String toJsonIndent(Object object) {
        return toJsonIndent(object, INDENT_GSON);
    }

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param object {@link Object}
     * @param gson   {@link Gson}
     * @return JSON String
     */
    public static String toJsonIndent(Object object, Gson gson) {
        if (gson != null) {
            try {
                return gson.toJson(object);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent");
            }
        }
        return null;
    }

    // ========
    // = Gson =
    // ========

    /**
     * 创建 GsonBuilder
     * @param serializeNulls 是否序列化 null 值
     * @return {@link GsonBuilder}
     */
    public static GsonBuilder createGson(boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) builder.serializeNulls();
        return builder;
    }

    /**
     * 创建过滤指定修饰符字段 GsonBuilder
     * @param builder   {@link GsonBuilder}
     * @param modifiers 需过滤不处理的字段修饰符 {@link Modifier}
     * @return {@link GsonBuilder}
     */
    public static GsonBuilder createGsonExcludeFields(GsonBuilder builder, int... modifiers) {
        if (builder != null) {
            return builder.excludeFieldsWithModifiers(modifiers);
        }
        return null;
    }
}