package com.dev.use.record;

import com.dev.use.Config;

import java.io.File;

import dev.utils.app.AnalysisRecordUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.ErrorUtils;
import dev.utils.app.FileRecordUtils;
import dev.utils.app.SDCardUtils;
import dev.utils.app.logger.DevLoggerUtils;

/**
 * detail: 日志、异常文件记录保存使用方法
 * Created by Ttt
 */
class FileRecordUse {

    // 日志TAG
    private static final String TAG = FileRecordUse.class.getSimpleName();

    /**
     * 日志、异常文件记录保存使用方法
     */
    private void fileRecordUse() {
        // AnalysisRecordUtils

        // DevLoggerUtils

        // FileRecordUtils

        // DevLoggerUtils => 内部的 Utils, 实际和 FileRecordUtils 代码相同, 使用方式一致

        // == 记录文件 ==

        // AnalysisRecordUtils 工具类使用方法
        analysisRecord();

        // DevLoggerUtils、FileRecordUtils 工具类
        logRecord();
    }

    /** 日志文件夹路径 */
    public static final String LOG_SD_PATH = Config.SDP_PATH + File.separator + "Logger" + File.separator;

    /**
     * AnalysisRecordUtils 工具类使用方法
     */
    private void analysisRecord() {

        // 默认存储到 android/data/包名/cache文件/ , 可以自己特殊设置
//        AnalysisRecordUtils.setLogStoragePath(SDCardUtils.getSDCardPath());

        // 设置存储文件夹名
        AnalysisRecordUtils.setLogFolderName(AnalysisRecordUtils.getLogFolderName() + "/v" + AppUtils.getAppVersionName());

        // AnalysisRecordUtils.HH、MM、SS => 以对应的时间, 创建文件夹 HH_23/MM_13/SS_01 依此类推，放到对应文件夹, 不传则放到当日文件夹下
        AnalysisRecordUtils.FileInfo fileInfo = AnalysisRecordUtils.FileInfo.obtain("test_log.txt", "测试记录", AnalysisRecordUtils.HH);

//        // 存储路径、存储文件夹、文件名、记录功能提示、时间间隔
//        // FileInfo(String storagePath, String folderName, String fileName, String fileFunction, @AnalysisRecordUtils.TIME int fileIntervalTime)
//
//        // ==
//
//        AnalysisRecordUtils.FileInfo.obtain("test_log.txt", "测试记录");
//
//        AnalysisRecordUtils.FileInfo.obtain("TempRecord","test_log.txt", "测试记录");
//
//        AnalysisRecordUtils.FileInfo.obtain(SDCardUtils.getSDCardPath(),"TempRecord","test_log.txt", "测试记录");
//
//        // ==
//
//        AnalysisRecordUtils.FileInfo.obtain("test_log.txt", "测试记录", AnalysisRecordUtils.HH);
//
//        AnalysisRecordUtils.FileInfo.obtain("TempRecord","test_log.txt", "测试记录", AnalysisRecordUtils.MM);
//
//        AnalysisRecordUtils.FileInfo.obtain(SDCardUtils.getSDCardPath(),"TempRecord","test_log.txt", "测试记录", AnalysisRecordUtils.SS);

        // =============================

        // 存储到 android/data/包名/LogFolderName/2018-08-23/LogFolderName/xxx/log.txt
        AnalysisRecordUtils.record(fileInfo, "急哦撒点娇阿什库大街阿奎罗圣诞节几点佛山的金佛i适当放宽就是可怜的");

        // 存储到 sdcard/LogFolderName/2018-08-23/SDRecord/xxx/log.txt
        AnalysisRecordUtils.record(AnalysisRecordUtils.FileInfo.obtain(SDCardUtils.getSDCardPath(),"SDRecord","sd_log.txt", "根目录保存", AnalysisRecordUtils.HH),
                "奇葩奇葩奇葩撒开排名第");

        // 存储到 sdcard/特殊地址/LogFolderName/2018-08-23/OtherRecord/xxx/log.txt
        AnalysisRecordUtils.record(AnalysisRecordUtils.FileInfo.obtain(SDCardUtils.getSDCardPath() + "/特殊地址","OtherRecord","log.txt", "临时地址", AnalysisRecordUtils.HH),
                "手机发的啥地方加快速度加快");

        // =

        // 保存错误信息
        NullPointerException nullPointerException = new NullPointerException("报错啦， null 异常啊");
        // 记录日志
        AnalysisRecordUtils.record(fileInfo, ErrorUtils.getThrowableMsg(nullPointerException));
    }

    /**
     * DevLoggerUtils、FileRecordUtils 工具类
     */
    private void logRecord() {
        // = 异常日志保存 =

        try {
            String s = null;
            s.indexOf("c");
        } catch (NullPointerException e) {
            // 保存的路径
            String fName = LOG_SD_PATH + System.currentTimeMillis() + ".log";
            // 保存日志信息
            DevLoggerUtils.saveErrorLog(e, fName, true);
            // --
            // 保存自定义头部、底部信息
            DevLoggerUtils.saveErrorLog(e, "头部", "底部", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", true);
            // --
            // 自定义(无设备信息、失败信息获取失败) - 正常不会出现，所以其实这个可以不用
            String[] eHint = new String[]{"DeviceInfo = 获取设备信息失败", "获取失败"};
            // 保存的路径
            fName = LOG_SD_PATH + System.currentTimeMillis() + "_orgs.log";
            // 保存日志信息
            DevLoggerUtils.saveErrorLog(e, fName, true, eHint);

            // ==  FileRecordUtils 使用方法 ==

            FileRecordUtils.saveErrorLog(e, "头部", "底部", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", true, true, "xaskdjaslkd");

            FileRecordUtils.saveLog("撒开多久阿什利", "头部", "底部", LOG_SD_PATH, System.currentTimeMillis() + "_存在头部_底部.log", true, "qqqqweqweqwe");
        }
    }

}
