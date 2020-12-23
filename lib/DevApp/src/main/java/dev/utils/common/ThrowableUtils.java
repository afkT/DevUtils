package dev.utils.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import dev.utils.JCLogUtils;

/**
 * detail: 异常处理工具类
 * @author Ttt
 */
public final class ThrowableUtils {

    private ThrowableUtils() {
    }

    // 日志 TAG
    private static final String TAG = ThrowableUtils.class.getSimpleName();

    /**
     * 获取异常信息
     * @param throwable 异常
     * @return 异常信息字符串
     */
    public static String getThrowable(final Throwable throwable) {
        return getThrowable(throwable, "error(null)");
    }

    /**
     * 获取异常信息
     * @param throwable 异常
     * @param errorInfo 获取失败返回字符串
     * @return 异常信息字符串
     */
    public static String getThrowable(
            final Throwable throwable,
            final String errorInfo
    ) {
        if (throwable != null) {
            Throwable cause = throwable.getCause();
            if (cause != null) {
                return cause.toString();
            }
            return throwable.toString();
        }
        return errorInfo;
    }

    // =============
    // = 异常栈信息 =
    // =============

    /**
     * 获取异常栈信息
     * @param throwable 异常
     * @return 异常栈信息字符串
     */
    public static String getThrowableStackTrace(final Throwable throwable) {
        return getThrowableStackTrace(throwable, "error(null)");
    }

    /**
     * 获取异常栈信息
     * @param throwable 异常
     * @param errorInfo 获取失败返回字符串
     * @return 异常栈信息字符串
     */
    public static String getThrowableStackTrace(
            final Throwable throwable,
            final String errorInfo
    ) {
        if (throwable != null) {
            PrintWriter printWriter = null;
            try {
                Writer writer = new StringWriter();
                printWriter = new PrintWriter(writer);
                throwable.printStackTrace(printWriter);
//                // 获取错误栈信息
//                StackTraceElement[] stElement = throwable.getStackTrace();
//                // 标题, 提示属于什么异常
//                printWriter.append(throwable.toString());
//                printWriter.append(DevFinal.NEW_LINE_STR);
//                // 遍历错误栈信息, 并且进行换行缩进
//                for (StackTraceElement element : stElement) {
//                    printWriter.append("\tat ");
//                    printWriter.append(element.toString());
//                    printWriter.append(DevFinal.NEW_LINE_STR);
//                }
                return writer.toString();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getThrowableStackTrace");
                return e.toString();
            } finally {
                CloseUtils.closeIOQuietly(printWriter);
            }
        }
        return errorInfo;
    }
}