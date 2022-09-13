package dev.capture

import dev.capture.HttpCaptureEventIMPL.Companion.REDACT_REPLACE_VALUE
import dev.utils.common.FileUtils
import java.io.File

/**
 * detail: 抓包信息隐藏字段
 * @author Ttt
 */
class CaptureRedact(
    val replaceValue: String = REDACT_REPLACE_VALUE
) {
    // 请求头隐藏信息 Key
    val requestHeader: MutableSet<String> = mutableSetOf()

    // 请求体隐藏信息 Key
    val requestBody: MutableSet<String> = mutableSetOf()

    // 响应头隐藏信息 Key
    val responseHeader: MutableSet<String> = mutableSetOf()

    /**
     * 克隆新的抓包信息隐藏字段
     * @return [CaptureRedact]
     */
    fun clone(): CaptureRedact {
        val redact = CaptureRedact()
        redact.requestHeader.addAll(requestHeader)
        redact.requestBody.addAll(requestBody)
        redact.responseHeader.addAll(responseHeader)
        return redact
    }

    /**
     * 插入抓包信息隐藏字段
     * @param source 数据源
     * @param clear 是否清空旧数据
     */
    fun insertRedact(
        source: CaptureRedact,
        clear: Boolean = false
    ) {
        if (clear) {
            requestHeader.clear()
            requestBody.clear()
            responseHeader.clear()
        }
        requestHeader.addAll(source.requestHeader)
        requestBody.addAll(source.requestBody)
        responseHeader.addAll(source.responseHeader)
    }
}

/**
 * detail: 抓包存储 Item
 * @author Ttt
 */
class CaptureItem(
    // 存储时间 - 年月日 ( 文件夹名 )
    val yyyyMMdd: String
) {
    // 存储数据 - 时分
    val data = linkedMapOf<String, MutableList<CaptureFile>>()
}

/**
 * detail: 抓包信息封装类
 * @author Ttt
 */
class CaptureInfo {

    // 请求链接
    var requestUrl: String? = null

    // 请求方法
    var requestMethod: String? = null

    // 请求头信息
    val requestHeader = linkedMapOf<String, String>()

    // 请求数据
    val requestBody = linkedMapOf<String, String>()

    // 响应状态
    val responseStatus = linkedMapOf<String, String>()

    // 响应头信息
    val responseHeader = linkedMapOf<String, String>()

    // 响应数据
    var responseBody: String? = null

    /**
     * 将对象转换为 JSON String
     * @return JSON String
     */
    fun toJson(): String? {
        return Utils.toJson(this)
    }
}

/**
 * detail: 抓包存储文件
 * @author Ttt
 * 加密情况下, 抓包数据不会进行解析展示, 只能自行导出进行解密
 * 非加密情况下 [httpCaptureData] 则会映射成 [CaptureInfo] 实体类
 */
class CaptureFile {

    // 请求链接
    private var url: String? = null

    // 请求方法
    private var method: String? = null

    // 是否加密
    private var encrypt = false

    // 创建时间 ( 本地时间戳 )
    private var time: Long = 0

    // 文件名
    private var fileName: String? = null

    // 模块名
    private var moduleName: String? = null

    // =======
    // = get =
    // =======

    fun getUrl(): String? {
        return url
    }

    fun getMethod(): String? {
        return method
    }

    fun isEncrypt(): Boolean {
        return encrypt
    }

    fun getTime(): Long {
        return time
    }

    fun getFileName(): String? {
        return fileName
    }

    fun getModuleName(): String? {
        return moduleName
    }

    // =======
    // = set =
    // =======

    internal fun setUrl(url: String?): CaptureFile {
        this.url = url
        return this
    }

    internal fun setMethod(method: String?): CaptureFile {
        this.method = method
        return this
    }

    internal fun setEncrypt(encrypt: Boolean): CaptureFile {
        this.encrypt = encrypt
        return this
    }

    internal fun setTime(time: Long): CaptureFile {
        this.time = time
        return this
    }

    internal fun setFileName(fileName: String?): CaptureFile {
        this.fileName = fileName
        return this
    }

    internal fun setModuleName(moduleName: String?): CaptureFile {
        this.moduleName = moduleName
        return this
    }

    // ==========
    // = 抓包数据 =
    // ==========

    // 请求数据 ( 抓包数据 )
    @Transient
    internal var httpCaptureData: String? = null

    fun getHttpCaptureData(): String? {
        if (httpCaptureData == null) {
            httpCaptureData = FileUtils.readFile(getDataFile())
        }
        return httpCaptureData
    }

    // ==========
    // = 其他处理 =
    // ==========

    // 抓包信息封装类
    @Transient
    private var captureInfo: CaptureInfo? = null

    /**
     * 获取抓包信息封装类
     * @return 抓包信息封装类
     */
    fun getCaptureInfo(): CaptureInfo? {
        if (captureInfo == null && !encrypt) {
            captureInfo = Utils.fromJson(
                getHttpCaptureData(), CaptureInfo::class.java
            )
        }
        return captureInfo
    }

    /**
     * 将对象转换为 JSON String
     * @return JSON String
     */
    fun toJson(): String? {
        return Utils.toJson(this)
    }

    // =============
    // = 文件操作相关 =
    // =============

    /**
     * 删除该对象抓包存储文件
     * @return `true` success, `false` fail
     */
    fun deleteFile(): Boolean {
        FileUtils.deleteFile(getDataFile())
        return FileUtils.deleteFile(getFile())
    }

    /**
     * 获取该对象抓包存储文件
     * @return 该对象抓包存储文件
     */
    fun getFile(): File {
        return Utils.getModuleHttpCaptureFile(this)
    }

    /**
     * 获取该对象抓包数据存储文件
     * @return 该对象抓包数据存储文件
     */
    fun getDataFile(): File {
        return Utils.getModuleHttpCaptureDataFile(this)
    }
}