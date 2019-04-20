package dev.utils.app;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import dev.utils.LogPrintUtils;

/**
 * detail: 错误信息处理工具类
 * @author Ttt
 */
public final class ErrorUtils {

    private ErrorUtils() {
    }

    // 日志 TAG
    private static final String TAG = ErrorUtils.class.getSimpleName();
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

    /**
     * 获取错误信息
     * @param e
     * @return
     */
    public static String getErrorMsg(final Exception e) {
        try {
            if (e != null) {
                return e.getMessage();
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "e(null)";
    }

    /**
     * 将异常栈信息转为字符串
     * @param e 字符串
     * @return 异常栈
     */
    public static String getThrowableMsg(final Throwable e) {
        try {
            if (e != null) {
                StringWriter writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                return writer.toString();
            }
        } catch (Exception ex) {
            LogPrintUtils.eTag(TAG, e, "getThrowableMsg");
            if (ex != null) {
                return ex.getMessage();
            } else {
                return "ex(null)";
            }
        }
        return "e(null)";
    }

    // ================
    // = 异常信息处理 =
    // ================

    /**
     * 获取错误信息(无换行)
     * @param eHint 获取失败提示
     * @param ex    错误信息
     * @return
     */
    public static String getThrowableMsg(final String eHint, final Throwable ex) {
        PrintWriter printWriter = null;
        try {
            if (ex != null) {
                // 初始化Writer,PrintWriter打印流
                Writer writer = new StringWriter();
                printWriter = new PrintWriter(writer);
                // 写入错误栈信息
                ex.printStackTrace(printWriter);
                // 关闭流
                printWriter.close();
                return writer.toString();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getThrowableMsg");
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
        return eHint;
    }

    /**
     * 获取错误信息(有换行)
     * @param ex 错误信息
     * @return 默认返回 ""
     */
    public static String getThrowableNewLinesMsg(final Throwable ex) {
        return getThrowableNewLinesMsg("", ex);
    }

    /**
     * 获取错误信息(有换行)
     * @param eHint 获取失败提示
     * @param ex    错误信息
     * @return
     */
    public static String getThrowableNewLinesMsg(final String eHint, final Throwable ex) {
        PrintWriter printWriter = null;
        try {
            if (ex != null) {
                // 初始化Writer,PrintWriter打印流
                Writer writer = new StringWriter();
                printWriter = new PrintWriter(writer);
                // 获取错误栈信息
                StackTraceElement[] stElement = ex.getStackTrace();
                // 标题,提示属于什么异常
                printWriter.append(ex.toString());
                printWriter.append(NEW_LINE_STR);
                // 遍历错误栈信息,并且进行换行,缩进
                for (StackTraceElement st : stElement) {
                    printWriter.append("\tat ");
                    printWriter.append(st.toString());
                    printWriter.append(NEW_LINE_STR);
                }
                // 关闭流
                printWriter.close();
                return writer.toString();
            }
        } catch (Exception e) {
        } finally {
            if (printWriter != null) {
                try {
                    printWriter.close();
                } catch (Exception e) {
                }
            }
        }
        return eHint;
    }
}
