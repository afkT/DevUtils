package utils_use.record;

import java.io.File;

import afkt.project.base.config.PathConfig;
import dev.utils.app.AnalysisRecordUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.DeviceUtils;
import dev.utils.app.PathUtils;
import dev.utils.common.FileRecordUtils;
import dev.utils.common.ThrowableUtils;

/**
 * detail: 日志、异常文件记录保存使用方法
 * @author Ttt
 */
public final class FileRecordUse {

    private FileRecordUse() {
    }

    // 日志 TAG
    private static final String TAG = FileRecordUse.class.getSimpleName();

    /**
     * 日志、异常文件记录保存使用方法
     */
    public static void fileRecordUse() {

        // AnalysisRecordUtils

        // FileRecordUtils

        // = 记录文件 =

        // AnalysisRecordUtils 工具类使用方法
        analysisRecord();

        // FileRecordUtils 工具类
        fileRecord();
    }

    /**
     * 日志文件夹路径
     */
    public static final String LOG_SD_PATH = PathConfig.AEP_PATH + File.separator + "Logger" + File.separator;

    /**
     * AnalysisRecordUtils 工具类使用方法
     */
    public static void analysisRecord() {
        AnalysisRecordUtils.setLogFolderName("LogRecord");

        // 默认存储到 android/data/包名/cache文件/, 可以自己特殊设置
        AnalysisRecordUtils.setLogStoragePath(PathUtils.getSDCard().getSDCardPath());

        // 设置存储文件夹名
        AnalysisRecordUtils.setLogFolderName(AnalysisRecordUtils.getLogFolderName() + "/v" + AppUtils.getAppVersionName());

        // AnalysisRecordUtils.HH、MM、SS 以对应的时间, 创建文件夹 HH_23/MM_13/SS_01 依此类推, 放到对应文件夹, 不传则放到当日文件夹下
        AnalysisRecordUtils.FileInfo fileInfo = AnalysisRecordUtils.FileInfo.get("test_log.txt", "测试记录", AnalysisRecordUtils.HH);

        // 存储路径、存储文件夹、文件名、记录功能提示、时间间隔、是否处理日志记录(是否保存)
        // FileInfo(String storagePath, String folderName, String fileName, String fileFunction, @AnalysisRecordUtils.TIME int fileIntervalTime, boolean handler)

        // = FileInfo 配置 =

        fileInfo = AnalysisRecordUtils.FileInfo.get("test_log.txt", "测试记录");

        fileInfo = AnalysisRecordUtils.FileInfo.get("TempRecord", "test_log.txt", "测试记录");

        fileInfo = AnalysisRecordUtils.FileInfo.get(PathUtils.getSDCard().getSDCardPath(), "TempRecord", "test_log.txt", "测试记录");

        // =

        fileInfo = AnalysisRecordUtils.FileInfo.get("test_log.txt", "测试记录", AnalysisRecordUtils.HH);

        fileInfo = AnalysisRecordUtils.FileInfo.get("TempRecord", "test_log.txt", "测试记录", AnalysisRecordUtils.MM);

        fileInfo = AnalysisRecordUtils.FileInfo.get(PathUtils.getSDCard().getSDCardPath(), "TempRecord", "test_log.txt", "测试记录", AnalysisRecordUtils.SS);

        // =

        // 存储到 android/data/包名/LogFolderName/2018-08-23/LogFolderName/xxx/log.txt
        AnalysisRecordUtils.record(fileInfo, "日志内容");

        // 存储到 sdcard/LogFolderName/2018-08-23/SDRecord/xxx/log.txt
        AnalysisRecordUtils.record(AnalysisRecordUtils.FileInfo.get(PathUtils.getSDCard().getSDCardPath(), "SDRecord", "sd_log.txt", "根目录保存", AnalysisRecordUtils.HH),
                "日志内容");

        // 存储到 sdcard/特殊地址/LogFolderName/2018-08-23/OtherRecord/xxx/log.txt
        AnalysisRecordUtils.record(AnalysisRecordUtils.FileInfo.get(PathUtils.getSDCard().getSDCardPath() + "/特殊地址", "OtherRecord",
                "log.txt", "临时地址", AnalysisRecordUtils.HH), "日志内容");

        // 保存错误信息
        NullPointerException nullPointerException = new NullPointerException("报错啦, null 异常啊");
        // 记录日志
        AnalysisRecordUtils.record(fileInfo, ThrowableUtils.getThrowable(nullPointerException));
    }

    /**
     * FileRecordUtils 工具类
     */
    public static void fileRecord() {
        try {
            String s = null;
            s.indexOf('c');
        } catch (NullPointerException e) {

            // 设置插入信息
            FileRecordUtils.setInsertInfo(DeviceUtils.getAppDeviceInfo());

            FileRecordUtils.saveErrorLog(e, LOG_SD_PATH, System.currentTimeMillis() + ".log");

            FileRecordUtils.saveErrorLog(e, LOG_SD_PATH, System.currentTimeMillis() + ".log", false);

            FileRecordUtils.saveErrorLog(e, LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", "头部", "底部", true);

            FileRecordUtils.saveLog("日志内容", LOG_SD_PATH, System.currentTimeMillis() + ".log");

            FileRecordUtils.saveLog("日志内容", LOG_SD_PATH, System.currentTimeMillis() + ".log", false);

            FileRecordUtils.saveLog("日志内容", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", "头部", "底部", true);
        }
    }
}