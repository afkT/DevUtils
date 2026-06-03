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

    private fun pass(): Boolean = isPrintLog()

    // =============================
    // = 使用默认 TAG ( 日志打印方法 ) =
    // =============================

    /**
     * 打印 Log.DEBUG
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun d(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).d(message, *args)
    }

    /**
     * 打印 Log.ERROR
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun e(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).e(message, *args)
    }

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     */
    override fun e(throwable: Throwable?) {
        if (!pass()) return
        DevLogger.other(logConfig).e(throwable)
    }

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun e(
        throwable: Throwable?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).e(throwable, message, *args)
    }

    /**
     * 打印 Log.WARN
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun w(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).w(message, *args)
    }

    /**
     * 打印 Log.INFO
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun i(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).i(message, *args)
    }

    /**
     * 打印 Log.VERBOSE
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun v(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).v(message, *args)
    }

    /**
     * 打印 Log.ASSERT
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun wtf(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).wtf(message, *args)
    }

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param json JSON 格式字符串
     */
    override fun json(json: String?) {
        if (!pass()) return
        DevLogger.other(logConfig).json(json)
    }

    /**
     * 格式化 XML 格式数据, 并打印
     * @param xml XML 格式字符串
     */
    override fun xml(xml: String?) {
        if (!pass()) return
        DevLogger.other(logConfig).xml(xml)
    }

    // ==============================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // ==============================

    /**
     * 打印 Log.DEBUG
     * @param tag 日志 TAG
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun dTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).dTag(tag, message, *args)
    }

    /**
     * 打印 Log.ERROR
     * @param tag 日志 TAG
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun eTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).eTag(tag, message, *args)
    }

    /**
     * 打印 Log.ERROR
     * @param tag 日志 TAG
     * @param throwable 异常
     */
    override fun eTag(
        tag: String?,
        throwable: Throwable?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).eTag(tag, throwable)
    }

    /**
     * 打印 Log.ERROR
     * @param tag 日志 TAG
     * @param throwable 异常
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun eTag(
        tag: String?,
        throwable: Throwable?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).eTag(tag, throwable, message, *args)
    }

    /**
     * 打印 Log.WARN
     * @param tag 日志 TAG
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun wTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).wTag(tag, message, *args)
    }

    /**
     * 打印 Log.INFO
     * @param tag 日志 TAG
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun iTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).iTag(tag, message, *args)
    }

    /**
     * 打印 Log.VERBOSE
     * @param tag 日志 TAG
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun vTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).vTag(tag, message, *args)
    }

    /**
     * 打印 Log.ASSERT
     * @param tag 日志 TAG
     * @param message 日志信息
     * @param args 格式化参数
     */
    override fun wtfTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).wtfTag(tag, message, *args)
    }

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param tag 日志 TAG
     * @param json JSON 格式字符串
     */
    override fun jsonTag(
        tag: String?,
        json: String?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).jsonTag(tag, json)
    }

    /**
     * 格式化 XML 格式数据, 并打印
     * @param tag 日志 TAG
     * @param xml XML 格式字符串
     */
    override fun xmlTag(
        tag: String?,
        xml: String?
    ) {
        if (!pass()) return
        DevLogger.other(logConfig).xmlTag(tag, xml)
    }
}