package dev.utils.app.logger;

/**
 * detail: 日志设置类
 * @author Ttt
 */
public class LogConfig {

    /**
     * 堆栈方法总数(显示经过的方法) = 默认 3
     */
    public int methodCount = LogConstants.DEFAULT_LOG_METHOD_COUNT;

    /**
     * 堆栈方法索引偏移(0 = 最新经过调用的方法信息,偏移则往上推,如 1 = 倒数第二条经过调用的方法信息) = 默认 0
     */
    public int methodOffset = LogConstants.DEFAULT_LOG_METHOD_OFFSET;

    /**
     * 是否输出全部方法(在特殊情况下,如想要打印全部经过的方法,但是不知道经过的总数) = 默认 false
     */
    public boolean outputMethodAll = LogConstants.JUDGE_OUTPUT_METHOD_ALL;

    /**
     * 显示日志线程信息(特殊情况,显示经过的线程信息,具体情况如上) = 默认 false
     */
    public boolean displayThreadInfo = LogConstants.JUDGE_DISPLAY_THREAD_LOG;

    /**
     * 是否排序日志(格式化) = 默认 false
     */
    public boolean sortLog = LogConstants.JUDGE_SORT_LOG;

    /**
     * 日志级别 - 默认异常级别(只有 e,wtf 才进行显示)
     */
    public LogLevel logLevel = LogConstants.DEFAULT_LOG_LEVEL;

    /**
     * 设置Tag特殊情况使用,不使用全部的Tag时,如单独输出在某个Tag下)
     */
    public String tag = LogConstants.DEFAULT_LOG_TAG;
}
