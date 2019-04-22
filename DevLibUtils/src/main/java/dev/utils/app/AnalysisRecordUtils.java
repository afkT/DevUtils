package dev.utils.app;

import android.content.Context;
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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dev.utils.LogPrintUtils;

/**
 * detail: 分析记录工具类
 * @author Ttt
 */
public final class AnalysisRecordUtils {

    private AnalysisRecordUtils() {
    }

    // 日志 TAG
    private static final String TAG = AnalysisRecordUtils.class.getSimpleName();
    // Context
    private static Context sContext;
    // 日志文件夹名字(目录名)
    private static String logFolderName = "LogRecord";
    // 日志存储路径
    private static String logStoragePath;
    // 是否处理保存
    private static boolean handler = true;
    // 判断是否加空格
    private static boolean appendSpace = true;
    // 正则 - 空格
    private static final String SPACE_STR = "\\s";


    /**
     * 初始化操作
     * @param context
     */
    public static void init(final Context context) {
        if (context != null) {
            sContext = context.getApplicationContext();
        }
        // 初始化设备信息
        getDeviceInfo();
        // 初始化 App 信息
        getAppInfo();
        // 如果为null, 才设置
        if (TextUtils.isEmpty(logStoragePath)) {
            // 获取根路径
            logStoragePath = FileInfo.getDiskCacheDir(sContext);
        }
    }

    // ================
    // = 对外提供方法 =
    // ================

    /**
     * 日志记录
     * @param fileInfo
     * @param args
     * @return log 直接打印
     */
    public static String record(final FileInfo fileInfo, final String... args) {
        // 如果不处理, 则直接跳过
        if (!handler) {
            return "record not handler";
        }
        if (fileInfo != null) {
            if (!fileInfo.isHandler()) {
                return "file record not handler";
            }
            if (args != null && args.length != 0) {
                return saveLogRecord(fileInfo, args);
            }
            // 无数据记录
            return "no data record";
        }
        // 信息为null
        return "info is null";
    }

    /**
     * 是否处理日志记录
     * @return
     */
    public static boolean isHandler() {
        return handler;
    }

    /**
     * 设置是否处理日志记录
     * @param handler
     */
    public static void setHandler(final boolean handler) {
        AnalysisRecordUtils.handler = handler;
    }

    /**
     * 是否追加空格
     * @return
     */
    public static boolean isAppendSpace() {
        return appendSpace;
    }

    /**
     * 设置是否追加空格
     * @param appendSpace
     */
    public static void setAppendSpace(final boolean appendSpace) {
        AnalysisRecordUtils.appendSpace = appendSpace;
    }

    /**
     * 获取文件日志名
     * @return
     */
    public static String getLogFolderName() {
        return logFolderName;
    }

    /**
     * 设置日志文件夹名
     * @param logFolderName
     */
    public static void setLogFolderName(final String logFolderName) {
        AnalysisRecordUtils.logFolderName = logFolderName;
    }

    /**
     * 获取日志存储路径
     * @return
     */
    public static String getLogStoragePath() {
        return logStoragePath;
    }

    /**
     * 设置日志存储路径
     * @param logStoragePath
     */
    public static void setLogStoragePath(final String logStoragePath) {
        AnalysisRecordUtils.logStoragePath = logStoragePath;
    }

    // ============
    // = 内部方法 =
    // ============

