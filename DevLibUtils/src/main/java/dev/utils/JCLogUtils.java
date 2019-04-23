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
 * detail: Java Common 日志打印工具类(简化版) - 项目内部使用 - 主要打印非 Android 日志
 * @author Ttt
 */
public final class JCLogUtils {

    private JCLogUtils() {
    }

    // 是否打印日志 上线 = false，开发、debug = true
    private static boolean JUDGE_PRINT_LOG = false;
    // 判断是否控制台打印信息
    private static boolean JUDGE_CONTROL_PRINT_LOG = false;
    // 默认 DEFAULT_TAG
    private static final String DEFAULT_TAG = JCLogUtils.class.getSimpleName();

    // ============
    // = 日志类型 =
    // ============

    // 普通信息模式
    private static final int INFO = 0;
    // DEBUG 模式
    private static final int DEBUG = 1;
    // ERROR 模式
    private static final int ERROR = 2;

    /**
     * 判断是否打印日志
     * @return {@code true} 打印日志, {@code false} 不打印日志
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
     * 判断是否为 null
     * @param str 待校验字符串
     * @return {@code true} is null, {@code false} not null
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    // =

    /**
     * 最终打印日志方法(全部调用此方法)
     * @param logType 打印日志类型
     * @param tag     打印Tag
     * @param msg     打印消息
     */
    private static void printLog(final int logType, final String tag, final String msg) {
        switch (logType) {
            case INFO:
                LogPrintUtils.iTag(tag, msg);
            case ERROR:
                LogPrintUtils.eTag(tag, msg);
                break;
            case DEBUG:
            default:
                LogPrintUtils.dTag(tag, msg);
                break;
        }
        if (JUDGE_CONTROL_PRINT_LOG) {
            // 打印信息
            if (isEmpty(tag)) {
                System.out.println(msg);
            } else {
                System.out.println(tag + " : " + msg);
            }
        }
    }

    /**
     * 处理信息
     * @param message 打印信息
     * @param args    占位符替换
     * @return 返回处理(格式化 、 判断)后准备打印的日志信息
     */
    private static String createMessage(final String message, final Object... args) {
        String result;
        try {
            if (message != null) {
                if (args == null) {
                    // 动态参数为null
                    result = "params is null";
                } else {
                    // 格式化字符串
                    result = (args.length == 0 ? message : String.format(message, args));
                }
            } else {
                // 打印内容为null
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
     * @param throwable 错误异常
     * @param message   需要打印的消息
     * @param args      动态参数
     * @return 返回处理(格式化 、 判断)后准备打印的日志信息
     */
    private static String splitErrorMessage(final Throwable throwable, final String message, final Object... args) {
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

    // ==============================
    // = 对外公开方法 - 使用默认TAG =
    // ==============================

    public static void d(final String message, final Object... args) {
        dTag(DEFAULT_TAG, message, args);
    }

    public static void e(final Throwable throwable) {
        eTag(DEFAULT_TAG, throwable);
    }

    public static void e(final String message, final Object... args) {
        e(null, message, args);
    }

    public static void e(final Throwable throwable, final String message, final Object... args) {
        eTag(DEFAULT_TAG, throwable, message, args);
    }

    public static void i(final String message, final Object... args) {
        iTag(DEFAULT_TAG, message, args);
    }

    public static void xml(final String xml) {
        xmlTag(DEFAULT_TAG, xml);
    }

    // ===============================
    // = 对外公开方法 - 日志打印方法 =
    // ===============================

    public static void dTag(final String tag, final String message, final Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(DEBUG, tag, createMessage(message, args));
        }
    }

    public static void eTag(final String tag, final String message, final Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(ERROR, tag, createMessage(message, args));
        }
    }

    public static void eTag(final String tag, final Throwable throwable) {
        if (JUDGE_PRINT_LOG) {
            printLog(ERROR, tag, splitErrorMessage(throwable, null));
        }
    }

    public static void eTag(final String tag, final Throwable throwable, final String message, final Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(ERROR, tag, splitErrorMessage(throwable, message, args));
        }
    }

    public static void iTag(final String tag, final String message, final Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(INFO, tag, createMessage(message, args));
        }
    }

    public static void xmlTag(final String tag, final String xml) {
        if (JUDGE_PRINT_LOG) {
            // 判断传入XML格式信息是否为null
            if (isEmpty(xml)) {
                printLog(ERROR, tag, "Empty/Null xml content");
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
                printLog(DEBUG, tag, message);
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
                printLog(ERROR, tag, eHint + "\n" + xml);
            }
        }
    }
}
