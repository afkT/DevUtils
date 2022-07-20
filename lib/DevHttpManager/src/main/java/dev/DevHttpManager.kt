package dev

import dev.http.BuildConfig
import dev.http.manager.*
import dev.http.progress.ProgressManager
import dev.http.progress.ProgressOperation
import okhttp3.HttpUrl

/**
 * detail: OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
 * @author Ttt
 * <p></p>
 * GitHub
 * @see https://github.com/afkT/DevUtils
 * DevApp Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
 * DevAssist Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md
 * DevBase README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md
 * DevBaseMVVM README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md
 * DevEngine README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 */
object DevHttpManager {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevHttpManager 版本号
     * @return DevHttpManager versionCode
     */
    fun getDevHttpManagerVersionCode(): Int {
        return BuildConfig.DevHttpManager_VersionCode
    }

    /**
     * 获取 DevHttpManager 版本
     * @return DevHttpManager versionName
     */
    fun getDevHttpManagerVersion(): String {
        return BuildConfig.DevHttpManager_Version
    }

    /**
     * 获取 DevApp 版本号
     * @return DevApp versionCode
     */
    fun getDevAppVersionCode(): Int {
        return BuildConfig.DevApp_VersionCode
    }

    /**
     * 获取 DevApp 版本
     * @return DevApp versionName
     */
    fun getDevAppVersion(): String {
        return BuildConfig.DevApp_Version
    }

    // =============
    // = 对外公开方法 =
    // =============

    // ===================
    // = RetrofitManager =
    // ===================

    object RM {

        // =================
        // = OkHttpBuilder =
        // =================

        /**
         * 获取全局 OkHttp Builder 接口对象
         * @return OkHttpBuilder
         */
        @JvmStatic
        fun getOkHttpBuilder(): OkHttpBuilder? {
            return RetrofitManager.getOkHttpBuilder()
        }

        /**
         * 设置全局 OkHttp Builder 接口对象
         * @param builder [OkHttpBuilder]
         */
        @JvmStatic
        fun setOkHttpBuilder(builder: OkHttpBuilder?) {
            RetrofitManager.setOkHttpBuilder(builder)
        }

        /**
         * 移除全局 OkHttp Builder 接口对象
         */
        @JvmStatic
        fun removeOkHttpBuilder() {
            RetrofitManager.removeOkHttpBuilder()
        }

        // ===========================
        // = OnRetrofitResetListener =
        // ===========================

        /**
         * 获取全局 Retrofit 重新构建监听事件
         * @return OnRetrofitResetListener
         */
        @JvmStatic
        fun getRetrofitResetListener(): OnRetrofitResetListener? {
            return RetrofitManager.getRetrofitResetListener()
        }

        /**
         * 设置全局 Retrofit 重新构建监听事件
         * @param listener [OnRetrofitResetListener]
         */
        @JvmStatic
        fun setRetrofitResetListener(listener: OnRetrofitResetListener?) {
            RetrofitManager.setRetrofitResetListener(listener)
        }

        /**
         * 移除全局 Retrofit 重新构建监听事件
         */
        @JvmStatic
        fun removeRetrofitResetListener() {
            RetrofitManager.removeRetrofitResetListener()
        }

        // ===================
        // = RetrofitBuilder =
        // ===================

        /**
         * 获取 Retrofit Operation 操作对象
         * @param key Key
         * @return Retrofit Operation
         */
        @JvmStatic
        fun getOperation(key: String): RetrofitOperation? {
            return RetrofitManager.getOperation(key)
        }

        /**
         * 通过 Key 判断是否存在 Retrofit Operation 操作对象
         * @param key Key
         * @return `true` yes, `false` no
         */
        @JvmStatic
        fun containsOperation(key: String): Boolean {
            return RetrofitManager.containsOperation(key)
        }

        /**
         * 通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象
         * @param key Key
         * @param builder [RetrofitBuilder]
         * @return Retrofit Operation
         */
        @JvmStatic
        fun putRetrofitBuilder(
            key: String,
            builder: RetrofitBuilder
        ): RetrofitOperation {
            return RetrofitManager.putRetrofitBuilder(key, builder)
        }

        /**
         * 通过 Key 解绑移除 RetrofitBuilder 并返回 Operation 操作对象
         * @param key Key
         * @return Retrofit Operation
         */
        @JvmStatic
        fun removeRetrofitBuilder(key: String): RetrofitOperation? {
            return RetrofitManager.removeRetrofitBuilder(key)
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
        @JvmStatic
        fun reset(
            key: String,
            httpUrl: HttpUrl? = null
        ): RetrofitOperation? {
            return RetrofitManager.reset(key, httpUrl)
        }

        /**
         * 重置处理 ( 重新构建全部 Retrofit )
         * @param mapHttpUrl MutableMap<String?, HttpUrl?>
         */
        @JvmStatic
        fun resetAll(mapHttpUrl: MutableMap<String?, HttpUrl?>? = null) {
            RetrofitManager.resetAll(mapHttpUrl)
        }
    }

    // ===================
    // = ProgressManager =
    // ===================

    object PM {

        /**
         * 获取全局默认 Progress Operation 操作对象
         * @return ProgressOperation
         */
        @JvmStatic
        fun getDefault(): ProgressOperation {
            return ProgressManager.getDefault()
        }

        /**
         * 获取 Progress Operation 操作对象
         * @param key Key
         * @return Progress Operation
         */
        @JvmStatic
        fun getOperation(key: String): ProgressOperation? {
            return ProgressManager.getOperation(key)
        }

        /**
         * 通过 Key 判断是否存在 Progress Operation 操作对象
         * @param key Key
         * @return `true` yes, `false` no
         */
        @JvmStatic
        fun containsOperation(key: String): Boolean {
            return ProgressManager.containsOperation(key)
        }

        /**
         * 通过 Key 解绑并返回 Operation 操作对象
         * @param key Key
         * @return Progress Operation
         */
        @JvmStatic
        fun removeOperation(key: String): ProgressOperation? {
            return ProgressManager.removeOperation(key)
        }

        /**
         * 清空所有 Progress Operation 操作对象
         */
        @JvmStatic
        fun clearOperation() {
            ProgressManager.clearOperation()
        }

        // =

        /**
         * 通过 Key 绑定并返回 Operation 操作对象 ( 监听上下行 )
         * @param key Key
         * @return Progress Operation
         */
        @JvmStatic
        fun putOperationTypeAll(key: String): ProgressOperation {
            return ProgressManager.putOperationTypeAll(key)
        }

        /**
         * 通过 Key 绑定并返回 Operation 操作对象 ( 监听上行 )
         * @param key Key
         * @return Progress Operation
         */
        @JvmStatic
        fun putOperationTypeRequest(key: String): ProgressOperation {
            return ProgressManager.putOperationTypeRequest(key)
        }

        /**
         * 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 )
         * @param key Key
         * @return Progress Operation
         */
        @JvmStatic
        fun putOperationTypeResponse(key: String): ProgressOperation {
            return ProgressManager.putOperationTypeResponse(key)
        }
    }
}