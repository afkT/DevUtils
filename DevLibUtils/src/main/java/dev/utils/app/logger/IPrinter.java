package dev.utils.app.logger;

/**
 * detail: 日志接口
 * @author Ttt
 */
public interface IPrinter {

    // ============
    // = 配置方法 =
    // ============

    /**
     * 使用单次其他日志配置
     * @param lConfig 日志配置
     * @return
     */
    IPrinter other(LogConfig lConfig);

    /**
     * 获取日志配置信息
     * @return 日志配置
     */
    LogConfig getLogConfig();

    /**
     * 初始化日志配置信息(可以不调用,使用了App默认配置)
     * @return 日志配置
     */
    LogConfig init();

    /**
     * 手动改变日志配置信息(非单次,一直持续)
     * @param lConfig 日志配置
     */
    void init(LogConfig lConfig);

    // ==============================
    // = 使用默认TAG - 日志打印方法 =
    // ==============================

    /**
     * Log.DEBUG
     */
    void d(String message, Object... args);

    /**
     * Log.ERROR
     */
    void e(String message, Object... args);

    /**
     * Log.ERROR,并且输出错误信息Throwable
     */
    void e(Throwable throwable);

    /**
     * Log.ERROR,并且输出错误信息Throwable
     */
    void e(Throwable throwable, String message, Object... args);

    /**
     * Log.WARN
     */
    void w(String message, Object... args);

    /**
     * Log.INFO
     */
    void i(String message, Object... args);

    /**
     * Log.VERBOSE
     */
    void v(String message, Object... args);

    /**
     * Log.ASSERT
     */
    void wtf(String message, Object... args);

    // =

    /**
     * 格式化Json格式数据,并打印
     */
    void json(String json);

    /**
     * 格式化xml格式数据,并打印
     */
    void xml(String xml);

    // ================================
    // = 使用自定义TAG - 日志打印方法 =
    // ================================

    /**
     * Log.DEBUG
     */
    void dTag(String tag, String message, Object... args);

    /**
     * Log.ERROR
     */
    void eTag(String tag, String message, Object... args);

    /**
     * Log.ERROR,并且输出错误信息Throwable
     */
    void eTag(String tag, Throwable throwable);

    /**
     * Log.ERROR,并且输出错误信息Throwable
     */
    void eTag(String tag, Throwable throwable, String message, Object... args);

    /**
     * Log.WARN
     */
    void wTag(String tag, String message, Object... args);

    /**
     * Log.INFO
     */
    void iTag(String tag, String message, Object... args);

    /**
     * Log.VERBOSE
     */
    void vTag(String tag, String message, Object... args);

    /**
     * Log.ASSERT
     */
    void wtfTag(String tag, String message, Object... args);

    // =

    /**
     * 格式化Json格式数据,并打印
     */
    void jsonTag(String tag, String json);

    /**
     * 格式化xml格式数据,并打印
     */
    void xmlTag(String tag, String xml);
}
