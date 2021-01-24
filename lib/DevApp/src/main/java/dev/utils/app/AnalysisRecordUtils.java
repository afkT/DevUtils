package dev.utils.app;

import android.text.TextUtils;

import androidx.annotation.IntDef;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.common.DateUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 日志记录分析工具类
 * @author Ttt
 */
public final class AnalysisRecordUtils {

    private AnalysisRecordUtils() {
    }

    // 日志 TAG
    private static final String TAG = AnalysisRecordUtils.class.getSimpleName();

    // 日志文件夹名字 ( 目录名 )
    private static String   sLogFolderName  = "LogRecord";
    // 日志存储路径
    private static String   sLogStoragePath;
    // 是否处理保存
    private static boolean  sIsHandler      = true;
    // 判断是否加空格
    private static boolean  sAppendSpace    = true;
    // 文件记录回调
    private static Callback RECORD_CALLBACK = null;

    // ===========
    // = 配置信息 =
    // ===========

    // APP 版本名 ( 主要用于对用户显示版本信息 )
    private static       String              APP_VERSION_NAME = "";
    // APP 版本号
    private static       String              APP_VERSION_CODE = "";
    // 应用包名
    private static       String              PACKAGE_NAME     = "";
    // 设备信息
    private static       String              DEVICE_INFO_STR  = null;
    // 设备信息存储 Map
    private static final Map<String, String> DEVICE_INFO_MAPS = new HashMap<>();

    /**
     * 初始化操作 ( 内部已调用 )
     */
    public static void init() {
        // 如果为 null, 才设置
        if (TextUtils.isEmpty(sLogStoragePath)) {
            // 获取根路径
            sLogStoragePath = PathUtils.getAppExternal().getAppCachePath();
        }

        // 如果版本信息为 null, 才进行处理
        if (TextUtils.isEmpty(APP_VERSION_CODE) || TextUtils.isEmpty(APP_VERSION_NAME)) {
            // 获取 APP 版本信息
            String[] versions = ManifestUtils.getAppVersion();
            // 防止为 null
            if (versions != null && versions.length == 2) {
                // 保存 APP 版本信息
                APP_VERSION_NAME = versions[0];
                APP_VERSION_CODE = versions[1];
            }
        }

        // 获取包名
        if (TextUtils.isEmpty(PACKAGE_NAME)) {
            PACKAGE_NAME = AppUtils.getPackageName();
        }

        // 判断是否存在设备信息
        if (DEVICE_INFO_MAPS.size() == 0) {
            // 获取设备信息
            DeviceUtils.getDeviceInfo(DEVICE_INFO_MAPS);
            // 转换字符串
            handlerDeviceInfo("");
        }
    }

    /**
     * 设置文件记录回调
     * @param callback {@link Callback}
     */
    public static void setCallback(final Callback callback) {
        RECORD_CALLBACK = callback;
    }

    // ===========
    // = 记录方法 =
    // ===========

    /**
     * 日志记录
     * @param fileInfo {@link FileInfo}
     * @param logs     日志内容数组
     * @return 日志内容
     */
    public static String record(
            final FileInfo fileInfo,
            final String... logs
    ) {
        // 如果不处理, 则直接跳过
        if (!sIsHandler) {
            return "do not process records";
        }
        if (fileInfo != null) {
            if (!fileInfo.isHandler()) {
                return "file not recorded";
            }
            if (logs != null && logs.length != 0) {
                return saveLogRecord(fileInfo, logs);
            }
            // 无数据记录
            return "no data record";
        }
        // 信息为 null
        return "fileInfo is null";
    }

    // =================
    // = 判断、获取方法 =
    // =================

    /**
     * 判断是否处理日志记录
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHandler() {
        return sIsHandler;
    }

    /**
     * 设置是否处理日志记录
     * @param handler 是否处理日志
     */
    public static void setHandler(final boolean handler) {
        AnalysisRecordUtils.sIsHandler = handler;
    }

    /**
     * 判断是否追加空格
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppendSpace() {
        return sAppendSpace;
    }

    /**
     * 设置是否追加空格
     * @param appendSpace 是否追加空格
     */
    public static void setAppendSpace(final boolean appendSpace) {
        AnalysisRecordUtils.sAppendSpace = appendSpace;
    }

    /**
     * 获取日志文件名
     * @return 日志文件名
     */
    public static String getLogFolderName() {
        return sLogFolderName;
    }

    /**
     * 设置日志文件夹名
     * @param logFolderName 日志文件夹名
     */
    public static void setLogFolderName(final String logFolderName) {
        AnalysisRecordUtils.sLogFolderName = logFolderName;
    }

