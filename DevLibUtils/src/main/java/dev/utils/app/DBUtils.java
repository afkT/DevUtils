package dev.utils.app;

import android.os.Environment;

import java.io.InputStream;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 数据库工具类 (导入导出等)
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
     * @return 是否倒出成功
     */
    public static boolean startExportDatabase(final String targetFile, final String dbName) {
        // 判断 SDCard 是否挂载
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return false;
        }
        try {
            //  Database 文件地址
            String sourceFilePath = getDBPath() + dbName;
            // 获取结果
            boolean result = FileUtils.copyFile(sourceFilePath, targetFile, true);
            // 返回结果
            return result;
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
     * @return 是否倒出成功
     */
    public static boolean startImportDatabase(final String srcFilePath, final String destFilePath) {
        // 判断 SDCard 是否挂载
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return false;
        }
        try {
            // 获取结果
            boolean result = FileUtils.copyFile(srcFilePath, destFilePath, true);
            // 返回结果
            return result;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startImportDatabase");
        }
        return false;
    }

    /**
     * 导入数据库
     * @param inputStream  文件流(被复制)
     * @param destFilePath 目标文件地址
     * @return 是否倒出成功
     */
    public static boolean startImportDatabase(final InputStream inputStream, final String destFilePath) {
        // 判断 SDCard 是否挂载
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return false;
        }
        try {
            // 获取结果
            boolean result = FileUtils.copyFile(inputStream, destFilePath, true);
            // 返回结果
            return result;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startImportDatabase");
        }
        return false;
    }

    // =

    /**
     * 获取数据库路径
     * @return
     */
    public static String getDBPath() {
        try {
            //  Database 文件地址
            String dbPath = Environment.getDataDirectory() + "/data/" + DevUtils.getContext().getPackageName() + "/databases/";
            // 返回数据库路径
            return dbPath;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDBPath");
        }
        return null;
    }

}
