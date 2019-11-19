package dev.utils.app;

import java.io.InputStream;

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
     * 获取内存应用数据库路径 - path /data/data/package/databases
     * @return /data/data/package/databases
     */
    public static String getInternalAppDbsPath() {
        return PathUtils.getInternalAppDbsPath();
    }

    /**
     * 获取内存应用数据库路径 - path /data/data/package/databases/name
     * @param name 数据库路径 + 名称
     * @return /data/data/package/databases/name
     */
    public static String getInternalAppDbPath(final String name) {
        return PathUtils.getInternalAppDbPath(name);
    }

    // ==============
    // = 导出数据库 =
    // ==============

    /**
     * 导出数据库
     * @param targetFile 目标文件
     * @param dbName     数据库名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startExportDatabase(final String targetFile, final String dbName) {
        return startExportDatabase(targetFile, dbName, true);
    }

    /**
     * 导出数据库
     * @param targetFile 目标文件
     * @param dbName     数据库名
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startExportDatabase(final String targetFile, final String dbName, final boolean overlay) {
        if (!SDCardUtils.isSDCardEnable()) return false;
        return FileUtils.copyFile(getInternalAppDbPath(dbName), targetFile, overlay);
    }

    // ==============
    // = 导入数据库 =
    // ==============

    /**
     * 导入数据库
     * @param srcFilePath  待复制的文件地址
     * @param destFilePath 目标文件地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(final String srcFilePath, final String destFilePath) {
        return startImportDatabase(srcFilePath, destFilePath, true);
    }

    /**
     * 导入数据库
     * @param srcFilePath  待复制的文件地址
     * @param destFilePath 目标文件地址
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(final String srcFilePath, final String destFilePath, final boolean overlay) {
        if (!SDCardUtils.isSDCardEnable()) return false;
        return FileUtils.copyFile(srcFilePath, destFilePath, overlay);
    }

    // =

    /**
     * 导入数据库
     * @param inputStream  文件流 ( 被复制 )
     * @param destFilePath 目标文件地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(final InputStream inputStream, final String destFilePath) {
        return startImportDatabase(inputStream, destFilePath, true);
    }

    /**
     * 导入数据库
     * @param inputStream  文件流 ( 被复制 )
     * @param destFilePath 目标文件地址
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(final InputStream inputStream, final String destFilePath, final boolean overlay) {
        if (!SDCardUtils.isSDCardEnable()) return false;
        return FileUtils.copyFile(inputStream, destFilePath, overlay);
    }
}