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

    /**
     * 获取应用内部存储数据库路径 ( path /data/data/package/databases )
     * @return /data/data/package/databases
     */
    public static String getAppDbsPath() {
        return PathUtils.getInternal().getAppDbsPath();
    }

    /**
     * 获取应用内部存储数据库路径 ( path /data/data/package/databases/name )
     * @param name 数据库名
     * @return /data/data/package/databases/name
     */
    public static String getAppDbPath(final String name) {
        return PathUtils.getInternal().getAppDbPath(name);
    }

    // =============
    // = 导出数据库 =
    // =============

    /**
     * 导出数据库
     * @param targetFile 目标文件
     * @param dbName     数据库名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startExportDatabase(
            final String targetFile,
            final String dbName
    ) {
        return startExportDatabase(targetFile, dbName, true);
    }

    /**
     * 导出数据库
     * @param targetFile 目标文件
     * @param dbName     数据库名
     * @param overlay    如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startExportDatabase(
            final String targetFile,
            final String dbName,
            final boolean overlay
    ) {
        if (!PathUtils.getSDCard().isSDCardEnable()) return false;
        return FileUtils.copyFile(getAppDbPath(dbName), targetFile, overlay);
    }

    // =============
    // = 导入数据库 =
    // =============

    /**
     * 导入数据库
     * @param srcFilePath  待复制的文件地址
     * @param destFilePath 目标文件地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(
            final String srcFilePath,
            final String destFilePath
    ) {
        return startImportDatabase(srcFilePath, destFilePath, true);
    }

    /**
     * 导入数据库
     * @param srcFilePath  待复制的文件地址
     * @param destFilePath 目标文件地址
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(
            final String srcFilePath,
            final String destFilePath,
            final boolean overlay
    ) {
        if (!PathUtils.getSDCard().isSDCardEnable()) return false;
        return FileUtils.copyFile(srcFilePath, destFilePath, overlay);
    }

    // =

    /**
     * 导入数据库
     * @param inputStream  文件流 ( 被复制 )
     * @param destFilePath 目标文件地址
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(
            final InputStream inputStream,
            final String destFilePath
    ) {
        return startImportDatabase(inputStream, destFilePath, true);
    }

    /**
     * 导入数据库
     * @param inputStream  文件流 ( 被复制 )
     * @param destFilePath 目标文件地址
     * @param overlay      如果目标文件存在, 是否覆盖
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startImportDatabase(
            final InputStream inputStream,
            final String destFilePath,
            final boolean overlay
    ) {
        if (!PathUtils.getSDCard().isSDCardEnable()) return false;
        return FileUtils.copyFile(inputStream, destFilePath, overlay);
    }
}