    /**
     * 获取日志存储路径
     * @return 日志存储路径
     */
    public static String getLogStoragePath() {
        return sLogStoragePath;
    }

    /**
     * 设置日志存储路径
     * @param logStoragePath 日志存储路径
     */
    public static void setLogStoragePath(final String logStoragePath) {
        AnalysisRecordUtils.sLogStoragePath = logStoragePath;
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 最终保存方法
     * @param fileInfo {@link FileInfo}
     * @param logs     日志内容数组
     * @return 拼接后的日志内容
     */
    private static String saveLogRecord(
            final FileInfo fileInfo,
            final String... logs
    ) {
        // 如果不处理, 则直接跳过
        if (!sIsHandler) return "do not process records";
        // 文件信息为 null, 则不处理
        if (fileInfo == null) return "fileInfo is null";
        // 如果文件地址为 null, 则不处理
        if (TextUtils.isEmpty(fileInfo.getFileName())) return "fileName is null";
        // 获取文件名
        String fileName = fileInfo.getFileName();
        // 获取文件提示
        String fileHint = fileInfo.getFileFunction();
        try {
            // 操作结果
            boolean result;
            // 获取处理的日志
            String logContent = splitLog(logs);
            // 日志保存路径
            String logPath = fileInfo.getLogPath();
            // 获取日志地址
            File file = new File(logPath, fileName);
            // 判断是否存在
            if (file.exists()) {
                result = FileUtils.appendFile(file, StringUtils.getBytes(logContent));
            } else {
                // = 首次则保存设备、APP 信息 =
                StringBuilder builder = new StringBuilder();
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("[设备信息]");
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append(handlerDeviceInfo("failed to get device information"));
                builder.append(DevFinal.NEW_LINE_STR);
                builder.append("===========================");
                builder.append(DevFinal.NEW_LINE_STR_X2);

                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("[版本信息]");
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("versionName: ").append(APP_VERSION_NAME);
                builder.append(DevFinal.NEW_LINE_STR);
                builder.append("versionCode: ").append(APP_VERSION_CODE);
                builder.append(DevFinal.NEW_LINE_STR);
                builder.append("package: ").append(PACKAGE_NAME);
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(DevFinal.NEW_LINE_STR_X2);

                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("[文件信息]");
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append(fileHint);
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(DevFinal.NEW_LINE_STR_X2);

                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("[日志内容]");
                builder.append(DevFinal.NEW_LINE_STR_X2);
                builder.append("===========================");
                // 创建文件夹, 并且进行处理
                FileUtils.saveFile(file, StringUtils.getBytes(builder.toString()));
                // 追加内容
                result = FileUtils.appendFile(file, StringUtils.getBytes(logContent));
            }
            // 触发回调
            if (RECORD_CALLBACK != null) {
                RECORD_CALLBACK.callback(result, fileInfo, logContent, logPath, fileName, logs);
            }
            // 返回打印日志
            return logContent;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveLogRecord");
            // 捕获异常
            return "catch exception";
        }
    }

    /**
     * 拼接日志
     * @param logs 日志内容数组
     * @return 拼接后的日志内容
     */
    private static String splitLog(final String... logs) {
        // 判断是否追加空格
        boolean isSpace = sAppendSpace;
        // =
        StringBuilder builder = new StringBuilder();
        // 增加换行
        builder.append(DevFinal.NEW_LINE_STR);
        builder.append(DevFinal.NEW_LINE_STR);
        // 获取保存时间
        builder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 追加边距
        builder.append(" => ");
        // 是否添加空格 ( 第一位不添加空格 )
        boolean isAdd = false;
        // 循环追加内容
        for (int i = 0, len = logs.length; i < len; i++) {
            if (isSpace && isAdd) { // 判断是否追加空格
                builder.append(" ");
            }
            // 标记添加空格 ( 第一位不添加空格 )
            isAdd = true;
            // 追加保存内容
            builder.append(logs[i]);
        }
        return builder.toString();
    }

    // ===============
    // = 日志保存时间 =
    // ===============

    // DEFAULT ( 默认天, 在根目录下 )
    public static final int DEFAULT = 0;
    // 小时
    public static final int HH      = 1;
    // 分钟
    public static final int MM      = 2;
    // 秒
    public static final int SS      = 3;

    @IntDef({DEFAULT, HH, MM, SS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TIME {
    }

    /**
     * detail: 记录文件信息实体类
     * @author Ttt
     */
    public static class FileInfo {

        // 存储路径
        private String storagePath;

        // 文件夹名
        private String folderName;

        // 文件名 如: xxx.txt
        private final String fileName;

        // 文件记录的功能
        private final String fileFunction;

        // 文件记录间隔时间 如: HH
        private final int fileIntervalTime;

        // 是否处理日志记录
        private boolean handler;

        // ===========
        // = 构造函数 =
        // ===========

        /**
         * 构造函数
         * @param storagePath      存储路径
         * @param folderName       文件夹名
         * @param fileName         文件名
         * @param fileFunction     文件记录的功能
         * @param fileIntervalTime 文件记录间隔时间
         * @param handler          是否处理日志记录
         */
        private FileInfo(
                final String storagePath,
                final String folderName,
                final String fileName,
                final String fileFunction,
                @TIME final int fileIntervalTime,
                final boolean handler
        ) {
            this.storagePath = storagePath;
            this.folderName = folderName;
            this.fileName = fileName;
            this.fileFunction = fileFunction;
            this.fileIntervalTime = fileIntervalTime;
            this.handler = handler;
        }

        // =

        /**
         * 获取日志记录分析文件对象
         * @param fileName     文件名
         * @param fileFunction 日志文件记录功能
         * @return {@link FileInfo}
         */
        public static FileInfo get(
                final String fileName,
                final String fileFunction
        ) {
            return new FileInfo(null, null, fileName, fileFunction, DEFAULT, true);
        }

        /**
         * 获取日志记录分析文件对象
         * @param folderName   日志文件名
         * @param fileName     文件名
         * @param fileFunction 日志文件记录功能
         * @return {@link FileInfo}
         */
        public static FileInfo get(
                final String folderName,
                final String fileName,
                final String fileFunction
        ) {
            return new FileInfo(null, folderName, fileName, fileFunction, DEFAULT, true);
        }

        /**
         * 获取日志记录分析文件对象
         * @param storagePath  存储路径
         * @param folderName   日志文件名
         * @param fileName     文件名
         * @param fileFunction 日志文件记录功能
         * @return {@link FileInfo}
         */
        public static FileInfo get(
                final String storagePath,
                final String folderName,
                final String fileName,
                final String fileFunction
        ) {
            return new FileInfo(storagePath, folderName, fileName, fileFunction, DEFAULT, true);
        }

        // =

        /**
         * 获取日志记录分析文件对象
         * @param fileName         文件名
         * @param fileFunction     日志文件记录功能
         * @param fileIntervalTime 日志文件记录间隔时间
         * @return {@link FileInfo}
         */
        public static FileInfo get(
                final String fileName,
                final String fileFunction,
                @TIME final int fileIntervalTime
        ) {
            return new FileInfo(null, null, fileName, fileFunction, fileIntervalTime, true);
        }

        /**
         * 获取日志记录分析文件对象
         * @param folderName       日志文件名
         * @param fileName         文件名
         * @param fileFunction     日志文件记录功能
         * @param fileIntervalTime 日志文件记录间隔时间
         * @return {@link FileInfo}
         */
        public static FileInfo get(
                final String folderName,
                final String fileName,
                final String fileFunction,
                @TIME final int fileIntervalTime
        ) {
            return new FileInfo(null, folderName, fileName, fileFunction, fileIntervalTime, true);
        }

        /**
         * 获取日志记录分析文件对象
         * @param storagePath      存储路径
         * @param folderName       日志文件名
         * @param fileName         文件名
         * @param fileFunction     日志文件记录功能
         * @param fileIntervalTime 日志文件记录间隔时间
         * @return {@link FileInfo}
         */
        public static FileInfo get(
                final String storagePath,
                final String folderName,
                final String fileName,
                final String fileFunction,
                @TIME final int fileIntervalTime
        ) {
            return new FileInfo(storagePath, folderName, fileName, fileFunction, fileIntervalTime, true);
        }

        /**
         * 获取日志记录分析文件对象
         * @param storagePath      存储路径
         * @param folderName       日志文件名
         * @param fileName         文件名
         * @param fileFunction     日志文件记录功能
         * @param fileIntervalTime 日志文件记录间隔时间
         * @param isHandler        是否处理日志记录
         * @return {@link FileInfo}
         */
        public static FileInfo get(
                final String storagePath,
                final String folderName,
                final String fileName,
                final String fileFunction,
                @TIME final int fileIntervalTime,
                final boolean isHandler
        ) {
            return new FileInfo(storagePath, folderName, fileName, fileFunction, fileIntervalTime, isHandler);
        }

        // ===========
        // = get/set =
        // ===========

        /**
         * 获取存储路径
         * @return 存储路径
         */
        public String getStoragePath() {
            if (TextUtils.isEmpty(storagePath)) {
                storagePath = getLogStoragePath();
            }
            return storagePath;
        }

        /**
         * 获取日志文件名
         * @return 日志文件名
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * 获取日志文件记录功能
         * @return 日志文件记录功能
         */
        public String getFileFunction() {
            return fileFunction;
        }

        /**
         * 获取日志文件记录间隔时间
         * @return 日志文件记录间隔时间
         */
        public int getFileIntervalTime() {
            return fileIntervalTime;
        }

        /**
         * 获取日志文件夹名
         * @return 日志文件夹名
         */
        public String getFolderName() {
            if (TextUtils.isEmpty(folderName)) {
                folderName = getLogFolderName();
            }
            return folderName;
        }

        /**
         * 判断是否处理日志记录
         * @return {@code true} yes, {@code false} no
         */
        public boolean isHandler() {
            return handler;
        }

        /**
         * 设置是否处理日志记录
         * @param handler 是否处理日志记录
         * @return {@link FileInfo}
         */
        public FileInfo setHandler(final boolean handler) {
            this.handler = handler;
            return this;
        }

        // ===============
        // = 内部处理方法 =
        // ===============

        /**
         * 获取日志文件地址
         * @return 日志文件地址
         */
        public String getLogPath() {
            // 返回拼接后的路径
            return FileUtils.getFilePathCreateFolder(getStoragePath(),
                    sLogFolderName + File.separator + DateUtils.getDateNow("yyyy_MM_dd")) + getIntervalTimeFolder();
        }

        /**
         * 获取时间间隔所属的文件夹
         * @return 时间间隔所属的文件夹
         */
        public String getIntervalTimeFolder() {
            // 文件夹
            String folder = File.separator + getFolderName() + File.separator;
            // 获取间隔时间
            int iTime = getFileIntervalTime();
            // 进行判断
            switch (iTime) {
                case DEFAULT:
                    return folder;
                case HH:
                case MM:
                case SS:
                    // 小时格式
                    String hh_Format = DateUtils.getDateNow("HH");
                    // 判断属于小时格式
                    if (iTime == HH) {
                        // /folder/HH/HH_number/
                        // /LogSpace/HH/HH_15/
                        return folder + "HH/HH_" + hh_Format + File.separator;
                    } else {
                        // 分钟格式
                        String mm_Format = DateUtils.getDateNow("mm");
                        // 判断是否属于分钟
                        if (iTime == MM) {
                            // /folder/HH/HH_number/MM/MM_number/
                            // /LogSpace/HH/HH_15/MM/MM_55/
                            return folder + "HH/HH_" + hh_Format + "/MM/MM_" + mm_Format + File.separator;
                        } else { // 属于秒
                            // 秒格式
                            String ss_Format = DateUtils.getDateNow("ss");
                            // /folder/HH/HH_number/MM/MM_number/SS_number/
                            // /LogSpace/HH/HH_15/MM/MM_55/SS_12/
                            return folder + "HH/HH_" + hh_Format + "/MM/MM_" + mm_Format + "/SS_" + ss_Format + File.separator;
                        }
                    }
            }
            // 放到未知目录下
            return "/Unknown/";
        }
    }

    // ===============
    // = 设备信息处理 =
    // ===============

    /**
     * 处理设备信息
     * @param errorInfo 错误提示信息, 如获取设备信息失败
     * @return 拼接后的设备信息字符串
     */
    private static String handlerDeviceInfo(final String errorInfo) {
        // 如果不为 null, 则直接返回之前的信息
        if (!TextUtils.isEmpty(DEVICE_INFO_STR)) {
            return DEVICE_INFO_STR;
        }
        // 设备信息
        String deviceInfo = DeviceUtils.handlerDeviceInfo(DEVICE_INFO_MAPS, null);
        // 如果为 null
        if (deviceInfo == null) {
            return errorInfo;
        }
        // 保存设备信息
        DEVICE_INFO_STR = deviceInfo;
        // 返回设备信息
        return DEVICE_INFO_STR;
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
         * @param result     保存结果
         * @param fileInfo   {@link FileInfo}
         * @param logContent 日志信息
         * @param filePath   保存路径
         * @param fileName   文件名 ( 含后缀 )
         * @param logs       原始日志内容数组
         */
        void callback(
                boolean result,
                FileInfo fileInfo,
                String logContent,
                String filePath,
                String fileName,
                String... logs
        );
    }
}