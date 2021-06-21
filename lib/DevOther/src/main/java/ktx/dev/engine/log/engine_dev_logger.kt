package ktx.dev.engine.log

import dev.engine.log.ILogEngine
import dev.utils.app.logger.DevLogger

/**
 * detail: DevLogger Log Engine 实现
 * @author Ttt
 */
abstract class DevLoggerEngineImpl : ILogEngine {

    // =============================
    // = 使用默认 TAG ( 日志打印方法 ) =
    // =============================

    override fun d(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.d(message, *args)
    }

    override fun e(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.e(message, *args)
    }

    override fun e(throwable: Throwable?) {
        DevLogger.e(throwable)
    }

    override fun e(
        throwable: Throwable?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.e(throwable, message, *args)
    }

    override fun w(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.w(message, *args)
    }

    override fun i(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.i(message, *args)
    }

    override fun v(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.v(message, *args)
    }

    override fun wtf(
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.wtf(message, *args)
    }

    override fun json(json: String?) {
        DevLogger.json(json)
    }

    override fun xml(xml: String?) {
        DevLogger.xml(xml)
    }

    // ==============================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // ==============================

    override fun dTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.dTag(tag, message, *args)
    }

    override fun eTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.eTag(tag, message, *args)
    }

    override fun eTag(
        tag: String?,
        throwable: Throwable?
    ) {
        DevLogger.eTag(tag, throwable)
    }

    override fun eTag(
        tag: String?,
        throwable: Throwable?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.eTag(tag, throwable, message, *args)
    }

    override fun wTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.wTag(tag, message, *args)
    }

    override fun iTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.iTag(tag, message, *args)
    }

    override fun vTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.vTag(tag, message, *args)
    }

    override fun wtfTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        DevLogger.wtfTag(tag, message, *args)
    }

    override fun jsonTag(
        tag: String?,
        json: String?
    ) {
        DevLogger.jsonTag(tag, json)
    }

    override fun xmlTag(
        tag: String?,
        xml: String?
    ) {
        DevLogger.xmlTag(tag, xml)
    }
}