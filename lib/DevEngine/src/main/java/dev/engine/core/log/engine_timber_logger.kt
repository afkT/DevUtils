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

    private fun pass(): Boolean = isPrintLog()

    private fun line(message: String?): String = message ?: ""

    // =============================
    // = 使用默认 TAG ( 日志打印方法 ) =
    // =============================

    override fun d(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.d(line(message), *args)
    }

    override fun e(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.e(line(message), *args)
    }

    override fun e(throwable: Throwable?) {
        if (!pass()) return
        val t = throwable ?: return
        Timber.e(t)
    }

    override fun e(
        throwable: Throwable?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.e(throwable, line(message), *args)
    }

    override fun w(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.w(line(message), *args)
    }

    override fun i(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.i(line(message), *args)
    }

    override fun v(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.v(line(message), *args)
    }

    override fun wtf(
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.wtf(line(message), *args)
    }

    override fun json(json: String?) {
        if (!pass()) return
        Timber.d(line(json))
    }

    override fun xml(xml: String?) {
        if (!pass()) return
        Timber.d(line(xml))
    }

    // ===============================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // ===============================

    override fun dTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        if (tag.isNullOrEmpty()) {
            d(message, args)
        } else {
            Timber.tag(tag).d(
                line(message), *args
            )
        }
    }

    override fun eTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        if (tag.isNullOrEmpty()) {
            e(message, args)
        } else {
            Timber.tag(tag).e(
                line(message), *args
            )
        }
    }

    override fun eTag(
        tag: String?,
        throwable: Throwable?
    ) {
        if (!pass()) return
        val t = throwable ?: return
        if (tag.isNullOrEmpty()) {
            e(t)
        } else {
            Timber.tag(tag).e(t)
        }
    }

    override fun eTag(
        tag: String?,
        throwable: Throwable?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        if (tag.isNullOrEmpty()) {
            e(throwable, message, args)
        } else {
            Timber.tag(tag).e(
                line(message), *args
            )
        }
    }

    override fun wTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.tag(line(tag)).w(line(message), *args)
    }

    override fun iTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.tag(line(tag)).i(line(message), *args)
    }

    override fun vTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.tag(line(tag)).v(line(message), *args)
    }

    override fun wtfTag(
        tag: String?,
        message: String?,
        vararg args: Any?
    ) {
        if (!pass()) return
        Timber.tag(line(tag)).wtf(line(message), *args)
    }

    override fun jsonTag(
        tag: String?,
        json: String?
    ) {
        if (!pass()) return
        Timber.tag(line(tag)).d("%s", json ?: "")
    }

    override fun xmlTag(
        tag: String?,
        xml: String?
    ) {
        if (!pass()) return
        Timber.tag(line(tag)).d("%s", xml ?: "")
    }
}