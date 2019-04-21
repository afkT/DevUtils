package dev.utils.app.logger;

/**
 * detail: 日志常量类
 * @author Ttt
 */
final class LogConstants {

    // ================
    // = 日志配置常量 =
    // ================

    /**
     * 判断是否排序日志
     */
    public static final boolean JUDGE_SORT_LOG = false;

    /**
     * 判断是否输出全部方法 - 异常的全部方法
     */
    public static final boolean JUDGE_OUTPUT_METHOD_ALL = false;

    /**
     * 判断是否显示日志线程信息
     */
    public static final boolean JUDGE_DISPLAY_THREAD_LOG = false;

    /**
     * 默认的日志Tag
     */
    public static final String DEFAULT_LOG_TAG = DevLogger.class.getSimpleName();

    /**
     * 默认输出方法数量
     */
    public static final int DEFAULT_LOG_METHOD_COUNT = 3;

    /**
     * 默认方法索引偏移
     */
    public static final int DEFAULT_LOG_METHOD_OFFSET = 0;

    /**
     * 默认日志级别 - 异常级别(只有 e,wtf 才进行显示)
     */
    public static final LogLevel DEFAULT_LOG_LEVEL = LogLevel.ERROR;

    // ================
    // = 日志配置信息 =
    // ================

    /**
     * Android 的最大限制为一个日志条目 4076字节,4000字节作为块的大小从默认字符集是UTF-8
     * <p></p>
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset is UTF-8
     */
    public static final int CHUNK_SIZE = 4000;

    /**
     * JSON格式内容缩进
     */
    public static final int JSON_INDENT = 4;

    /**
     * 最小堆栈跟踪索引,在本类中启动2次本地调用。
     */
    public static final int MIN_STACK_OFFSET = 3;

    // =======================
    // = 绘制日志工具 - 字符 =
    // =======================

    public static final char TOP_LEFT_CORNER = '╔';
    public static final char BOTTOM_LEFT_CORNER = '╚';
    public static final char MIDDLE_CORNER = '╟';
    public static final char HORIZONTAL_DOUBLE_LINE = '║';
    public static final String DOUBLE_DIVIDER = "═══════";
    public static final String SINGLE_DIVIDER = "───────";
    // =
    public static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
}
