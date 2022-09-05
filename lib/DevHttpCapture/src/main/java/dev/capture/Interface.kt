package dev.capture

import dev.utils.common.cipher.Encrypt
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.Protocol

/**
 * detail: Http 拦截过滤器
 * @author Ttt
 */
interface IHttpFilter {

    /**
     * 是否过滤该 Http 请求不进行抓包存储
     * @param url      请求链接
     * @param method   请求方法
     * @param protocol 请求协议
     * @param headers  请求头部信息
     * @return `true` yes, `false` no
     * 返回 true 则不进行存储 Http 请求信息
     */
    fun filter(
        url: HttpUrl,
        method: String,
        protocol: Protocol,
        headers: Headers
    ): Boolean
}

/**
 * detail: Http 抓包接口信息获取
 * @author Ttt
 */
interface IHttpCapture {

    // ==========
    // = 基础方法 =
    // ==========

    /**
     * 获取模块名 ( 要求唯一性 )
     * @return 模块名
     */
    fun getModuleName(): String

    /**
     * 获取抓包数据加密中间层
     * @return [Encrypt]
     */
    fun getEncrypt(): Encrypt?

    /**
     * 获取 Http 拦截过滤器
     * @return [IHttpFilter]
     */
    fun getHttpFilter(): IHttpFilter?

    /**
     * 是否进行 Http 抓包拦截
     * @return `true` yes, `false` no
     */
    fun isCapture(): Boolean

    /**
     * 设置是否进行 Http 抓包拦截
     * @param capture `true` yes, `false` no
     */
    fun setCapture(capture: Boolean)

    // ==========
    // = 获取操作 =
    // ==========

    /**
     * 获取模块抓包存储路径
     * @return 模块抓包存储路径
     */
    fun getModulePath(): String

    /**
     * 获取模块所有抓包数据
     * @return 模块所有抓包数据
     */
    fun getModuleHttpCaptures(): List<CaptureItem>
}