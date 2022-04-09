package utils_use.record;

import dev.utils.app.PathUtils;
import dev.utils.common.assist.record.FileRecordUtils;
import dev.utils.common.assist.record.RecordConfig;

/**
 * detail: 日志、异常文件记录保存使用方法
 * @author Ttt
 */
public final class FileRecordUse {

    private FileRecordUse() {
    }

    /**
     * 日志、异常文件记录保存使用方法
     */
    public static void fileRecordUse() {
        String storagePath = PathUtils.getAppExternal().getAppCachePath();

        // 创建文件夹 ( 以秒为存储单位 ) 创建如: HH_23/MM_13/SS_01 对应文件夹, 并存储到该目录下
        RecordConfig config = RecordConfig.get(storagePath, "Main_Module", RecordConfig.TIME.HH);

        // 创建文件夹 ( 以小时为存储单位 ) 创建如: HH_23 对应文件夹, 并存储到该目录下
        RecordConfig config2 = RecordConfig.get(storagePath, "User_Module", RecordConfig.TIME.HH);

        // 存储到 storagePath/FileRecord/yyyy_MM_dd/FolderName/HH_number/MM_number/SS_number/ 内
        FileRecordUtils.record(config, "日志内容");

        // 保存错误信息
        NullPointerException nullPointerException = new NullPointerException("报错啦, null 异常啊");

        // 单独异常
        FileRecordUtils.record(config2, nullPointerException);

        // 异常 + 日志
        FileRecordUtils.record(config2, "第一个日志内容", nullPointerException, "其他日志内容");
    }
}