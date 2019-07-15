package dev.utils.app;

import android.os.Environment;

import java.io.InputStream;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 数据库工具类 ( 导入导出等 )
 * @author Ttt
 */
public final class DBUtils {

    private DBUtils() {
    }

    // 日志 TAG
    private static final String TAG = DBUtils.class.getSimpleName();

    /**
     * 导出数据库
     * @param targetFile 目标文件
     * @param dbName     数据库文件名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startExportDatabase(final String targetFile, final String dbName) {
        if (!isSDCardEnable()) return false;
        try {
            // Database 文件地址
            String sourceFilePath = getDBPath() + dbName;
            // 复制文件
            return FileUtils.copyFile(sourceFilePath, targetFile, true);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startExportDatabase");
        }
        return false;
    }

    // =

    /**
     * 导入数据库
     * @param srcFilePath  待复制的文件地址
     * @param destFilePath 目标文件地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(final String srcFilePath, final String destFilePath) {
        if (!isSDCardEnable()) return false;
        try {
            // 复制文件
            return FileUtils.copyFile(srcFilePath, destFilePath, true);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startImportDatabase");
        }
        return false;
    }

    /**
     * 导入数据库
     * @param inputStream  文件流 ( 被复制 )
     * @param destFilePath 目标文件地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(final InputStream inputStream, final String destFilePath) {
        if (!isSDCardEnable()) return false;
        try {
            // 复制文件
            return FileUtils.copyFile(inputStream, destFilePath, true);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startImportDatabase");
        }
        return false;
    }

    // =

    /**
     * 获取数据库路径
     * @return 数据库路径
     */
    public static String getDBPath() {
        try {
            // Database 文件地址
            return Environment.getDataDirectory() + "/data/" + DevUtils.getContext().getPackageName() + "/databases/";
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDBPath");
        }
        return null;
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
}