package dev.expand.engine.log

import dev.engine.DevEngine
import dev.engine.log.ILogEngine

// ==============
// = ILogEngine =
// ==============

/**
 * 通过 Key 获取 Log Engine
 * @param engine String?
 * @return ILogEngine
 * 内部做了处理如果匹配不到则返回默认 Log Engine
 */
fun String?.getEngine(): ILogEngine? {
    DevEngine.getLog(this)?.let { value ->
        return value
    }
    return DevEngine.getLog()
}

// ==================
// = Key Log Engine =
// ==================

fun log_isPrintLog(
    engine: String? = null
): Boolean {
    return engine.getEngine()?.isPrintLog ?: false
}

// =============================
// = 使用默认 TAG ( 日志打印方法 ) =
// =============================

fun log_d(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.d(message, *args)
}

fun log_e(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.e(message, *args)
}

fun log_e(
    engine: String? = null,
    throwable: Throwable?
) {
    engine.getEngine()?.e(throwable)
}

fun log_e(
    engine: String? = null,
    throwable: Throwable?,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.e(throwable, message, *args)
}

fun log_w(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.w(message, *args)
}

fun log_i(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.i(message, *args)
}

fun log_v(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.v(message, *args)
}

fun log_wtf(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.wtf(message, *args)
}

// =

fun log_json(
    engine: String? = null,
    json: String?
) {
    engine.getEngine()?.json(json)
}

fun log_xml(
    engine: String? = null,
    xml: String?
) {
    engine.getEngine()?.xml(xml)
}

// ==============================
// = 使用自定义 TAG ( 日志打印方法 ) =
// ==============================

fun String.log_dTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.dTag(this, message, *args)
}

fun String.log_eTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.eTag(this, message, *args)
}

fun String.log_eTag(
    engine: String? = null,
    throwable: Throwable?
) {
    engine.getEngine()?.eTag(this, throwable)
}

fun String.log_eTag(
    engine: String? = null,
    throwable: Throwable?,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.eTag(this, throwable, message, *args)
}

fun String.log_wTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.wTag(this, message, *args)
}

fun String.log_iTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.iTag(this, message, *args)
}

fun String.log_vTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.vTag(this, message, *args)
}

fun String.log_wtfTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    engine.getEngine()?.wtfTag(this, message, *args)
}

// =

fun String.log_jsonTag(
    engine: String? = null,
    json: String?
) {
    engine.getEngine()?.jsonTag(this, json)
}

fun String.log_xmlTag(
    engine: String? = null,
    xml: String?
) {
    engine.getEngine()?.xmlTag(this, xml)
}