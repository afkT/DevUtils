package dev.utils.app.logger;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 内部快捷操作工具类 ( 便于单独提取 Logger, 不依赖其他工具类 )
 * @author Ttt
 * <pre>
 *     代码与 FileRecordUtils ( 文件记录工具类 ) 完全一致
 * </pre>
 */
final class Utils {

    private Utils() {
    }

    // 日志 TAG
    private static final String TAG = Utils.class.getSimpleName();

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
    // 换行字符串 ( 两行 )
    private static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 初始化调用方法 ( 内部已调用 )
     */
    public static void init() {
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
        return saveErrorLog(ex, null, null, filePath, fileName, true);
    }

    /**
     * 保存异常日志
     * @param ex       错误信息
     * @param head     顶部标题
     * @param bottom   底部内容
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String head, final String bottom,
                                       final String filePath, final String fileName) {
        return saveErrorLog(ex, head, bottom, filePath, fileName, true);
    }

    // =

    /**
     * 保存异常日志
     * @param ex          错误信息
     * @param filePath    保存路径
     * @param fileName    文件名 ( 含后缀 )
     * @param printDevice 是否打印设备信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String filePath, final String fileName, final boolean printDevice) {
        return saveErrorLog(ex, null, null, filePath, fileName, printDevice);
    }

    /**
     * 保存异常日志
     * @param ex          错误信息
     * @param head        顶部标题
     * @param bottom      底部内容
     * @param filePath    保存路径
     * @param fileName    文件名 ( 含后缀 )
     * @param printDevice 是否打印设备信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String head, final String bottom,
                                       final String filePath, final String fileName, final boolean printDevice) {
        return saveLog(getThrowableStackTrace(ex, "failed to get exception information"),
                head, bottom, filePath, fileName, printDevice);
    }

    // ============
    // = 正常日志 =
    // ============

    /**
     * 保存日志
     * @param log      日志信息
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath, final String fileName) {
        return saveLog(log, null, null, filePath, fileName, true);
    }

    /**
     * 保存日志
     * @param log      日志信息
     * @param head     顶部标题
     * @param bottom   底部内容
     * @param filePath 保存路径
     * @param fileName 文件名 ( 含后缀 )
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String head, final String bottom,
                                  final String filePath, final String fileName) {
        return saveLog(log, head, bottom, filePath, fileName, true);
    }

    // =

    /**
     * 保存日志
     * @param log         日志信息
     * @param filePath    保存路径
     * @param fileName    文件名 ( 含后缀 )
     * @param printDevice 是否打印设备信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath, final String fileName, final boolean printDevice) {
        return saveLog(log, null, null, filePath, fileName, printDevice);
    }

    /**
     * 保存日志
     * @param log         日志信息
     * @param head        顶部标题
     * @param bottom      底部内容
     * @param filePath    保存路径
     * @param fileName    文件名 ( 含后缀 )
     * @param printDevice 是否打印设备信息
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String head, final String bottom,
                                  final String filePath, final String fileName, final boolean printDevice) {
        // 日志拼接
        StringBuilder builder = new StringBuilder();
        // 如果存在顶部内容, 则进行添加
        if (!TextUtils.isEmpty(head)) {
            builder.append(head);
            builder.append(NEW_LINE_STR_X2);
            builder.append("===========================");
            builder.append(NEW_LINE_STR_X2);
        }
        // 保存 APP 信息
        builder.append("date: " + getDateNow());
        builder.append(NEW_LINE_STR);
        builder.append("versionName: " + APP_VERSION_NAME);
        builder.append(NEW_LINE_STR);
        builder.append("versionCode: " + APP_VERSION_CODE);
        builder.append(NEW_LINE_STR);
        builder.append("package: " + PACKAGE_NAME);
        builder.append(NEW_LINE_STR_X2);
        builder.append("===========================");
        builder.append(NEW_LINE_STR_X2);
        // 如果需要打印设备信息
        if (printDevice) {
            // 设备信息
            String deviceInfo = handlerDeviceInfo("failed to get device information");
            // 保存设备信息
            builder.append(deviceInfo);
            builder.append(NEW_LINE_STR);
            builder.append("===========================");
            builder.append(NEW_LINE_STR_X2);
        }
        // 保存日志信息
        builder.append(log);
        // 如果存在顶部内容, 则进行添加
        if (!TextUtils.isEmpty(bottom)) {
            builder.append(NEW_LINE_STR_X2);
            builder.append("===========================");
            builder.append(NEW_LINE_STR_X2);
            builder.append(bottom);
        }
        // 保存日志到文件
        return saveFile(filePath, fileName, builder.toString());
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
                LogPrintUtils.eTag(TAG, e, "saveFile");
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