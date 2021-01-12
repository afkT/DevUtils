package dev.engine.log;

/**
 * detail: Log Engine 接口
 * @author Ttt
 */
public interface ILogEngine {

    /**
     * 判断是否打印日志
     * @return {@code true} yes, {@code false} no
     */
    boolean isPrintLog();

    // ===============================
    // = 使用默认 TAG ( 日志打印方法 ) =
    // ===============================

    /**
     * 打印 Log.DEBUG
     * @param message 日志信息
     * @param args    格式化参数
     */
    void d(
            String message,
            Object... args
    );

    /**
     * 打印 Log.ERROR
     * @param message 日志信息
     * @param args    格式化参数
     */
    void e(
            String message,
            Object... args
    );

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     */
    void e(Throwable throwable);

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     * @param message   日志信息
     * @param args      格式化参数
     */
    void e(
            Throwable throwable,
            String message,
            Object... args
    );

    /**
     * 打印 Log.WARN
     * @param message 日志信息
     * @param args    格式化参数
     */
    void w(
            String message,
            Object... args
    );

    /**
     * 打印 Log.INFO
     * @param message 日志信息
     * @param args    格式化参数
     */
    void i(
            String message,
            Object... args
    );

    /**
     * 打印 Log.VERBOSE
     * @param message 日志信息
     * @param args    格式化参数
     */
    void v(
            String message,
            Object... args
    );

    /**
     * 打印 Log.ASSERT
     * @param message 日志信息
     * @param args    格式化参数
     */
    void wtf(
            String message,
            Object... args
    );

    // =

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param json JSON 格式字符串
     */
    void json(String json);

    /**
     * 格式化 XML 格式数据, 并打印
     * @param xml XML 格式字符串
     */
    void xml(String xml);

    // =================================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // =================================

    /**
     * 打印 Log.DEBUG
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    void dTag(
            String tag,
            String message,
            Object... args
    );

    /**
     * 打印 Log.ERROR
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    void eTag(
            String tag,
            String message,
            Object... args
    );

    /**
     * 打印 Log.ERROR
     * @param tag       日志 TAG
     * @param throwable 异常
     */
    void eTag(
            String tag,
            Throwable throwable
    );

    /**
     * 打印 Log.ERROR
     * @param tag       日志 TAG
     * @param throwable 异常
     * @param message   日志信息
     * @param args      格式化参数
     */
    void eTag(
            String tag,
            Throwable throwable,
            String message,
            Object... args
    );

    /**
     * 打印 Log.WARN
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    void wTag(
            String tag,
            String message,
            Object... args
    );

    /**
     * 打印 Log.INFO
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    void iTag(
            String tag,
            String message,
            Object... args
    );

    /**
     * 打印 Log.VERBOSE
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    void vTag(
            String tag,
            String message,
            Object... args
    );

    /**
     * 打印 Log.ASSERT
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    void wtfTag(
            String tag,
            String message,
            Object... args
    );

    // =

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param tag  日志 TAG
     * @param json JSON 格式字符串
     */
    void jsonTag(
            String tag,
            String json
    );

    /**
     * 格式化 XML 格式数据, 并打印
     * @param tag 日志 TAG
     * @param xml XML 格式字符串
     */
    void xmlTag(
            String tag,
            String xml
    );
}