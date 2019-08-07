package dev.utils.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

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
    private static String sLogFolderName = "LogRecord";
    // 日志存储路径
    private static String sLogStoragePath;
    // 是否处理保存
    private static boolean sIsHandler = true;
    // 判断是否加空格
    private static boolean sAppendSpace = true;

    // ============
    // = 配置信息 =
    // ============

    // APP 版本 ( 如 1.0.01) 显示给用户看的
    private static String APP_VERSION_NAME = "";
    // android:versionCode 整数值, 代表应用程序代码的相对版本
    private static String APP_VERSION_CODE = "";
    // 应用包名
    private static String PACKAGE_NAME = "";
    // 设备信息
    private static String DEVICE_INFO_STR = null;
    // 设备信息存储 Map
    private static Map<String, String> DEVICE_INFO_MAPS = new HashMap<>();
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");
    // 换行字符串 - 两行
    private static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

    /**
     * 初始化操作 ( 内部已调用 )
     */
    public static void init() {
        // 如果为 null, 才设置
        if (TextUtils.isEmpty(sLogStoragePath)) {
            // 获取根路径
            sLogStoragePath = getDiskCacheDir();
        }

        // 如果版本信息为 null, 才进行处理
        if (TextUtils.isEmpty(APP_VERSION_CODE) || TextUtils.isEmpty(APP_VERSION_NAME)) {
            // 获取 APP 版本信息
            String[] versions = getAppVersion();
            // 防止为 null
            if (versions != null && versions.length == 2) {
                // 保存 APP 版本信息
                APP_VERSION_NAME = versions[0];
                APP_VERSION_CODE = versions[1];
            }
        }

        // 获取包名
        if (TextUtils.isEmpty(PACKAGE_NAME)) {
            try {
                PACKAGE_NAME = DevUtils.getContext().getPackageName();
            } catch (Exception e) {
            }
        }

        // 判断是否存在设备信息
        if (DEVICE_INFO_MAPS.size() == 0) {
            // 获取设备信息
            getDeviceInfo(DEVICE_INFO_MAPS);
            // 转换字符串
            handlerDeviceInfo("");
        }
    }

    // ============
    // = 记录方法 =
    // ============

    /**
     * 日志记录
     * @param fileInfo {@link FileInfo}
     * @param logs     日志内容数组
     * @return 日志内容
     */
    public static String record(final FileInfo fileInfo, final String... logs) {
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

    // ==================
    // = 判断、获取方法 =
    // ==================

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

    // ============
    // = 内部方法 =
    // ============

    /**
     * 最终保存方法
     * @param fileInfo {@link FileInfo}
     * @param logs     日志内容数组
     * @return 拼接后的日志内容
     */
    private static String saveLogRecord(final FileInfo fileInfo, final String... logs) {
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
            // 获取处理的日志
            String logContent = splitLog(logs);
            // 日志保存路径
            String logPath = fileInfo.getLogPath();
            // 获取日志地址
            String logFile = logPath + File.separator + fileName;
            // 返回地址
            File file = new File(logFile);
            // 判断是否存在
            if (file.exists()) {
                appendFile(logFile, logContent);
            } else {
                // = 首次则保存设备、APP 信息 =
                StringBuilder builder = new StringBuilder();
                builder.append(NEW_LINE_STR_X2);
                builder.append("【设备信息】");
                builder.append(NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(NEW_LINE_STR_X2);
                builder.append(handlerDeviceInfo("failed to get device information"));
                builder.append(NEW_LINE_STR);
                builder.append("===========================");
                builder.append(NEW_LINE_STR_X2);

                builder.append(NEW_LINE_STR_X2);
                builder.append(NEW_LINE_STR_X2);
                builder.append("【版本信息】");
                builder.append(NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(NEW_LINE_STR_X2);
                builder.append("versionName: " + APP_VERSION_NAME);
                builder.append(NEW_LINE_STR);
                builder.append("versionCode: " + APP_VERSION_CODE);
                builder.append(NEW_LINE_STR);
                builder.append("package: " + PACKAGE_NAME);
                builder.append(NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(NEW_LINE_STR_X2);

                builder.append(NEW_LINE_STR_X2);
                builder.append(NEW_LINE_STR_X2);
                builder.append("【文件信息】");
                builder.append(NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(NEW_LINE_STR_X2);
                builder.append(fileHint);
                builder.append(NEW_LINE_STR_X2);
                builder.append("===========================");
                builder.append(NEW_LINE_STR_X2);

                builder.append(NEW_LINE_STR_X2);
                builder.append(NEW_LINE_STR_X2);
                builder.append("【日志内容】");
                builder.append(NEW_LINE_STR_X2);
                builder.append("===========================");
                // 创建文件夹, 并且进行处理
                saveFile(logPath, fileName, builder.toString());
                // 追加内容
                appendFile(logFile, logContent);
            }
            // 返回打印日志
            return logContent;
        } catch (Exception ignore) {
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
        builder.append(NEW_LINE_STR);
        builder.append(NEW_LINE_STR);
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

    // ================
    // = 日志保存时间 =
    // ================

    // DEFAULT - 默认天, 在根目录下
    public static final int DEFAULT = 0;
    // 小时
    public static final int HH = 1;
    // 分钟
    public static final int MM = 2;
    // 秒
    public static final int SS = 3;

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
        private String fileName;

        // 文件记录的功能
        private String fileFunction;

        // 文件记录间隔时间 如: HH
        private int fileIntervalTime;

        // 是否处理日志记录
        private boolean handler;

        // ============
        // = 构造函数 =
        // ============

        /**
         * 构造函数
         * @param storagePath      存储路径
         * @param folderName       文件夹名
         * @param fileName         文件名
         * @param fileFunction     文件记录的功能
         * @param fileIntervalTime 文件记录间隔时间
         * @param handler          是否处理日志记录
         */
        private FileInfo(final String storagePath, final String folderName, final String fileName,
                         final String fileFunction, @TIME final int fileIntervalTime, final boolean handler) {
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
        public static FileInfo obtain(final String fileName, final String fileFunction) {
            return new FileInfo(null, null, fileName, fileFunction, DEFAULT, true);
        }

        /**
         * 获取日志记录分析文件对象
         * @param folderName   日志文件名
         * @param fileName     文件名
         * @param fileFunction 日志文件记录功能
         * @return {@link FileInfo}
         */
        public static FileInfo obtain(final String folderName, final String fileName, final String fileFunction) {
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
        public static FileInfo obtain(final String storagePath, final String folderName, final String fileName, final String fileFunction) {
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
        public static FileInfo obtain(final String fileName, final String fileFunction, @TIME final int fileIntervalTime) {
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
        public static FileInfo obtain(final String folderName, final String fileName, final String fileFunction, @TIME final int fileIntervalTime) {
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
        public static FileInfo obtain(final String storagePath, final String folderName, final String fileName, final String fileFunction,
                                      @TIME final int fileIntervalTime) {
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
        public static FileInfo obtain(final String storagePath, final String folderName, final String fileName, final String fileFunction,
                                      @TIME final int fileIntervalTime, final boolean isHandler) {
            return new FileInfo(storagePath, folderName, fileName, fileFunction, fileIntervalTime, isHandler);
        }

        // ================
        // = get/set 方法 =
        // ================

        /**
         * 获取存储路径
         * @return 存储路径
         */
        public String getStoragePath() {
            if (TextUtils.isEmpty(storagePath)) {
                return storagePath = getLogStoragePath();
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
                return folderName = getLogFolderName();
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

        // ================
        // = 内部处理方法 =
        // ================

        /**
         * 获取日志文件地址
         * @return 日志文件地址
         */
        public String getLogPath() {
            // 返回拼接后的路径
            return getFilePathCreateFolder(getStoragePath(),
                    sLogFolderName + File.separator + getDateNow("yyyy_MM_dd")) + getIntervalTimeFolder();
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
                    String hh_Foramt = getDateNow("HH");
                    // 判断属于小时格式
                    if (iTime == HH) {
                        // /folder/HH/HH_小时/ => /LogSpace/HH/HH_15/
                        return folder + "HH/HH_" + hh_Foramt + File.separator;
                    } else {
                        // 分钟格式
                        String mm_Foramt = getDateNow("mm");
                        // 判断是否属于分钟
                        if (iTime == MM) {
                            // /folder/HH/HH_小时/MM_分钟/ => /LogSpace/HH/HH_15/MM/MM_55/
                            return folder + "HH/HH_" + hh_Foramt + "/MM/MM_" + mm_Foramt + File.separator;
                        } else { // 属于秒
                            // 秒格式
                            String ss_Foramt = getDateNow("ss");
                            // /folder/HH/HH_小时/MM_分钟/ => /LogSpace/HH/HH_15/MM/MM_55/SS_12/
                            return folder + "HH/HH_" + hh_Foramt + "/MM/MM_" + mm_Foramt + "/SS_" + ss_Foramt + File.separator;
                        }
                    }
            }
            // 放到未知目录下
            return "/Unknown/";
        }
    }

    // ================
    // = 设备信息处理 =
    // ================

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
        String deviceInfo = handlerDeviceInfo(DEVICE_INFO_MAPS, null);
        // 如果为 null
        if (deviceInfo == null) {
            return errorInfo;
        }
        // 保存设备信息
        DEVICE_INFO_STR = deviceInfo;
        // 返回设备信息
        return DEVICE_INFO_STR;
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ===============
    // = SDCardUtils =
    // ===============

    /**
     * 判断内置 SDCard 是否正常挂载
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取 APP Cache 文件夹地址
     * @return APP Cache 文件夹地址
     */
    private static String getDiskCacheDir() {
        String cachePath;
        if (isSDCardEnable()) { // 判断 SDCard 是否挂载
            cachePath = DevUtils.getContext().getExternalCacheDir().getPath();
        } else {
            cachePath = DevUtils.getContext().getCacheDir().getPath();
        }
        // 防止不存在目录文件, 自动创建
        createFolder(cachePath);
        // 返回文件存储地址
        return cachePath;
    }

    // =============
    // = FileUtils =
    // =============

    /**
     * 追加文件 ( 使用 FileWriter)
     * @param filePath 文件路径
     * @param content  追加内容
     */
    private static void appendFile(final String filePath, final String content) {
        if (filePath == null || content == null) return;
        File file = new File(filePath);
        // 如果文件不存在, 则跳过
        if (!file.exists()) return;
        FileWriter writer = null;
        try {
            // 打开一个写文件器, 构造函数中的第二个参数 true 表示以追加形式写文件
            writer = new FileWriter(file, true);
            writer.write(content);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "appendFile");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }

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
                LogPrintUtils.eTag(TAG, e, "saveFile");
            }
        }
        return false;
    }

    /**
     * 获取文件绝对路径
     * @param file 文件
     * @return 文件绝对路径
     */
    private static String getAbsolutePath(final File file) {
        return file != null ? file.getAbsolutePath() : null;
    }

    /**
     * 获取文件
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return 文件 {@link File}
     */
    private static File getFile(final String filePath, final String fileName) {
        return (filePath != null && fileName != null) ? new File(filePath, fileName) : null;
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
     * 获取路径, 并且进行创建目录
     * @param filePath 保存目录
     * @param fileName 文件名
     * @return 文件 {@link File}
     */
    private static String getFilePathCreateFolder(final String filePath, final String fileName) {
        // 防止不存在目录文件, 自动创建
        createFolder(filePath);
        // 返回处理过后的 File
        File file = getFile(filePath, fileName);
        // 返回文件路径
        return getAbsolutePath(file);
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
                    // 这个无法创建多级目录
                    // rootFile.mkdir();
                }
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "createFolder");
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
                LogPrintUtils.eTag(TAG, e, "getThrowableStackTrace");
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
     * @param format 日期格式, 如: yyyy-MM-dd HH:mm:ss
     * @return 当前日期指定格式字符串
     */
    private static String getDateNow(final String format) {
        if (format == null) return null;
        try {
            return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDateNow");
        }
        return null;
    }

    // ===============
    // = DeviceUtils =
    // ===============

    /**
     * 获取设备信息
     * @param deviceInfoMap 设备信息 Map
     */
    private static void getDeviceInfo(final Map<String, String> deviceInfoMap) {
        // 获取设备信息类的所有申明的字段, 即包括 public、private 和 proteced, 但是不包括父类的申明字段
        Field[] fields = Build.class.getDeclaredFields();
        // 遍历字段
        for (Field field : fields) {
            try {
                // 取消 Java 的权限控制检查
                field.setAccessible(true);
                // 转换当前设备支持的 ABI - CPU 指令集
                if (field.getName().toLowerCase().startsWith("SUPPORTED".toLowerCase())) {
                    try {
                        Object object = field.get(null);
                        // 判断是否数组
                        if (object instanceof String[]) {
                            if (object != null) {
                                // 获取类型对应字段的数据, 并保存支持的指令集 [arm64-v8a, armeabi-v7a, armeabi]
                                deviceInfoMap.put(field.getName(), Arrays.toString((String[]) object));
                            }
                            continue;
                        }
                    } catch (Exception e) {
                    }
                }
                // 获取类型对应字段的数据, 并保存
                deviceInfoMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getDeviceInfo");
            }
        }
    }

    /**
     * 处理设备信息
     * @param deviceInfoMap 设备信息 Map
     * @param errorInfo     错误提示信息, 如获取设备信息失败
     * @return 拼接后的设备信息字符串
     */
    private static String handlerDeviceInfo(final Map<String, String> deviceInfoMap, final String errorInfo) {
        try {
            // 初始化 Builder, 拼接字符串
            StringBuilder builder = new StringBuilder();
            // 获取设备信息
            Iterator<Map.Entry<String, String>> mapIter = deviceInfoMap.entrySet().iterator();
            // 遍历设备信息
            while (mapIter.hasNext()) {
                // 获取对应的 key - value
                Map.Entry<String, String> rnEntry = mapIter.next();
                String rnKey = rnEntry.getKey(); // key
                String rnValue = rnEntry.getValue(); // value
                // 保存设备信息
                builder.append(rnKey);
                builder.append(" = ");
                builder.append(rnValue);
                builder.append(NEW_LINE_STR);
            }
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "handlerDeviceInfo");
        }
        return errorInfo;
    }

    // =================
    // = ManifestUtils =
    // =================

    /**
     * 获取 APP 版本信息
     * @return 0 = versionName, 1 = versionCode
     */
    private static String[] getAppVersion() {
        try {
            PackageManager packageManager = DevUtils.getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(DevUtils.getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                return new String[]{versionName, versionCode};
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersion");
        }
        return null;
    }
}