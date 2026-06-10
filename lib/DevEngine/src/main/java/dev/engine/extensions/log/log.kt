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
 * @receiver 日志信息
 * @param engine String?
 * @param args 格式化参数
 */
fun String?.log_d(
    engine: String? = null,
    vararg args: Any?
) {
    engine.getLogEngine()?.d(this, *args)
}

/**
 * 打印 Log.ERROR
 * @receiver 日志信息
 * @param engine String?
 * @param args 格式化参数
 */
fun String?.log_e(
    engine: String? = null,
    vararg args: Any?
) {
    engine.getLogEngine()?.e(this, *args)
}

/**
 * 打印 Log.ERROR
 * @receiver 异常
 * @param engine String?
 */
fun Throwable?.log_e(
    engine: String? = null
) {
    engine.getLogEngine()?.e(this)
}

/**
 * 打印 Log.ERROR
 * @receiver 异常
 * @param engine String?
 * @param message 日志信息
 * @param args 格式化参数
 */
fun Throwable?.log_e(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.e(this, message, *args)
}

/**
 * 打印 Log.WARN
 * @receiver 日志信息
 * @param engine String?
 * @param args 格式化参数
 */
fun String?.log_w(
    engine: String? = null,
    vararg args: Any?
) {
    engine.getLogEngine()?.w(this, *args)
}

/**
 * 打印 Log.INFO
 * @receiver 日志信息
 * @param engine String?
 * @param args 格式化参数
 */
fun String?.log_i(
    engine: String? = null,
    vararg args: Any?
) {
    engine.getLogEngine()?.i(this, *args)
}

/**
 * 打印 Log.VERBOSE
 * @receiver 日志信息
 * @param engine String?
 * @param args 格式化参数
 */
fun String?.log_v(
    engine: String? = null,
    vararg args: Any?
) {
    engine.getLogEngine()?.v(this, *args)
}

/**
 * 打印 Log.ASSERT
 * @receiver 日志信息
 * @param engine String?
 * @param args 格式化参数
 */
fun String?.log_wtf(
    engine: String? = null,
    vararg args: Any?
) {
    engine.getLogEngine()?.wtf(this, *args)
}

// =

/**
 * 格式化 JSON 格式数据, 并打印
 * @receiver JSON 格式字符串
 * @param engine String?
 */
fun String?.log_json(
    engine: String? = null
) {
    engine.getLogEngine()?.json(this)
}

/**
 * 格式化 XML 格式数据, 并打印
 * @receiver XML 格式字符串
 * @param engine String?
 */
fun String?.log_xml(
    engine: String? = null
) {
    engine.getLogEngine()?.xml(this)
}

// ==============================
// = 使用自定义 TAG ( 日志打印方法 ) =
// ==============================

/**
 * 打印 Log.DEBUG
 * @receiver 日志信息
 * @param engine String?
 * @param tag 日志 TAG
 * @param args 格式化参数
 */
fun String?.log_dTag(
    engine: String? = null,
    tag: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.dTag(tag, this, *args)
}

/**
 * 打印 Log.ERROR
 * @receiver 日志信息
 * @param engine String?
 * @param tag 日志 TAG
 * @param args 格式化参数
 */
fun String?.log_eTag(
    engine: String? = null,
    tag: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.eTag(tag, this, *args)
}

/**
 * 打印 Log.ERROR
 * @receiver 异常
 * @param engine String?
 * @param tag 日志 TAG
 */
fun Throwable?.log_eTag(
    engine: String? = null,
    tag: String?
) {
    engine.getLogEngine()?.eTag(tag, this)
}

/**
 * 打印 Log.ERROR
 * @receiver 异常
 * @param engine String?
 * @param tag 日志 TAG
 * @param message 日志信息
 * @param args 格式化参数
 */
fun Throwable?.log_eTag(
    engine: String? = null,
    tag: String?,
    message: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.eTag(tag, this, message, *args)
}

/**
 * 打印 Log.WARN
 * @receiver 日志信息
 * @param engine String?
 * @param tag 日志 TAG
 * @param args 格式化参数
 */
fun String?.log_wTag(
    engine: String? = null,
    tag: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.wTag(tag, this, *args)
}

/**
 * 打印 Log.INFO
 * @receiver 日志信息
 * @param engine String?
 * @param tag 日志 TAG
 * @param args 格式化参数
 */
fun String?.log_iTag(
    engine: String? = null,
    tag: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.iTag(tag, this, *args)
}

/**
 * 打印 Log.VERBOSE
 * @receiver 日志信息
 * @param engine String?
 * @param tag 日志 TAG
 * @param args 格式化参数
 */
fun String?.log_vTag(
    engine: String? = null,
    tag: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.vTag(tag, this, *args)
}

/**
 * 打印 Log.ASSERT
 * @receiver 日志信息
 * @param engine String?
 * @param tag 日志 TAG
 * @param args 格式化参数
 */
fun String?.log_wtfTag(
    engine: String? = null,
    tag: String?,
    vararg args: Any?
) {
    engine.getLogEngine()?.wtfTag(tag, this, *args)
}

// =

/**
 * 格式化 JSON 格式数据, 并打印
 * @receiver JSON 格式字符串
 * @param engine String?
 * @param tag 日志 TAG
 */
fun String?.log_jsonTag(
    engine: String? = null,
    tag: String?
) {
    engine.getLogEngine()?.jsonTag(tag, this)
}

/**
 * 格式化 XML 格式数据, 并打印
 * @receiver XML 格式字符串
 * @param engine String?
 * @param tag 日志 TAG
 */
fun String?.log_xmlTag(
    engine: String? = null,
    tag: String?
) {
    engine.getLogEngine()?.xmlTag(tag, this)
}