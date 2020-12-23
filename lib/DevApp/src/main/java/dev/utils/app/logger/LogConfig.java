package dev.utils.app.logger;

/**
 * detail: 日志配置类
 * @author Ttt
 */
public class LogConfig {

    /**
     * 堆栈方法总数 ( 显示经过的方法 )
     */
    public int methodCount = LogConstants.DEFAULT_LOG_METHOD_COUNT;

    /**
     * 堆栈方法索引偏移 ( 0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息 )
     */
    public int methodOffset = LogConstants.DEFAULT_LOG_METHOD_OFFSET;

    /**
     * 是否输出全部方法 ( 在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数 )
     */
    public boolean outputMethodAll = LogConstants.JUDGE_OUTPUT_METHOD_ALL;

    /**
     * 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
     */
    public boolean displayThreadInfo = LogConstants.JUDGE_DISPLAY_THREAD_LOG;

    /**
     * 是否排序日志 ( 格式化 )
     */
    public boolean sortLog = LogConstants.JUDGE_SORT_LOG;

    /**
     * 日志级别 ( 只有 e, wtf 才进行显示 )
     */
    public LogLevel logLevel = LogConstants.DEFAULT_LOG_LEVEL;

    /**
     * 设置 TAG ( 特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下 )
     */
    public String tag = LogConstants.DEFAULT_LOG_TAG;

    // =======================
    // = 快速初始化 LogConfig =
    // =======================

    /**
     * 获取 Release Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、不进行排序、默认只打印 ERROR 级别日志 )
     * @param tag 日志 TAG
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getReleaseLogConfig(final String tag) {
        return getLogConfig(tag, 3, 0, false, true, false, LogLevel.ERROR);
    }

    /**
     * 获取 Release Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、不进行排序 )
     * @param tag      日志 TAG
     * @param logLevel 日志级别
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getReleaseLogConfig(
            final String tag,
            final LogLevel logLevel
    ) {
        return getLogConfig(tag, 3, 0, false, true, false, logLevel);
    }

    // =

    /**
     * 获取 Debug Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、不进行排序、默认只打印 ERROR 级别日志 )
     * @param tag 日志 TAG
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getDebugLogConfig(final String tag) {
        return getLogConfig(tag, 3, 0, false, true, false, LogLevel.DEBUG);
    }

    /**
     * 获取 Debug Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、进行排序 )
     * @param tag      日志 TAG
     * @param logLevel 日志级别
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getDebugLogConfig(
            final String tag,
            final LogLevel logLevel
    ) {
        return getLogConfig(tag, 3, 0, false, true, false, logLevel);
    }

    // =

    /**
     * 获取 Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、并且美化日志信息、默认打印 DEBUG 级别及以上日志 )
     * @param tag 日志 TAG
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getSortLogConfig(final String tag) {
        return getLogConfig(tag, 3, 0, false, true, true, LogLevel.DEBUG);
    }

    /**
     * 获取 Log 配置 ( 打印线程信息、显示方法总数 3、从 0 开始、并且美化日志信息 )
     * @param tag      日志 TAG
     * @param logLevel 日志级别
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getSortLogConfig(
            final String tag,
            final LogLevel logLevel
    ) {
        return getLogConfig(tag, 3, 0, false, true, true, logLevel);
    }

    // =

    /**
     * 获取 Log 配置
     * @param tag        日志 TAG
     * @param count      显示的方法总数 ( 推荐 3)
     * @param offset     方法偏移索引 ( 从第几个方法开始打印, 默认推荐 0)
     * @param allMethod  是否打印全部方法
     * @param threadInfo 是否显示线程信息
     * @param sortLog    是否排序日志 ( 美化 )
     * @param logLevel   日志级别
     * @return {@link LogConfig} 日志配置
     */
    public static LogConfig getLogConfig(
            final String tag,
            final int count,
            final int offset,
            final boolean allMethod,
            final boolean threadInfo,
            final boolean sortLog,
            final LogLevel logLevel
    ) {
        // 生成默认配置信息
        LogConfig logConfig = new LogConfig();
        // 堆栈方法总数 ( 显示经过的方法 )
        logConfig.methodCount = count;
        // 堆栈方法索引偏移 (0 = 最新经过调用的方法信息, 偏移则往上推, 如 1 = 倒数第二条经过调用的方法信息 )
        logConfig.methodOffset = offset;
        // 是否输出全部方法 ( 在特殊情况下, 如想要打印全部经过的方法, 但是不知道经过的总数 )
        logConfig.outputMethodAll = allMethod;
        // 显示日志线程信息 ( 特殊情况, 显示经过的线程信息, 具体情况如上 )
        logConfig.displayThreadInfo = threadInfo;
        // 是否排序日志 ( 格式化 )
        logConfig.sortLog = sortLog;
        // 日志级别
        logConfig.logLevel = logLevel;
        // 设置 TAG ( 特殊情况使用, 不使用全部的 TAG 时, 如单独输出在某个 TAG 下 )
        logConfig.tag = tag;
        // 返回日志配置
        return logConfig;
    }

    // =

    /**
     * 设置堆栈方法总数
     * @param methodCount 堆栈方法总数
     * @return {@link LogConfig}
     */
    public LogConfig methodCount(int methodCount) {
        this.methodCount = methodCount;
        return this;
    }

    /**
     * 设置堆栈方法索引偏移
     * @param methodOffset 堆栈方法索引偏移
     * @return {@link LogConfig}
     */
    public LogConfig methodOffset(int methodOffset) {
        this.methodOffset = methodOffset;
        return this;
    }

    /**
     * 设置是否输出全部方法
     * @param outputMethodAll {@code true} yes, {@code false} no
     * @return {@link LogConfig}
     */
    public LogConfig outputMethodAll(boolean outputMethodAll) {
        this.outputMethodAll = outputMethodAll;
        return this;
    }

    /**
     * 设置是否显示日志线程信息
     * @param displayThreadInfo {@code true} yes, {@code false} no
     * @return {@link LogConfig}
     */
    public LogConfig displayThreadInfo(boolean displayThreadInfo) {
        this.displayThreadInfo = displayThreadInfo;
        return this;
    }

    /**
     * 设置是否排序日志
     * @param sortLog {@code true} yes, {@code false} no
     * @return {@link LogConfig}
     */
    public LogConfig sortLog(boolean sortLog) {
        this.sortLog = sortLog;
        return this;
    }

    /**
     * 设置日志级别
     * @param logLevel 日志级别
     * @return {@link LogConfig}
     */
    public LogConfig logLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    /**
     * 设置 TAG
     * @param tag TAG
     * @return {@link LogConfig}
     */
    public LogConfig tag(String tag) {
        this.tag = tag;
        return this;
    }
}