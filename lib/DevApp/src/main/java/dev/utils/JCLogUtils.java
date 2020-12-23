package dev.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * detail: Java Common 日志打印工具类 ( 简化版 )
 * @author Ttt
 * <pre>
 *     项目内部使用 ( 主要打印 Java 日志 )
 * </pre>
 */
public final class JCLogUtils {

    private JCLogUtils() {
    }

    // 是否打印日志 线上 (release) = false, 开发 (debug) = true
    private static       boolean JUDGE_PRINT_LOG         = false;
    // 判断是否控制台打印信息
    private static       boolean JUDGE_CONTROL_PRINT_LOG = false;
    // 默认 DEFAULT_TAG
    private static final String  DEFAULT_TAG             = JCLogUtils.class.getSimpleName();

    // ===========
    // = 日志类型 =
    // ===========

    // INFO 模式
    public static final int INFO  = 0;
    // DEBUG 模式
    public static final int DEBUG = 1;
    // ERROR 模式
    public static final int ERROR = 2;

    /**
     * 判断是否打印日志
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPrintLog() {
        return JUDGE_PRINT_LOG;
    }

    /**
     * 设置是否打印日志
     * @param judgePrintLog 是否允许打印日志
     */
    public static void setPrintLog(final boolean judgePrintLog) {
        JUDGE_PRINT_LOG = judgePrintLog;
    }

    /**
     * 设置是否在控制台打印日志
     * @param judgeControlPrintLog 是否允许控制台打印日志
     */
    public static void setControlPrintLog(final boolean judgeControlPrintLog) {
        JUDGE_CONTROL_PRINT_LOG = judgeControlPrintLog;
    }

    /**
     * 判断字符串是否为 null
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    // =

    /**
     * 最终打印日志方法 ( 全部调用此方法 )
     * @param logType 日志类型
     * @param tag     打印 Tag
     * @param message 日志信息
     */
    private static void printLog(
            final int logType,
            final String tag,
            final String message
    ) {
        if (JCLogUtils.sPrint != null) {
            JCLogUtils.sPrint.printLog(logType, tag, message);
        }

        if (JUDGE_CONTROL_PRINT_LOG) {
            // 打印信息
            if (isEmpty(tag)) {
                System.out.println(message);
            } else {
                System.out.println(tag + " : " + message);
            }
        }
    }

    /**
     * 处理信息
     * @param message 日志信息
     * @param args    占位符替换
     * @return 处理 ( 格式化 ) 后准备打印的日志信息
     */
    private static String createMessage(
            final String message,
            final Object... args
    ) {
        String result;
        try {
            if (message != null) {
                if (args == null) {
                    // 动态参数为 null
                    result = "params is null";
                } else {
                    // 格式化字符串
                    result = (args.length == 0 ? message : String.format(message, args));
                }
            } else {
                // 打印内容为 null
                result = "message is null";
            }
        } catch (Exception e) {
            // 出现异常
            result = e.toString();
        }
        return result;
    }

    /**
     * 拼接错误信息
     * @param throwable 异常
     * @param message   需要打印的消息
     * @param args      动态参数
     * @return 处理 ( 格式化 ) 后准备打印的日志信息
     */
    private static String splitErrorMessage(
            final Throwable throwable,
            final String message,
            final Object... args
    ) {
        String result;
        try {
            if (throwable != null) {
                if (message != null) {
                    result = createMessage(message, args) + " : " + throwable.toString();
                } else {
                    result = throwable.toString();
                }
            } else {
                result = createMessage(message, args);
            }
        } catch (Exception e) {
            result = e.toString();
        }
        return result;
    }

    // ===============================
    // = 对外公开方法 ( 使用默认 TAG ) =
    // ===============================

    public static void d(
            final String message,
            final Object... args
    ) {
        dTag(DEFAULT_TAG, message, args);
    }

    public static void e(final Throwable throwable) {
        eTag(DEFAULT_TAG, throwable);
    }

    public static void e(
            final String message,
            final Object... args
    ) {
        e(null, message, args);
    }

    public static void e(
            final Throwable throwable,
            final String message,
            final Object... args
    ) {
        eTag(DEFAULT_TAG, throwable, message, args);
    }

    public static void i(
            final String message,
            final Object... args
    ) {
        iTag(DEFAULT_TAG, message, args);
    }

    public static void xml(final String xml) {
        xmlTag(DEFAULT_TAG, xml);
    }

    // ===============================
    // = 对外公开方法 ( 日志打印方法 ) =
    // ===============================

    public static void dTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (JUDGE_PRINT_LOG) {
            printLog(DEBUG, tag, createMessage(message, args));
        }
    }

    public static void eTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (JUDGE_PRINT_LOG) {
            printLog(ERROR, tag, createMessage(message, args));
        }
    }

    public static void eTag(
            final String tag,
            final Throwable throwable
    ) {
        if (JUDGE_PRINT_LOG) {
            printLog(ERROR, tag, splitErrorMessage(throwable, null));
        }
    }

    public static void eTag(
            final String tag,
            final Throwable throwable,
            final String message,
            final Object... args
    ) {
        if (JUDGE_PRINT_LOG) {
            printLog(ERROR, tag, splitErrorMessage(throwable, message, args));
        }
    }

    public static void iTag(
            final String tag,
            final String message,
            final Object... args
    ) {
        if (JUDGE_PRINT_LOG) {
            printLog(INFO, tag, createMessage(message, args));
        }
    }

    public static void xmlTag(
            final String tag,
            final String xml
    ) {
        if (JUDGE_PRINT_LOG) {
            // 判断传入 XML 格式信息是否为 null
            if (isEmpty(xml)) {
                printLog(ERROR, tag, "Empty/Null xml content");
                return;
            }
            try {
                Source       xmlInput    = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput   = new StreamResult(new StringWriter());
                Transformer  transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(xmlInput, xmlOutput);
                // 获取打印消息
                String message = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
                // 打印信息
                printLog(DEBUG, tag, message);
            } catch (Exception e) {
                String errorInfo = DevFinal.NULL_STR;
                if (e != null) {
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
                }
                printLog(ERROR, tag, errorInfo + "\n" + xml);
            }
        }
    }

    // ===========
    // = 通知输出 =
    // ===========

    private static Print sPrint;

    /**
     * 设置日志输出接口
     * @param print 日志输出接口
     */
    public static void setPrint(final Print print) {
        JCLogUtils.sPrint = print;
    }

    /**
     * detail: 日志输出接口
     * @author Ttt
     */
    public interface Print {

        /**
         * 日志打印
         * @param logType 日志类型
         * @param tag     打印 Tag
         * @param message 日志信息
         */
        void printLog(
                int logType,
                String tag,
                String message
        );
    }
}