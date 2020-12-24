package dev.engine.log;

/**
 * detail: Log Engine
 * @author Ttt
 * <pre>
 *     Application: DevLogEngine.initEngine()
 *     使用: DevLogEngine.xxx
 * </pre>
 */
public final class DevLogEngine {

    private DevLogEngine() {
    }

    // ILogEngine
    private static ILogEngine sLogEngine;

    /**
     * 初始化 Engine
     * @param logEngine {@link ILogEngine}
     */
    public static void initEngine(final ILogEngine logEngine) {
        DevLogEngine.sLogEngine = logEngine;
    }

    // ==============
    // = ILogEngine =
    // ==============

    // ===========
    // = 配置方法 =
    // ===========

    /**
     * 判断是否打印日志
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPrintLog() {
        if (sLogEngine != null) {
            return sLogEngine.isPrintLog();
        }
        return false;
    }

    // ===============================
    // = 使用默认 TAG ( 日志打印方法 ) =
    // ===============================

    /**
     * 打印 Log.DEBUG
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void d(
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.d(message, args);
        }
    }

    /**
     * 打印 Log.ERROR
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void e(
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.e(message, args);
        }
    }

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     */
    public static void e(final Throwable throwable) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.e(throwable);
        }
    }

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     * @param message   日志信息
     * @param args      格式化参数
     */
    public static void e(
            final Throwable throwable,
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.e(throwable, message, args);
        }
    }

    /**
     * 打印 Log.WARN
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void w(
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.w(message, args);
        }
    }

    /**
     * 打印 Log.INFO
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void i(
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.i(message, args);
        }
    }

    /**
     * 打印 Log.VERBOSE
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void v(
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.v(message, args);
        }
    }

    /**
     * 打印 Log.ASSERT
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void wtf(
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.wtf(message, args);
        }
    }

    // =

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param json JSON 格式字符串
     */
    public static void json(final String json) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.json(json);
        }
    }

    /**
     * 格式化 XML 格式数据, 并打印
     * @param xml XML 格式字符串
     */
    public static void xml(final String xml) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.xml(xml);
        }
    }

    // =================================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // =================================

    /**
     * 打印 Log.DEBUG
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void dTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.dTag(tag, message, args);
        }
    }

    /**
     * 打印 Log.ERROR
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void eTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.eTag(tag, message, args);
        }
    }

    /**
     * 打印 Log.ERROR
     * @param tag       日志 TAG
     * @param throwable 异常
     */
    public static void eTag(
            final String tag,
            final Throwable throwable
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.eTag(tag, throwable);
        }
    }

    /**
     * 打印 Log.ERROR
     * @param tag       日志 TAG
     * @param throwable 异常
     * @param message   日志信息
     * @param args      格式化参数
     */
    public static void eTag(
            final String tag,
            final Throwable throwable,
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.eTag(tag, throwable, message, args);
        }
    }

    /**
     * 打印 Log.WARN
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void wTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.wTag(tag, message, args);
        }
    }

    /**
     * 打印 Log.INFO
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void iTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.iTag(tag, message, args);
        }
    }

    /**
     * 打印 Log.VERBOSE
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void vTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.vTag(tag, message, args);
        }
    }

    /**
     * 打印 Log.ASSERT
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    public static void wtfTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.wtfTag(tag, message, args);
        }
    }

    // =

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param tag  日志 TAG
     * @param json JSON 格式字符串
     */
    public static void jsonTag(
            final String tag,
            final String json
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.jsonTag(tag, json);
        }
    }

    /**
     * 格式化 XML 格式数据, 并打印
     * @param tag 日志 TAG
     * @param xml XML 格式字符串
     */
    public static void xmlTag(
            final String tag,
            final String xml
    ) {
        if (isPrintLog() && sLogEngine != null) {
            sLogEngine.xmlTag(tag, xml);
        }
    }
}