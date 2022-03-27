package dev.http.manager

import dev.utils.LogPrintUtils
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
         * 创建 Retrofit Operation
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

    // 日志 TAG
    private val TAG = RetrofitOperation::class.java.simpleName

    // Retrofit
    private var mRetrofit: Retrofit? = null

    // 是否重置操作 ( 首次为初始化 )
    private var mReset: Boolean = false

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 构建 Retrofit 方法 ( 最终调用 )
     * @param httpUrl 构建使用指定 baseUrl
     * @return Retrofit Operation
     * 执行循序为
     * Global onResetBefore
     * builder ( this ) onResetBefore
     * Global createOkHttpBuilder
     * builder ( this ) createRetrofitBuilder
     * builder ( this ) onReset
     * Global onReset
     * 使用全局监听事件、构建操作是为了提供统一管理方法, 方便统一做处理
     * 并且自身也存在回调方法, 也能够单独处理
     */
    private fun buildRetrofit(httpUrl: HttpUrl? = null): RetrofitOperation {
        if (mReset) {
            try {
                RetrofitManager.getRetrofitResetListener()?.onResetBefore(
                    key, mRetrofit
                )
            } catch (e: Exception) {
            }
            builder.onResetBefore(key, mRetrofit)
        }

        // 获取全局 OkHttp Builder
        val okHttpBuilder = try {
            RetrofitManager.getOkHttpBuilder()?.createOkHttpBuilder(key)
        } catch (e: Exception) {
            null
        }
        // 可以通过 mRetrofit?.baseUrl() 获取之前的配置
        mRetrofit = builder.createRetrofitBuilder(
            mRetrofit, httpUrl, okHttpBuilder
        ).build()

        if (mReset) {
            builder.onReset(key, mRetrofit)
            try {
                RetrofitManager.getRetrofitResetListener()?.onReset(
                    key, mRetrofit
                )
            } catch (e: Exception) {
            }
        }
        // 首次为初始化, 后续操作为重置操作
        mReset = true
        return this
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Retrofit 对象
     * @param check 是否需要判断 Retrofit 是否为 null
     * @return Retrofit
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
        try {
            return getRetrofit()?.create(service)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "create")
        }
        return null
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