package dev.utils.common;

import java.io.File;

import dev.utils.DevFinal;

/**
 * detail: 文件记录工具类
 * @author Ttt
 */
public final class FileRecordUtils {

    private FileRecordUtils() {
    }

    // 插入信息 ( 如设备信息等 )
    private static String   INSERT_INFO     = null;
    // 文件记录回调
    private static Callback RECORD_CALLBACK = null;

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 设置插入信息
     * @param insertInfo 插入信息 ( 如设备信息等 )
     */
    public static void setInsertInfo(final String insertInfo) {
        INSERT_INFO = insertInfo;
    }

    /**
     * 设置文件记录回调
     * @param callback {@link Callback}
     */
    public static void setCallback(final Callback callback) {
        RECORD_CALLBACK = callback;
    }

    // ===========
    // = 异常日志 =
    // ===========

    /**
     * 保存异常日志
     * @param ex       错误信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(
            final Throwable ex,
            final String filePath,
            final String fileName
    ) {
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
    public static boolean saveErrorLog(
            final Throwable ex,
            final String filePath,
            final String fileName,
            final String head,
            final String bottom
    ) {
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
    public static boolean saveErrorLog(
            final Throwable ex,
            final String filePath,
            final String fileName,
            final boolean printInsertInfo
    ) {
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
    public static boolean saveErrorLog(
            final Throwable ex,
            final String filePath,
            final String fileName,
            final String head,
            final String bottom,
            final boolean printInsertInfo
    ) {
        return saveLog(ThrowableUtils.getThrowableStackTrace(ex, "failed to get exception information"),
                filePath, fileName, head, bottom, printInsertInfo);
    }

    // =======
    // = 日志 =
    // =======

    /**
     * 保存日志
     * @param log      日志信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(
            final String log,
            final String filePath,
            final String fileName
    ) {
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
    public static boolean saveLog(
            final String log,
            final String filePath,
            final String fileName,
            final String head,
            final String bottom
    ) {
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
    public static boolean saveLog(
            final String log,
            final String filePath,
            final String fileName,
            final boolean printInsertInfo
    ) {
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
    public static boolean saveLog(
            final String log,
            final String filePath,
            final String fileName,
            final String head,
            final String bottom,
            final boolean printInsertInfo
    ) {
        // 日志拼接
        StringBuilder builder = new StringBuilder();
        // 保存时间
        builder.append(DevFinal.NEW_LINE_STR_X2);
        builder.append("date: ").append(DateUtils.getDateNow());
        builder.append(DevFinal.NEW_LINE_STR_X2);
        builder.append("===========================");

        // 如果存在顶部内容, 则进行添加
        if (!StringUtils.isEmpty(head)) {
            builder.append(DevFinal.NEW_LINE_STR_X2);
            builder.append(head);
            builder.append(DevFinal.NEW_LINE_STR_X2);
            builder.append("===========================");
        }
        // 是否需要插入数据
        if (printInsertInfo && !StringUtils.isEmpty(INSERT_INFO)) {
            builder.append(DevFinal.NEW_LINE_STR_X2);
            builder.append(INSERT_INFO);
            builder.append(DevFinal.NEW_LINE_STR_X2);
            builder.append("===========================");
        }
        // 保存日志信息
        builder.append(DevFinal.NEW_LINE_STR_X2);
        builder.append(log);
        builder.append(DevFinal.NEW_LINE_STR_X2);
        // 如果存在顶部内容, 则进行添加
        if (!StringUtils.isEmpty(bottom)) {
            builder.append("===========================");
            builder.append(DevFinal.NEW_LINE_STR_X2);
            builder.append(bottom);
            builder.append(DevFinal.NEW_LINE_STR_X2);
        }
        File   file    = FileUtils.getFile(filePath, fileName);
        String content = builder.toString();
        // 保存日志到文件
        boolean result = FileUtils.saveFile(file, StringUtils.getBytes(content));
        // 触发回调
        if (RECORD_CALLBACK != null) {
            RECORD_CALLBACK.callback(result, log, filePath, fileName, head, bottom, printInsertInfo, INSERT_INFO);
        }
        // 返回保存结果
        return result;
    }

    // ===========
    // = 接口回调 =
    // ===========

    /**
     * detail: 文件记录回调
     * @author Ttt
     */
    public interface Callback {

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
        void callback(
                boolean result,
                String log,
                String filePath,
                String fileName,
                String head,
                String bottom,
                boolean printInsertInfo,
                String insertInfo
        );
    }
}