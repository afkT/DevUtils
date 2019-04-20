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

/**
 * detail: 日志输出类(处理方法)
 * @author Ttt
 */
final class LoggerPrinter implements IPrinter {

    // 日志配置
    private static LogConfig LOG_CONFIG = null;
    // 每个线程的日志配置信息
    private static final ThreadLocal<LogConfig> LOCAL_LOG_CONFIGS = new ThreadLocal<>();

    // =================================
    // = 实现IPrinter接口,对外公开方法 =
    // =================================

    /**
     * 其他(特殊情况,如不使用默认配置,单使用一次自定义配置)
     * @param lConfig 对应的配置信息
     */
    @Override
    public IPrinter other(LogConfig lConfig) {
        if (lConfig != null) {
            LOCAL_LOG_CONFIGS.set(lConfig);
        }
        return this;
    }

    /**
     * 返回日志配置
     * @return
     */
    @Override
    public LogConfig getLogConfig() {
        return LOG_CONFIG;
    }

    /**
     * 默认参数初始化,防止设置信息为null
     * @return 日志配置
     */
    @Override
    public LogConfig init() {
        // 判断日志配置信息是否等于null
        if (LOG_CONFIG == null) {
            // 生成默认配置信息
            LOG_CONFIG = new LogConfig();
        }
        // 返回配置信息
        return LOG_CONFIG;
    }

    /**
     * 自定义日志配置
     * @param lConfig 日志配置
     */
    @Override
    public void init(LogConfig lConfig) {
        LOG_CONFIG = lConfig;
        // 防止日志配置参数为null
        init();
    }

    // ==============================
    // = 使用默认TAG - 日志打印方法 =
    // ==============================

    @Override
    public void d(String message, Object... args) {
        logHandle(Log.DEBUG, message, args);
    }

    @Override
    public void e(String message, Object... args) {
        e(null, message, args);
    }

    @Override
    public void e(Throwable throwable) {
        e(throwable, null);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        if (throwable != null && message != null) {
            message += " : " + throwable.toString();
        } else if (throwable != null && message == null) {
            message = throwable.toString();
        } else if (message == null) {
            // 没有日志信息,也没有异常信息传入
            message = "No message/exception is set";
        }
        logHandle(Log.ERROR, message, args);
    }

    @Override
    public void w(String message, Object... args) {
        logHandle(Log.WARN, message, args);
    }

    @Override
    public void i(String message, Object... args) {
        logHandle(Log.INFO, message, args);
    }

    @Override
    public void v(String message, Object... args) {
        logHandle(Log.VERBOSE, message, args);
    }

    @Override
    public void wtf(String message, Object... args) {
        logHandle(Log.ASSERT, message, args);
    }

    // =

