package dev.utils.app;

import java.io.File;

import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 本应用数据清除管理工具类
 * @author Ttt
 * <pre>
 *     主要功能有清除内 / 外缓存、清除数据库、清除 SharedPreferences、清除 files 和清除自定义目录
 * </pre>
 */
public final class CleanUtils {

    private CleanUtils() {
    }

    // 日志 TAG
    private static final String TAG = CleanUtils.class.getSimpleName();

    /**
     * 清除外部缓存 ( path /storage/emulated/0/android/data/package/cache )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanCache() {
        return FileUtils.deleteFilesInDir(PathUtils.getAppExternal().getAppCachePath());
    }

    /**
     * 清除内部缓存 ( path /data/data/package/cache )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanAppCache() {
        return FileUtils.deleteFilesInDir(PathUtils.getInternal().getAppCachePath());
    }

    /**
     * 清除内部文件 ( path /data/data/package/files )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanAppFiles() {
        return FileUtils.deleteFilesInDir(PathUtils.getInternal().getAppFilesPath());
    }

    /**
     * 清除内部 SP ( path /data/data/package/shared_prefs )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanAppSp() {
        return FileUtils.deleteFilesInDir(PathUtils.getInternal().getAppSpPath());
    }

    /**
     * 清除内部 SP ( path /data/data/package/shared_prefs )
     * @param spName SP 文件名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanAppSp(final String spName) {
        return FileUtils.deleteFilesInDir(PathUtils.getInternal().getAppSpPath(spName));
    }

    /**
     * 清除内部数据库 ( path /data/data/package/databases )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanAppDbs() {
        return FileUtils.deleteFilesInDir(PathUtils.getInternal().getAppDbsPath());
    }

    /**
     * 根据名称清除数据库 ( path /data/data/package/databases/dbName )
     * @param dbName 数据库名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanAppDbByName(final String dbName) {
        return AppUtils.deleteDatabase(dbName);
    }

    // =

    /**
     * 清除自定义路径下的文件
     * <pre>
     *     使用需小心请不要误删, 而且只支持目录下的文件删除
     * </pre>
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanCustomDir(final String filePath) {
        return FileUtils.deleteFilesInDir(filePath);
    }

    /**
     * 清除自定义路径下的文件
     * <pre>
     *     使用需小心请不要误删, 而且只支持目录下的文件删除
     * </pre>
     * @param file 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanCustomDir(final File file) {
        return FileUtils.deleteFilesInDir(file);
    }

    /**
     * 清除本应用所有的数据
     * @param filePaths 文件路径数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cleanApplicationData(final String... filePaths) {
        boolean result = true;
        try {
            cleanCache();
            cleanAppCache();
            cleanAppFiles();
            cleanAppSp();
            cleanAppDbs();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanApplicationData");
            result = false;
        }
        try {
            for (String path : filePaths) {
                cleanCustomDir(path);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cleanApplicationData");
            result = false;
        }
        return result;
    }
}