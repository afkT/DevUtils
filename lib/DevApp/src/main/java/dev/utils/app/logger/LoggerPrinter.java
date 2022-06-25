package dev.utils.app.logger;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import dev.utils.DevFinal;

/**
 * detail: 日志输出类 ( 处理方法 )
 * @author Ttt
 */
final class LoggerPrinter
        implements IPrinter {

    // 日志配置
    private static       LogConfig              LOG_CONFIG        = null;
    // 每个线程的日志配置信息
    private static final ThreadLocal<LogConfig> LOCAL_LOG_CONFIGS = new ThreadLocal<>();

    // ================================
    // = 实现 IPrinter 接口, 对外公开方法 =
    // ================================

    /**
     * 使用单次其他日志配置
     * @param logConfig 日志配置
     * @return {@link IPrinter}
     */
    @Override
    public IPrinter other(final LogConfig logConfig) {
        if (logConfig != null) {
            LOCAL_LOG_CONFIGS.set(logConfig);
        }
        return this;
    }

    /**
     * 获取日志配置信息
     * @return {@link LogConfig} 日志配置
     */
    @Override
    public LogConfig getLogConfig() {
        return LOG_CONFIG;
    }

    /**
     * 初始化日志配置信息 ( 使用默认配置 )
     * @return {@link LogConfig} 日志配置
     */
    @Override
    public LogConfig initialize() {
        // 判断日志配置信息是否等于 null
        if (LOG_CONFIG == null) {
            // 生成默认配置信息
            LOG_CONFIG = new LogConfig();
        }
        // 返回配置信息
        return LOG_CONFIG;
    }

    /**
     * 自定义日志配置信息
     * @param logConfig 日志配置
     */
    @Override
    public void initialize(final LogConfig logConfig) {
        LOG_CONFIG = logConfig;
        // 防止日志配置参数为 null
        initialize();
    }

    // =============================
    // = 使用默认 TAG ( 日志打印方法 ) =
    // =============================

    /**
     * 打印 Log.DEBUG
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void d(
            final String message,
            final Object... args
    ) {
        logHandle(Log.DEBUG, message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void e(
            final String message,
            final Object... args
    ) {
        e(null, message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     */
    @Override
    public void e(final Throwable throwable) {
        e(throwable, null);
    }

    /**
     * 打印 Log.ERROR
     * @param throwable 异常
     * @param message   日志信息
     * @param args      格式化参数
     */
    @Override
    public void e(
            final Throwable throwable,
            final String message,
            final Object... args
    ) {
        // 日志消息
        String logMsg = message;
        // 判断消息
        if (throwable != null && message != null) {
            logMsg = message + " : " + throwable.toString();
        } else if (throwable != null) {
            logMsg = throwable.toString();
        } else if (message == null) {
            // 没有日志信息, 也没有异常信息传入
            logMsg = "No message/exception is set";
        }
        logHandle(Log.ERROR, logMsg, args);
    }

    /**
     * 打印 Log.WARN
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void w(
            final String message,
            final Object... args
    ) {
        logHandle(Log.WARN, message, args);
    }

    /**
     * 打印 Log.INFO
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void i(
            final String message,
            final Object... args
    ) {
        logHandle(Log.INFO, message, args);
    }

    /**
     * 打印 Log.VERBOSE
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void v(
            final String message,
            final Object... args
    ) {
        logHandle(Log.VERBOSE, message, args);
    }

    /**
     * 打印 Log.ASSERT
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void wtf(
            final String message,
            final Object... args
    ) {
        logHandle(Log.ASSERT, message, args);
    }

    // =

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param json JSON 格式字符串
     */
    @Override
    public void json(final String json) {
        // 获取当前线程日志配置信息
        LogConfig logConfig = getThreadLogConfig();
        // 判断是否打印日志 ( 日志级别 )
        if (!isPrintLog(logConfig, Log.DEBUG)) {
            return;
        }
        // 日志 TAG
        String tag = logConfig.tag;
        // 判断传入 JSON 格式信息是否为 null
        if (TextUtils.isEmpty(json)) {
            logHandle(logConfig, tag, Log.DEBUG, "Empty/Null json content");
            return;
        }
        try {
            // 属于对象的 JSON 格式信息
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                // 进行缩进
                String message = jsonObject.toString(LogConstants.JSON_INDENT);
                // 打印信息
                logHandle(logConfig, tag, Log.DEBUG, message);
            } else if (json.startsWith("[")) {
                // 属于数据的 JSON 格式信息
                JSONArray jsonArray = new JSONArray(json);
                // 进行缩进
                String message = jsonArray.toString(LogConstants.JSON_INDENT);
                // 打印信息
                logHandle(logConfig, tag, Log.DEBUG, message);
            } else {
                // 打印信息
                logHandle(logConfig, tag, Log.DEBUG, "json content format error");
            }
        } catch (Exception e) {
            String    errorInfo;
            Throwable throwable = e.getCause();
            if (throwable != null) {
                errorInfo = throwable.toString();
            } else {
                try {
                    errorInfo = e.toString();
                } catch (Exception e1) {
                    errorInfo = e1.toString();
                }
            }
            logHandle(
                    logConfig, tag, Log.ERROR,
                    errorInfo + DevFinal.SYMBOL.NEW_LINE + json
            );
        }
    }

    /**
     * 格式化 XML 格式数据, 并打印
     * @param xml XML 格式字符串
     */
    @Override
    public void xml(final String xml) {
        // 获取当前线程日志配置信息
        LogConfig logConfig = getThreadLogConfig();
        // 判断是否打印日志 ( 日志级别 )
        if (!isPrintLog(logConfig, Log.DEBUG)) {
            return;
        }
        // 日志 TAG
        String tag = logConfig.tag;
        // 判断传入 XML 格式信息是否为 null
        if (TextUtils.isEmpty(xml)) {
            logHandle(logConfig, tag, Log.DEBUG, "Empty/Null xml content");
            return;
        }
        try {
            Source       xmlInput    = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput   = new StreamResult(new StringWriter());
            Transformer  transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "2"
            );
            transformer.transform(xmlInput, xmlOutput);
            // 获取打印消息
            String message = xmlOutput.getWriter().toString()
                    .replaceFirst(">", ">\n");
            // 打印信息
            logHandle(logConfig, tag, Log.DEBUG, message);
        } catch (Exception e) {
            String    errorInfo;
            Throwable throwable = e.getCause();
            if (throwable != null) {
                errorInfo = throwable.toString();
            } else {
                try {
                    errorInfo = e.toString();
                } catch (Exception e1) {
                    errorInfo = e1.toString();
                }
            }
            logHandle(
                    logConfig, tag, Log.ERROR,
                    errorInfo + DevFinal.SYMBOL.NEW_LINE + xml
            );
        }
    }

    // ==============================
    // = 使用自定义 TAG ( 日志打印方法 ) =
    // ==============================

    /**
     * 打印 Log.DEBUG
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void dTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        logHandle(tag, Log.DEBUG, message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void eTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        eTag(tag, null, message, args);
    }

    /**
     * 打印 Log.ERROR
     * @param tag       日志 TAG
     * @param throwable 异常
     */
    @Override
    public void eTag(
            final String tag,
            final Throwable throwable
    ) {
        eTag(tag, throwable, null);
    }

    /**
     * 打印 Log.ERROR
     * @param tag       日志 TAG
     * @param throwable 异常
     * @param message   日志信息
     * @param args      格式化参数
     */
    @Override
    public void eTag(
            final String tag,
            final Throwable throwable,
            final String message,
            final Object... args
    ) {
        // 日志消息
        String logMsg = message;
        // 判断消息
        if (throwable != null && message != null) {
            logMsg = message + " : " + throwable.toString();
        } else if (throwable != null) {
            logMsg = throwable.toString();
        } else if (message == null) {
            // 没有日志信息, 也没有异常信息传入
            logMsg = "No message/exception is set";
        }
        logHandle(tag, Log.ERROR, logMsg, args);
    }

    /**
     * 打印 Log.WARN
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void wTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        logHandle(tag, Log.WARN, message, args);
    }

    /**
     * 打印 Log.INFO
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void iTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        logHandle(tag, Log.INFO, message, args);
    }

    /**
     * 打印 Log.VERBOSE
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void vTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        logHandle(tag, Log.VERBOSE, message, args);
    }

    /**
     * 打印 Log.ASSERT
     * @param tag     日志 TAG
     * @param message 日志信息
     * @param args    格式化参数
     */
    @Override
    public void wtfTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        logHandle(tag, Log.ASSERT, message, args);
    }

    // =

    /**
     * 格式化 JSON 格式数据, 并打印
     * @param tag  日志 TAG
     * @param json JSON 格式字符串
     */
    @Override
    public void jsonTag(
            final String tag,
            final String json
    ) {
        // 获取当前线程日志配置信息
        LogConfig logConfig = getThreadLogConfig();
        // 判断是否打印日志 ( 日志级别 )
        if (!isPrintLog(logConfig, Log.DEBUG)) {
            return;
        }
        // 判断传入 JSON 格式信息是否为 null
        if (TextUtils.isEmpty(json)) {
            logHandle(logConfig, tag, Log.DEBUG, "Empty/Null json content");
            return;
        }
        try {
            // 属于对象的 JSON 格式信息
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                // 进行缩进
                String message = jsonObject.toString(LogConstants.JSON_INDENT);
                // 打印信息
                logHandle(logConfig, tag, Log.DEBUG, message);
            } else if (json.startsWith("[")) {
                // 属于数据的 JSON 格式信息
                JSONArray jsonArray = new JSONArray(json);
                // 进行缩进
                String message = jsonArray.toString(LogConstants.JSON_INDENT);
                // 打印信息
                logHandle(logConfig, tag, Log.DEBUG, message);
            } else {
                // 打印信息
                logHandle(logConfig, tag, Log.DEBUG, "json content format error");
            }
        } catch (Exception e) {
            String    errorInfo;
            Throwable throwable = e.getCause();
            if (throwable != null) {
                errorInfo = throwable.toString();
            } else {
                try {
                    errorInfo = e.toString();
                } catch (Exception e1) {
                    errorInfo = e1.toString();
                }
            }
            logHandle(
                    logConfig, tag, Log.ERROR,
                    errorInfo + DevFinal.SYMBOL.NEW_LINE + json
            );
        }
    }

    /**
     * 格式化 XML 格式数据, 并打印
     * @param tag 日志 TAG
     * @param xml XML 格式字符串
     */
    @Override
    public void xmlTag(
            final String tag,
            final String xml
    ) {
        // 获取当前线程日志配置信息
        LogConfig logConfig = getThreadLogConfig();
        // 判断是否打印日志 ( 日志级别 )
        if (!isPrintLog(logConfig, Log.DEBUG)) {
            return;
        }
        // 判断传入 XML 格式信息是否为 null
        if (TextUtils.isEmpty(xml)) {
            logHandle(logConfig, tag, Log.DEBUG, "Empty/Null xml content");
            return;
        }
        try {
            Source       xmlInput    = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput   = new StreamResult(new StringWriter());
            Transformer  transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "2"
            );
            transformer.transform(xmlInput, xmlOutput);
            // 获取打印消息
            String message = xmlOutput.getWriter().toString()
                    .replaceFirst(">", ">\n");
            // 打印信息
            logHandle(logConfig, tag, Log.DEBUG, message);
        } catch (Exception e) {
            String    errorInfo;
            Throwable throwable = e.getCause();
            if (throwable != null) {
                errorInfo = throwable.toString();
            } else {
                try {
                    errorInfo = e.toString();
                } catch (Exception e1) {
                    errorInfo = e1.toString();
                }
            }
            logHandle(
                    logConfig, tag, Log.ERROR,
                    errorInfo + DevFinal.SYMBOL.NEW_LINE + xml
            );
        }
    }

    // =============
    // = 内部判断方法 =
    // =============

    /**
     * 是否打印日志
     * @param logConfig 日志配置
     * @param logType   日志类型
     * @return {@code true} yes, {@code false} no
     */
    private boolean isPrintLog(
            final LogConfig logConfig,
            final int logType
    ) {
        // 是否打印日志 ( 默认不打印 )
        boolean isPrint = false;
        // 日志级别
        LogLevel logLevel = logConfig.logLevel;
        // =
        switch (logLevel) {
            case NONE: // 全部不打印
                break;
            case DEBUG: // 调试级别 v, d ( 全部打印 )
                isPrint = true;
                break;
            case INFO: // 正常级别 i
            case WARN: // 警告级别 w
            case ERROR: // 异常级别 e, wtf
                isPrint = checkLogLevel(logLevel, logType);
                break;
            default:
                break;
        }
        return isPrint;
    }

    /**
     * 判断日志级别是否允许输出
     * @param logLevel 日志级别
     * @param logType  日志类型
     * @return {@code true} yes, {@code false} no
     */
    private boolean checkLogLevel(
            final LogLevel logLevel,
            final int logType
    ) {
        switch (logLevel) {
            case INFO: // 正常级别 i
                if (logType != Log.VERBOSE && logType != Log.DEBUG) {
                    return true;
                }
                break;
            case WARN: // 警告级别 w
                if (logType != Log.VERBOSE && logType != Log.DEBUG && logType != Log.INFO) {
                    return true;
                }
                break;
            case ERROR: // 异常级别 e, wtf
                if (logType == Log.ERROR || logType == Log.ASSERT) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    // ================
    // = 打印日志处理方法 =
    // ================

    /**
     * 最终打印方法
     * @param logType 日志类型
     * @param tag     日志 TAG
     * @param message 日志信息
     */
    private void finalLogPrinter(
            final int logType,
            final String tag,
            final String message
    ) {
        if (DevLogger.sPrint != null) {
            DevLogger.sPrint.printLog(logType, tag, message);
        }
    }

    /**
     * 日志处理方法
     * @param logType 日志类型
     * @param message 日志信息
     * @param args    占位符替换
     */
    private void logHandle(
            final int logType,
            final String message,
            final Object... args
    ) {
        logHandle(null, null, logType, message, args);
    }

    /**
     * 日志处理方法
     * @param tag     日志 TAG
     * @param logType 日志类型
     * @param message 日志信息
     * @param args    占位符替换
     */
    private void logHandle(
            final String tag,
            final int logType,
            final String message,
            final Object... args
    ) {
        logHandle(null, tag, logType, message, args);
    }

    /**
     * 日志处理方法 ( 此方法是同步的, 以避免混乱的日志的顺序 )
     * @param config  配置信息
     * @param tag     日志 TAG
     * @param logType 日志类型
     * @param msg     日志信息
     * @param args    占位符替换
     */
    private synchronized void logHandle(
            final LogConfig config,
            final String tag,
            final int logType,
            final String msg,
            final Object... args
    ) {
        LogConfig logConfig = config;
        // 如果配置为 null, 才进行获取
        if (logConfig == null) {
            // 获取当前线程日志配置信息
            logConfig = getThreadLogConfig();
        }
        // 判断是否打印日志 ( 日志级别 )
        if (!isPrintLog(logConfig, logType)) {
            return;
        }
        String logTag = tag;
        // 防止 TAG 为 null
        if (TextUtils.isEmpty(logTag)) {
            // 获取配置的 TAG
            logTag = logConfig.tag;
            // 防止配置的 TAG 也为 null
            if (TextUtils.isEmpty(logTag)) {
                // 使用默认的 TAG
                logTag = LogConstants.DEFAULT_LOG_TAG;
            }
        }
        // 判断是否显示排序后的日志 ( 如果不排序, 则显示默认 )
        if (!logConfig.sortLog) {
            finalLogPrinter(logType, logTag, createMessage(msg, args));
            return;
        }
        // = 日志配置信息获取 =
        // 获取方法总数
        int methodCount = logConfig.methodCount;
        // 获取方法偏移索引
        int methodOffset = logConfig.methodOffset;
        // 如果出现小于 0 的设置, 则设置默认值处理
        if (methodOffset < 0) {
            methodOffset = LogConstants.DEFAULT_LOG_METHOD_OFFSET;
        }
        // 如果出现小于 0 的设置, 则设置默认值处理
        if (methodCount < 0) {
            methodCount = LogConstants.DEFAULT_LOG_METHOD_COUNT;
        }
        // 获取打印的日志信息
        String message = createMessage(msg, args);
        // 防止 null 处理
        if (message == null) return;
        // 打印头部
        logTopBorder(logType, logTag);
        // 打印头部线程信息
        logHeaderContent(logConfig, logType, logTag, methodCount, methodOffset);
        // 获取系统的默认字符集的信息字节 (UTF-8)
        byte[] bytes = message.getBytes();
        // 获取字节总数
        int length = bytes.length;
        // 判断是否超过总数, 没有超过则一次性打印, 超过则遍历打印
        if (length <= LogConstants.CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(logType, logTag);
            }
            // 打印日志内容
            logContent(logType, logTag, message);
            // 打印结尾
            logBottomBorder(logType, logTag);
            return;
        }
        // 打印换行符
        if (methodCount > 0) {
            // 换行
            logDivider(logType, logTag);
        }
        // 因为超过系统打印字节总数, 遍历打印
        for (int i = 0; i < length; i += LogConstants.CHUNK_SIZE) {
            int count = Math.min(length - i, LogConstants.CHUNK_SIZE);
            // 创建系统的默认字符集的一个新的字符串 (UTF-8), 并打印日志内容
            logContent(logType, logTag, new String(bytes, i, count));
        }
        // 打印结尾
        logBottomBorder(logType, logTag);
    }

    // =============
    // = 日志格式拼接 =
    // =============

    /**
     * 日志线程信息主体部分
     * @param logConfig    日志配置
     * @param logType      日志类型
     * @param tag          日志 TAG
     * @param methodCount  方法总数
     * @param methodOffset 方法偏移索引
     */
    private void logHeaderContent(
            final LogConfig logConfig,
            final int logType,
            final String tag,
            int methodCount,
            int methodOffset
    ) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        // 判断是否显示日志线程信息
        if (!logConfig.displayThreadInfo) return;

        // 打印线程信息 ( 线程名 )
        finalLogPrinter(
                logType, tag, LogConstants.HORIZONTAL_DOUBLE_LINE
                        + " Thread: " + Thread.currentThread().getName()
        );
        // 进行换行
        logDivider(logType, tag);

        // 堆栈总数
        int traceCount = trace.length;
        // 获取堆栈偏移量
        int stackOffset = getStackOffset(trace) + methodOffset;
        // 对应的方法计数与当前堆栈可能超过, 进行堆栈跟踪
        if (methodCount + stackOffset > traceCount) {
            methodCount = traceCount - stackOffset - 1;
        }
        // 判断是否显示全部方法
        if (logConfig.outputMethodAll) {
            // 设置方法总数
            methodCount = traceCount;
            // 设置方法偏移索引为 0
            stackOffset = 0;
        } else if (methodCount <= 0) {
            // 如果打印数小于等于 0, 则直接跳过
            return;
        }

        // 手动进行偏移
        StringBuilder traceLevel = new StringBuilder();
        // 遍历打印的方法数量 ( 类名、行数、操作的方法名 )
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= traceCount) {
                continue;
            }
            // 拼接中间内容、操作的类名、行数、方法名等信息
            StringBuilder builder = new StringBuilder();
            builder.append("║ ").append(traceLevel);
            builder.append(getSimpleClassName(trace[stackIndex].getClassName()));
            builder.append(".").append(trace[stackIndex].getMethodName());
            builder.append(" (");
            builder.append(trace[stackIndex].getFileName());
            builder.append(":");
            builder.append(trace[stackIndex].getLineNumber());
            builder.append(")");
            traceLevel.append("   ");
            // 打印日志信息
            finalLogPrinter(logType, tag, builder.toString());
        }
    }

    /**
     * 日志顶部
     * @param logType 日志类型
     * @param tag     日志 TAG
     */
    private void logTopBorder(
            final int logType,
            final String tag
    ) {
        finalLogPrinter(logType, tag, LogConstants.TOP_BORDER);
    }

    /**
     * 日志结尾
     * @param logType 日志类型
     * @param tag     日志 TAG
     */
    private void logBottomBorder(
            final int logType,
            final String tag
    ) {
        finalLogPrinter(logType, tag, LogConstants.BOTTOM_BORDER);
    }

    /**
     * 日志换行
     * @param logType 日志类型
     * @param tag     日志 TAG
     */
    private void logDivider(
            final int logType,
            final String tag
    ) {
        finalLogPrinter(logType, tag, LogConstants.MIDDLE_BORDER);
    }

    /**
     * 日志内容
     * @param logType 日志类型
     * @param tag     日志 TAG
     * @param msg     日志信息
     */
    private void logContent(
            final int logType,
            final String tag,
            final String msg
    ) {
        String[] lines = msg.split(DevFinal.SYMBOL.NEW_LINE);
        for (String line : lines) {
            finalLogPrinter(
                    logType, tag,
                    LogConstants.HORIZONTAL_DOUBLE_LINE + " " + line
            );
        }
    }

    /**
     * 处理信息
     * @param message 日志信息
     * @param args    占位符替换
     * @return 处理 ( 格式化 ) 后准备打印的日志信息
     */
    private String createMessage(
            final String message,
            final Object... args
    ) {
        if (message != null) {
            try {
                return args.length == 0 ? message : String.format(message, args);
            } catch (Exception ignored) {
            }
        }
        return "message is null";
    }

    // =============
    // = 获取堆栈信息 =
    // =============

    /**
     * 获取类名
     * @param name 类.class
     * @return ClassName
     */
    private String getSimpleClassName(final String name) {
        int lastIndex = name.lastIndexOf('.');
        return name.substring(lastIndex + 1);
    }

    /**
     * 确定该类的方法调用后的堆栈跟踪的起始索引
     * @param trace 堆栈
     * @return 堆栈跟踪索引
     */
    private int getStackOffset(final StackTraceElement[] trace) {
        for (int i = LogConstants.MIN_STACK_OFFSET, len = trace.length; i < len; i++) {
            StackTraceElement e    = trace[i];
            String            name = e.getClassName();
            if (!LoggerPrinter.class.getName().equals(name)
                    && !DevLogger.class.getName().equals(name)) {
                return --i;
            }
        }
        return -1;
    }

    // =============
    // = 日志配置获取 =
    // =============

    /**
     * 返回对应线程的日志配置信息
     * @return {@link LogConfig} 日志配置
     */
    private LogConfig getThreadLogConfig() {
        // 获取当前线程的日志配置信息
        LogConfig logConfig = LOCAL_LOG_CONFIGS.get();
        // 如果等于 null, 则返回默认配置信息
        if (logConfig == null) {
            return initialize();
        } else {
            LOCAL_LOG_CONFIGS.remove();
        }
        // 如果存在当前线程的配置信息, 则返回
        return logConfig;
    }
}