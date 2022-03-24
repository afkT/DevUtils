package dev.http.manager

import okhttp3.HttpUrl

/**
 * detail: Retrofit Manager
 * @author Ttt
 */
internal object RetrofitManager {

    // 存储 Retrofit Operation 操作对象
    private val sOperationMaps: MutableMap<String, RetrofitOperation> = LinkedHashMap()

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Retrofit Operation 操作对象
     * @param key Key
     * @return Retrofit Operation
     */
    fun getOperation(key: String): RetrofitOperation? {
        return sOperationMaps[key]
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

    // =========================
    // = RetrofitOperation 方法 =
    // =========================

    /**
     * 重置处理 ( 重新构建 Retrofit )
     * @param key Key
     * @param httpUrl 构建使用指定 baseUrl
     */
    fun reset(
        key: String,
        httpUrl: HttpUrl? = null
    ) {
        getOperation(key)?.reset(httpUrl)
    }


}