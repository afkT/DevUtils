package dev.utils.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dev.utils.JCLogUtils;

/**
 * detail: 文件记录工具类
 * @author Ttt
 */
public final class FileRecordUtils {

    private FileRecordUtils() {
    }

    // 日志 TAG
    private static final String TAG = FileRecordUtils.class.getSimpleName();

    // ============
    // = 配置信息 =
    // ============

    // 插入信息 ( 如设备信息等 )
    private static String INSERT_INFO = null;
    // 文件记录回调
    private static CallBack RECORD_CALLBACK = null;
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");
    // 换行字符串 ( 两行 )
    private static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 设置插入信息
     * @param insertInfo 插入信息 ( 如设备信息等 )
     */
    public static void setInsertInfo(final String insertInfo) {
        INSERT_INFO = insertInfo;
    }

    /**
     * 设置文件记录回调
     * @param callBack {@link CallBack}
     */
    public static void setCallBack(final CallBack callBack) {
        RECORD_CALLBACK = callBack;
    }

    // ============
    // = 异常日志 =
    // ============

    /**
     * 保存异常日志
     * @param ex       错误信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String filePath, final String fileName) {
        return saveErrorLog(ex, filePath, fileName, null, null, true);
    }

    /**
     * 保存异常日志
     * @param ex       错误信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @param head     顶部标题
     * @param bottom   底部内容
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String filePath, final String fileName,
                                       final String head, final String bottom) {
        return saveErrorLog(ex, filePath, fileName, head, bottom, true);
    }

    /**
     * 保存异常日志
     * @param ex              错误信息
     * @param filePath        保存路径
     * @param fileName        文件名 ( 含后缀 )
     * @param printInsertInfo 是否打印插入信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String filePath, final String fileName,
                                       final boolean printInsertInfo) {
        return saveErrorLog(ex, filePath, fileName, null, null, printInsertInfo);
    }

    /**
     * 保存异常日志
     * @param ex              错误信息
     * @param filePath        保存路径
     * @param fileName        文件名 ( 含后缀 )
     * @param head            顶部标题
     * @param bottom          底部内容
     * @param printInsertInfo 是否打印插入信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String filePath, final String fileName,
                                       final String head, final String bottom, final boolean printInsertInfo) {
        return saveLog(getThrowableStackTrace(ex, "failed to get exception information"),
                filePath, fileName, head, bottom, printInsertInfo);
    }

    // ========
    // = 日志 =
    // ========

    /**
     * 保存日志
     * @param log      日志信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath, final String fileName) {
        return saveLog(log, filePath, fileName, null, null, true);
    }

    /**
     * 保存日志
     * @param log      日志信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @param head     顶部标题
     * @param bottom   底部内容
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath, final String fileName,
                                  final String head, final String bottom) {
        return saveLog(log, filePath, fileName, head, bottom, true);
    }

    // =

    /**
     * 保存日志
     * @param log             日志信息
     * @param filePath        保存路径
     * @param fileName        文件名 ( 含后缀 )
     * @param printInsertInfo 是否打印插入信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath, final String fileName,
                                  final boolean printInsertInfo) {
        return saveLog(log, filePath, fileName, null, null, printInsertInfo);
    }

    /**
     * 保存日志
     * @param log             日志信息
     * @param filePath        保存路径
     * @param fileName        文件名 ( 含后缀 )
     * @param head            顶部标题
     * @param bottom          底部内容
     * @param printInsertInfo 是否打印插入信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath, final String fileName,
                                  final String head, final String bottom, final boolean printInsertInfo) {
        // 日志拼接
        StringBuilder builder = new StringBuilder();
        // 保存时间
        builder.append(NEW_LINE_STR_X2);
        builder.append("date: " + getDateNow());
        builder.append(NEW_LINE_STR_X2);
        builder.append("===========================");

        // 如果存在顶部内容, 则进行添加
        if (!isEmpty(head)) {
            builder.append(NEW_LINE_STR_X2);
            builder.append(head);
            builder.append(NEW_LINE_STR_X2);
            builder.append("===========================");
        }
        // 是否需要插入数据
        if (printInsertInfo && !isEmpty(INSERT_INFO)) {
            builder.append(NEW_LINE_STR_X2);
            builder.append(INSERT_INFO);
            builder.append(NEW_LINE_STR_X2);
            builder.append("===========================");
        }
        // 保存日志信息
        builder.append(NEW_LINE_STR_X2);
        builder.append(log);
        builder.append(NEW_LINE_STR_X2);
        // 如果存在顶部内容, 则进行添加
        if (!isEmpty(bottom)) {
            builder.append("===========================");
            builder.append(NEW_LINE_STR_X2);
            builder.append(bottom);
            builder.append(NEW_LINE_STR_X2);
        }
        // 保存日志到文件
        boolean result = saveFile(filePath, fileName, builder.toString());
        // 触发回调
        if (RECORD_CALLBACK != null) {
            RECORD_CALLBACK.callback(result, log, filePath, fileName, head, bottom, printInsertInfo, INSERT_INFO);
        }
        // 返回保存结果
        return result;
    }

    // ============
    // = 接口回调 =
    // ============

    /**
     * detail: 文件记录回调
     * @author Ttt
     */
    public interface CallBack {

