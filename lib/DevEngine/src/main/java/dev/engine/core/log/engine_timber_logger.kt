package dev.engine.core.log

import dev.engine.log.ILogEngine
import timber.log.Timber

/**
 * detail: [Timber](https://github.com/JakeWharton/timber) Log Engine 实现
 *
 * 与 [DevLoggerEngineImpl] 相同，均实现 [ILogEngine]，便于在 [dev.engine.log.DevLogEngine] 中切换底层输出。
 *
 * **初始化说明**：Timber 为进程级单例，需至少 [Timber.plant] 一棵 [Timber.Tree] 后日志才会输出。
 * 常见做法在 `Application.onCreate` 中：`if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())`。
 * 本类仅在 [isPrintLog] 为 true 时转发调用，不会自动 plant，避免覆盖业务自定义 Tree。
 *
 * **与 DevLogger 的差异**：`json` / `xml` 无内置美化，以 DEBUG 级整段输出；若需与 DevLogger 一致的可视化格式，可继续用 [DevLoggerEngineImpl] 或在此类中自行接入格式化工具。
 *
 * @author Ttt
 */
abstract class TimberEngineImpl : ILogEngine {

    /**
     * 判断是否打印日志
     * @return `true` yes, `false` no
     */
    abstract override fun isPrintLog(): Boolean

    private fun pass(): Boolean = isPrintLog()

    private fun line(message: String?): String = message ?: ""

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
        dTag(null, message, *args)
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
        eTag(null, message, *args)
    }

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     */
    override fun e(throwable: Throwable?) {
        eTag(null, throwable)
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
        eTag(null, throwable, message, *args)
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
        wTag(null, message, *args)
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
        iTag(null, message, *args)
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
        vTag(null, message, *args)
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
        wtfTag(null, message, *args)
    }

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param json JSON 格式字符串
     */
    override fun json(json: String?) {
        jsonTag(null, json)
    }

    /**
     * 格式化 XML 格式数据, 并打印
     * @param xml XML 格式字符串
     */
    override fun xml(xml: String?) {
        xmlTag(null, xml)
    }

    // ===============================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // ===============================

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
        if (tag.isNullOrEmpty()) {
            Timber.d(line(message), *args)
        } else {
            Timber.tag(tag).d(line(message), *args)
        }
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
        if (tag.isNullOrEmpty()) {
            Timber.e(line(message), *args)
        } else {
            Timber.tag(tag).e(line(message), *args)
        }
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
        val t = throwable ?: return
        if (tag.isNullOrEmpty()) {
            Timber.e(t)
        } else {
            Timber.tag(tag).e(t)
        }
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
        if (tag.isNullOrEmpty()) {
            Timber.e(throwable, line(message), *args)
        } else {
            Timber.tag(tag).e(throwable, line(message), *args)
        }
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
        if (tag.isNullOrEmpty()) {
            Timber.w(line(message), *args)
        } else {
            Timber.tag(tag).w(line(message), *args)
        }
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
        if (tag.isNullOrEmpty()) {
            Timber.i(line(message), *args)
        } else {
            Timber.tag(tag).i(line(message), *args)
        }
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
        if (tag.isNullOrEmpty()) {
            Timber.v(line(message), *args)
        } else {
            Timber.tag(tag).v(line(message), *args)
        }
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
        if (tag.isNullOrEmpty()) {
            Timber.wtf(line(message), *args)
        } else {
            Timber.tag(tag).wtf(line(message), *args)
        }
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
        if (tag.isNullOrEmpty()) {
            Timber.d(line(json))
        } else {
            Timber.tag(tag).d(line(json))
        }
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
        if (tag.isNullOrEmpty()) {
            Timber.d(line(xml))
        } else {
            Timber.tag(tag).d(line(xml))
        }
    }
}