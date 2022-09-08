package dev.capture

import dev.capture.HttpCaptureEventIMPL.Companion.REDACT_REPLACE_VALUE

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
    val data: Map<String, List<CaptureFile>> = LinkedHashMap()
}