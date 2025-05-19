package dev.capture.interfaces

import dev.capture.CaptureInfo
import dev.capture.CaptureItem
import dev.capture.CaptureRedact
import dev.utils.common.cipher.Encrypt
import okhttp3.*
import okio.Buffer

/**
 * detail: Http 拦截过滤器
 * @author Ttt
 */
interface IHttpFilter {

    /**
     * 是否过滤该 Http 请求不进行抓包存储
     * @param request  请求对象
     * @param url      请求链接
     * @param method   请求方法
     * @param protocol 请求协议
     * @param headers  请求头信息
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filter(
        request: Request,
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

    /**
     * 获取抓包信息隐藏字段
     * @return [dev.capture.CaptureRedact]
     */
    fun captureRedact(): CaptureRedact

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
    fun getModuleHttpCaptures(): MutableList<CaptureItem>
}

/**
 * detail: Http 抓包事件回调
 * @author Ttt
 * 方便二次扩展, 允许自行解析抓包数据
 */
interface IHttpCaptureEvent : IHttpCaptureEnd {

    // ===========
    // = request =
    // ===========

    /**
     * 生成请求链接字符串
     * @param request 请求对象
     * @param url 请求链接
     * @return 请求链接字符串
     */
    fun callRequestUrl(
        request: Request,
        url: HttpUrl
    ): String

    /**
     * 生成请求方法字符串
     * @param request 请求对象
     * @param method 请求方法
     * @param protocol 请求协议
     * @param requestBody 请求体
     * @return 请求方法字符串
     */
    fun callRequestMethod(
        request: Request,
        method: String,
        protocol: Protocol,
        requestBody: RequestBody?
    ): String

    /**
     * 生成请求头信息 Map
     * @param request 请求对象
     * @param headers 请求头信息
     * @param requestBody 请求体
     * @param captureRedact 抓包信息隐藏字段
     * @return 请求头信息 Map
     */
    fun callRequestHeaders(
        request: Request,
        headers: Headers,
        requestBody: RequestBody?,
        captureRedact: CaptureRedact
    ): LinkedHashMap<String, String>

    /**
     * 生成请求体信息 Map
     * @param request 请求对象
     * @param requestBody 请求体
     * @param captureRedact 抓包信息隐藏字段
     * @return 请求体信息 Map
     */
    fun callRequestBody(
        request: Request,
        requestBody: RequestBody?,
        captureRedact: CaptureRedact
    ): LinkedHashMap<String, String>

    // ============
    // = response =
    // ============

    /**
     * 生成响应状态 Map
     * @param request 请求对象
     * @param response 响应对象
     * @param responseBody 响应体
     * @param tookMs 响应耗时
     * @return 响应状态 Map
     */
    fun callResponseStatus(
        request: Request,
        response: Response,
        responseBody: ResponseBody,
        tookMs: Long
    ): LinkedHashMap<String, String>

    /**
     * 生成响应头信息 Map
     * @param request 请求对象
     * @param response 响应对象
     * @param headers 响应头信息
     * @param responseBody 响应体
     * @param captureRedact 抓包信息隐藏字段
     * @return 响应头信息 Map
     */
    fun callResponseHeaders(
        request: Request,
        response: Response,
        headers: Headers,
        responseBody: ResponseBody,
        captureRedact: CaptureRedact
    ): LinkedHashMap<String, String>

    /**
     * 生成错误响应体信息
     * @param request 请求对象
     * @param error 异常信息
     * @return 错误响应体信息
     */
    fun callResponseBodyFailed(
        request: Request,
        error: Exception
    ): String

    /**
     * 生成响应体信息 Map
     * @param request 请求对象
     * @param response 响应对象
     * @param responseBody 响应体
     * @return 响应体信息 Map
     */
    fun callResponseBody(
        request: Request,
        response: Response,
        responseBody: ResponseBody,
    ): String

    // ==========
    // = 转换处理 =
    // ==========

