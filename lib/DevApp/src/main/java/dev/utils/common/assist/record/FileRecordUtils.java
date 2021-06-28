package dev.utils.common.assist.record;

import java.io.File;

import dev.utils.DevFinal;
import dev.utils.common.ConvertUtils;
import dev.utils.common.DateUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.ThrowableUtils;

/**
 * detail: 文件记录分析工具类
 * @author Ttt
 */
public final class FileRecordUtils {

    private FileRecordUtils() {
    }

    // 成功常量字符串
    private static final String       RECORD_SUCCESS = "record successful";
    // 是否处理记录
    private static       boolean      sHandler       = true;
    // 日志记录插入信息
    private static       RecordInsert sRecordInsert  = null;
    // 文件记录回调
    private static       Callback     sCallback      = null;

    // ==========
    // = 接口回调 =
    // ==========

    /**
     * detail: 文件记录回调
     * @author Ttt
     */
    public interface Callback {

        /**
         * 记录结果回调
         * @param result     保存结果
         * @param config     日志记录配置信息
         * @param filePath   存储路径
         * @param fileName   文件名 ( 含后缀 )
         * @param logContent 日志信息
         * @param logs       原始日志内容数组
         */
        void callback(
                boolean result,
                RecordConfig config,
                String filePath,
                String fileName,
                String logContent,
                Object... logs
        );
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 拼接插入日志
     * @param recordInsert 日志记录插入信息
     * @param logContent   日志内容
     * @return 最终日志信息
     */
    private static String concatInsertLog(
            final RecordInsert recordInsert,
            final String logContent
    ) {
        if (recordInsert == null) {
            return logContent;
        }
        StringBuilder builder = new StringBuilder();
        // 追加头部信息
        if (StringUtils.isNotEmpty(recordInsert.getLogHeader())) {
            builder.append(recordInsert.getLogHeader());
        }
        // 加入日志内容
        builder.append(logContent);
        // 追加尾部信息
        if (StringUtils.isNotEmpty(recordInsert.getLogTail())) {
            builder.append(recordInsert.getLogTail());
        }
        return builder.toString();
    }

    /**
     * 拼接日志
     * @param logs 日志内容数组
     * @return 拼接后的日志内容
     */
    private static String concatLog(final Object... logs) {
        if (logs == null || logs.length == 0) return null;
        StringBuilder builder = new StringBuilder()
                // 增加换行
                .append(DevFinal.NEW_LINE_STR_X2)
                // 获取保存时间
                .append(DateUtils.getDateNow())
                // 追加边距、换行
                .append(" =>");
        // 循环追加内容
        for (int i = 0, len = logs.length; i < len; i++) {
            // 追加存储内容
            builder.append(DevFinal.NEW_LINE_STR_X2)
                    .append("logs[").append(i).append("]: ")
                    .append(DevFinal.NEW_LINE_STR);

            Object object = logs[i];
            if (object instanceof Throwable) {
                String errorLog = ThrowableUtils.getThrowableStackTrace((Throwable) object);
                builder.append(errorLog);
            } else {
                builder.append(ConvertUtils.toString(logs[i]));
            }
        }
        return builder.toString();
    }

    /**
     * 最终记录方法
     * @param config 日志记录配置信息
     * @param logs   日志内容数组
     * @return 记录结果提示
     */
    private static String finalRecord(
            final RecordConfig config,
            final Object... logs
    ) {
        // 判断全局是否处理
        if (!sHandler) return "global do not handle";
        // 判断配置是否为 null
        if (config == null) return "config is null";
        // 判断配置是否处理
        if (!config.isHandler()) return "config do not handle";
        // 判断是否存在日志内容
        if (logs == null || logs.length == 0) return "no data record";

        // 文件路径
        String filePath = config.getFinalPath();
        // 文件名
        String fileName = config.getFileName();
        // 文件路径、文件名为 null 则不处理
        if (StringUtils.isEmpty(filePath, fileName)) return "filePath is null";

        // 日志记录插入信息
        RecordInsert recordInsert = config.getRecordInsert(sRecordInsert);
        // 日志内容
        String logContent = concatLog(logs);
        // 拼接最终内容
        String finalLogContent = concatInsertLog(recordInsert, logContent);

        // 获取存储文件
        File file = FileUtils.getFile(filePath, fileName);
        // 文件不存在则进行追加文件信息
        if (!FileUtils.isFileExists(file)) {
            if (recordInsert != null) {
                String fileInfo = recordInsert.getFileInfo();
                if (fileInfo != null) {
                    // 文件信息 ( 一个文件只会添加一次文件信息, 且在最顶部 )
                    FileUtils.saveFile(file, StringUtils.getBytes(fileInfo));
                }
            }
        }
        // 追加日志内容
        boolean result = FileUtils.appendFile(file, StringUtils.getBytes(finalLogContent));

        if (sCallback != null) {
            sCallback.callback(result, config, filePath, fileName, finalLogContent, logs);
        }
        return result ? RECORD_SUCCESS : "record failed";
    }

    // =============
    // = 对外公开方法 =
    // =============

    // ===========
    // = get/set =
    // ===========

    /**
     * 校验记录方法返回字符串是否成功
     * @param value 待校验值
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSuccessful(final String value) {
        return RECORD_SUCCESS.equals(value);
    }

    /**
     * 是否处理记录
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHandler() {
        return sHandler;
    }

    /**
     * 设置是否处理记录
     * @param handler 是否处理记录
     */
    public static void setHandler(final boolean handler) {
        FileRecordUtils.sHandler = handler;
    }

    /**
     * 获取日志记录插入信息
     * @return 日志记录插入信息
     */
    public static RecordInsert getRecordInsert() {
        return sRecordInsert;
    }

    /**
     * 设置日志记录插入信息
     * @param recordInsert 日志记录插入信息
     */
    public static void setRecordInsert(final RecordInsert recordInsert) {
        FileRecordUtils.sRecordInsert = recordInsert;
    }

    /**
     * 设置文件记录回调
     * @param callback 文件记录回调
     */
    public static void setCallback(final Callback callback) {
        FileRecordUtils.sCallback = callback;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 获取日志内容
     * @param config 日志记录配置信息
     * @param logs   日志内容数组
     * @return 日志内容
     */
    public static String getLogContent(
            final RecordConfig config,
            final Object... logs
    ) {
        if (config != null) {
            return getLogContent(config.getRecordInsert(sRecordInsert), logs);
        }
        return getLogContent(sRecordInsert, logs);
    }

    /**
     * 获取日志内容
     * @param recordInsert 日志记录插入信息
     * @param logs         日志内容数组
     * @return 日志内容
     */
    public static String getLogContent(
            final RecordInsert recordInsert,
            final Object... logs
    ) {
        String logContent = concatLog(logs);
        if (StringUtils.isEmpty(logContent)) return null;
        return concatInsertLog(recordInsert, logContent);
    }

    // =

    /**
     * 记录方法
     * @param config 日志记录配置信息
     * @param logs   日志内容数组
     * @return 记录结果提示
     */
    public static String record(
            final RecordConfig config,
            final Object... logs
    ) {
        return finalRecord(config, logs);
    }
}