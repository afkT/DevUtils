package dev.engine.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import dev.utils.JCLogUtils
import java.io.StringReader
import java.lang.reflect.Modifier
import java.lang.reflect.Type

/**
 * detail: Gson 工具类
 * @author Ttt
 * Gson 详细使用
 * @see https://www.jianshu.com/p/d62c2be60617
 */
internal object GsonUtils {

    // 日志 TAG
    private val TAG = GsonUtils::class.java.simpleName

    // Object 转 JSON 字符串
    private val TO_GSON = createGson(true).create()

    // JSON 字符串转 T Object
    private val FROM_GSON = createGson(true).create()

    // JSON 缩进
    private val INDENT_GSON = createGson(true).setPrettyPrinting().create()

    // ==========
    // = 转换方法 =
    // ==========

    /**
     * 将对象转换为 JSON String
     * @param obj [Object]
     * @return JSON String
     */
    fun toJson(obj: Any?): String? {
        return toJson(obj, TO_GSON)
    }

    /**
     * 将对象转换为 JSON String
     * @param obj  [Object]
     * @param gson [Gson]
     * @return JSON String
     */
    fun toJson(
        obj: Any?,
        gson: Gson?
    ): String? {
        if (gson != null) {
            try {
                return gson.toJson(obj)
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "toJson")
            }
        }
        return null
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT [Class] T
     * @return instance of type
     */
    fun <T> fromJson(
        json: String?,
        classOfT: Class<T>?
    ): T? {
        return fromJson(json, classOfT, FROM_GSON)
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json     JSON String
     * @param classOfT [Class] T
     * @param gson     [Gson]
     * @return instance of type
     */
    fun <T> fromJson(
        json: String?,
        classOfT: Class<T>?,
        gson: Gson?
    ): T? {
        if (gson != null) {
            try {
                return gson.fromJson(json, classOfT)
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "fromJson")
            }
        }
        return null
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT [Type] T
     * @return instance of type
     */
    fun <T> fromJson(
        json: String?,
        typeOfT: Type?
    ): T? {
        return fromJson(json, typeOfT, FROM_GSON)
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json    JSON String
     * @param typeOfT [Type] T
     * @param gson    [Gson]
     * @return instance of type
     */
    fun <T> fromJson(
        json: String?,
        typeOfT: Type?,
        gson: Gson?
    ): T? {
        if (gson != null) {
            try {
                return gson.fromJson(json, typeOfT)
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "fromJson")
            }
        }
        return null
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 判断字符串是否 JSON 格式
     * @param json 待校验 JSON String
     * @return `true` yes, `false` no
     */
    fun isJSON(json: String?): Boolean {
        try {
            val jsonElement: JsonElement = JsonParser.parseString(json) ?: return false
            return jsonElement.isJsonObject || jsonElement.isJsonArray
        } catch (e: Exception) {
        }
        return false
    }

    /**
     * 判断字符串是否 JSON Object 格式
     * @param json 待校验 JSON String
     * @return `true` yes, `false` no
     */
    fun isJSONObject(json: String?): Boolean {
        try {
            val jsonElement: JsonElement = JsonParser.parseString(json) ?: return false
            return jsonElement.isJsonObject
        } catch (e: Exception) {
        }
        return false
    }

    /**
     * 判断字符串是否 JSON Array 格式
     * @param json 待校验 JSON String
     * @return `true` yes, `false` no
     */
    fun isJSONArray(json: String?): Boolean {
        try {
            val jsonElement: JsonElement = JsonParser.parseString(json) ?: return false
            return jsonElement.isJsonArray
        } catch (e: Exception) {
        }
        return false
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    fun toJsonIndent(json: String?): String? {
        return toJsonIndent(json, INDENT_GSON)
    }

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @param gson [Gson]
     * @return JSON String
     */
    fun toJsonIndent(
        json: String?,
        gson: Gson?
    ): String? {
        if (gson != null) {
            try {
                val reader = JsonReader(StringReader(json))
                reader.isLenient = true
                val jsonElement = JsonParser.parseReader(reader)
                return gson.toJson(jsonElement)
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent")
            }
        }
        return null
    }

    // =

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param obj [Object]
     * @return JSON String
     */
    fun toJsonIndent(obj: Any?): String? {
        return toJsonIndent(obj, INDENT_GSON)
    }

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param obj  [Object]
     * @param gson [Gson]
     * @return JSON String
     */
    fun toJsonIndent(
        obj: Any?,
        gson: Gson?
    ): String? {
        if (gson != null) {
            try {
                return gson.toJson(obj)
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent")
            }
        }
        return null
    }

    // ========
    // = Gson =
    // ========

    /**
     * 创建 GsonBuilder
     * @param serializeNulls 是否序列化 null 值
     * @return [GsonBuilder]
     */
    fun createGson(serializeNulls: Boolean): GsonBuilder {
        val builder = GsonBuilder()
        if (serializeNulls) builder.serializeNulls()
        return builder
    }

    /**
     * 创建过滤指定修饰符字段 GsonBuilder
     * @param builder   [GsonBuilder]
     * @param modifiers 需过滤不处理的字段修饰符 [Modifier]
     * @return [GsonBuilder]
     */
    fun createGsonExcludeFields(
        builder: GsonBuilder?,
        vararg modifiers: Int
    ): GsonBuilder? {
        return builder?.excludeFieldsWithModifiers(*modifiers)
    }

    // ========
    // = Type =
    // ========

    /**
     * 获取 Array Type
     * @param type Bean.class
     * @return Bean[] Type
     */
    fun getArrayType(type: Type?): Type? {
        return TypeToken.getArray(type).type
    }

    /**
     * 获取 List Type
     * @param type Bean.class
     * @return List<Bean> Type
     */
    fun getListType(type: Type?): Type? {
        return TypeToken.getParameterized(List::class.java, type).type
    }

    /**
     * 获取 Set Type
     * @param type Bean.class
     * @return Set<Bean> Type
     */
    fun getSetType(type: Type?): Type? {
        return TypeToken.getParameterized(Set::class.java, type).type
    }

    /**
     * 获取 Map Type
     * @param keyType   Key.class
     * @param valueType Value.class
     * @return Map<Key></Key>, Value> Type
     */
    fun getMapType(
        keyType: Type?,
        valueType: Type?
    ): Type? {
        return TypeToken.getParameterized(Map::class.java, keyType, valueType).type
    }

    /**
     * 获取 Type
     * @param rawType       raw type
     * @param typeArguments type arguments
     * @return Type
     */
    fun getType(
        rawType: Type?,
        vararg typeArguments: Type?
    ): Type? {
        return TypeToken.getParameterized(rawType, *typeArguments).type
    }
}