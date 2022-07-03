package dev.engine.json

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.JSONWriter
import com.alibaba.fastjson2.util.ParameterizedTypeImpl
import dev.utils.JCLogUtils
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type

/**
 * detail: Fastjson 工具类
 * @author Ttt
 */
internal object FastjsonUtils {

    // 日志 TAG
    private val TAG = FastjsonUtils::class.java.simpleName

    // ==========
    // = 转换方法 =
    // ==========

    /**
     * 将对象转换为 JSON String
     * @param obj [Object]
     * @return JSON String
     */
    fun toJson(obj: Any?): String? {
        if (obj != null) {
            try {
                return JSON.toJSONString(obj)
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "toJson")
            }
        }
        return null
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json JSON String
     * @param classOfT [Class] T
     * @return instance of type
     */
    fun <T> fromJson(
        json: String?,
        classOfT: Class<T>?
    ): T? {
        if (json != null) {
            try {
                return JSON.parseObject(json, classOfT)
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "fromJson")
            }
        }
        return null
    }

    // =

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json JSON String
     * @param typeOfT [Type] T
     * @return instance of type
     */
    fun <T> fromJson(
        json: String?,
        typeOfT: Type?
    ): T? {
        if (json != null) {
            try {
                return JSON.parseObject(json, typeOfT)
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
            val obj = JSON.parse(json)
            return obj != null
        } catch (e: Exception) {
            return false
        }
    }

    /**
     * 判断字符串是否 JSON Object 格式
     * @param json 待校验 JSON String
     * @return `true` yes, `false` no
     */
    fun isJSONObject(json: String?): Boolean {
        try {
            val obj = JSON.parse(json)
            return obj is JSONObject
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
            val obj = JSON.parse(json)
            return obj is JSONArray
        } catch (e: Exception) {
        }
        return false
    }

    // =

    /**
     * JSON String 缩进处理
     * @param json JSON String
     * @return JSON String
     */
    fun toJsonIndent(json: String?): String? {
        if (json != null) {
            try {
                // 保持 JSON 字符串次序
                val obj = JSON.parse(json)
                return JSON.toJSONString(
                    obj, JSONWriter.Feature.PrettyFormat
                )
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent")
            }
        }
        return null
    }

    /**
     * Object 转 JSON String 并进行缩进处理
     * @param obj [Object]
     * @return JSON String
     */
    fun toJsonIndent(obj: Any?): String? {
        if (obj != null) {
            try {
                return toJsonIndent(toJson(obj))
            } catch (e: Exception) {
                JCLogUtils.eTag(TAG, e, "toJsonIndent")
            }
        }
        return null
    }

    // ========
    // = Type =
    // ========

    /**
     * 获取 Array Type
     * @param type Bean.class
     * @return Bean[] Type
     */
    fun getArrayType(type: Type): Type {
        return GenericArrayType { type }
    }

    /**
     * 获取 List Type
     * @param type Bean.class
     * @return List<Bean> Type
     */
    fun getListType(type: Type): Type {
        return ParameterizedTypeImpl(
            arrayOf(type), null,
            List::class.java
        )
    }

    /**
     * 获取 Set Type
     * @param type Bean.class
     * @return Set<Bean> Type
     */
    fun getSetType(type: Type): Type {
        return ParameterizedTypeImpl(
            arrayOf(type), null,
            Set::class.java
        )
    }

    /**
     * 获取 Map Type
     * @param keyType   Key.class
     * @param valueType Value.class
     * @return Map<Key, Value> Type
     */
    fun getMapType(
        keyType: Type?,
        valueType: Type?
    ): Type {
        return ParameterizedTypeImpl(
            arrayOf(keyType, valueType), null,
            Map::class.java
        )
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
    ): Type {
        return ParameterizedTypeImpl(typeArguments, null, rawType)
    }
}