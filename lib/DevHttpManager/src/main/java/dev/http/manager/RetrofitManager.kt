package dev.http.manager

import okhttp3.HttpUrl

/**
 * detail: Retrofit Manager
 * @author Ttt
 */
internal object RetrofitManager {

    // 全局通用 OkHttp Builder 接口
    private var sOkHttpBuilder: OkHttpBuilder? = null

//    // Retrofit 重新构建监听事件
//    private var sOnRetrofitResetListener: OnRetrofitResetListener? = null

    // 存储 Retrofit Operation 操作对象
    private val sOperationMaps: MutableMap<String, RetrofitOperation> = LinkedHashMap()

    // =============
    // = 对外公开方法 =
    // =============

    // =================
    // = OkHttpBuilder =
    // =================

    /**
     * 获取全局通用 OkHttp Builder 接口对象
     * @return OkHttpBuilder
     */
    fun getOkHttpBuilder(): OkHttpBuilder? {
        return sOkHttpBuilder
    }

    /**
     * 设置全局通用 OkHttp Builder 接口对象
     * @param builder [OkHttpBuilder]
     */
    fun setOkHttpBuilder(builder: OkHttpBuilder?) {
        sOkHttpBuilder = builder
    }

    /**
     * 移除全局通用 OkHttp Builder 接口对象
     */
    fun removeOkHttpBuilder() {
        setOkHttpBuilder(null)
    }

    // ===================
    // = RetrofitBuilder =
    // ===================

    /**
     * 获取 Retrofit Operation 操作对象
     * @param key Key
     * @return Retrofit Operation
     */
    fun getOperation(key: String): RetrofitOperation? {
        return sOperationMaps[key]
    }

    /**
     * 通过 Key 判断是否存在 Retrofit Operation 操作对象
     * @param key Key
     * @return `true` yes, `false` no
     */
    fun containsOperation(key: String): Boolean {
        return sOperationMaps.containsKey(key)
    }

    /**
     * 通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象
     * @param key Key
     * @param builder [RetrofitBuilder]
     * @return Retrofit Operation
     */
    fun putRetrofitBuilder(
        key: String,
        builder: RetrofitBuilder
    ): RetrofitOperation {
        val operation = RetrofitOperation.get(key, builder)
        sOperationMaps[key] = operation
        return operation
    }

    /**
     * 通过 Key 解绑移除 RetrofitBuilder 并返回 Operation 操作对象
     * @param key Key
     * @return Retrofit Operation
     */
    fun removeRetrofitBuilder(key: String): RetrofitOperation? {
        return sOperationMaps.remove(key)
    }

    // =====================
    // = RetrofitOperation =
    // =====================

    /**
     * 重置处理 ( 重新构建 Retrofit )
     * @param key Key
     * @param httpUrl 构建使用指定 baseUrl
     * @return Retrofit Operation
     */
    fun reset(
        key: String,
        httpUrl: HttpUrl? = null
    ): RetrofitOperation? {
        return getOperation(key)?.reset(httpUrl)
    }

    /**
     * 重置处理 ( 重新构建全部 Retrofit )
     * @param mapHttpUrl MutableMap<String?, HttpUrl?>
     */
    fun resetAll(mapHttpUrl: MutableMap<String?, HttpUrl?>? = null) {
        sOperationMaps.forEach {
            it.value.reset(mapHttpUrl?.get(it.key))
        }
    }
}