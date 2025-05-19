package dev

import dev.capture.BuildConfig
import dev.capture.CaptureInfo
import dev.capture.CaptureItem
import dev.capture.UtilsPublic
import dev.capture.interceptor.StorageInterceptor
import dev.capture.interfaces.HttpCaptureEventIMPL
import dev.capture.interfaces.IHttpCapture
import dev.capture.interfaces.IHttpCaptureEvent
import dev.capture.interfaces.IHttpFilter
import dev.utils.common.StringUtils
import dev.utils.common.cipher.Encrypt
import okhttp3.OkHttpClient

/**
 * detail: OkHttp 抓包工具库
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
 * DevSimple README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 */
object DevHttpCapture {

    // 日志 TAG
    val TAG = DevHttpCapture::class.java.simpleName

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevHttpCapture 版本号
     * @return DevHttpCapture versionCode
     */
    fun getDevHttpCaptureVersionCode(): Int {
        return BuildConfig.DevHttpCapture_VersionCode
    }

    /**
     * 获取 DevHttpCapture 版本
     * @return DevHttpCapture versionName
     */
    fun getDevHttpCaptureVersion(): String {
        return BuildConfig.DevHttpCapture_Version
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

    // ===============
    // = Interceptor =
    // ===============

    // Http 抓包接口 Map
    private val sCaptureMaps = linkedMapOf<String, IHttpCapture>()

    /**
     * 添加 Http 抓包拦截处理
     * @param builder    OkHttpClient Builder
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param encrypt    抓包数据加密中间层
     * @param httpFilter Http 拦截过滤器
     * @param capture    是否进行 Http 抓包拦截
     * @param eventIMPL  Http 抓包事件回调
     * @return `true` success, `false` fail
     */
    fun addInterceptor(
        builder: OkHttpClient.Builder,
        moduleName: String,
        encrypt: Encrypt? = null,
        httpFilter: IHttpFilter? = null,
        capture: Boolean = true,
        eventIMPL: IHttpCaptureEvent = object : HttpCaptureEventIMPL() {
            override fun callEnd(info: CaptureInfo) {
            }
        }
    ): Boolean {
        if (StringUtils.isNotEmpty(moduleName)) {
            if (!sCaptureMaps.containsKey(moduleName)) {
                val interceptor = StorageInterceptor(
                    moduleName, encrypt, httpFilter,
                    capture, eventIMPL
                )
                // 添加抓包拦截
                builder.addInterceptor(interceptor)
                // 保存 IHttpCapture 接口对象
                sCaptureMaps[moduleName] = interceptor
                return true
            }
        }
        return false
    }

    /**
     * 是否存在对应 Module Http 抓包拦截
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return `true` yes, `false` no
     */
    fun containsInterceptor(moduleName: String): Boolean {
        return if (StringUtils.isNotEmpty(moduleName)) {
            sCaptureMaps.containsKey(moduleName)
        } else false
    }

    /**
     * 移除对应 Module Http 抓包拦截
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return `true` success, `false` fail
     */
    fun removeInterceptor(moduleName: String): Boolean {
        if (StringUtils.isNotEmpty(moduleName)) {
            val httpCapture = sCaptureMaps[moduleName]
            if (httpCapture != null) {
                httpCapture.setCapture(false)
                sCaptureMaps.remove(moduleName)
                return true
            }
        }
        return false
    }

    /**
     * 更新对应 Module Http 抓包拦截处理
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param capture    是否进行 Http 抓包拦截
     * @return `true` success, `false` fail
     */
    fun updateInterceptor(
        moduleName: String,
        capture: Boolean
    ): Boolean {
        if (StringUtils.isNotEmpty(moduleName)) {
            val httpCapture = sCaptureMaps[moduleName]
            if (httpCapture != null) {
                httpCapture.setCapture(capture)
                return true
            }
        }
        return false
    }

    // ==========
    // = 获取操作 =
    // ==========

    /**
     * 获取指定模块抓包存储路径
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块抓包存储路径
     */
    fun getModulePath(moduleName: String): String? {
        if (StringUtils.isNotEmpty(moduleName)) {
            val httpCapture = sCaptureMaps[moduleName]
            if (httpCapture != null) {
                return httpCapture.getModulePath()
            }
        }
        return null
    }

    /**
     * 获取指定模块所有抓包数据
     * @param moduleName 模块名 ( 要求唯一性 )
     * @return 指定模块所有抓包数据
     */
    fun getModuleHttpCaptures(moduleName: String): MutableList<CaptureItem> {
        if (StringUtils.isNotEmpty(moduleName)) {
            val httpCapture = sCaptureMaps[moduleName]
            if (httpCapture != null) {
                return httpCapture.getModuleHttpCaptures()
            }
        }
        return mutableListOf()
    }

    // =================
    // = 对外公开快捷方法 =
    // =================

    /**
     * 对外公开快捷工具类 ( UtilsPublic )
     * @return [UtilsPublic]
     */
    fun utils(): UtilsPublic {
        return UtilsPublic.get()
    }
}