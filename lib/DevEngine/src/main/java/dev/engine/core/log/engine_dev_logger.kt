package dev.engine.core.log

import dev.engine.log.ILogEngine
import dev.utils.app.logger.DevLogger
import dev.utils.app.logger.LogConfig

/**
 * detail: DevLogger Log Engine 实现
 * @author Ttt
 */
abstract class DevLoggerEngineImpl(
    open val logConfig: LogConfig? = null
) : ILogEngine {

    // =============================
    // = 使用默认 TAG ( 日志打印方法 ) =
    // =============================

    override fun d(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).d(message, *args)
    }

    override fun e(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).e(message, *args)
    }

    override fun e(throwable: Throwable?) {
        DevLogger.other(logConfig).e(throwable)
    }

    override fun e(
        throwable: Throwable?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).e(throwable, message, *args)
    }

    override fun w(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).w(message, *args)
    }

    override fun i(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).i(message, *args)
    }

    override fun v(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).v(message, *args)
    }

    override fun wtf(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).wtf(message, *args)
    }

    override fun json(json: String?) {
        DevLogger.other(logConfig).json(json)
    }

    override fun xml(xml: String?) {
        DevLogger.other(logConfig).xml(xml)
    }

    // ==============================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // ==============================

    override fun dTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).dTag(tag, message, *args)
    }

    override fun eTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).eTag(tag, message, *args)
    }

    override fun eTag(
        tag: String?,
        throwable: Throwable?
    ) {
        DevLogger.other(logConfig).eTag(tag, throwable)
    }

    override fun eTag(
        tag: String?,
        throwable: Throwable?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).eTag(tag, throwable, message, *args)
    }

    override fun wTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).wTag(tag, message, *args)
    }

    override fun iTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).iTag(tag, message, *args)
    }

    override fun vTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).vTag(tag, message, *args)
    }

    override fun wtfTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.other(logConfig).wtfTag(tag, message, *args)
    }

    override fun jsonTag(
        tag: String?,
        json: String?
    ) {
        DevLogger.other(logConfig).jsonTag(tag, json)
    }

    override fun xmlTag(
        tag: String?,
        xml: String?
    ) {
        DevLogger.other(logConfig).xmlTag(tag, xml)
    }
}