    /**
     * 转换请求体信息 Map
     * @param request 请求对象
     * @param requestBody 请求体
     * @param captureRedact 抓包信息隐藏字段
     * @param buffer Body Buffer
     * @return 请求体信息 Map
     */
    fun converterRequestBody(
        request: Request,
        requestBody: RequestBody,
        captureRedact: CaptureRedact,
        buffer: Buffer
    ): LinkedHashMap<String, String>
}

/**
 * detail: Http 抓包结束回调
 * @author Ttt
 */
interface IHttpCaptureEnd {

    /**
     * 抓包结束
     * @param info 抓包数据
     */
    fun callEnd(info: CaptureInfo)
}

/**
 * detail: Http 抓包事件处理拦截
 * @author Ttt
 * 用于拦截 [IHttpCaptureEvent] 对应事件数据是否进行转换并设置到 [CaptureInfo] 对应属性
 */
interface IHttpCaptureEventFilter {

    // ===========
    // = request =
    // ===========

    /**
     * 是否过滤请求链接字符串
     * @param request  请求对象
     * @param url      请求链接
     * @param method   请求方法
     * @param protocol 请求协议
     * @param headers  请求头信息
     * @param requestBody 请求体
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterRequestUrl(
        request: Request,
        url: HttpUrl,
        method: String,
        protocol: Protocol,
        headers: Headers,
        requestBody: RequestBody?
    ): Boolean = false

    /**
     * 是否过滤请求方法字符串
     * @param request  请求对象
     * @param url      请求链接
     * @param method   请求方法
     * @param protocol 请求协议
     * @param headers  请求头信息
     * @param requestBody 请求体
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterRequestMethod(
        request: Request,
        url: HttpUrl,
        method: String,
        protocol: Protocol,
        headers: Headers,
        requestBody: RequestBody?
    ): Boolean = false

    /**
     * 是否过滤请求头信息 Map
     * @param request  请求对象
     * @param url      请求链接
     * @param method   请求方法
     * @param protocol 请求协议
     * @param headers  请求头信息
     * @param requestBody 请求体
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterRequestHeaders(
        request: Request,
        url: HttpUrl,
        method: String,
        protocol: Protocol,
        headers: Headers,
        requestBody: RequestBody?
    ): Boolean = false

    /**
     * 是否过滤请求体信息 Map
     * @param request  请求对象
     * @param url      请求链接
     * @param method   请求方法
     * @param protocol 请求协议
     * @param headers  请求头信息
     * @param requestBody 请求体
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterRequestBody(
        request: Request,
        url: HttpUrl,
        method: String,
        protocol: Protocol,
        headers: Headers,
        requestBody: RequestBody?
    ): Boolean = false

    // ============
    // = response =
    // ============

    /**
     * 是否过滤响应状态 Map
     * @param request 请求对象
     * @param response 响应对象
     * @param headers 响应头信息
     * @param responseBody 响应体
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterResponseStatus(
        request: Request,
        response: Response,
        headers: Headers,
        responseBody: ResponseBody
    ): Boolean = false

    /**
     * 是否过滤响应头信息 Map
     * @param request 请求对象
     * @param response 响应对象
     * @param headers 响应头信息
     * @param responseBody 响应体
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterResponseHeaders(
        request: Request,
        response: Response,
        headers: Headers,
        responseBody: ResponseBody
    ): Boolean = false

    /**
     * 是否过滤错误响应体信息
     * @param request 请求对象
     * @param error 异常信息
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterResponseBodyFailed(
        request: Request,
        error: Exception
    ): Boolean = false

    /**
     * 是否过滤响应体信息 Map
     * @param request 请求对象
     * @param response 响应对象
     * @param headers 响应头信息
     * @param responseBody 响应体
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterResponseBody(
        request: Request,
        response: Response,
        headers: Headers,
        responseBody: ResponseBody
    ): Boolean = false

    // ============================
    // = HttpCaptureStorageEngine =
    // ============================

    /**
     * 是否过滤抓包数据存储
     * @param info 抓包信息封装类
     * @return `true` 拦截操作, `false` 不拦截
     */
    fun filterCaptureStorage(
        info: CaptureInfo
    ): Boolean = false
}