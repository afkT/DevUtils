package dev.utils.app.logger;

/**
 * detail: 日志操作类(对外公开直接调用)
 * @author Ttt
 */
public final class DevLogger {

    private DevLogger() {
    }

    // 包下LoggerPrinter类持有对象
    private static final IPrinter sPrinter = new LoggerPrinter();

    // ============
    // = 配置方法 =
    // ============

    /**
     * 使用单次其他日志配置
     * @param lConfig
     * @return
     */
    public static IPrinter other(final LogConfig lConfig) {
        return sPrinter.other(lConfig);
    }

    /**
     * 获取日志配置信息
     * @return
     */
    public static LogConfig getLogConfig() {
        return sPrinter.getLogConfig();
    }

    /**
     * 初始化日志配置信息(使用默认配置)
     * @return
     */
    public static LogConfig init() {
        return sPrinter.init();
    }

    /**
     * 手动改变日志配置信息
     * @param lConfig
     */
    public static void init(final LogConfig lConfig) {
        sPrinter.init(lConfig);
    }

    // ==============================
    // = 使用默认TAG - 日志打印方法 =
    // ==============================

    /**
     * 打印 Log.DEBUG
     * @param message
     * @param args
     */
    public static void d(final String message, final Object... args) {
        sPrinter.d(message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param message
     * @param args
     */
    public static void e(final String message, final Object... args) {
        sPrinter.e(message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param throwable
     */
    public static void e(final Throwable throwable) {
        sPrinter.e(throwable, null);
    }

    /**
     * 打印 Log.ERROR
     * @param throwable
     * @param message
     * @param args
     */
    public static void e(final Throwable throwable, final String message, final Object... args) {
        sPrinter.e(throwable, message, args);
    }

    /**
     * 打印 Log.WARN
     * @param message
     * @param args
     */
    public static void w(final String message, final Object... args) {
        sPrinter.w(message, args);
    }

    /**
     * 打印 Log.INFO
     * @param message
     * @param args
     */
    public static void i(final String message, final Object... args) {
        sPrinter.i(message, args);
    }

    /**
     * 打印 Log.VERBOSE
     * @param message
     * @param args
     */
    public static void v(final String message, final Object... args) {
        sPrinter.v(message, args);
    }

    /**
     * 打印 Log.ASSERT
     * @param message
     * @param args
     */
    public static void wtf(final String message, final Object... args) {
        sPrinter.wtf(message, args);
    }

    // =

    /**
     * 格式化Json格式数据,并打印
     * @param json
     */
    public static void json(final String json) {
        sPrinter.json(json);
    }

    /**
     * 格式化XML格式数据,并打印
     * @param xml
     */
    public static void xml(final String xml) {
        sPrinter.xml(xml);
    }

    // ================================
    // = 使用自定义TAG - 日志打印方法 =
    // ================================

    /**
     * 打印 Log.DEBUG
     * @param tag
     * @param message
     * @param args
     */
    public static void dTag(final String tag, final String message, final Object... args) {
        sPrinter.dTag(tag, message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param tag
     * @param message
     * @param args
     */
    public static void eTag(final String tag, final String message, final Object... args) {
        sPrinter.eTag(tag, message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param tag
     * @param throwable
     * @param message
     * @param args
     */
    public static void eTag(final String tag, final Throwable throwable, final String message, final Object... args) {
        sPrinter.eTag(tag, throwable, message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param tag
     * @param throwable
     */
    public static void eTag(final String tag, final Throwable throwable) {
        sPrinter.eTag(tag, throwable, null);
    }

    /**
     * 打印 Log.WARN
     * @param tag
     * @param message
     * @param args
     */
    public static void wTag(final String tag, final String message, final Object... args) {
        sPrinter.wTag(tag, message, args);
    }

    /**
     * 打印 Log.INFO
     * @param tag
     * @param message
     * @param args
     */
    public static void iTag(final String tag, final String message, final Object... args) {
        sPrinter.iTag(tag, message, args);
    }

    /**
     * 打印 Log.VERBOSE
     * @param tag
     * @param message
     * @param args
     */
    public static void vTag(final String tag, final String message, final Object... args) {
        sPrinter.vTag(tag, message, args);
    }

    /**
     * 打印 Log.ASSERT
     * @param tag
     * @param message
     * @param args
     */
    public static void wtfTag(final String tag, final String message, final Object... args) {
        sPrinter.wtfTag(tag, message, args);
    }

    // =

    /**
     * 格式化Json格式数据,并打印
     * @param tag
     * @param json
     */
    public static void jsonTag(final String tag, final String json) {
        sPrinter.jsonTag(tag, json);
    }

    /**
     * 格式化XML格式数据,并打印
     * @param tag
     * @param xml
     */
    public static void xmlTag(final String tag, final String xml) {
        sPrinter.xmlTag(tag, xml);
    }
}