    /**
     * 最终保存方法
     * @param fileInfo
     * @param args
     */
    private static String saveLogRecord(final FileInfo fileInfo, final String... args) {
        // 如果不处理, 则直接跳过
        if (!handler) {
            return "record not handler";
        }
        // 文件信息为null, 则不处理
        if (fileInfo == null) {
            return "info is null";
        }
        // 如果文件地址为null, 则不处理
        if (TextUtils.isEmpty(fileInfo.getFileName())) {
            // 文件名为null
            return "fileName is null";
        }
        // 获取文件名
        String fileName = fileInfo.getFileName();
        // 获取文件提示
        String fileHint = fileInfo.getFileFunction();
        try {
            // 获取处理的日志
            String logContent = splitLog(args);
            // 日志保存路径
            String logPath = fileInfo.getLogPath();
            // 获取日志地址
            String logFile = logPath + File.separator + fileName;
            // 返回地址
            File file = new File(logFile);
            // 判断是否存在
            if (file.exists()) {
                // 追加内容
                appendFile(logFile, logContent);
            } else {
                // = 首次则保存设备、App 信息 =
                StringBuffer buffer = new StringBuffer();
                buffer.append("【设备信息】");
                buffer.append(NEW_LINE_STR);
                buffer.append("===========================");
                buffer.append(NEW_LINE_STR);
                buffer.append(getDeviceInfo());
                buffer.append("===========================");
                buffer.append(NEW_LINE_STR);

                buffer.append(NEW_LINE_STR);
                buffer.append(NEW_LINE_STR);
                buffer.append("【版本信息】");
                buffer.append(NEW_LINE_STR);
                buffer.append("===========================");
                buffer.append(NEW_LINE_STR);
                buffer.append(getAppInfo());
                buffer.append(NEW_LINE_STR);
                buffer.append("===========================");
                buffer.append(NEW_LINE_STR);

                buffer.append(NEW_LINE_STR);
                buffer.append(NEW_LINE_STR);
                buffer.append("【文件信息】");
                buffer.append(NEW_LINE_STR);
                buffer.append("===========================");
                buffer.append(NEW_LINE_STR);
                buffer.append(fileHint);
                buffer.append(NEW_LINE_STR);
                buffer.append("===========================");
                buffer.append(NEW_LINE_STR);
                // 创建文件夹,并且进行处理
                saveFile(buffer.toString(), logPath, fileName);
                // 追加内容
                appendFile(logFile, logContent);
            }
            // 返回打印日志
            return logContent;
        } catch (Exception ignore) {
            // 捕获异常
            return "catch error";
        }
    }

    /**
     * 拼接日志
     * @param args
     * @return
     */
    private static String splitLog(final String... args) {
        // 判断是否追加空格
        boolean isSpace = appendSpace;
        // =
        StringBuffer buffer = new StringBuffer();
        // 增加换行
        buffer.append(NEW_LINE_STR);
        buffer.append(NEW_LINE_STR);
        // 获取保存时间
        buffer.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 追加个边距
        buffer.append(" => ");
        // 循环追加内容
        for (int i = 0, c = args.length; i < c; i++) {
            if (isSpace) { // 判断是否追加空格
                buffer.append(SPACE_STR);
            }
            // 追加保存内容
            buffer.append(args[i]);
        }
        return buffer.toString();
    }

    // ================
    // = 设备信息统计 =
    // ================

    // App 信息
    private static String APP_INFO_STR = null;
    // 设备信息
    private static String DEVICE_INFO_STR = null;
    // 用来存储设备信息
    private static Map<String, String> DEVICE_INFO_MAPS = new HashMap<>();
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

    /**
     * 获取设备信息
     * @return
     */
    private static String getDeviceInfo() {
        if (DEVICE_INFO_STR != null) {
            return DEVICE_INFO_STR;
        }
        // 获取设备信息
        getDeviceInfo(DEVICE_INFO_MAPS);
        // 转换设备信息
        handlerDeviceInfo("获取设备信息失败");
        // 返回设备信息
        return DEVICE_INFO_STR;
    }

    /**
     * 获取设备信息
     * @param dInfoMaps 传入设备信息传出HashMap
     */
    private static void getDeviceInfo(final Map<String, String> dInfoMaps) {
        // 获取设备信息类的所有申明的字段,即包括public、private和proteced, 但是不包括父类的申明字段。
        Field[] fields = Build.class.getDeclaredFields();
        // 遍历字段
        for (Field field : fields) {
            try {
                // 取消 java 的权限控制检查
                field.setAccessible(true);

                // 转换 当前设备支持的ABI - CPU指令集
                if (field.getName().toLowerCase().startsWith("SUPPORTED".toLowerCase())) {
                    try {
                        Object object = field.get(null);
                        // 判断是否数组
                        if (object instanceof String[]) {
                            if (object != null) {
                                // 获取类型对应字段的数据，并保存 - 保存支持的指令集 [arm64-v8a, armeabi-v7a, armeabi]
                                dInfoMaps.put(field.getName(), Arrays.toString((String[]) object));
                            }
                            continue;
                        }
                    } catch (Exception e) {
                    }
                }
                // 获取类型对应字段的数据, 并保存
                dInfoMaps.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getDeviceInfo");
            }
        }
    }

