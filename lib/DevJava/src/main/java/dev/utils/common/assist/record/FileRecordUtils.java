package dev.utils.common.assist.record;

import dev.utils.DevFinal;
import dev.utils.common.DateUtils;

/**
 * detail: 文件记录分析工具类
 * @author Ttt
 */
public final class FileRecordUtils {

    private FileRecordUtils() {
    }

    // 日志 TAG
    private static final String   TAG          = FileRecordUtils.class.getSimpleName();
    // 是否处理保存
    private static       boolean  sHandler     = true;
    // 判断是否加空格
    private static       boolean  sAppendSpace = true;
    // 文件记录回调
    private static       Callback CALLBACK     = null;

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
                String... logs
        );
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 拼接日志
     * @param logs 日志内容数组
     * @return 拼接后的日志内容
     */
    private static String splitLog(final String... logs) {
        StringBuilder builder = new StringBuilder();

//        if () {
//
//        }
                // 增加换行
        builder.append(DevFinal.NEW_LINE_STR_X2)
                // 获取保存时间
                .append(DateUtils.getDateNow())
                // 追加边距
                .append(" => ");

        // 判断是否追加空格
        boolean isSpace = sAppendSpace;
        // 是否添加空格 ( 第一位不添加空格 )
        boolean isAdd = false;
        // 循环追加内容
        for (int i = 0, len = logs.length; i < len; i++) {
            if (isSpace && isAdd) { // 判断是否追加空格
                builder.append(" ");
            }
            // 标记添加空格 ( 第一位不添加空格 )
            isAdd = true;
            // 追加存储内容
            builder.append(logs[i]);
        }
        return builder.toString();
    }
}