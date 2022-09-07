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