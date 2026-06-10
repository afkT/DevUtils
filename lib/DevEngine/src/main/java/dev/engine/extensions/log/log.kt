package dev.engine.extensions.log

import dev.engine.DevEngine
import dev.engine.log.ILogEngine

// ==============
// = ILogEngine =
// ==============

/**
 * 通过 Key 获取 Log Engine
 * @receiver String?
 * @return ILogEngine
 * 内部做了处理如果匹配不到则返回默认 Log Engine
 */
fun String?.getLogEngine(): ILogEngine? {
    DevEngine.getLog(this)?.let { value ->
        return value
    }
    return DevEngine.getLog()
}

// ==================
// = Key Log Engine =
// ==================

/**
 * 判断是否打印日志
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun log_isPrintLog(
    engine: String? = null
): Boolean {
    return engine.getLogEngine()?.isPrintLog ?: false
}

// =============================
// = 使用默认 TAG ( 日志打印方法 ) =
// =============================

/**
 * 打印 Log.DEBUG
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun log_d(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.d(message, *args)
}

/**
 * 打印 Log.ERROR
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun log_e(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.e(message, *args)
}

/**
 * 打印 Log.ERROR
 * @param engine String?
 * @param throwable 异常
 */
fun log_e(
    engine: String? = null,
    throwable: Throwable?
) {
    engine.getLogEngine()?.e(throwable)
}

/**
 * 打印 Log.ERROR
 * @param engine String?
 * @param throwable 异常
 * @param message 日志信息
 * @param args 格式化参数
 */
fun log_e(
    engine: String? = null,
    throwable: Throwable?,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.e(throwable, message, *args)
}

/**
 * 打印 Log.WARN
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun log_w(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.w(message, *args)
}

/**
 * 打印 Log.INFO
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun log_i(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.i(message, *args)
}

/**
 * 打印 Log.VERBOSE
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun log_v(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.v(message, *args)
}

/**
 * 打印 Log.ASSERT
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun log_wtf(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.wtf(message, *args)
}

// =

/**
 * 格式化 JSON 格式数据, 并打印
 * @param engine String?
 * @param json JSON 格式字符串
 */
fun log_json(
    engine: String? = null,
    json: String?
) {
    engine.getLogEngine()?.json(json)
}

/**
 * 格式化 XML 格式数据, 并打印
 * @param engine String?
 * @param xml XML 格式字符串
 */
fun log_xml(
    engine: String? = null,
    xml: String?
) {
    engine.getLogEngine()?.xml(xml)
}

// ==============================
// = 使用自定义 TAG ( 日志打印方法 ) =
// ==============================

/**
 * 打印 Log.DEBUG
 * @receiver 日志 TAG
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun String.log_dTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.dTag(this, message, *args)
}

/**
 * 打印 Log.ERROR
 * @receiver 日志 TAG
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun String.log_eTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.eTag(this, message, *args)
}

/**
 * 打印 Log.ERROR
 * @receiver 日志 TAG
 * @param engine String?
 * @param throwable 异常
 */
fun String.log_eTag(
    engine: String? = null,
    throwable: Throwable?
) {
    engine.getLogEngine()?.eTag(this, throwable)
}

/**
 * 打印 Log.ERROR
 * @receiver 日志 TAG
 * @param engine String?
 * @param throwable 异常
 * @param message 日志信息
 * @param args 格式化参数
 */
fun String.log_eTag(
    engine: String? = null,
    throwable: Throwable?,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.eTag(this, throwable, message, *args)
}

/**
 * 打印 Log.WARN
 * @receiver 日志 TAG
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun String.log_wTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.wTag(this, message, *args)
}

/**
 * 打印 Log.INFO
 * @receiver 日志 TAG
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun String.log_iTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.iTag(this, message, *args)
}

/**
 * 打印 Log.VERBOSE
 * @receiver 日志 TAG
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun String.log_vTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.vTag(this, message, *args)
}

/**
 * 打印 Log.ASSERT
 * @receiver 日志 TAG
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun String.log_wtfTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.wtfTag(this, message, *args)
}

// =

/**
 * 格式化 JSON 格式数据, 并打印
 * @receiver 日志 TAG
 * @param engine String?
 * @param json JSON 格式字符串
 */
fun String.log_jsonTag(
    engine: String? = null,
    json: String?
) {
    engine.getLogEngine()?.jsonTag(this, json)
}

/**
 * 格式化 XML 格式数据, 并打印
 * @receiver 日志 TAG
 * @param engine String?
 * @param xml XML 格式字符串
 */
fun String.log_xmlTag(
    engine: String? = null,
    xml: String?
) {
    engine.getLogEngine()?.xmlTag(this, xml)
}