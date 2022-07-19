package dev.kotlin.engine.log

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
internal fun getEngine(engine: String?): ILogEngine? {
    DevEngine.getLog(engine)?.let { value ->
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
    return getEngine(engine)?.isPrintLog ?: false
}

// =============================
// = 使用默认 TAG ( 日志打印方法 ) =
// =============================

fun log_d(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.d(message, *args)
}

fun log_e(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.e(message, *args)
}

fun log_e(
    engine: String? = null,
    throwable: Throwable?
) {
    getEngine(engine)?.e(throwable)
}

fun log_e(
    engine: String? = null,
    throwable: Throwable?,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.e(throwable, message, *args)
}

fun log_w(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.w(message, *args)
}

fun log_i(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.i(message, *args)
}

fun log_v(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.v(message, *args)
}

fun log_wtf(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.wtf(message, *args)
}

// =

fun log_json(
    engine: String? = null,
    json: String?
) {
    getEngine(engine)?.json(json)
}

fun log_xml(
    engine: String? = null,
    xml: String?
) {
    getEngine(engine)?.xml(xml)
}

// ==============================
// = 使用自定义 TAG ( 日志打印方法 ) =
// ==============================

fun String.log_dTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.dTag(this, message, *args)
}

fun String.log_eTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.eTag(this, message, *args)
}

fun String.log_eTag(
    engine: String? = null,
    throwable: Throwable?
) {
    getEngine(engine)?.eTag(this, throwable)
}

fun String.log_eTag(
    engine: String? = null,
    throwable: Throwable?,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.eTag(this, throwable, message, *args)
}

fun String.log_wTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.wTag(this, message, *args)
}

fun String.log_iTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.iTag(this, message, *args)
}

fun String.log_vTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.vTag(this, message, *args)
}

fun String.log_wtfTag(
    engine: String? = null,
    message: String?,
    vararg args: Any?
) {
    getEngine(engine)?.wtfTag(this, message, *args)
}

// =

fun String.log_jsonTag(
    engine: String? = null,
    json: String?
) {
    getEngine(engine)?.jsonTag(this, json)
}

fun String.log_xmlTag(
    engine: String? = null,
    xml: String?
) {
    getEngine(engine)?.xmlTag(this, xml)
}