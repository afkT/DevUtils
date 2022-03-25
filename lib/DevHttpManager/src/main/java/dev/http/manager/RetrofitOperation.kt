package dev.http.manager

import okhttp3.HttpUrl
import retrofit2.Retrofit

/**
 * detail: Retrofit Operation
 * @author Ttt
 */
class RetrofitOperation private constructor(
    private val key: String,
    private val builder: RetrofitBuilder
) {

    companion object {

        /**
         * 保存 RetrofitBuilder 并返回 Operation 操作对象
         * @param key Key
         * @param builder [RetrofitBuilder]
         * @return Retrofit Operation
         */
        internal fun get(
            key: String,
            builder: RetrofitBuilder
        ): RetrofitOperation {
            return RetrofitOperation(key, builder)
        }
    }

    // Retrofit
    private var mRetrofit: Retrofit? = null

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 构建 Retrofit 方法 ( 最终调用 )
     * @param httpUrl 构建使用指定 baseUrl
     * @return Retrofit Operation
     */
    private fun buildRetrofit(httpUrl: HttpUrl? = null): RetrofitOperation {
        val okHttpBuilder = RetrofitManager.getOkHttpBuilder()?.createOkHttpBuilder(key)
        // 可以通过 retrofit?.baseUrl() 获取之前的配置
        mRetrofit = builder.createRetrofitBuilder(
            mRetrofit, httpUrl, okHttpBuilder
        ).build()
        return this
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Retrofit 对象
     * @param check 是否需要判断 Retrofit 是否为 null
     * @return Retrofit?
     */
    fun getRetrofit(check: Boolean = true): Retrofit? {
        if (check && mRetrofit == null) {
            buildRetrofit()
        }
        return mRetrofit
    }

    /**
     * 通过 Retrofit 代理创建 Service
     * @param service Class<T>
     * @return Service Class
     */
    fun <T> create(service: Class<T>): T? {
        return getRetrofit()?.create(service)
    }

    /**
     * 重置处理 ( 重新构建 Retrofit )
     * @param httpUrl 构建使用指定 baseUrl
     * @return Retrofit Operation
     */
    fun reset(httpUrl: HttpUrl? = null): RetrofitOperation {
        return buildRetrofit(httpUrl)
    }

    /**
     * 重置处理 ( 重新构建 Retrofit ) 并代理创建 Service
     * @param httpUrl 构建使用指定 baseUrl
     * @return Retrofit Operation
     */
    fun <T> resetAndCreate(
        service: Class<T>,
        httpUrl: HttpUrl? = null
    ): T? {
        return reset(httpUrl).create(service)
    }
}