    @Override
    public void json(String json) {
        // 获取当前线程日志配置信息
        LogConfig lConfig = getThreadLogConfig();
        // 判断是否打印日志(日志级别)
        if (!isPrintLog(lConfig, Log.DEBUG)) {
            return;
        }
        // 日志 TAG
        String tag = lConfig.tag;
        // 判断传入JSON格式信息是否为null
        if (TextUtils.isEmpty(json)) {
            logHandle(lConfig, tag, Log.DEBUG, "Empty/Null json content");
            return;
        }
        try {
            // 属于对象的JSON格式信息
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                // 进行缩进
                String message = jsonObject.toString(LogConstants.JSON_INDENT);
                // 打印信息
                logHandle(lConfig, tag, Log.DEBUG, message);
            } else if (json.startsWith("[")) {
                // 属于数据的JSON格式信息
                JSONArray jsonArray = new JSONArray(json);
                // 进行缩进
                String message = jsonArray.toString(LogConstants.JSON_INDENT);
                // 打印信息
                logHandle(lConfig, tag, Log.DEBUG, message);
            }
        } catch (Exception e) {
            String eHint = "null";
            if (e != null) {
                Throwable throwable = e.getCause();
                if (throwable != null) {
                    eHint = throwable.getMessage();
                } else {
                    try {
                        eHint = e.getMessage();
                    } catch (Exception e1) {
                        eHint = e1.getMessage();
                    }
                }
            }
            logHandle(lConfig, tag, Log.ERROR, eHint + "\n" + json);

        }
    }

    @Override
    public void xml(String xml) {
        // 获取当前线程日志配置信息
        LogConfig lConfig = getThreadLogConfig();
        // 判断是否打印日志(日志级别)
        if (!isPrintLog(lConfig, Log.DEBUG)) {
            return;
        }
        // 日志 TAG
        String tag = lConfig.tag;
        // 判断传入XML格式信息是否为null
        if (TextUtils.isEmpty(xml)) {
            logHandle(lConfig, tag, Log.DEBUG, "Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            // 获取打印消息
            String message = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
            // 打印信息
            logHandle(lConfig, tag, Log.DEBUG, message);
        } catch (Exception e) {
            String eHint = "null";
            if (e != null) {
                Throwable throwable = e.getCause();
                if (throwable != null) {
                    eHint = throwable.getMessage();
                } else {
                    try {
                        eHint = e.getMessage();
                    } catch (Exception e1) {
                        eHint = e1.getMessage();
                    }
                }
            }
            logHandle(lConfig, tag, Log.ERROR, eHint + "\n" + xml);
        }
    }

    // ================================
    // = 使用自定义TAG - 日志打印方法 =
    // ================================

    @Override
    public void dTag(String tag, String message, Object... args) {
        logHandle(tag, Log.DEBUG, message, args);
    }

    @Override
    public void eTag(String tag, String message, Object... args) {
        eTag(tag, null, message, args);
    }

    @Override
    public void eTag(String tag, Throwable throwable) {
        eTag(tag, throwable, null);
    }

    @Override
    public void eTag(String tag, Throwable throwable, String message, Object... args) {
        if (throwable != null && message != null) {
            message += " : " + throwable.toString();
        } else if (throwable != null && message == null) {
            message = throwable.toString();
        } else if (message == null) {
            // 没有日志信息,也没有异常信息传入
            message = "No message/exception is set";
        }
        logHandle(tag, Log.ERROR, message, args);
    }

    @Override
    public void wTag(String tag, String message, Object... args) {
        logHandle(tag, Log.WARN, message, args);
    }

    @Override
    public void iTag(String tag, String message, Object... args) {
        logHandle(tag, Log.INFO, message, args);
    }

    @Override
    public void vTag(String tag, String message, Object... args) {
        logHandle(tag, Log.VERBOSE, message, args);
    }

    @Override
    public void wtfTag(String tag, String message, Object... args) {
        logHandle(tag, Log.ASSERT, message, args);
    }

    // =

    @Override
    public void jsonTag(String tag, String json) {
        // 获取当前线程日志配置信息
        LogConfig lConfig = getThreadLogConfig();
        // 判断是否打印日志(日志级别)
        if (!isPrintLog(lConfig, Log.DEBUG)) {
            return;
        }
        // 判断传入JSON格式信息是否为null
        if (TextUtils.isEmpty(json)) {
            logHandle(lConfig, tag, Log.DEBUG, "Empty/Null json content");
            return;
        }
        try {
            // 属于对象的JSON格式信息
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                // 进行缩进
                String message = jsonObject.toString(LogConstants.JSON_INDENT);
                // 打印信息
                logHandle(lConfig, tag, Log.DEBUG, message);
            } else if (json.startsWith("[")) {
                // 属于数据的JSON格式信息
                JSONArray jsonArray = new JSONArray(json);
                // 进行缩进
                String message = jsonArray.toString(LogConstants.JSON_INDENT);
                // 打印信息
                logHandle(lConfig, tag, Log.DEBUG, message);
            }
        } catch (Exception e) {
            String eHint = "null";
            if (e != null) {
                Throwable throwable = e.getCause();
                if (throwable != null) {
                    eHint = throwable.getMessage();
                } else {
                    try {
                        eHint = e.getMessage();
                    } catch (Exception e1) {
                        eHint = e1.getMessage();
                    }
                }
            }
            logHandle(lConfig, tag, Log.ERROR, eHint + "\n" + json);
        }
    }

    @Override
    public void xmlTag(String tag, String xml) {
        // 获取当前线程日志配置信息
        LogConfig lConfig = getThreadLogConfig();
        // 判断是否打印日志(日志级别)
        if (!isPrintLog(lConfig, Log.DEBUG)) {
            return;
        }
        // 判断传入XML格式信息是否为null
        if (TextUtils.isEmpty(xml)) {
            logHandle(lConfig, tag, Log.DEBUG, "Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            // 获取打印消息
            String message = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
            // 打印信息
            logHandle(lConfig, tag, Log.DEBUG, message);
        } catch (Exception e) {
            String eHint = "null";
            if (e != null) {
                Throwable throwable = e.getCause();
                if (throwable != null) {
                    eHint = throwable.getMessage();
                } else {
                    try {
                        eHint = e.getMessage();
                    } catch (Exception e1) {
                        eHint = e1.getMessage();
                    }
                }
            }
            logHandle(lConfig, tag, Log.ERROR, eHint + "\n" + xml);
        }
    }

    // ================
    // = 内部判断方法 =
    // ================

    /**
     * 是否打印日志
     * @param lConfig 日志配置
     * @param logType 日志类型
     * @return
     */
    private boolean isPrintLog(LogConfig lConfig, int logType) {
        // 是否打印日志 - 默认不打印
        boolean isPrint = false;
        // 日志级别
        LogLevel lLevel = lConfig.logLevel;
        // =
        switch (lLevel) {
            case NONE: // 全部不打印
                break;
            case DEBUG: // 调试级别 v,d - 全部打印
                isPrint = true;
                break;
            case INFO: // 正常级别  i
            case WARN: // 警告级别  w
            case ERROR: // 异常级别  e,wtf
                isPrint = checkLogLevel(lLevel, logType);
                break;
            default:
                break;
        }
        return isPrint;
    }

    /**
     * 判断日志级别是否允许输出
     * @param lLevel  日志级别
     * @param logType 日志类型
     * @return
     */
    private boolean checkLogLevel(LogLevel lLevel, int logType) {
        switch (lLevel) {
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
            case ERROR: // 异常级别 e,wtf
                if (logType == Log.ERROR || logType == Log.ASSERT) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    // ====================
    // = 打印日志处理方法 =
    // ====================

    /**
     * 最终打印方法 f = Final
     * @param logType 日志类型
     * @param tag     日志Tag
     * @param msg     打印信息
     */
    private void fLogPrinter(int logType, String tag, String msg) {
        switch (logType) {
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            case Log.ERROR:
                Log.e(tag, msg);
                break;
            case Log.ASSERT:
                Log.wtf(tag, msg);
                break;
            default: // 默认使用,自定义级别
                Log.wtf(tag, msg);
                break;
        }
    }

    /**
     * 日志处理方法(统一调用这个)
     * @param logType 日志类型
     * @param msg     打印信息
     * @param args    占位符替换
     */
    private void logHandle(int logType, String msg, Object... args) {
        logHandle(null, null, logType, msg, args);
    }

    /**
     * 日志处理方法(统一调用这个)
     * @param tag     日志Tag
     * @param logType 日志类型
     * @param msg     打印信息
     * @param args    占位符替换
     */
    private void logHandle(String tag, int logType, String msg, Object... args) {
        logHandle(null, tag, logType, msg, args);
    }

    /**
     * 日志处理方法(统一调用这个) - 此方法是同步的,以避免混乱的日志的顺序。
     * @param lConfig 配置信息
     * @param tag     日志Tag
     * @param logType 日志类型
     * @param msg     打印信息
     * @param args    占位符替换
     */
    private synchronized void logHandle(LogConfig lConfig, String tag, int logType, String msg, Object... args) {
        if (lConfig == null) { // 如果配置为null,才进行获取
            // 获取当前线程日志配置信息
            lConfig = getThreadLogConfig();
        }
        // 判断是否打印日志(日志级别)
        if (!isPrintLog(lConfig, logType)) {
            return;
        }
        // 防止TAG为null
        if (TextUtils.isEmpty(tag)) {
            // 获取配置的TAG
            tag = lConfig.tag;
            // 防止配置的TAG也为null
            if (TextUtils.isEmpty(tag)) {
                // 使用默认的TAG
                tag = LogConstants.DEFAULT_LOG_TAG;
            }
        }
        // 判断是否显示排序后的日志(如果不排序,则显示默认)
        if (!lConfig.sortLog) {
            fLogPrinter(logType, tag, createMessage(msg, args));
            return;
        }
        // = 日志配置信息获取 =
        // 获取方法总数
        int methodCount = lConfig.methodCount;
        // 获取方法偏移索引
        int methodOffset = lConfig.methodOffset;
        // 如果出现小于0的设置,则设置默认值处理
        if (methodOffset < 0) {
            methodOffset = LogConstants.DEFAULT_LOG_METHOD_OFFSET;
        }
        // 如果出现小于0的设置,则设置默认值处理
        if (methodCount < 0) {
            methodCount = LogConstants.DEFAULT_LOG_METHOD_COUNT;
        }
        // 获取打印的日志信息
        String message = createMessage(msg, args);
        // 打印头部
        logTopBorder(logType, tag);
        // 打印头部线程信息
        logHeaderContent(lConfig, logType, tag, methodCount, methodOffset);
        // 获取系统的默认字符集的信息字节(UTF-8)
        byte[] bytes = message.getBytes();
        // 获取字节总数
        int length = bytes.length;
        // 判断是否超过总数,没有超过则一次性打印,超过则遍历打印
        if (length <= LogConstants.CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(logType, tag);
            }
            // 打印日志内容
            logContent(logType, tag, message);
            // 打印结尾
            logBottomBorder(logType, tag);
            return;
        }
        // 打印换行符
        if (methodCount > 0) {
            // 换行
            logDivider(logType, tag);
        }
        // 因为超过系统打印字节总数,遍历打印
        for (int i = 0; i < length; i += LogConstants.CHUNK_SIZE) {
            int count = Math.min(length - i, LogConstants.CHUNK_SIZE);
            // 创建系统的默认字符集的一个新的字符串(UTF-8),并打印日志内容
            logContent(logType, tag, new String(bytes, i, count));
        }
        // 打印结尾
        logBottomBorder(logType, tag);
    }

    // ================
    // = 日志格式拼接 =
    // ================

    /**
     * 日志线程信息主体部分
     * @param lConfig      日志配置
     * @param logType      日志类型
     * @param tag          日志Tag
     * @param methodCount  方法总数
     * @param methodOffset 方法偏移索引
     */
    private void logHeaderContent(LogConfig lConfig, int logType, String tag, int methodCount, int methodOffset) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        // 判断是否显示日志线程信息
        if (lConfig.displayThreadInfo) {
            // 打印线程信息(线程名)
            fLogPrinter(logType, tag, LogConstants.HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
            // 进行换行
            logDivider(logType, tag);
        } else {
            // 不打印线程信息,都设置为0
            methodCount = methodOffset = 0;
            return;
        }
        // 手动进行偏移
        String level = "";
        // 堆栈总数
        int traceCount = trace.length;
        // 获取堆栈偏移量
        int stackOffset = getStackOffset(trace) + methodOffset;
        // 对应的方法计数与当前堆栈可能超过,进行堆栈跟踪。修剪计数
        if (methodCount + stackOffset > traceCount) {
            methodCount = traceCount - stackOffset - 1;
        }
        // 判断是否显示全部方法
        if (lConfig.outputMethodAll) {
            // 设置方法总数
            methodCount = traceCount;
            // 设置方法偏移索引为0
            stackOffset = 0;
        } else if (methodCount <= 0) {
            // 如果打印数小于等于0,则直接跳过
            return;
        }
        // 遍历打印的方法数量(类名、行数、操作的方法名)
        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= traceCount) {
                continue;
            }
            // 拼接中间内容,以及操作的类名,行数,方法名等信息
            StringBuilder builder = new StringBuilder();
            builder.append("║ ");
            builder.append(level);
            builder.append(getSimpleClassName(trace[stackIndex].getClassName()));
            builder.append(".").append(trace[stackIndex].getMethodName());
            builder.append(" (");
            builder.append(trace[stackIndex].getFileName());
            builder.append(":");
            builder.append(trace[stackIndex].getLineNumber());
            builder.append(")");
            level += "   ";
            // 打印日志信息
            fLogPrinter(logType, tag, builder.toString());
        }
    }

    /**
     * 日志顶部
     * @param logType 日志类型
     * @param tag     日志Tag
     */
    private void logTopBorder(int logType, String tag) {
        fLogPrinter(logType, tag, LogConstants.TOP_BORDER);
    }

    /**
     * 日志结尾
     * @param logType 日志类型
     * @param tag     日志Tag
     */
    private void logBottomBorder(int logType, String tag) {
        fLogPrinter(logType, tag, LogConstants.BOTTOM_BORDER);
    }

    /**
     * 日志换行
     * @param logType 日志类型
     * @param tag     日志Tag
     */
    private void logDivider(int logType, String tag) {
        fLogPrinter(logType, tag, LogConstants.MIDDLE_BORDER);
    }

    /**
     * 日志内容
     * @param logType 日志类型
     * @param tag     日志Tag
     * @param msg     日志信息
     */
    private void logContent(int logType, String tag, String msg) {
        String[] lines = msg.split(System.getProperty("line.separator"));
        for (String line : lines) {
            fLogPrinter(logType, tag, LogConstants.HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

    /**
     * 处理信息
     * @param message 打印信息
     * @param args    占位符替换
     * @return
     */
    private String createMessage(String message, Object... args) {
        try {
            return args.length == 0 ? message : String.format(message, args);
        } catch (Exception e) {
        }
        return "";
    }

    // ================
    // = 获取堆栈信息 =
    // ================

    /**
     * 获取类名
     * @param name 类名.class
     */
    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    /**
     * 确定该类的方法调用后的堆栈跟踪的起始索引。
     * @param trace 堆栈
     * @return 堆栈跟踪索引
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = LogConstants.MIN_STACK_OFFSET, len = trace.length; i < len; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(DevLogger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    // ================
    // = 日志配置获取 =
    // ================

    /**
     * 返回对应线程的日志配置信息
     */
    private LogConfig getThreadLogConfig() {
        // 获取当前线程的日志配置信息
        LogConfig lConfig = LOCAL_LOG_CONFIGS.get();
        // 如果等于null,则返回默认配置信息
        if (lConfig == null) {
            return init();
        } else {
            LOCAL_LOG_CONFIGS.remove();
        }
        // 如果存在当前线程的配置信息,则返回
        return lConfig;
    }
}
