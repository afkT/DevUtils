package dev.environment.log;

/**
 * detail: 日志打印工具类 ( 简化版 )
 * @author Ttt
 */
public final class LogUtils {

    private LogUtils() {
    }

    // 是否打印日志
    private static boolean PRINT_LOG = true;

    /**
     * 判断是否打印日志
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPrintLog() {
        return PRINT_LOG;
    }

    /**
     * 设置是否打印日志
     * @param printLog 是否允许打印日志
     */
    public static void setPrintLog(final boolean printLog) {
        PRINT_LOG = printLog;
    }

    /**
     * 打印错误信息
     * @param error 异常信息
     */
    public static void printStackTrace(final Throwable error) {
        if (PRINT_LOG && error != null) error.printStackTrace();
    }
}