        /**
         * 记录结果回调
         * @param result          保存结果
         * @param log             日志信息
         * @param filePath        保存路径
         * @param fileName        文件名 ( 含后缀 )
         * @param head            顶部标题
         * @param bottom          底部内容
         * @param printInsertInfo 是否打印插入信息
         * @param insertInfo      插入信息
         */
        void callback(boolean result, final String log, final String filePath, final String fileName,
                      final String head, final String bottom, final boolean printInsertInfo, final String insertInfo);
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // =============
    // = FileUtils =
    // =============

    /**
     * 保存文件
     * @param filePath 保存路径
     * @param fileName 文件名. 后缀
     * @param content  保存内容
     * @return {@code true} success, {@code false} fail
     */
    private static boolean saveFile(final String filePath, final String fileName, final String content) {
        if (filePath != null && fileName != null && content != null) {
            try {
                // 防止文件没创建
                createFolder(filePath);
                // 保存路径
                File file = new File(filePath, fileName);
                // 保存内容到一个文件
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                fos.close();
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "saveFile");
            }
        }
        return false;
    }

    /**
     * 获取文件
     * @param filePath 文件路径
     * @return 文件 {@link File}
     */
    private static File getFileByPath(final String filePath) {
        return filePath != null ? new File(filePath) : null;
    }

    /**
     * 判断某个文件夹是否创建, 未创建则创建 ( 纯路径 - 无文件名 )
     * @param dirPath 文件夹路径 ( 无文件名字. 后缀 )
     * @return {@code true} success, {@code false} fail
     */
    private static boolean createFolder(final String dirPath) {
        return createFolder(getFileByPath(dirPath));
    }

    /**
     * 判断某个文件夹是否创建, 未创建则创建 ( 纯路径 - 无文件名 )
     * @param file 文件夹路径 ( 无文件名字. 后缀 )
     * @return {@code true} success, {@code false} fail
     */
    private static boolean createFolder(final File file) {
        if (file != null) {
            try {
                // 当这个文件夹不存在的时候则创建文件夹
                if (!file.exists()) {
                    // 允许创建多级目录
                    return file.mkdirs();
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "createFolder");
            }
        }
        return false;
    }

    // ==================
    // = ThrowableUtils =
    // ==================

    /**
     * 获取异常栈信息
     * @param throwable 异常
     * @param errorInfo 获取失败返回字符串
     * @return 异常栈信息字符串
     */
    private static String getThrowableStackTrace(final Throwable throwable, final String errorInfo) {
        if (throwable != null) {
            PrintWriter printWriter = null;
            try {
                Writer writer = new StringWriter();
                printWriter = new PrintWriter(writer);
                throwable.printStackTrace(printWriter);
                return writer.toString();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getThrowableStackTrace");
                return e.toString();
            } finally {
                if (printWriter != null) {
                    try {
                        printWriter.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return errorInfo;
    }

    // =============
    // = DateUtils =
    // =============

    /**
     * 获取当前日期的字符串
     * @return 当前日期 yyyy-MM-dd HH:mm:ss 格式字符串
     */
    private static String getDateNow() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        } catch (Exception e) {
        }
        return null;
    }

    // ===============
    // = StringUtils =
    // ===============

    /**
     * 判断字符串是否为 null
     * @param str 待校验的字符串
     * @return {@code true} is null, {@code false} not null
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }
}