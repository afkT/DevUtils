package dev.utils.app.logger;

import android.util.Log;

/**
 * detail: 日志操作类 ( 对外公开直接调用 )
 * @author Ttt
 */
public final class DevLogger {

    private DevLogger() {
    }

    // 包下 LoggerPrinter 类持有对象
    private static final IPrinter sPrinter = new LoggerPrinter();

    // ===========
    // = 配置方法 =
    // ===========

    /**
     * 使用单次其他日志配置
     * @param logConfig 日志配置
     * @return {@link IPrinter}
     */
    public static IPrinter other(final LogConfig logConfig) {
        return sPrinter.other(logConfig);
    }

    /**
     * 获取日志配置信息
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getLogConfig() {
        return sPrinter.getLogConfig();
    }

    /**
     * 初始化日志配置信息 ( 使用默认配置 )
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig init() {
        return sPrinter.init();
    }

    /**
     * 自定义日志配置信息
     * @param logConfig 日志配置
     */
    public static void init(final LogConfig logConfig) {
        sPrinter.init(logConfig);
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
        sPrinter.d(message, args);
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
        sPrinter.e(message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     */
    public static void e(final Throwable throwable) {
        sPrinter.e(throwable, null);
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
        sPrinter.e(throwable, message, args);
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
        sPrinter.w(message, args);
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
        sPrinter.i(message, args);
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
        sPrinter.v(message, args);
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
        sPrinter.wtf(message, args);
    }

    // =

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param json JSON 格式字符串
     */
    public static void json(final String json) {
        sPrinter.json(json);
    }

    /**
     * 格式化 XML 格式数据, 并打印
     * @param xml XML 格式字符串
     */
    public static void xml(final String xml) {
        sPrinter.xml(xml);
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
        sPrinter.dTag(tag, message, args);
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
        sPrinter.eTag(tag, message, args);
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
        sPrinter.eTag(tag, throwable, null);
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
        sPrinter.eTag(tag, throwable, message, args);
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
        sPrinter.wTag(tag, message, args);
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
        sPrinter.iTag(tag, message, args);
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
        sPrinter.vTag(tag, message, args);
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
        sPrinter.wtfTag(tag, message, args);
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
        sPrinter.jsonTag(tag, json);
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
        sPrinter.xmlTag(tag, xml);
    }

    // ===========
    // = 通知输出 =
    // ===========

    // 默认日志输出接口
    static Print sPrint = new Print() {
        @Override
        public void printLog(
                int logType,
                String tag,
                String message
        ) {
            // 防止 null 处理
            if (message == null) return;
            // 获取日志类型
            switch (logType) {
                case Log.VERBOSE:
                    Log.v(tag, message);
                    break;
                case Log.DEBUG:
                    Log.d(tag, message);
                    break;
                case Log.INFO:
                    Log.i(tag, message);
                    break;
                case Log.WARN:
                    Log.w(tag, message);
                    break;
                case Log.ERROR:
                    Log.e(tag, message);
                    break;
                case Log.ASSERT:
                    Log.wtf(tag, message);
                    break;
                default:
                    Log.wtf(tag, message);
                    break;
            }
        }
    };

    /**
     * 设置日志输出接口
     * @param print 日志输出接口
     */
    public static void setPrint(final Print print) {
        DevLogger.sPrint = print;
    }

    /**
     * detail: 日志输出接口
     * @author Ttt
     */
    public interface Print {

        /**
         * 日志打印
         * @param logType 日志类型
         * @param tag     打印 Tag
         * @param message 日志信息
         */
        void printLog(
                int logType,
                String tag,
                String message
        );
    }
}