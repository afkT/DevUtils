package dev.engine.log;

import dev.utils.app.logger.DevLogger;

/**
 * detail: DevLogger Log Engine 实现
 * @author Ttt
 */
public abstract class LoggerEngineImpl
        implements ILogEngine {

    // ===============================
    // = 使用默认 TAG ( 日志打印方法 ) =
    // ===============================

    @Override
    public void d(
            String message,
            Object... args
    ) {
        DevLogger.d(message, args);
    }

    @Override
    public void e(
            String message,
            Object... args
    ) {
        DevLogger.e(message, args);
    }

    @Override
    public void e(Throwable throwable) {
        DevLogger.e(throwable);
    }

    @Override
    public void e(
            Throwable throwable,
            String message,
            Object... args
    ) {
        DevLogger.e(throwable, message, args);
    }

    @Override
    public void w(
            String message,
            Object... args
    ) {
        DevLogger.w(message, args);
    }

    @Override
    public void i(
            String message,
            Object... args
    ) {
        DevLogger.i(message, args);
    }

    @Override
    public void v(
            String message,
            Object... args
    ) {
        DevLogger.v(message, args);
    }

    @Override
    public void wtf(
            String message,
            Object... args
    ) {
        DevLogger.wtf(message, args);
    }

    @Override
    public void json(String json) {
        DevLogger.json(json);
    }

    @Override
    public void xml(String xml) {
        DevLogger.xml(xml);
    }

    // =================================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // =================================

    @Override
    public void dTag(
            String tag,
            String message,
            Object... args
    ) {
        DevLogger.dTag(tag, message, args);
    }

    @Override
    public void eTag(
            String tag,
            String message,
            Object... args
    ) {
        DevLogger.eTag(tag, message, args);
    }

    @Override
    public void eTag(
            String tag,
            Throwable throwable
    ) {
        DevLogger.eTag(tag, throwable);
    }

    @Override
    public void eTag(
            String tag,
            Throwable throwable,
            String message,
            Object... args
    ) {
        DevLogger.eTag(tag, throwable, message, args);
    }

    @Override
    public void wTag(
            String tag,
            String message,
            Object... args
    ) {
        DevLogger.wTag(tag, message, args);
    }

    @Override
    public void iTag(
            String tag,
            String message,
            Object... args
    ) {
        DevLogger.iTag(tag, message, args);
    }

    @Override
    public void vTag(
            String tag,
            String message,
            Object... args
    ) {
        DevLogger.vTag(tag, message, args);
    }

    @Override
    public void wtfTag(
            String tag,
            String message,
            Object... args
    ) {
        DevLogger.wtfTag(tag, message, args);
    }

    @Override
    public void jsonTag(
            String tag,
            String json
    ) {
        DevLogger.jsonTag(tag, json);
    }

    @Override
    public void xmlTag(
            String tag,
            String xml
    ) {
        DevLogger.xmlTag(tag, xml);
    }
}