package dev.utils;

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
 * detail: 日志打印工具类(简化版) - 项目内部使用
 * Created by Ttt
 */
public final class LogPrintUtils {

    private LogPrintUtils() {
    }

    // JSON格式内容缩进
    private static final int JSON_INDENT = 4;
    // 是否打印日志 上线 = false，开发、debug = true
    private static boolean JUDGE_PRINT_LOG = false;
    // 默认DEFAULT_TAG
    private static final String DEFAULT_TAG = LogPrintUtils.class.getSimpleName();

    /**
     * 判断是否打印日志
     * @return
     */
    public static boolean isPrintLog() {
        return JUDGE_PRINT_LOG;
    }

    /**
     * 设置是否打印日志
     * @param judgePrintLog
     */
    public static void setPrintLog(boolean judgePrintLog) {
        JUDGE_PRINT_LOG = judgePrintLog;
    }

    // =

    /**
     * 最终打印日志方法(全部调用此方法)
     * @param logType 打印日志类型
     * @param tag 打印Tag
     * @param msg 打印消息
     */
    private static void printLog(int logType, String tag, String msg) {
        switch (logType) {
            case Log.ERROR:
                Log.e(tag, msg);
                break;
            case Log.INFO:
                Log.i(tag, msg);
                break;
            case Log.VERBOSE:
                Log.v(tag, msg);
                break;
            case Log.WARN:
                Log.w(tag, msg);
                break;
            case Log.ASSERT:
                Log.wtf(tag, msg);
                break;
            case Log.DEBUG:
                Log.d(tag, msg);
                break;
            default:
                Log.d(tag, msg);
                break;
        }
    }

    /**
     * 处理信息
     * @param message 打印信息
     * @param args 占位符替换
     * @return
     */
    private static String createMessage(String message, Object... args) {
        String result = null;
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
     * @param message 需要打印的消息
     * @param args 动态参数
     * @return
     */
    private static String splitErrorMessage(Throwable throwable, String message, Object... args) {
        String result = null;
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

    // ===================  对外公开方法   =========================

    // ========= 使用默认TAG =========

    public static void d(String message, Object... args) {
        dTag(DEFAULT_TAG, message, args);
    }

    public static void e(Throwable throwable) {
        eTag(DEFAULT_TAG, throwable, null);
    }

    public static void e(String message, Object... args) {
        e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        eTag(DEFAULT_TAG, throwable, message, args);
    }

    public static void w(String message, Object... args) {
        wTag(DEFAULT_TAG, message, args);
    }

    public static void i(String message, Object... args) {
        iTag(DEFAULT_TAG, message, args);
    }

    public static void v(String message, Object... args) {
        vTag(DEFAULT_TAG, message, args);
    }

    public static void wtf(String message, Object... args) {
        wtfTag(DEFAULT_TAG, message, args);
    }

    public static void json(String json) {
        jsonTag(DEFAULT_TAG, json);
    }

    public static void xml(String xml) {
        xmlTag(DEFAULT_TAG, xml);
    }

    // -- 日志打印方法 --

    public static void dTag(String tag, String message, Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(Log.DEBUG, tag, createMessage(message, args));
        }
    }

    public static void eTag(String tag, String message, Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(Log.ERROR, tag, createMessage(message, args));
        }
    }

    public static void eTag(String tag, Throwable throwable) {
        if (JUDGE_PRINT_LOG) {
            printLog(Log.ERROR, tag, splitErrorMessage(throwable, null));
        }
    }

    public static void eTag(String tag, Throwable throwable, String message, Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(Log.ERROR, tag, splitErrorMessage(throwable, message, args));
        }
    }

    public static void wTag(String tag, String message, Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(Log.WARN, tag, createMessage(message, args));
        }
    }

    public static void iTag(String tag, String message, Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(Log.INFO, tag, createMessage(message, args));
        }
    }

    public static void vTag(String tag, String message, Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(Log.VERBOSE, tag, createMessage(message, args));
        }
    }

    public static void wtfTag(String tag, String message, Object... args) {
        if (JUDGE_PRINT_LOG) {
            printLog(Log.ASSERT, tag, createMessage(message, args));
        }
    }

    public static void jsonTag(String tag, String json) {
        if (JUDGE_PRINT_LOG) {
            // 判断传入JSON格式信息是否为null
            if (TextUtils.isEmpty(json)) {
                printLog(Log.ERROR, tag, "Empty/Null json content");
                return;
            }
            try {
                // 属于对象的JSON格式信息
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    // 进行缩进
                    String message = jsonObject.toString(JSON_INDENT);
                    // 打印信息
                    printLog(Log.DEBUG, tag, message);
                } else if (json.startsWith("[")) {
                    // 属于数据的JSON格式信息
                    JSONArray jsonArray = new JSONArray(json);
                    // 进行缩进
                    String message = jsonArray.toString(JSON_INDENT);
                    // 打印信息
                    printLog(Log.DEBUG, tag, message);
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
                printLog(Log.ERROR, tag, eHint + "\n" + json);
            }
        }
    }

    public static void xmlTag(String tag, String xml) {
        if (JUDGE_PRINT_LOG) {
            // 判断传入XML格式信息是否为null
            if (TextUtils.isEmpty(xml)) {
                printLog(Log.ERROR, tag, "Empty/Null xml content");
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
                printLog(Log.DEBUG, tag, message);
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
                printLog(Log.ERROR, tag, eHint + "\n" + xml);
            }
        }
    }
}
