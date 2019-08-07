package dev.utils.app;

import android.os.Environment;

import java.io.File;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 本应用数据清除管理工具类
 * @author Ttt
 * <pre>
 *     主要功能有清除内 / 外缓存、清除数据库、清除 SharedPreferences、清除 files 和清除自定义目录
 *     <p></p>
 *     Context.getExternalFilesDir() --> SDCard/Android/data/packageName/files/ 目录, 一般放一些长时间保存的数据
 *     Context.getExternalCacheDir() --> SDCard/Android/data/packageName/cache/ 目录, 一般存放临时缓存数据
 * </pre>
 */
public final class CleanUtils {

    private CleanUtils() {
    }

    // 日志 TAG
    private static final String TAG = CleanUtils.class.getSimpleName();

    /**
     * 清除内部缓存 - path /data/data/package/cache
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanInternalCache() {
        try {
            return deleteFilesInDir(DevUtils.getContext().getCacheDir());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalCache");
        }
        return false;
    }

    /**
     * 清除内部文件 - path /data/data/package/files
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanInternalFiles() {
        try {
            return deleteFilesInDir(DevUtils.getContext().getFilesDir());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalFiles");
        }
        return false;
    }

    /**
     * 清除内部数据库 - path /data/data/package/databases
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanInternalDbs() {
        try {
            return deleteFilesInDir(new File(DevUtils.getContext().getFilesDir().getParent(), "databases"));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalDbs");
        }
        return false;
    }

    /**
     * 根据名称清除数据库 - path /data/data/package/databases/dbName
     * @param dbName 数据库名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanInternalDbByName(final String dbName) {
        try {
            return DevUtils.getContext().deleteDatabase(dbName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalDbByName");
        }
        return false;
    }

    /**
     * 清除内部 SP - path /data/data/package/shared_prefs
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanInternalSp() {
        try {
            return deleteFilesInDir(new File(DevUtils.getContext().getFilesDir().getParent(), "shared_prefs"));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanInternalSp");
        }
        return false;
    }

    /**
     * 清除外部缓存 - path /storage/emulated/0/android/data/package/cache
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanExternalCache() {
        try {
            if (isSDCardEnable())
                return deleteFilesInDir(DevUtils.getContext().getExternalCacheDir());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanExternalCache");
        }
        return false;
    }

    // =

    /**
     * 清除自定义路径下的文件, 使用需小心请不要误删, 而且只支持目录下的文件删除
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanCustomDir(final String filePath) {
        return deleteFilesInDir(getFileByPath(filePath));
    }

    /**
     * 清除自定义路径下的文件, 使用需小心请不要误删, 而且只支持目录下的文件删除
     * @param file 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanCustomDir(final File file) {
        return deleteFilesInDir(file);
    }

    /**
     * 清除本应用所有的数据
     * @param filePaths 文件路径数组
     */
    public static void cleanApplicationData(final String... filePaths) {
        cleanInternalCache();
        cleanExternalCache();
        cleanInternalDbs();
        cleanInternalSp();
        cleanInternalFiles();
        try {
            for (String path : filePaths) {
                cleanCustomDir(path);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanApplicationData");
        }
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // =============
    // = FileUtils =
    // =============

    /**
     * 删除目录下所有过滤的文件
     * @param dir 目录
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    private static boolean deleteFilesInDir(final File dir) {
        // dir is null then return false
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除目录
     * @param dir 目录
     * @return {@code true} 删除成功, {@code false} 删除失败
     */
    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取文件
     * @param filePath 文件路径
     * @return 文件 {@link File}
     */
    private static File getFileByPath(final String filePath) {
        return filePath != null ? new File(filePath) : null;
    }

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