    /**
     * 处理设备信息
     * @param eHint 错误提示,如获取设备信息失败
     */
    private static String handlerDeviceInfo(final String eHint) {
        try {
            // 如果不为null,则直接返回之前的信息
            if (!TextUtils.isEmpty(DEVICE_INFO_STR)) {
                return DEVICE_INFO_STR;
            }
            // 初始化 StringBuffer,拼接字符串
            StringBuffer buffer = new StringBuffer();
            // 获取设备信息
            Iterator<Map.Entry<String, String>> mapIter = DEVICE_INFO_MAPS.entrySet().iterator();
            // 遍历设备信息
            while (mapIter.hasNext()) {
                // 获取对应的key-value
                Map.Entry<String, String> rnEntry = mapIter.next();
                String rnKey = rnEntry.getKey(); // key
                String rnValue = rnEntry.getValue(); // value
                // 保存设备信息
                buffer.append(rnKey);
                buffer.append(" = ");
                buffer.append(rnValue);
                buffer.append(NEW_LINE_STR);
            }
            // 保存设备信息
            DEVICE_INFO_STR = buffer.toString();
            // 返回设备信息
            return DEVICE_INFO_STR;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "handlerDeviceInfo");
        }
        return eHint;
    }

    /**
     * 获取 App 信息
     * @return
     */
    private static String getAppInfo() {
        // 如果不为null,则直接返回之前的信息
        if (!TextUtils.isEmpty(APP_INFO_STR)) {
            return APP_INFO_STR;
        }
        try {
            StringBuffer buffer = new StringBuffer();
            // =
            PackageManager pm = sContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(sContext.getPackageName(), PackageManager.GET_SIGNATURES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                // 保存版本信息
                buffer.append("versionName: " + versionName);
                buffer.append("\nversionCode: " + versionCode);
                // 保存其他信息
                buffer.append("\npackageName: " + pi.packageName); // 保存包名
                // 赋值版本信息
                APP_INFO_STR = buffer.toString();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppInfo");
        }
        return APP_INFO_STR;
    }

    // ================
    // = 日志保存时间 =
    // ================

    // DEFAULT - 默认天,在根目录下
    public static final int DEFAULT = 0;
    // 小时
    public static final int HH = 1;
    // 分钟
    public static final int MM = 2;
    // 秒
    public static final int SS = 3;

    //用 @IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
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
        private int fileIntervalTime = DEFAULT;

        // 是否处理日志记录
        private boolean handler = true;

        // ============
        // = 构造函数 =
        // ============

        private FileInfo(final String storagePath, final String folderName, final String fileName, final String fileFunction, @TIME final int fileIntervalTime, final boolean handler) {
            this.storagePath = storagePath;
            this.folderName = folderName;
            this.fileName = fileName;
            this.fileFunction = fileFunction;
            this.fileIntervalTime = fileIntervalTime;
            this.handler = handler;
        }

        // ================
        // = get/set 方法 =
        // ================

        /**
         * 获取存储路径
         * @return
         */
        public String getStoragePath() {
            if (TextUtils.isEmpty(storagePath)) {
                return storagePath = getLogStoragePath();
            }
            return storagePath;
        }

        /**
         * 获取日志文件名
         * @return
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * 获取日志文件记录的功能
         * @return
         */
        public String getFileFunction() {
            return fileFunction;
        }

        /**
         * 获取日志文件记录间隔时间
         * @return
         */
        public int getFileIntervalTime() {
            return fileIntervalTime;
        }

        /**
         * 获取日志文件夹名
         * @return
         */
        public String getFolderName() {
            if (TextUtils.isEmpty(folderName)) {
                return folderName = getLogFolderName();
            }
            return folderName;
        }

        /**
         * 获取记录分析文件信息
         * @param fileName
         * @param fileFunction
         * @return
         */
        public static FileInfo obtain(final String fileName, final String fileFunction) {
            return new FileInfo(null, null, fileName, fileFunction, DEFAULT, true);
        }

        /**
         * 获取记录分析文件信息
         * @param folderName
         * @param fileName
         * @param fileFunction
         * @return
         */
        public static FileInfo obtain(final String folderName, final String fileName, final String fileFunction) {
            return new FileInfo(null, folderName, fileName, fileFunction, DEFAULT, true);
        }

        /**
         * 获取记录分析文件信息
         * @param storagePath
         * @param folderName
         * @param fileName
         * @param fileFunction
         * @return
         */
        public static FileInfo obtain(final String storagePath, final String folderName, final String fileName, final String fileFunction) {
            return new FileInfo(storagePath, folderName, fileName, fileFunction, DEFAULT, true);
        }

        // =

        /**
         * 获取记录分析文件信息
         * @param fileName
         * @param fileFunction
         * @param fileIntervalTime
         * @return
         */
        public static FileInfo obtain(final String fileName, final String fileFunction, @TIME final int fileIntervalTime) {
            return new FileInfo(null, null, fileName, fileFunction, fileIntervalTime, true);
        }

        /**
         * 获取记录分析文件信息
         * @param folderName
         * @param fileName
         * @param fileFunction
         * @param fileIntervalTime
         * @return
         */
        public static FileInfo obtain(final String folderName, final String fileName, final String fileFunction, @TIME final int fileIntervalTime) {
            return new FileInfo(null, folderName, fileName, fileFunction, fileIntervalTime, true);
        }

        /**
         * 获取记录分析文件信息
         * @param storagePath
         * @param folderName
         * @param fileName
         * @param fileFunction
         * @param fileIntervalTime
         * @return
         */
        public static FileInfo obtain(final String storagePath, final String folderName, final String fileName, final String fileFunction, @TIME final int fileIntervalTime) {
            return new FileInfo(storagePath, folderName, fileName, fileFunction, fileIntervalTime, true);
        }

        /**
         * 获取记录分析文件信息
         * @param storagePath
         * @param folderName
         * @param fileName
         * @param fileFunction
         * @param fileIntervalTime
         * @param isHandler
         * @return
         */
        public static FileInfo obtain(final String storagePath, final String folderName, final String fileName, final String fileFunction, @TIME final int fileIntervalTime, final boolean isHandler) {
            return new FileInfo(storagePath, folderName, fileName, fileFunction, fileIntervalTime, isHandler);
        }

        // ================
        // = 内部处理方法 =
        // ================

        /**
         * 获取日志地址
         * @return
         */
        public String getLogPath() {
            // 返回拼接后的路径
            return getSavePath(getStoragePath(), logFolderName + File.separator + getDateNow("yyyy_MM_dd")) + getIntervalTimeFolder();
        }

        /**
         * 获取时间间隔 - 文件夹
         * @return
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

        /**
         * 是否处理日志记录
         * @return
         */
        public boolean isHandler() {
            return handler;
        }

        /**
         * 设置是否处理日志记录
         * @param handler
         * @return
         */
        public FileInfo setHandler(final boolean handler) {
            this.handler = handler;
            return this;
        }

        /**
         * 获取当前日期的字符串
         * @param format 日期格式，如: HH, mm, ss
         * @return 字符串
         */
        private String getDateNow(final String format) {
            try {
                Calendar cld = Calendar.getInstance();
                DateFormat df = new SimpleDateFormat(format);
                return df.format(cld.getTime());
            } catch (Exception e) {
            }
            return null;
        }

        // =

        /**
         * 获取保存地址
         * @param storagePath 存储路径
         * @param filePath    文件路径
         * @return
         */
        private String getSavePath(final String storagePath, final String filePath) {
            // 获取保存地址
            File file = new File(storagePath, filePath);
            // 防止不存在目录文件，自动创建
            createFolder(file);
            // 返回缓存地址
            return file.getAbsolutePath();
        }

        /**
         * 获取缓存地址
         * @return
         */
        private static String getDiskCacheDir(final Context context) {
            String cachePath;
            // 判断SDCard是否挂载
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
            // 防止不存在目录文件，自动创建
            createFolder(new File(cachePath));
            // 返回文件存储地址
            return cachePath;
        }

        /**
         * 判断某个文件夹是否创建,未创建则创建(纯路径 - 无文件名)
         * @param file 文件夹路径 (无文件名字.后缀)
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
    }

    // ================
    // = 内部处理方法 =
    // ================

    /**
     * 追加文件: 使用FileWriter
     * @param filePath 文件路径
     * @param text     追加内容
     */
    private static void appendFile(final String filePath, final String text) {
        if (filePath == null || text == null) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) { // 如果文件不存在,则跳过
            return;
        }
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(file, true);
            writer.write(text);
        } catch (IOException e) {
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
     * @param txt      保存内容
     * @param filePath 保存路径
     * @param fileName 文件名.后缀
     * @return 是否保存成功
     */
    private static boolean saveFile(final String txt, final String filePath, final String fileName) {
        try {
            // 防止文件没创建
            createFile(filePath);
            // 保存路径
            File file = new File(filePath, fileName);
            // 保存内容到一个文件
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(txt.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveFile");
        }
        return false;
    }

    /**
     * 判断某个文件夹是否创建,未创建则创建(纯路径 - 无文件名)
     * @param filePath 文件夹路径
     */
    private static File createFile(final String filePath) {
        try {
            File file = new File(filePath);
            // 当这个文件夹不存在的时候则创建文件夹
            if (!file.exists()) {
                // 允许创建多级目录
                file.mkdirs();
                // 这个无法创建多级目录
                // rootFile.mkdir();
            }
            return file;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createFile");
        }
        return null;
    }
}
