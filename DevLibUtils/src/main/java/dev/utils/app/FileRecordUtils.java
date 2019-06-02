package dev.utils.app;

import android.annotation.SuppressLint;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 文件记录工具类
 * @author Ttt
 */
public final class FileRecordUtils {

    private FileRecordUtils() {
    }

    // 日志 TAG
    private static final String TAG = FileRecordUtils.class.getSimpleName();

    // ============
    // = 配置信息 =
    // ============

    // App 版本(如 1.0.01) 显示给用户看的
    private static String APP_VERSION_NAME = "";
    // android:versionCode 整数值, 代表应用程序代码的相对版本
    private static String APP_VERSION_CODE = "";
    // 设备信息
    private static String DEVICE_INFO_STR = null;
    // 设备信息存储 Map
    private static Map<String, String> DEVICE_INFO_MAPS = new HashMap<>();
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");
    // 换行字符串 - 两行
    private static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

    // =====================
    // = App、设备信息处理 =
    // =====================

    /**
     * 获取 App 版本信息
     * @return 0 = versionName, 1 = versionCode
     */
    private static String[] getAppVersion() {
        String[] versions = null;
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(DevUtils.getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                // =
                versions = new String[]{versionName, versionCode};
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersion");
        }
        return versions;
    }

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
     * @param errorInfo 错误提示信息, 如获取设备信息失败
     * @return 拼接后的设备信息字符串
     */
    private static String handlerDeviceInfo(final String errorInfo) {
        try {
            // 如果不为 null, 则直接返回之前的信息
            if (!TextUtils.isEmpty(DEVICE_INFO_STR)) {
                return DEVICE_INFO_STR;
            }
            StringBuilder builder = new StringBuilder();
            // 获取设备信息
            Iterator<Map.Entry<String, String>> mapIter = DEVICE_INFO_MAPS.entrySet().iterator();
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
            // 保存设备信息
            DEVICE_INFO_STR = builder.toString();
            // 返回设备信息
            return DEVICE_INFO_STR;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "handlerDeviceInfo");
        }
        return errorInfo;
    }

    // ==============
    // = 时间格式化 =
    // ==============

    // 日期格式类型
    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前日期的字符串
     * @return 当前日期 yyyy-MM-dd HH:mm:ss 格式字符串
     */
    @SuppressLint("SimpleDateFormat")
    private static String getDateNow() {
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat(yyyyMMddHHmmss);
            return df.format(calendar.getTime());
        } catch (Exception e) {
        }
        return null;
    }

    // ============
    // = 文件操作 =
    // ============

    /**
     * 判断某个文件夹是否创建, 未创建则创建(不能加入文件名)
     * @param filePath 文件路径
     * @return 文件 {@link File}
     */
    private static File createFile(final String filePath) {
        try {
            File file = new File(filePath);
            // 当这个文件夹不存在的时候则创建文件夹
            if (!file.exists()) {
                // 允许创建多级目录
                file.mkdirs();
            }
            return file;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createFile");
        }
        return null;
    }

    /**
     * 保存文件
     * @param txt  保存内容
     * @param fUrl 保存路径(包含文件名.后缀)
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    private static boolean saveFile(final String txt, final String fUrl) {
        try {
            // 保存内容到一个文件
            FileOutputStream fos = new FileOutputStream(fUrl);
            fos.write(txt.getBytes());
            fos.close();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saveFile");
            return false;
        }
        return true;
    }

    // ================
    // = 异常信息处理 =
    // ================

    /**
     * 获取错误信息(无换行)
     * @param errorInfo 获取失败, 返回信息
     * @param ex        错误信息
     * @return 错误信息字符串
     */
    private static String getThrowableMsg(final String errorInfo, final Throwable ex) {
        PrintWriter printWriter = null;
        try {
            if (ex != null) {
                // 初始化 Writer、PrintWriter 打印流
                Writer writer = new StringWriter();
                printWriter = new PrintWriter(writer);
                // 写入错误栈信息
                ex.printStackTrace(printWriter);
                // 关闭流
                printWriter.close();
                return writer.toString();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getThrowableMsg");
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
        return errorInfo;
    }

    /**
     * 获取错误信息(有换行)
     * @param errorInfo 获取失败, 返回信息
     * @param ex        错误信息
     * @return 错误信息字符串
     */
    private static String getThrowableNewLinesMsg(final String errorInfo, final Throwable ex) {
        PrintWriter printWriter = null;
        try {
            if (ex != null) {
                // 初始化 Writer、PrintWriter 打印流
                Writer writer = new StringWriter();
                printWriter = new PrintWriter(writer);
                // 获取错误栈信息
                StackTraceElement[] stElement = ex.getStackTrace();
                // 标题, 提示属于什么异常
                printWriter.append(ex.toString());
                printWriter.append(NEW_LINE_STR);
                // 遍历错误栈信息, 并且进行换行缩进
                for (StackTraceElement st : stElement) {
                    printWriter.append("\tat ");
                    printWriter.append(st.toString());
                    printWriter.append(NEW_LINE_STR);
                }
                // 关闭流
                printWriter.close();
                return writer.toString();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getThrowableNewLinesMsg");
        } finally {
            if (printWriter != null) {
                try {
                    printWriter.close();
                } catch (Exception e) {
                }
            }
        }
        return errorInfo;
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 初始化调用方法
     */
    public static void init() {
        // 如果版本信息为 null, 才进行处理
        if (TextUtils.isEmpty(APP_VERSION_CODE) || TextUtils.isEmpty(APP_VERSION_NAME)) {
            // 获取 App 版本信息
            String[] versions = getAppVersion();
            // 防止为 null
            if (versions != null && versions.length == 2) {
                // 保存 App 版本信息
                APP_VERSION_NAME = versions[0];
                APP_VERSION_CODE = versions[1];
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

    // ====================
    // = 保存错误日志信息 =
    // ====================

    /**
     * 保存 App 错误日志
     * @param ex          错误信息
     * @param filePath    保存路径
     * @param fileName    文件名(含后缀)
     * @param isNewLines  是否换行
     * @param printDevice 是否打印设备信息
     * @param errorInfos  错误提示(无设备信息、失败信息获取失败)
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String filePath, final String fileName, final boolean isNewLines,
                                       final boolean printDevice, final String... errorInfos) {
        return saveErrorLog(ex, null, null, filePath, fileName, isNewLines, printDevice, errorInfos);
    }

    /**
     * 保存 App 错误日志
     * @param ex          错误信息
     * @param head        顶部标题
     * @param bottom      底部内容
     * @param filePath    保存路径
     * @param fileName    文件名(含后缀)
     * @param isNewLines  是否换行
     * @param printDevice 是否打印设备信息
     * @param errorInfos  错误提示(无设备信息、失败信息获取失败)
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveErrorLog(final Throwable ex, final String head, final String bottom, final String filePath, final String fileName,
                                       final boolean isNewLines, final boolean printDevice, final String... errorInfos) {
        // 处理可变参数(错误提示)
        String[] errorArrays = handlerVariable(2, errorInfos);
        // 日志拼接
        StringBuilder builder = new StringBuilder();
        // 防止文件夹不存在
        createFile(filePath);
        // 设备信息
        String deviceInfo = handlerDeviceInfo(errorArrays[0]);
        // 如果存在顶部内容, 则进行添加
        if (!TextUtils.isEmpty(head)) {
            builder.append(head);
            builder.append(NEW_LINE_STR_X2);
            builder.append("============================");
            builder.append(NEW_LINE_STR_X2);
        }
        // =
        // 保存 App 信息
        builder.append("date: " + getDateNow());
        builder.append(NEW_LINE_STR);
        builder.append("versionName: " + APP_VERSION_NAME);
        builder.append(NEW_LINE_STR);
        builder.append("versionCode: " + APP_VERSION_CODE);
        builder.append(NEW_LINE_STR_X2);
        builder.append("============================");
        builder.append(NEW_LINE_STR_X2);
        // 如果需要打印设备信息
        if (printDevice) {
            // 保存设备信息
            builder.append(deviceInfo);
            builder.append(NEW_LINE_STR);
            builder.append("============================");
            builder.append(NEW_LINE_STR_X2);
        }
        // =
        // 错误信息
        String errorMessage;
        // 是否换行
        if (isNewLines) {
            errorMessage = getThrowableNewLinesMsg(errorArrays[1], ex);
        } else {
            errorMessage = getThrowableMsg(errorArrays[1], ex);
        }
        // 保存异常信息
        builder.append(errorMessage);
        // 如果存在顶部内容, 则进行添加
        if (!TextUtils.isEmpty(bottom)) {
            builder.append(NEW_LINE_STR);
            builder.append("============================");
            builder.append(NEW_LINE_STR_X2);
            builder.append(bottom);
        }
        // 保存日志到文件
        return saveFile(builder.toString(), filePath + File.separator + fileName);
    }

    /**
     * 保存 App 日志
     * @param log         日志信息
     * @param filePath    保存路径
     * @param fileName    文件名(含后缀)
     * @param printDevice 是否打印设备信息
     * @param errorInfos  错误提示(无设备信息、失败信息获取失败)
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String filePath, final String fileName, final boolean printDevice, final String... errorInfos) {
        return saveLog(log, null, null, filePath, fileName, printDevice, errorInfos);
    }

    /**
     * 保存 App 日志
     * @param log         日志信息
     * @param head        顶部标题
     * @param bottom      底部内容
     * @param filePath    保存路径
     * @param fileName    文件名(含后缀)
     * @param printDevice 是否打印设备信息
     * @param errorInfos  错误提示(无设备信息、失败信息获取失败)
     * @return {@code true} 保存成功, {@code false} 保存失败
     */
    public static boolean saveLog(final String log, final String head, final String bottom, final String filePath,
                                  final String fileName, final boolean printDevice, final String... errorInfos) {
        // 处理可变参数(错误提示)
        String[] errorArrays = handlerVariable(2, errorInfos);
        // 日志拼接
        StringBuilder builder = new StringBuilder();
        // 防止文件夹不存在
        createFile(filePath);
        // 设备信息
        String deviceInfo = handlerDeviceInfo(errorArrays[0]);
        // 如果存在顶部内容, 则进行添加
        if (!TextUtils.isEmpty(head)) {
            builder.append(head);
            builder.append(NEW_LINE_STR_X2);
            builder.append("============================");
            builder.append(NEW_LINE_STR_X2);
        }
        // =
        // 保存 App 信息
        builder.append("date: " + getDateNow());
        builder.append(NEW_LINE_STR);
        builder.append("versionName: " + APP_VERSION_NAME);
        builder.append(NEW_LINE_STR);
        builder.append("versionCode: " + APP_VERSION_CODE);
        builder.append(NEW_LINE_STR_X2);
        builder.append("============================");
        builder.append(NEW_LINE_STR_X2);
        // 如果需要打印设备信息
        if (printDevice) {
            // 保存设备信息
            builder.append(deviceInfo);
            builder.append(NEW_LINE_STR);
            builder.append("============================");
            builder.append(NEW_LINE_STR_X2);
        }
        // =
        // 保存日志信息
        builder.append(log);
        // 如果存在顶部内容, 则进行添加
        if (!TextUtils.isEmpty(bottom)) {
            builder.append(NEW_LINE_STR);
            builder.append("============================");
            builder.append(NEW_LINE_STR_X2);
            builder.append(bottom);
        }
        // 保存日志到文件
        return saveFile(builder.toString(), filePath + File.separator + fileName);
    }

    // =

    /**
     * 处理可变参数
     * @param length  保留长度
     * @param strings 可变参数数组
     * @return
     */
    public static String[] handlerVariable(final int length, final String[] strings) {
        // 处理后的数据
        String[] hArrays = new String[length];
        // 是否统一处理
        boolean isUnifiedHandler = true;
        try {
            if (strings != null) {
                // 获取可变参数数组长度
                int vLength = strings.length;
                // 如果长度超出预留长度
                if (vLength >= length) {
                    for (int i = 0; i < length; i++) {
                        if (strings[i] == null) {
                            hArrays[i] = "";
                        } else {
                            hArrays[i] = strings[i];
                        }
                    }
                    // 但可变参数长度, 超过预留长度时, 已经处理完毕, 不需要再次处理, 节省遍历资源
                    isUnifiedHandler = false;
                } else {
                    for (int i = 0; i < vLength; i++) {
                        if (strings[i] == null) {
                            hArrays[i] = "";
                        } else {
                            hArrays[i] = strings[i];
                        }
                    }
                }
            }
            if (isUnifiedHandler) {
                // 统一处理, 如果数据为 null, 则设置为 "", 防止拼接出现 "null"
                for (int i = 0; i < length; i++) {
                    if (hArrays[i] == null) {
                        hArrays[i] = "";
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "handlerVariable");
        }
        return hArrays;
    }
}
